package irpbc.iteh.service.dto;

import irpbc.iteh.config.Constants;

import irpbc.iteh.domain.enumeration.UserType;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.*;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO extends AbstractEntityDTO {

    private UserType userType;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 100)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    private String fullName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 6)
    private String langKey;

    private String code;

    private Set<String> authorities;

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public UserDTO id(Long id) {
        return (UserDTO) super.id(id);
    }

    public UserDTO login(String login) {
        this.login = login;
        return this;
    }

    public UserDTO firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public UserDTO lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public UserDTO email(String email) {
        this.email = email;
        return this;
    }

    public UserDTO imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public UserDTO activated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public UserDTO langKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public UserDTO authorities(Set<String> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", authorities=" + authorities +
            "}";
    }
}
