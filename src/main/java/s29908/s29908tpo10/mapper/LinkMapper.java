package s29908.s29908tpo10.mapper;

import org.springframework.stereotype.Component;
import s29908.s29908tpo10.model.DTO.LinkDTO;
import s29908.s29908tpo10.model.DTO.UpdateLinkDTO;
import s29908.s29908tpo10.model.Link;

@Component
public class LinkMapper {

    public LinkDTO toDTO(Link link) {
        LinkDTO dto = new LinkDTO();
        dto.setId(link.getId());
        dto.setName(link.getName());
        dto.setTargetUrl(link.getTargetURL());
        dto.setRedirectUrl("http://localhost:8080/red/" + link.getId());
        dto.setCount(link.getCount());
        dto.setHasPassword(link.getPassword() != null && !link.getPassword().isBlank());
        return dto;
    }

    public UpdateLinkDTO toUpdateDTO(Link link, String password) {
        UpdateLinkDTO dto = new UpdateLinkDTO();
        dto.setName(link.getName());
        dto.setTargetUrl(link.getTargetURL());
        dto.setPassword(password);
        return dto;
    }
}
