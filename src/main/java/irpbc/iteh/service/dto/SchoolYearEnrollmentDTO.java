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
public class SchoolYearEnrollmentDTO implements Serializable {

    private Long id;

    @DecimalMin(value = "6")
    @DecimalMax(value = "10")
    private BigDecimal averageGrade;

    @NotNull
    private Integer espbPointsDeclared;

    @NotNull
    private Integer espbPointsAttained;

    private Long studentId;

    private String studentFullName;

    private Long yearId;

    private String yearName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public void setStudentId(Long userId) {
        this.studentId = userId;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String userFullName) {
        this.studentFullName = userFullName;
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

    public void setYearName(String schoolYearName) {
        this.yearName = schoolYearName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SchoolYearEnrollmentDTO schoolYearEnrollmentDTO = (SchoolYearEnrollmentDTO) o;
        if(schoolYearEnrollmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), schoolYearEnrollmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
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
