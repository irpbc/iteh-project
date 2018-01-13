package irpbc.iteh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A OptionalCourseGroup.
 */
@Entity
@Table(name = "optional_course_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "optionalcoursegroup")
public class OptionalCourseGroup extends AbstractEntity {

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    private Semester semester;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public String getName() {
        return name;
    }

    public OptionalCourseGroup name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Semester getSemester() {
        return semester;
    }

    public OptionalCourseGroup semester(Semester semester) {
        this.semester = semester;
        return this;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "OptionalCourseGroup{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
