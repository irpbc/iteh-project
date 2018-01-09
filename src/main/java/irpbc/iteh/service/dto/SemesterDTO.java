package irpbc.iteh.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Semester entity.
 */
public class SemesterDTO extends AbstractEntityDTO {

    @NotNull
    @Size(max = 100)
    private String name;

    private Long yearId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long schoolYearId) {
        this.yearId = schoolYearId;
    }

    @Override
    public String toString() {
        return "SemesterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
