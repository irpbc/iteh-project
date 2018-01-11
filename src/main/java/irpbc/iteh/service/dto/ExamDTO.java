package irpbc.iteh.service.dto;


import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Exam entity.
 */
public class ExamDTO extends AbstractEntityDTO {

    @NotNull
    private Instant time;

    private Long periodId;

    private String periodName;

    private Long courseId;

    private String courseName;

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long examPeriodId) {
        this.periodId = examPeriodId;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String examPeriodName) {
        this.periodName = examPeriodName;
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
        return "ExamDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            "}";
    }
}
