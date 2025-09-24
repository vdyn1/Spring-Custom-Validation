package s29908.s29908tpo10.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import s29908.s29908tpo10.model.DTO.LinkDTO;
import s29908.s29908tpo10.service.LinkService;

import java.util.Optional;

@Controller
public class RedController {

    private final LinkService linkService;

    public RedController(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping("/red/{id}")
    public String redirectToTargetLink(@PathVariable String id) {
        Optional<LinkDTO> link = linkService.getById(id);

        if (link.isPresent()) {
            linkService.incrementCount(id);
            System.out.println(link.get().getRedirectUrl());
            return "redirect:" + link.get().getRedirectUrl();
        }
        return "redirect:/links?error=notfound";
    }
}
