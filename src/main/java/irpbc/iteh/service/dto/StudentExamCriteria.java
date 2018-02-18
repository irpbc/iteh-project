package irpbc.iteh.service.dto;

import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;

import java.io.Serializable;

/**
 * Criteria class for the StudentExam entity. This class is used in StudentExamResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /student-exams?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class StudentExamCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private BooleanFilter attended;
    private IntegerFilter grade;
    private LongFilter studentId;
    private LongFilter examId;
    private LongFilter evaluatedById;

    public StudentExamCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getAttended() {
        return attended;
    }

    public void setAttended(BooleanFilter attended) {
        this.attended = attended;
    }

    public IntegerFilter getGrade() {
        return grade;
    }

    public void setGrade(IntegerFilter grade) {
        this.grade = grade;
    }

    public LongFilter getStudentId() {
        return studentId;
    }

    public void setStudentId(LongFilter studentId) {
        this.studentId = studentId;
    }

    public LongFilter getExamId() {
        return examId;
    }

    public void setExamId(LongFilter examId) {
        this.examId = examId;
    }

    public LongFilter getEvaluatedById() {
        return evaluatedById;
    }

    public void setEvaluatedById(LongFilter evaluatedById) {
        this.evaluatedById = evaluatedById;
    }

    @Override
    public String toString() {
        return "StudentExamCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (attended != null ? "attended=" + attended + ", " : "") +
            (grade != null ? "grade=" + grade + ", " : "") +
            (studentId != null ? "studentId=" + studentId + ", " : "") +
            (examId != null ? "examId=" + examId + ", " : "") +
            (evaluatedById != null ? "evaluatedById=" + evaluatedById + ", " : "") +
            "}";
    }
}
