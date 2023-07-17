package ir.tinyLink.service;


import ir.tinyLink.exception.TinyLinkGeneralException;
import ir.tinyLink.message.LinkMessage;
import ir.tinyLink.model.dto.LinkSrv;
import ir.tinyLink.model.dto.UrlProtocol;
import ir.tinyLink.model.entity.LinkEntity;
import ir.tinyLink.repository.LinkRepository;
import ir.tinyLink.service.contract.LinkService;
import ir.tinyLink.util.BaseEncoderUtil;
import ir.tinyLink.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class LinkServiceImp implements LinkService {

    private final UserServiceImp userServiceImp;

    private final LinkRepository linkRepository;

    @Value("${limit.insert.link}")
    private int LIMIT_INSERT_LINK;

    @Value("${prefix.url}")
    private String PREFIX_URL;


    public LinkSrv insert(String longLink) {
        checkCount();
        LinkEntity linkEntity = saveLink(removeQueryAndProtocol(longLink));

        return convertToObjectDto(linkEntity);
    }

    private void checkCount() {
        if (linkRepository.countLinkEntitiesByUserId(UserUtil.getCurrentUser().getId()) >= LIMIT_INSERT_LINK)
            throw new TinyLinkGeneralException(HttpStatus.TOO_MANY_REQUESTS, LinkMessage.errorLinkTooMany(LIMIT_INSERT_LINK));
    }

    public Page<LinkSrv> list(int page, int size) {
        Page<LinkEntity> linkEntity = findByUserIdOrderById(page, size);

        return linkEntity.map(this::convertToObjectDto);

    }


    public LinkSrv get(String shortLink) {
        UrlProtocol urlProtocol = new UrlProtocol(shortLink);
        LinkEntity linkEntity = findById(seperatePrefixTinyLinkAndGetId(urlProtocol));
        updateViewCount(linkEntity);
        linkEntity.setLongLink(addQuery(urlProtocol.getQuery(), linkEntity.getLongLink()));


        return convertToObjectDto(linkEntity);
    }


    public Integer view(String shortLink) {
        UrlProtocol urlProtocol = new UrlProtocol(shortLink);
        return linkRepository.findViewCountById(seperatePrefixTinyLinkAndGetId(urlProtocol));
    }

    public void updateViewCount(LinkEntity linkEntity) {
        linkEntity.setViewCount(linkEntity.getViewCount() + 1);
        linkRepository.save(linkEntity);

    }

    public void delete(String shortLink) {
        UrlProtocol urlProtocol = new UrlProtocol(shortLink);
        linkRepository.delete(findByIdAndUserId(seperatePrefixTinyLinkAndGetId(urlProtocol)));
    }

    private LinkEntity saveLink(String longLink) {
        var link = LinkEntity.builder().longLink(longLink)
                .user(userServiceImp.findLoginUser()).build();

        return linkRepository.save(link);
    }

    private Page<LinkEntity> findByUserIdOrderById(int page, int size) {
        return linkRepository.findByUserIdOrderById(
                UserUtil.getCurrentUser().getId(), PageRequest.of(page, size));
    }

    private LinkEntity findByIdAndUserId(long id) {
        Optional<LinkEntity> linkEntity = linkRepository.findByIdAndUserId(id, UserUtil.getCurrentUser().getId());
        if (!linkEntity.isPresent())
            throw new TinyLinkGeneralException(HttpStatus.NOT_FOUND, LinkMessage.errorLinkNotFound(generateTinyLink(id)));

        return linkEntity.get();
    }

    private LinkEntity findById(long id) {
        Optional<LinkEntity> linkEntity = linkRepository.findById(id);

        if (!linkEntity.isPresent())
            throw new TinyLinkGeneralException(HttpStatus.NOT_FOUND, LinkMessage.errorLinkNotFound(generateTinyLink(id)));

        return linkEntity.get();

    }

    private LinkSrv convertToObjectDto(LinkEntity entity) {
        return LinkSrv.builder().longLink("http://" + entity.getLongLink())
                .tinyLink(generateTinyLink(entity.getId()))
                .id(entity.getId())
                .view(entity.getViewCount())
                .build();
    }

    private String generateTinyLink(long id) {
        StringBuilder shortLink = new StringBuilder(PREFIX_URL).
                append(BaseEncoderUtil.encode(id));

        return shortLink.toString();
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "GMT+03:30")
    public void job() {
        linkRepository.deleteLinkEntitiesByUpdatedAtIsExpired();
    }

    private String removeQueryAndProtocol(String longLink) {
        UrlProtocol urlProtocol = new UrlProtocol(longLink);
        if (urlProtocol.getQuery() != null)
            longLink = urlProtocol.getUrl().substring(0, urlProtocol.getUrl().indexOf(urlProtocol.getQuery()) - 1);
        if (longLink.startsWith("http"))
            longLink = urlProtocol.getUrl().substring(urlProtocol.getUrl().indexOf(urlProtocol.getProtocol()) + urlProtocol.getProtocol().length() + 3, longLink.length());

        return longLink;
    }

    private String addQuery(String query, String longLink) {
        if (query != null) {
            StringBuilder builder = new StringBuilder(longLink)
                    .append("?").append(query);
            return builder.toString();
        }

        return longLink;
    }

    private long seperatePrefixTinyLinkAndGetId(UrlProtocol urlProtocol) {
        return BaseEncoderUtil.decode(urlProtocol.getPath().replace("/", ""));
    }

}
