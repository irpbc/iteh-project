package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.StudentCommitmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity StudentCommitment and its DTO StudentCommitmentDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class,
    uses = {CourseEnrollmentMapper.class, CommitmentMapper.class, UserMapper.class})
public interface StudentCommitmentMapper extends EntityMapper<StudentCommitmentDTO, StudentCommitment> {

    @Mapping(source = "enrollment.id", target = "enrollmentId")
    @Mapping(source = "commitment.id", target = "commitmentId")
    @Mapping(source = "commitment.name", target = "commitmentName")
    @Mapping(source = "evaluatedBy.id", target = "evaluatedById")
    @Mapping(source = "evaluatedBy.fullName", target = "evaluatedByFullName")
    StudentCommitmentDTO toDto(StudentCommitment studentCommitment); 

    @Mapping(source = "enrollmentId", target = "enrollment")
    @Mapping(source = "commitmentId", target = "commitment")
    @Mapping(source = "evaluatedById", target = "evaluatedBy")
    StudentCommitment toEntity(StudentCommitmentDTO studentCommitmentDTO);

    default StudentCommitment fromId(Long id) {
        if (id == null) {
            return null;
        }
        StudentCommitment studentCommitment = new StudentCommitment();
        studentCommitment.setId(id);
        return studentCommitment;
    }
}
