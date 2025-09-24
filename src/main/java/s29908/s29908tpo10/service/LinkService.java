package s29908.s29908tpo10.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import s29908.s29908tpo10.mapper.LinkMapper;
import s29908.s29908tpo10.model.DTO.CreateLinkDTO;
import s29908.s29908tpo10.model.DTO.LinkDTO;
import s29908.s29908tpo10.model.DTO.UpdateLinkDTO;
import s29908.s29908tpo10.model.Link;
import s29908.s29908tpo10.repository.ILinkRepository;

import java.util.*;

@Service
public class LinkService {

    private final ILinkRepository repository;
    private final LinkMapper mapper;
    private final Validator validator;

    public LinkService(ILinkRepository repository, LinkMapper mapper, Validator validator) {
        this.repository = repository;
        this.mapper = mapper;
        this.validator = validator;
    }

    public LinkDTO createLink(CreateLinkDTO dto) {
        Set<ConstraintViolation<CreateLinkDTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        Link link = new Link();
        link.setId(generateRandomId());
        link.setName(dto.getName());
        link.setTargetURL(dto.getUrl());
        link.setPassword(dto.getPassword());
        link.setCount(0);

        repository.save(link);
        return mapper.toDTO(link);
    }

    public Optional<LinkDTO> getByName(String name) {
        return repository.findByName(name).map(mapper::toDTO);
    }

    public boolean passwordMatches(String id, String rawPassword) {
        Optional<Link> linkOpt = repository.findById(id);
        return linkOpt.isPresent()
                && Objects.equals(linkOpt.get().getPassword(), rawPassword);
    }

    public UpdateLinkDTO toUpdateDTO(String name, String password) {
        Optional<Link> linkOpt = repository.findByName(name);
        return linkOpt.map(link -> mapper.toUpdateDTO(link, password)).orElse(null);
    }

    public int updateLink(UpdateLinkDTO dto) {
        Optional<Link> opt = repository.findByName(dto.getName());
        if (opt.isEmpty()) return 404;

        Link link = opt.get();
        String storedPassword = link.getPassword();
        String inputPassword = dto.getPassword();

        if (storedPassword != null && !storedPassword.equals(inputPassword)) {
            return 403;
        }

        link.setTargetURL(dto.getTargetUrl());
        repository.save(link);
        return 204;
    }

    public Optional<String> getIdByRedirectUrl(String redirectUrl) {
        return repository.findLinkIdByRedirectUrl(redirectUrl);
    }

    public Optional<LinkDTO> getById(String id) {
        return repository.findById(id).map(mapper::toDTO);
    }

    public void incrementCount(String id) {
        repository.incrementVisits(id);
    }


    public int deleteLink(String id, String password) {
        Optional<Link> opt = repository.findById(id);
        if (opt.isEmpty()) return 404;

        Link link = opt.get();
        if (link.getPassword() != null && !link.getPassword().equals(password)) {
            return 403;
        }

        repository.delete(link);
        return 204;
    }

    private String generateRandomId() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
