package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.AbstractEntity;
import irpbc.iteh.service.dto.AbstractEntityDTO;
import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

/**
 * Created by ivan on 9.1.18..
 */
@MapperConfig(componentModel = "spring", mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG)
public interface EntityMapperConfig {

    //@Mapping(target = "id", ignore = true)
    AbstractEntity toEntity(AbstractEntityDTO dto);
}
