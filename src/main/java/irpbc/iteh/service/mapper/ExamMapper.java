package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.repository.ExamRepository;
import irpbc.iteh.service.dto.ExamDTO;

import org.mapstruct.*;

import javax.inject.Inject;

/**
 * Mapper for the entity Exam and its DTO ExamDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class,
    uses = {ExamPeriodMapper.class, CourseMapper.class})
public abstract class ExamMapper implements EntityMapper<ExamDTO, Exam> {

    @Inject
    private ExamRepository examRepository;

    @Mapping(source = "period.id", target = "periodId")
    @Mapping(source = "period.name", target = "periodName")
    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    public abstract ExamDTO toDto(Exam exam);

    @Mapping(source = "periodId", target = "period")
    @Mapping(source = "courseId", target = "course")
    @InheritConfiguration(name = "toEntity")
    public abstract Exam toEntity(ExamDTO examDTO);

    public Exam fromId(Long id) {
        return id == null ? null : examRepository.findOne(id);
    }
}
