package irpbc.iteh.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SchoolYearEnrollment entity.
 */
public class SchoolYearEnrollmentDTO extends AbstractEntityDTO {

    @DecimalMin(value = "6")
    @DecimalMax(value = "10")
    private BigDecimal averageGrade;

    @NotNull
    private Integer espbPointsDeclared;

    @NotNull
    private Integer espbPointsAttained;

    private Long studentId;

    private Long yearId;

    public BigDecimal getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Integer getEspbPointsDeclared() {
        return espbPointsDeclared;
    }

    public void setEspbPointsDeclared(Integer espbPointsDeclared) {
        this.espbPointsDeclared = espbPointsDeclared;
    }

    public Integer getEspbPointsAttained() {
        return espbPointsAttained;
    }

    public void setEspbPointsAttained(Integer espbPointsAttained) {
        this.espbPointsAttained = espbPointsAttained;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getYearId() {
        return yearId;
    }

    public void setYearId(Long schoolYearId) {
        this.yearId = schoolYearId;
    }

    @Override
    public String toString() {
        return "SchoolYearEnrollmentDTO{" +
            "id=" + getId() +
            ", averageGrade=" + getAverageGrade() +
            ", espbPointsDeclared=" + getEspbPointsDeclared() +
            ", espbPointsAttained=" + getEspbPointsAttained() +
            "}";
    }
}
