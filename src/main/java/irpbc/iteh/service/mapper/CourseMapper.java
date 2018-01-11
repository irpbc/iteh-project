package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.CourseDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Course and its DTO CourseDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class,
    uses = {SemesterMapper.class, OptionalCourseGroupMapper.class, UserMapper.class})
public interface CourseMapper extends EntityMapper<CourseDTO, Course> {

    @Mapping(source = "semester.id", target = "semesterId")
    @Mapping(source = "semester.name", target = "semesterName")
    @Mapping(source = "optionalGroup.id", target = "optionalGroupId")
    @Mapping(source = "optionalGroup.name", target = "optionalGroupName")
    CourseDTO toDto(Course course); 

    @Mapping(source = "semesterId", target = "semester")
    @Mapping(source = "optionalGroupId", target = "optionalGroup")
    Course toEntity(CourseDTO courseDTO);

    default Course fromId(Long id) {
        if (id == null) {
            return null;
        }
        Course course = new Course();
        course.setId(id);
        return course;
    }
}
