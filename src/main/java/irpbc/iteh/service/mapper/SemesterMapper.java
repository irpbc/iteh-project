package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.SemesterDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Semester and its DTO SemesterDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class,
    uses = {SchoolYearMapper.class})
public interface SemesterMapper extends EntityMapper<SemesterDTO, Semester> {

    @Mapping(source = "year.id", target = "yearId")
    SemesterDTO toDto(Semester semester); 

    @Mapping(source = "yearId", target = "year")
    Semester toEntity(SemesterDTO semesterDTO);

    default Semester fromId(Long id) {
        if (id == null) {
            return null;
        }
        Semester semester = new Semester();
        semester.setId(id);
        return semester;
    }
}
