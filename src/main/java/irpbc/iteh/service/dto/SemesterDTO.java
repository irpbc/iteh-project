package irpbc.iteh.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO for the Semester entity.
 */
public class SemesterDTO extends AbstractEntityDTO {

    @NotNull
    @Size(max = 100)
    private String name;

    private Long yearId;
    private String yearName;

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

    public String getYearName() {
        return yearName;
    }

    public void setYearName(String yearName) {
        this.yearName = yearName;
    }

    @Override
    public String toString() {
        return "SemesterDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
