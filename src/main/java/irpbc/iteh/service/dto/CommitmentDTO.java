package irpbc.iteh.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Commitment entity.
 */
public class CommitmentDTO extends AbstractEntityDTO {

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private BigDecimal maxPoints;

    private Long courseId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(BigDecimal maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "CommitmentDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", maxPoints=" + getMaxPoints() +
            "}";
    }
}
