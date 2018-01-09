package irpbc.iteh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * A Exam.
 */
@Entity
@Table(name = "exam")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "exam")
public class Exam extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_time", nullable = false)
    private Instant time;

    @ManyToOne(optional = false)
    @NotNull
    private ExamPeriod period;

    @ManyToOne(optional = false)
    @NotNull
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Instant getTime() {
        return time;
    }

    public Exam time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public ExamPeriod getPeriod() {
        return period;
    }

    public Exam period(ExamPeriod examPeriod) {
        this.period = examPeriod;
        return this;
    }

    public void setPeriod(ExamPeriod examPeriod) {
        this.period = examPeriod;
    }

    public Course getCourse() {
        return course;
    }

    public Exam course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "Exam{" +
            "id=" + getId() +
            ", time='" + getTime() + "'" +
            "}";
    }
}
