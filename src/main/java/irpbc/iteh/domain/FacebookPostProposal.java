package irpbc.iteh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import irpbc.iteh.domain.enumeration.FacebookPostKind;

/**
 * A FacebookPostProposal.
 */
@Entity
@Table(name = "facebook_post_proposal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FacebookPostProposal extends AbstractEntity {

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "kind", nullable = false)
    private FacebookPostKind kind;

    @NotNull
    @Column(name = "data", nullable = false)
    private String data;

    @NotNull
    @Column(name = "time", nullable = false)
    private Instant time;

    @ManyToOne(optional = false)
    @NotNull
    private User student;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public FacebookPostKind getKind() {
        return kind;
    }

    public FacebookPostProposal kind(FacebookPostKind kind) {
        this.kind = kind;
        return this;
    }

    public void setKind(FacebookPostKind kind) {
        this.kind = kind;
    }

    public String getData() {
        return data;
    }

    public FacebookPostProposal data(String data) {
        this.data = data;
        return this;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Instant getTime() {
        return time;
    }

    public FacebookPostProposal time(Instant time) {
        this.time = time;
        return this;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public User getStudent() {
        return student;
    }

    public FacebookPostProposal student(User user) {
        this.student = user;
        return this;
    }

    public void setStudent(User user) {
        this.student = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public String toString() {
        return "FacebookPostProposal{" +
            "id=" + getId() +
            ", kind='" + getKind() + "'" +
            ", data='" + getData() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
