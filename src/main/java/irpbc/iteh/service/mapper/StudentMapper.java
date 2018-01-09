package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.StudentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Student and its DTO StudentDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class, uses = {})
public interface StudentMapper extends UserMapper {

    @InheritConfiguration
    Student toEntity(StudentDTO studentDTO);

    StudentDTO toDto(Student student);

    default Student fromId(Long id) {
        if (id == null) {
            return null;
        }
        Student student = new Student();
        student.setId(id);
        return student;
    }
}
