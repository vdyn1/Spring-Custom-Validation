package s29908.s29908tpo10.model.DTO;

import jakarta.validation.constraints.Size;
import s29908.s29908tpo10.constraint.HTTPSOnly;
import s29908.s29908tpo10.constraint.StrongPassword;

public class UpdateLinkDTO {

    @Size(min = 5, max = 20, message = "{error.name.size}")
    private String name;

    @HTTPSOnly
    private String targetUrl;

    @StrongPassword
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
