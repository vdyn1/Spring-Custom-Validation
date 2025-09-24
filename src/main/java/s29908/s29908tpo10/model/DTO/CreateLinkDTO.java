package s29908.s29908tpo10.model.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import s29908.s29908tpo10.constraint.HTTPSOnly;
import s29908.s29908tpo10.constraint.StrongPassword;
import s29908.s29908tpo10.constraint.UniqueTargetUrl;

public class CreateLinkDTO {

    @Column(unique = true, nullable = false)
    @Size(min = 5, max = 20, message = "{error.name.size}")
    private String name;

    @NotBlank
    @HTTPSOnly
    @UniqueTargetUrl
    private String url;

    @StrongPassword
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
