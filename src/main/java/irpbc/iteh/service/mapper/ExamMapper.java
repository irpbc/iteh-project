package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.ExamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Exam and its DTO ExamDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class,
    uses = {ExamPeriodMapper.class, CourseMapper.class})
public interface ExamMapper extends EntityMapper<ExamDTO, Exam> {

    @Mapping(source = "period.id", target = "periodId")
    @Mapping(source = "course.id", target = "courseId")
    ExamDTO toDto(Exam exam); 

    @Mapping(source = "periodId", target = "period")
    @Mapping(source = "courseId", target = "course")
    @InheritConfiguration(name = "toEntity")
    Exam toEntity(ExamDTO examDTO);

    default Exam fromId(Long id) {
        if (id == null) {
            return null;
        }
        Exam exam = new Exam();
        exam.setId(id);
        return exam;
    }
}
