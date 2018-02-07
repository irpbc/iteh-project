package irpbc.iteh.repository;

import irpbc.iteh.domain.FacebookPostProposal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the FacebookPostProposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FacebookPostProposalRepository extends JpaRepository<FacebookPostProposal, Long> {

    Page<FacebookPostProposal> findByStudent_Id(Long studentId, Pageable pageable);
}
