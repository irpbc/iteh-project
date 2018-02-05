package irpbc.iteh.repository;

import irpbc.iteh.domain.FacebookPostProposal;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the FacebookPostProposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacebookPostProposalRepository extends JpaRepository<FacebookPostProposal, Long> {

    List<FacebookPostProposal> findByStudent_Id(Long studentId);
}
