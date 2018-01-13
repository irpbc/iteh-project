package irpbc.iteh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * A CourseEnrollment.
 */
@Entity
@Table(name = "course_enrollment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "courseenrollment")
public class CourseEnrollment extends AbstractEntity {

    @NotNull
    @Column(name = "total_points", precision=10, scale=2, nullable = false)
    private BigDecimal totalPoints;

    @Min(value = 6)
    @Max(value = 10)
    @Column(name = "grade")
    private Integer grade;

    @NotNull
    @Column(name = "completed", nullable = false)
    private Boolean completed;

    @ManyToOne(optional = false)
    @NotNull
    private SchoolYearEnrollment yearEnrollment;

    @ManyToOne(optional = false)
    @NotNull
    private Course course;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public BigDecimal getTotalPoints() {
        return totalPoints;
    }

    public CourseEnrollment totalPoints(BigDecimal totalPoints) {
        this.totalPoints = totalPoints;
        return this;
    }

    public void setTotalPoints(BigDecimal totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Integer getGrade() {
        return grade;
    }

    public CourseEnrollment grade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Boolean isCompleted() {
        return completed;
    }

    public CourseEnrollment completed(Boolean completed) {
        this.completed = completed;
        return this;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public SchoolYearEnrollment getYearEnrollment() {
        return yearEnrollment;
    }

    public CourseEnrollment yearEnrollment(SchoolYearEnrollment schoolYearEnrollment) {
        this.yearEnrollment = schoolYearEnrollment;
        return this;
    }

    public void setYearEnrollment(SchoolYearEnrollment schoolYearEnrollment) {
        this.yearEnrollment = schoolYearEnrollment;
    }

    public Course getCourse() {
        return course;
    }

    public CourseEnrollment course(Course course) {
        this.course = course;
        return this;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "CourseEnrollment{" +
            "id=" + getId() +
            ", totalPoints=" + getTotalPoints() +
            ", grade=" + getGrade() +
            ", completed='" + isCompleted() + "'" +
            "}";
    }
}
