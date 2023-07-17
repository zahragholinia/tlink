package ir.tinyLink.service.contract;

import ir.tinyLink.model.dto.LinkSrv;
import org.springframework.data.domain.Page;

public interface LinkService {

    LinkSrv insert(String longLink);

    Page<LinkSrv> list(int page, int size);

    LinkSrv get(String tinyLink);

    Integer view(String tinyLink);

    void delete(String tinyLink);
}
