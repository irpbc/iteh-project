package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.SchoolYearEnrollmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SchoolYearEnrollment and its DTO SchoolYearEnrollmentDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, SchoolYearMapper.class})
public interface SchoolYearEnrollmentMapper extends EntityMapper<SchoolYearEnrollmentDTO, SchoolYearEnrollment> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.fullName", target = "studentFullName")
    @Mapping(source = "year.id", target = "yearId")
    @Mapping(source = "year.name", target = "yearName")
    SchoolYearEnrollmentDTO toDto(SchoolYearEnrollment schoolYearEnrollment); 

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "yearId", target = "year")
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
