package irpbc.iteh.service.dto;

import io.github.jhipster.service.filter.*;

import java.io.Serializable;

/**
 * Criteria class for the Course entity. This class is used in CourseResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /courses?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CourseCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private LongFilter id;
    private StringFilter name;
    private IntegerFilter espbPoints;
    private IntegerFilter yearOfStudies;
    private BooleanFilter optional;
    private LongFilter semesterId;
    private LongFilter optionalGroupId;
    private LongFilter lecturersId;

    public CourseCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getEspbPoints() {
        return espbPoints;
    }

    public void setEspbPoints(IntegerFilter espbPoints) {
        this.espbPoints = espbPoints;
    }

    public IntegerFilter getYearOfStudies() {
        return yearOfStudies;
    }

    public void setYearOfStudies(IntegerFilter yearOfStudies) {
        this.yearOfStudies = yearOfStudies;
    }

    public BooleanFilter getOptional() {
        return optional;
    }

    public void setOptional(BooleanFilter optional) {
        this.optional = optional;
    }

    public LongFilter getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(LongFilter semesterId) {
        this.semesterId = semesterId;
    }

    public LongFilter getOptionalGroupId() {
        return optionalGroupId;
    }

    public void setOptionalGroupId(LongFilter optionalGroupId) {
        this.optionalGroupId = optionalGroupId;
    }

    public LongFilter getLecturersId() {
        return lecturersId;
    }

    public void setLecturersId(LongFilter lecturersId) {
        this.lecturersId = lecturersId;
    }

    @Override
    public String toString() {
        return "CourseCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (espbPoints != null ? "espbPoints=" + espbPoints + ", " : "") +
            (yearOfStudies != null ? "yearOfStudies=" + yearOfStudies + ", " : "") +
            (optional != null ? "optional=" + optional + ", " : "") +
            (semesterId != null ? "semesterId=" + semesterId + ", " : "") +
            (optionalGroupId != null ? "optionalGroupId=" + optionalGroupId + ", " : "") +
            (lecturersId != null ? "lecturersId=" + lecturersId + ", " : "") +
            "}";
    }
}
