package irpbc.iteh.service.dto;

/**
 * A DTO for the Lecturer entity.
 */
public class LecturerDTO extends UserDTO {

    @Override
    public String toString() {
        return "LecturerDTO{" +
            "id=" + getId() +
            ", login='" + getLogin() + "'" +
            ", email='" + getEmail() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
