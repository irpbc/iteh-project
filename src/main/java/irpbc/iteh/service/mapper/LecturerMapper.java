package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.LecturerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Lecturer and its DTO LecturerDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class, uses = {})
public interface LecturerMapper extends UserMapper {

    @Mapping(target = "courses", ignore = true)
    @InheritConfiguration
    Lecturer toEntity(LecturerDTO lecturerDTO);

    @InheritConfiguration
    LecturerDTO toDto(Lecturer lecturer);

    default Lecturer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Lecturer lecturer = new Lecturer();
        lecturer.setId(id);
        return lecturer;
    }
}
