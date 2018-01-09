package irpbc.iteh.service.dto;

import java.math.BigDecimal;

/**
 * A DTO for the StudentCommitment entity.
 */
public class StudentCommitmentDTO extends AbstractEntityDTO {

    private BigDecimal points;

    private Long enrollmentId;

    private Long commitmentId;

    private Long evaluatedById;

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

    public Long getEvaluatedById() {
        return evaluatedById;
    }

    public void setEvaluatedById(Long lecturerId) {
        this.evaluatedById = lecturerId;
    }

    @Override
    public String toString() {
        return "StudentCommitmentDTO{" +
            "id=" + getId() +
            ", points=" + getPoints() +
            "}";
    }
}
