package irpbc.iteh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A Semester.
 */
@Entity
@Table(name = "semester")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "semester")
public class Semester extends AbstractEntity {

    @NotNull
    @Size(max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @NotNull
    private SchoolYear year;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public String getName() {
        return name;
    }

    public Semester name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SchoolYear getYear() {
        return year;
    }

    public Semester year(SchoolYear schoolYear) {
        this.year = schoolYear;
        return this;
    }

    public void setYear(SchoolYear schoolYear) {
        this.year = schoolYear;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "Semester{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
