package irpbc.iteh.service.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * A DTO for the StudentExam entity.
 */
public class StudentExamDTO extends AbstractEntityDTO {

    private Boolean attended;

    @Min(value = 6)
    @Max(value = 10)
    private Integer grade;

    private Long studentId;

    private Long examId;

    private Long evaluatedById;

    public Boolean isAttended() {
        return attended;
    }

    public void setAttended(Boolean attended) {
        this.attended = attended;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Long getEvaluatedById() {
        return evaluatedById;
    }

    public void setEvaluatedById(Long lecturerId) {
        this.evaluatedById = lecturerId;
    }

    @Override
    public String toString() {
        return "StudentExamDTO{" +
            "id=" + getId() +
            ", attended='" + isAttended() + "'" +
            ", grade=" + getGrade() +
            "}";
    }
}
