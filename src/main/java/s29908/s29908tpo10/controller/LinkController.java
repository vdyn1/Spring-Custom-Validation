package s29908.s29908tpo10.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import s29908.s29908tpo10.model.DTO.CreateLinkDTO;
import s29908.s29908tpo10.model.DTO.LinkDTO;
import s29908.s29908tpo10.model.DTO.UpdateLinkDTO;
import s29908.s29908tpo10.service.LinkService;
import java.util.Locale;
import java.util.Optional;

@Controller
@RequestMapping("/links")
public class LinkController {

    private final LinkService service;
    private final MessageSource messageSource;

    public LinkController(LinkService service, MessageSource messageSource) {
        this.service = service;
        this.messageSource = messageSource;

    }

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("link", new CreateLinkDTO());
        model.addAttribute("queryName", "");
        model.addAttribute("queryPassword", "");
        model.addAttribute("linkUpdate", new UpdateLinkDTO());
        return "create";
    }


    @PostMapping
    public String createLink(@Valid @ModelAttribute("link") CreateLinkDTO dto,
                             BindingResult result,
                             Model model,
                             Locale locale) {
        if (result.hasErrors()) {
            model.addAttribute("queryName", "");
            model.addAttribute("queryPassword", "");
            return "create";
        }

        try {
            service.createLink(dto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", messageSource.getMessage(
                    "error.invalid.input", null, locale));
            return "error";
        }
        model.addAttribute("link", new CreateLinkDTO());
        model.addAttribute("queryName", "");
        model.addAttribute("queryPassword", "");
        return "create";
    }

    @GetMapping("/find")
    public String showFindPage(Model model) {

        model.addAttribute("queryName", "");
        model.addAttribute("queryPassword", "");
        model.addAttribute("linkUpdate", new UpdateLinkDTO());
        model.addAttribute("link", new CreateLinkDTO());

        return "find";
    }


    @PostMapping("/find")
    public String findLink(@RequestParam String queryName,
                           @RequestParam(required = false) String queryPassword,
                           Model model,
                           Locale locale) {
        model.addAttribute("link", new CreateLinkDTO());
        model.addAttribute("queryName", queryName);
        model.addAttribute("queryPassword",
                queryPassword == null ? "" : queryPassword);

        try {
            Optional<LinkDTO> dtoOpt = service.getByName(queryName);

            if (dtoOpt.isEmpty()) {

                model.addAttribute("errorMessage", messageSource.getMessage(
                        "error.invalid.name", null, locale));
                return "error";
            }

            LinkDTO dto = dtoOpt.get();

            boolean badPassword = dto.isHasPassword() &&
                    (queryPassword == null ||
                            !service.passwordMatches(dto.getId(),
                                    queryPassword));
            if (badPassword) {
                model.addAttribute("errorMessage", messageSource.getMessage(
                        "error.invalid.password", null, locale));
                return "error";
            }

            model.addAttribute("linkInfo", dto);
            model.addAttribute("linkUpdate",
                    service.toUpdateDTO(queryName, queryPassword));
            return "find";

        } catch (Exception ex) {
            return "error" + ex.getMessage();
        }
    }


    @PostMapping("/update")
    public String updateLink(@Valid @ModelAttribute("linkUpdate") UpdateLinkDTO dto,
                             BindingResult result,
                             Model model,
                             Locale locale) {

        model.addAttribute("link", new CreateLinkDTO());
        model.addAttribute("queryName", dto.getName());
        model.addAttribute("queryPassword", dto.getPassword());

        if (result.hasErrors()) {
            return "find";
        }

        Optional<LinkDTO> foundByName = service.getByName(dto.getName());
        Optional<String> currentId = service.getIdByRedirectUrl(dto.getTargetUrl());

        if (foundByName.isPresent() && currentId.isPresent()) {
            LinkDTO existing = foundByName.get();

            if (!existing.getId().equals(currentId.get())) {
                result.rejectValue("name", "error.invalid.input.name", "This name already exists.");
                return "find";
            }
        }

        int res = service.updateLink(dto);
        if (res == 403) {
            model.addAttribute("errorMessage", messageSource.getMessage("link.error.invalid", null, locale));
            return "create";
        }

        return "create";
    }


    @PostMapping("/delete")
    public String deleteLink(@RequestParam String id,
                             @RequestParam String password,
                             Model model,
                             Locale locale) {
        int result = service.deleteLink(id, password);

        model.addAttribute("link", new CreateLinkDTO());
        model.addAttribute("queryName", "");
        model.addAttribute("queryPassword", "");

        if (result == 404) {
            model.addAttribute("errorMessage", messageSource.getMessage("error.invalid.name", null, locale));
            return "create";
        }

        if (result == 403) {
            model.addAttribute("errorMessage", messageSource.getMessage("error.invalid.password", null, locale));
            return "create";
        }

        model.addAttribute("success", "Link deleted successfully!");
        return "create";
    }


    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("status", status);
        model.addAttribute("uri", request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
        return "error";
    }

}


