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

    private String studentFullName;

    private Long examId;
    private String examName;

    private Long evaluatedById;

    private String evaluatedByFullName;

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

    public void setStudentId(Long userId) {
        this.studentId = userId;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String userFullName) {
        this.studentFullName = userFullName;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public Long getEvaluatedById() {
        return evaluatedById;
    }

    public void setEvaluatedById(Long userId) {
        this.evaluatedById = userId;
    }

    public String getEvaluatedByFullName() {
        return evaluatedByFullName;
    }

    public void setEvaluatedByFullName(String userFullName) {
        this.evaluatedByFullName = userFullName;
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
