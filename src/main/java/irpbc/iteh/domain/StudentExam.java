package irpbc.iteh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * A StudentExam.
 */
@Entity
@Table(name = "student_exam")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "studentexam")
public class StudentExam extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attended")
    private Boolean attended;

    @Min(value = 6)
    @Max(value = 10)
    @Column(name = "grade")
    private Integer grade;

    @ManyToOne(optional = false)
    @NotNull
    private Student student;

    @ManyToOne(optional = false)
    @NotNull
    private Exam exam;

    @ManyToOne
    private Lecturer evaluatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Boolean isAttended() {
        return attended;
    }

    public StudentExam attended(Boolean attended) {
        this.attended = attended;
        return this;
    }

    public void setAttended(Boolean attended) {
        this.attended = attended;
    }

    public Integer getGrade() {
        return grade;
    }

    public StudentExam grade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Student getStudent() {
        return student;
    }

    public StudentExam student(Student student) {
        this.student = student;
        return this;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Exam getExam() {
        return exam;
    }

    public StudentExam exam(Exam exam) {
        this.exam = exam;
        return this;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Lecturer getEvaluatedBy() {
        return evaluatedBy;
    }

    public StudentExam evaluatedBy(Lecturer lecturer) {
        this.evaluatedBy = lecturer;
        return this;
    }

    public void setEvaluatedBy(Lecturer lecturer) {
        this.evaluatedBy = lecturer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "StudentExam{" +
            "id=" + getId() +
            ", attended='" + isAttended() + "'" +
            ", grade=" + getGrade() +
            "}";
    }
}
