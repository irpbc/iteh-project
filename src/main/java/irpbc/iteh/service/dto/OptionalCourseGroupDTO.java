package irpbc.iteh.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the OptionalCourseGroup entity.
 */
public class OptionalCourseGroupDTO extends AbstractEntityDTO {

    @NotNull
    @Size(max = 100)
    private String name;

    private Long semesterId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    @Override
    public String toString() {
        return "OptionalCourseGroupDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
