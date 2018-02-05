package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.FacebookPostProposalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity FacebookPostProposal and its DTO FacebookPostProposalDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface FacebookPostProposalMapper extends EntityMapper<FacebookPostProposalDTO, FacebookPostProposal> {

    @Mapping(target = "studentId", source = "student.id")
    @Mapping(target = "studentName", source = "student.fullName")
    FacebookPostProposalDTO toDto(FacebookPostProposal facebookPostProposal);

    @Mapping(target = "student", source = "studentId")
    FacebookPostProposal toEntity(FacebookPostProposalDTO facebookPostProposalDTO);

    default FacebookPostProposal fromId(Long id) {
        if (id == null) {
            return null;
        }
        FacebookPostProposal facebookPostProposal = new FacebookPostProposal();
        facebookPostProposal.setId(id);
        return facebookPostProposal;
    }
}
