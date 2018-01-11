package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.CommitmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Commitment and its DTO CommitmentDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class, uses = {CourseMapper.class})
public interface CommitmentMapper extends EntityMapper<CommitmentDTO, Commitment> {

    @Mapping(source = "course.id", target = "courseId")
    @Mapping(source = "course.name", target = "courseName")
    CommitmentDTO toDto(Commitment commitment); 

    @Mapping(source = "courseId", target = "course")
    Commitment toEntity(CommitmentDTO commitmentDTO);

    default Commitment fromId(Long id) {
        if (id == null) {
            return null;
        }
        Commitment commitment = new Commitment();
        commitment.setId(id);
        return commitment;
    }
}
