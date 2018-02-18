package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.StudentExamDTO;

import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for the entity StudentExam and its DTO StudentExamDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class,
    uses = {UserMapper.class, ExamMapper.class})
public interface StudentExamMapper {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "student.fullName", target = "studentFullName")
    @Mapping(source = "exam.id", target = "examId")
    @Mapping(source = "exam.course.name", target = "examName")
    @Mapping(source = "evaluatedBy.id", target = "evaluatedById")
    @Mapping(source = "evaluatedBy.fullName", target = "evaluatedByFullName")
    StudentExamDTO toDto(StudentExam studentExam);

    List<StudentExamDTO> toDto(List<StudentExam> studentExams);

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "examId", target = "exam")
    @Mapping(source = "evaluatedById", target = "evaluatedBy")
    StudentExam toStudentExam(StudentExamDTO studentExamDTO);

    @InheritConfiguration(name = "toStudentExam")
    void toStudentExam(StudentExamDTO dto, @MappingTarget StudentExam entity);

    default StudentExam fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentExam studentExam = new StudentExam();
        studentExam.setId(id);
        return studentExam;
    }
}
