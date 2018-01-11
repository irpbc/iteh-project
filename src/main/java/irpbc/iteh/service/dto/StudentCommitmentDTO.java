package irpbc.iteh.service.dto;

import java.math.BigDecimal;

/**
 * A DTO for the StudentCommitment entity.
 */
public class StudentCommitmentDTO extends AbstractEntityDTO {

    private BigDecimal points;

    private Long enrollmentId;

    private Long commitmentId;

    private String commitmentName;

    private Long evaluatedById;

    private String evaluatedByFullName;

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public Long getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Long courseEnrollmentId) {
        this.enrollmentId = courseEnrollmentId;
    }

    public Long getCommitmentId() {
        return commitmentId;
    }

    public void setCommitmentId(Long commitmentId) {
        this.commitmentId = commitmentId;
    }

    public String getCommitmentName() {
        return commitmentName;
    }

    public void setCommitmentName(String commitmentName) {
        this.commitmentName = commitmentName;
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
        return "StudentCommitmentDTO{" +
            "id=" + getId() +
            ", points=" + getPoints() +
            "}";
    }
}
