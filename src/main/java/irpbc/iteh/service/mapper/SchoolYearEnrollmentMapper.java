package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.SchoolYearEnrollmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SchoolYearEnrollment and its DTO SchoolYearEnrollmentDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class,
    uses = {StudentMapper.class, SchoolYearMapper.class})
public interface SchoolYearEnrollmentMapper extends EntityMapper<SchoolYearEnrollmentDTO, SchoolYearEnrollment> {

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "yearId", source = "year.id")
    SchoolYearEnrollmentDTO toDto(SchoolYearEnrollment schoolYearEnrollment);

    @Mapping(target = "student", source = "studentId")
    @Mapping(target = "year", source = "yearId")
    SchoolYearEnrollment toEntity(SchoolYearEnrollmentDTO schoolYearEnrollmentDTO);

    default SchoolYearEnrollment fromId(Long id) {
        if (id == null) {
            return null;
        }
        SchoolYearEnrollment schoolYearEnrollment = new SchoolYearEnrollment();
        schoolYearEnrollment.setId(id);
        return schoolYearEnrollment;
    }
}
