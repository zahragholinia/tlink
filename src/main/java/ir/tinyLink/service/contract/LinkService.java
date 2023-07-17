package ir.tinyLink.service.contract;

import ir.tinyLink.model.entity.LinkEntity;
import ir.tinyLink.model.srv.LinkSrv;
import ir.tinyLink.model.vo.LinkVo;
import org.springframework.data.domain.Page;

import java.net.MalformedURLException;
import java.util.List;

public interface LinkService {

     LinkSrv insert(String longLink) throws MalformedURLException;

     Page<LinkSrv> list(int page,int size);

     LinkSrv get(String shortLink) throws MalformedURLException;

     Integer view(String shortLink) throws MalformedURLException;

     void delete(long id);
}
