package irpbc.iteh.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the CourseEnrollment entity.
 */
public class CourseEnrollmentDTO extends AbstractEntityDTO {

    @NotNull
    private BigDecimal totalPoints;

    @Min(value = 6)
    @Max(value = 10)
    private Integer grade;

    @NotNull
    private Boolean completed;

    private Long yearEnrollmentId;

    private Long courseId;

    private String courseName;

    public BigDecimal getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(BigDecimal totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Long getYearEnrollmentId() {
        return yearEnrollmentId;
    }

    public void setYearEnrollmentId(Long schoolYearEnrollmentId) {
        this.yearEnrollmentId = schoolYearEnrollmentId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "CourseEnrollmentDTO{" +
            "id=" + getId() +
            ", totalPoints=" + getTotalPoints() +
            ", grade=" + getGrade() +
            ", completed='" + isCompleted() + "'" +
            "}";
    }
}
