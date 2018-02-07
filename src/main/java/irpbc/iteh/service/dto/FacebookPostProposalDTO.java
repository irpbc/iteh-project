package irpbc.iteh.service.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import irpbc.iteh.domain.enumeration.FacebookPostKind;

import javax.validation.constraints.NotNull;
import java.time.Instant;

/**
 * A DTO for the FacebookPostProposal entity.
 */
public class FacebookPostProposalDTO extends AbstractEntityDTO {

    @NotNull
    private FacebookPostKind kind;

    @NotNull
    @JsonRawValue
    private String data;

    @NotNull
    private Instant time;

    private Long studentId;
    private String studentName;

    public FacebookPostKind getKind() {
        return kind;
    }

    public void setKind(FacebookPostKind kind) {
        this.kind = kind;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long userId) {
        this.studentId = userId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "FacebookPostProposalDTO{" +
            "id=" + getId() +
            ", kind='" + getKind() + "'" +
            ", data='" + getData() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
