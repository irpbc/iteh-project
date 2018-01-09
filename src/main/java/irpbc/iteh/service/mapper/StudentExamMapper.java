package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.StudentExamDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentExam and its DTO StudentExamDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class,
    uses = {StudentMapper.class, ExamMapper.class, LecturerMapper.class})
public interface StudentExamMapper extends EntityMapper<StudentExamDTO, StudentExam> {

    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "exam.id", target = "examId")
    @Mapping(source = "evaluatedBy.id", target = "evaluatedById")
    StudentExamDTO toDto(StudentExam studentExam); 

    @Mapping(source = "studentId", target = "student")
    @Mapping(source = "examId", target = "exam")
    @Mapping(source = "evaluatedById", target = "evaluatedBy")
    StudentExam toEntity(StudentExamDTO studentExamDTO);

    default StudentExam fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentExam studentExam = new StudentExam();
        studentExam.setId(id);
        return studentExam;
    }
}
