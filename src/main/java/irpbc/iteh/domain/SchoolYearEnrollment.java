package irpbc.iteh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * A SchoolYearEnrollment.
 */
@Entity
@Table(name = "school_year_enrollment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "schoolyearenrollment")
public class SchoolYearEnrollment extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DecimalMin(value = "6")
    @DecimalMax(value = "10")
    @Column(name = "average_grade", precision=10, scale=2)
    private BigDecimal averageGrade;

    @NotNull
    @Column(name = "espb_points_declared", nullable = false)
    private Integer espbPointsDeclared;

    @NotNull
    @Column(name = "espb_points_attained", nullable = false)
    private Integer espbPointsAttained;

    @ManyToOne(optional = false)
    @NotNull
    private Student student;

    @ManyToOne(optional = false)
    @NotNull
    private SchoolYear year;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public BigDecimal getAverageGrade() {
        return averageGrade;
    }

    public SchoolYearEnrollment averageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
        return this;
    }

    public void setAverageGrade(BigDecimal averageGrade) {
        this.averageGrade = averageGrade;
    }

    public Integer getEspbPointsDeclared() {
        return espbPointsDeclared;
    }

    public SchoolYearEnrollment espbPointsDeclared(Integer espbPointsDeclared) {
        this.espbPointsDeclared = espbPointsDeclared;
        return this;
    }

    public void setEspbPointsDeclared(Integer espbPointsDeclared) {
        this.espbPointsDeclared = espbPointsDeclared;
    }

    public Integer getEspbPointsAttained() {
        return espbPointsAttained;
    }

    public SchoolYearEnrollment espbPointsAttained(Integer espbPointsAttained) {
        this.espbPointsAttained = espbPointsAttained;
        return this;
    }

    public void setEspbPointsAttained(Integer espbPointsAttained) {
        this.espbPointsAttained = espbPointsAttained;
    }

    public Student getStudent() {
        return student;
    }

    public SchoolYearEnrollment student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public SchoolYear getYear() {
        return year;
    }

    public SchoolYearEnrollment year(SchoolYear schoolYear) {
        this.year = schoolYear;
        return this;
    }

    public void setYear(SchoolYear schoolYear) {
        this.year = schoolYear;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "SchoolYearEnrollment{" +
            "id=" + getId() +
            ", averageGrade=" + getAverageGrade() +
            ", espbPointsDeclared=" + getEspbPointsDeclared() +
            ", espbPointsAttained=" + getEspbPointsAttained() +
            "}";
    }
}
