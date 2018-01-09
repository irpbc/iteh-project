package irpbc.iteh.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by ivan on 5.1.18..
 */
public class AbstractEntityDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AbstractEntityDTO id(Long id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractEntityDTO that = (AbstractEntityDTO) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
