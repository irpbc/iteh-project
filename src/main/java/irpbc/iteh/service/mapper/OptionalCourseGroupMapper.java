package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.OptionalCourseGroupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OptionalCourseGroup and its DTO OptionalCourseGroupDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class, uses = {SemesterMapper.class})
public interface OptionalCourseGroupMapper extends EntityMapper<OptionalCourseGroupDTO, OptionalCourseGroup> {

    @Mapping(source = "semester.id", target = "semesterId")
    OptionalCourseGroupDTO toDto(OptionalCourseGroup optionalCourseGroup); 

    @Mapping(source = "semesterId", target = "semester")
    OptionalCourseGroup toEntity(OptionalCourseGroupDTO optionalCourseGroupDTO);

    default OptionalCourseGroup fromId(Long id) {
        if (id == null) {
            return null;
        }
        OptionalCourseGroup optionalCourseGroup = new OptionalCourseGroup();
        optionalCourseGroup.setId(id);
        return optionalCourseGroup;
    }
}
