package irpbc.iteh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * A Course.
 */
@Entity
@Table(name = "course")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "course")
public class Course extends AbstractEntity {

    @NotNull
    @Size(max = 255)
    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @NotNull
    @Column(name = "espb_points", nullable = false)
    private Integer espbPoints;

    @NotNull
    @Column(name = "year_of_studies", nullable = false)
    private Integer yearOfStudies;

    @NotNull
    @Column(name = "optional", nullable = false)
    private Boolean optional;

    @ManyToOne(optional = false)
    @NotNull
    private Semester semester;

    @ManyToOne
    private OptionalCourseGroup optionalGroup;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "course_lecturers",
               joinColumns = @JoinColumn(name="courses_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="lecturers_id", referencedColumnName="id"))
    private Set<Lecturer> lecturers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public String getName() {
        return name;
    }

    public Course name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEspbPoints() {
        return espbPoints;
    }

    public Course espbPoints(Integer espbPoints) {
        this.espbPoints = espbPoints;
        return this;
    }

    public void setEspbPoints(Integer espbPoints) {
        this.espbPoints = espbPoints;
    }

    public Integer getYearOfStudies() {
        return yearOfStudies;
    }

    public Course yearOfStudies(Integer yearOfStudies) {
        this.yearOfStudies = yearOfStudies;
        return this;
    }

    public void setYearOfStudies(Integer yearOfStudies) {
        this.yearOfStudies = yearOfStudies;
    }

    public Boolean isOptional() {
        return optional;
    }

    public Course optional(Boolean optional) {
        this.optional = optional;
        return this;
    }

    public void setOptional(Boolean optional) {
        this.optional = optional;
    }

    public Semester getSemester() {
        return semester;
    }

    public Course semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public OptionalCourseGroup getOptionalGroup() {
        return optionalGroup;
    }

    public Course optionalGroup(OptionalCourseGroup optionalCourseGroup) {
        this.optionalGroup = optionalCourseGroup;
        return this;
    }

    public void setOptionalGroup(OptionalCourseGroup optionalCourseGroup) {
        this.optionalGroup = optionalCourseGroup;
    }

    public Set<Lecturer> getLecturers() {
        return lecturers;
    }

    public Course lecturers(Set<Lecturer> lecturers) {
        this.lecturers = lecturers;
        return this;
    }

    public Course addLecturers(Lecturer lecturer) {
        this.lecturers.add(lecturer);
        lecturer.getCourses().add(this);
        return this;
    }

    public Course removeLecturers(Lecturer lecturer) {
        this.lecturers.remove(lecturer);
        lecturer.getCourses().remove(this);
        return this;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", espbPoints=" + getEspbPoints() +
            ", yearOfStudies=" + getYearOfStudies() +
            ", optional='" + isOptional() + "'" +
            "}";
    }
}
