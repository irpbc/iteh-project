package irpbc.iteh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * A StudentCommitment.
 */
@Entity
@Table(name = "student_commitment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "studentcommitment")
public class StudentCommitment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "points", precision=10, scale=2)
    private BigDecimal points;

    @ManyToOne(optional = false)
    @NotNull
    private CourseEnrollment enrollment;

    @ManyToOne(optional = false)
    @NotNull
    private Commitment commitment;

    @ManyToOne
    private Lecturer evaluatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public BigDecimal getPoints() {
        return points;
    }

    public StudentCommitment points(BigDecimal points) {
        this.points = points;
        return this;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public CourseEnrollment getEnrollment() {
        return enrollment;
    }

    public StudentCommitment enrollment(CourseEnrollment courseEnrollment) {
        this.enrollment = courseEnrollment;
        return this;
    }

    public void setEnrollment(CourseEnrollment courseEnrollment) {
        this.enrollment = courseEnrollment;
    }

    public Commitment getCommitment() {
        return commitment;
    }

    public StudentCommitment commitment(Commitment commitment) {
        this.commitment = commitment;
        return this;
    }

    public void setCommitment(Commitment commitment) {
        this.commitment = commitment;
    }

    public Lecturer getEvaluatedBy() {
        return evaluatedBy;
    }

    public StudentCommitment evaluatedBy(Lecturer lecturer) {
        this.evaluatedBy = lecturer;
        return this;
    }

    public void setEvaluatedBy(Lecturer lecturer) {
        this.evaluatedBy = lecturer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "StudentCommitment{" +
            "id=" + getId() +
            ", points=" + getPoints() +
            "}";
    }
}
