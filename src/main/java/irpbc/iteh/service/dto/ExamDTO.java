package irpbc.iteh.service.dto;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    private Long courseId;

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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "ExamDTO{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            "}";
    }
}
