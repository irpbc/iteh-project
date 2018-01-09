package irpbc.iteh.service.dto;

/**
 * A DTO for the Student entity.
 */
public class StudentDTO extends UserDTO {

    @Override
    public String toString() {
        return "StudentDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            "}";
    }
}
