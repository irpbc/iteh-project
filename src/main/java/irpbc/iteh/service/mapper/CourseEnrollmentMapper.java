package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.CourseEnrollmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CourseEnrollment and its DTO CourseEnrollmentDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class,
    uses = {SchoolYearEnrollmentMapper.class, CourseMapper.class})
public interface CourseEnrollmentMapper extends EntityMapper<CourseEnrollmentDTO, CourseEnrollment> {

    @Mapping(source = "yearEnrollment.id", target = "yearEnrollmentId")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    CourseEnrollmentDTO toDto(CourseEnrollment courseEnrollment); 

    @Mapping(source = "yearEnrollmentId", target = "yearEnrollment")
    @Mapping(source = "courseId", target = "course")
    CourseEnrollment toEntity(CourseEnrollmentDTO courseEnrollmentDTO);

    default CourseEnrollment fromId(Long id) {
        if (id == null) {
            return null;
        }
        CourseEnrollment courseEnrollment = new CourseEnrollment();
        courseEnrollment.setId(id);
        return courseEnrollment;
    }
}
