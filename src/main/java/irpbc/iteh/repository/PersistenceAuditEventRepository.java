package irpbc.iteh.repository;

import irpbc.iteh.domain.PersistentAuditEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

/**
 * Spring Data JPA repository for the PersistentAuditEvent entity.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {

    List<PersistentAuditEvent> findByUserId(Long userId);

    List<PersistentAuditEvent> findByEventDateAfter(Instant after);

    List<PersistentAuditEvent> findByUserIdAndEventDateAfter(Long userId, Instant after);

    List<PersistentAuditEvent> findByUserIdAndEventDateAfterAndEventType(Long userId, Instant after, String type);

    Page<PersistentAuditEvent> findAllByEventDateBetween(Instant fromDate, Instant toDate, Pageable pageable);
}
