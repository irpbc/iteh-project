package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.AbstractEntity;
import irpbc.iteh.service.dto.AbstractEntityDTO;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Contract for a generic dto to entity mapper.
 *
 * @param <D> - DTO type parameter.
 * @param <E> - Entity type parameter.
 */

public interface EntityMapper <D extends AbstractEntityDTO, E extends AbstractEntity> {

    E toEntity(D dto);

    D toDto(E entity);

    List <E> toEntity(List<D> dtoList);

    List <D> toDto(List<E> entityList);
}
