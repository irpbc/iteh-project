package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.SchoolYearDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SchoolYear and its DTO SchoolYearDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class)
public interface SchoolYearMapper extends EntityMapper<SchoolYearDTO, SchoolYear> {

    default SchoolYear fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setId(id);
        return schoolYear;
    }
}
