package ir.tinyLink.service;



import ir.tinyLink.message.LinkMessage;
import ir.tinyLink.model.entity.LinkEntity;
import ir.tinyLink.model.srv.LinkSrv;
import ir.tinyLink.model.vo.UrlProtocol;
import ir.tinyLink.repository.LinkRepository;
import ir.tinyLink.service.contract.LinkService;
import ir.tinyLink.util.BaseEncoderUtil;
import ir.tinyLink.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.net.MalformedURLException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class LinkServiceImp implements LinkService {

    private final UserServiceImp userServiceImp;

    private final LinkRepository linkRepository;

    public LinkSrv insert(String longLink) throws MalformedURLException {
//        checkCount();
        UrlProtocol urlProtocol = new UrlProtocol(longLink);
        String longLinkWithoutQuery = urlProtocol.getUrl().substring(0, urlProtocol.getUrl().indexOf(urlProtocol.getQuery()) - 1);
        var link = LinkEntity.builder().longLink(longLinkWithoutQuery)
                .user(userServiceImp.findLoginUser()).build();

        LinkEntity linkEntity = linkRepository.save(link);

        return LinkSrv.builder().tinyLink(generateShortLink(linkEntity.getId()))
                .id(linkEntity.getId()).longLink(longLink).build();
    }

//    public void checkCount() {
//        if (linkRepository.countLinkEntitiesByUserId(UserUtil.getCurrentUser().getId()) >= 10)
//            throw new InvalidRequestException("زیاد فرستادی");
//    }

    public Page<LinkSrv> list(int page, int size) {
        Page<LinkEntity> linkEntity = linkRepository.findByUserId(
                UserUtil.getCurrentUser().getId(), PageRequest.of(page, size));

        return linkEntity.map(this::convertToObjectDto);

    }

    private LinkSrv convertToObjectDto(LinkEntity entity) {
        LinkSrv dto = LinkSrv.builder().longLink(entity.getLongLink())
                .tinyLink(generateShortLink(entity.getId()))
                .id(entity.getId())
                .view(entity.getViewCount())
                .build();
        // Conversion logic

        return dto;
    }

    private String generateShortLink(long id) {
        StringBuilder shortLink = new StringBuilder("https://tiny.ir/").
                append(BaseEncoderUtil.encode(id));

        return shortLink.toString();
    }

    public LinkSrv get(String shortLink) throws MalformedURLException {
        UrlProtocol urlProtocol = new UrlProtocol(shortLink);
        long id = BaseEncoderUtil.decode(urlProtocol.getPath().replace("/", ""));
        Optional<LinkEntity> linkEntity = linkRepository.findById(id);

//        if (!linkEntity.isPresent())
//            throw new ResourceNotFoundException(LinkMessage.errorLinkNotFound());

        update(linkEntity.get());
        LinkEntity result = linkEntity.get();

        if (urlProtocol.getQuery() != null) {
            StringBuilder builder = new StringBuilder(result.getLongLink())
                    .append("?").append(urlProtocol.getQuery());
            result.setLongLink(builder.toString());
        }

        return LinkSrv.builder().longLink(result.getLongLink())
                .tinyLink(generateShortLink(result.getId()))
                .id(result.getId()).view(result.getViewCount()).build();
    }


    public Integer view(String shortLink) throws MalformedURLException {
        return get(shortLink).getView();
    }

    public void update(LinkEntity linkEntity) {
        linkEntity.setViewCount(linkEntity.getViewCount() + 1);
        linkRepository.save(linkEntity);

    }

    public void delete(long id) {
        Optional<LinkEntity> linkEntity = linkRepository.findById(id);

//        if (!linkEntity.isPresent())
//            throw new ResourceNotFoundException(LinkMessage.errorLinkNotFound());
        linkRepository.delete(linkEntity.get());
    }

    @Scheduled(cron = "0 0 0 * * ?", zone = "GMT+03:30")
    public void job() {
        System.out.println("hiiii");
        linkRepository.deleteLinkEntitiesByUpdatedAtIsExpired();
    }


}
