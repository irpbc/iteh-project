package irpbc.iteh.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Course entity.
 */
public class CourseDTO extends AbstractEntityDTO {

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private Integer espbPoints;

    @NotNull
    private Integer yearOfStudies;

    @NotNull
    private Boolean optional;

    private Long semesterId;

    private Long optionalGroupId;

    private Set<LecturerDTO> lecturers = new HashSet<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEspbPoints() {
        return espbPoints;
    }

    public void setEspbPoints(Integer espbPoints) {
        this.espbPoints = espbPoints;
    }

    public Integer getYearOfStudies() {
        return yearOfStudies;
    }

    public void setYearOfStudies(Integer yearOfStudies) {
        this.yearOfStudies = yearOfStudies;
    }

    public Boolean isOptional() {
        return optional;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getOptionalGroupId() {
        return optionalGroupId;
    }

    public void setOptionalGroupId(Long optionalCourseGroupId) {
        this.optionalGroupId = optionalCourseGroupId;
    }

    public Set<LecturerDTO> getLecturers() {
        return lecturers;
    }

    public void setLecturers(Set<LecturerDTO> lecturers) {
        this.lecturers = lecturers;
    }

    @Override
    public String toString() {
        return "CourseDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", espbPoints=" + getEspbPoints() +
            ", yearOfStudies=" + getYearOfStudies() +
            ", optional='" + isOptional() + "'" +
            "}";
    }
}
