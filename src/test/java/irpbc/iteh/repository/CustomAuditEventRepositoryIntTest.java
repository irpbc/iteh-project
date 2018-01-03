package irpbc.iteh.repository;

import irpbc.iteh.ItehProjectApp;
import irpbc.iteh.config.Constants;
import irpbc.iteh.config.audit.AuditEventConverter;
import irpbc.iteh.domain.PersistentAuditEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static irpbc.iteh.repository.CustomAuditEventRepository.EVENT_DATA_COLUMN_MAX_LENGTH;

/**
 * Test class for the CustomAuditEventRepository class.
 *
 * @see CustomAuditEventRepository
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ItehProjectApp.class)
@Transactional
public class CustomAuditEventRepositoryIntTest {

    @Autowired
    private PersistenceAuditEventRepository persistenceAuditEventRepository;

    @Autowired
    private AuditEventConverter auditEventConverter;

    private CustomAuditEventRepository customAuditEventRepository;

    private PersistentAuditEvent testUserEvent;

    private PersistentAuditEvent testOtherUserEvent;

    private PersistentAuditEvent testOldUserEvent;

    @Before
    public void setup() {
        customAuditEventRepository = new CustomAuditEventRepository(persistenceAuditEventRepository, auditEventConverter);
        persistenceAuditEventRepository.deleteAll();
        Instant oneHourAgo = Instant.now().minusSeconds(3600);

        testUserEvent = new PersistentAuditEvent();
        testUserEvent.setUserId("test-user");
        testUserEvent.setEventType("test-type");
        testUserEvent.setEventDate(oneHourAgo);
        Map<String, String> data = new HashMap<>();
        data.put("test-key", "test-value");
        testUserEvent.setData(data);

        testOldUserEvent = new PersistentAuditEvent();
        testOldUserEvent.setUserId("test-user");
        testOldUserEvent.setEventType("test-type");
        testOldUserEvent.setEventDate(oneHourAgo.minusSeconds(10000));

        testOtherUserEvent = new PersistentAuditEvent();
        testOtherUserEvent.setUserId("other-test-user");
        testOtherUserEvent.setEventType("test-type");
        testOtherUserEvent.setEventDate(oneHourAgo);
    }

    @Test
    public void testFindAfter() {
        persistenceAuditEventRepository.save(testUserEvent);
        persistenceAuditEventRepository.save(testOldUserEvent);

        List<AuditEvent> events =
            customAuditEventRepository.find(Date.from(testUserEvent.getEventDate().minusSeconds(3600)));
        assertThat(events).hasSize(1);
        AuditEvent event = events.get(0);
        assertThat(event.getPrincipal()).isEqualTo(testUserEvent.getUserId());
        assertThat(event.getType()).isEqualTo(testUserEvent.getEventType());
        assertThat(event.getData()).containsKey("test-key");
        assertThat(event.getData().get("test-key").toString()).isEqualTo("test-value");
        assertThat(event.getTimestamp()).isEqualTo(Date.from(testUserEvent.getEventDate()));
    }

    @Test
    public void testFindByPrincipal() {
        persistenceAuditEventRepository.save(testUserEvent);
        persistenceAuditEventRepository.save(testOldUserEvent);
        persistenceAuditEventRepository.save(testOtherUserEvent);

        List<AuditEvent> events = customAuditEventRepository
            .find("test-user", Date.from(testUserEvent.getEventDate().minusSeconds(3600)));
        assertThat(events).hasSize(1);
        AuditEvent event = events.get(0);
        assertThat(event.getPrincipal()).isEqualTo(testUserEvent.getUserId());
        assertThat(event.getType()).isEqualTo(testUserEvent.getEventType());
        assertThat(event.getData()).containsKey("test-key");
        assertThat(event.getData().get("test-key").toString()).isEqualTo("test-value");
        assertThat(event.getTimestamp()).isEqualTo(Date.from(testUserEvent.getEventDate()));
    }

    @Test
    public void testFindByPrincipalNotNullAndAfterIsNull() {
        persistenceAuditEventRepository.save(testUserEvent);
        persistenceAuditEventRepository.save(testOtherUserEvent);

        List<AuditEvent> events = customAuditEventRepository.find("test-user", null);
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getPrincipal()).isEqualTo("test-user");
    }

    @Test
    public void testFindByPrincipalIsNullAndAfterIsNull() {
        persistenceAuditEventRepository.save(testUserEvent);
        persistenceAuditEventRepository.save(testOtherUserEvent);

        List<AuditEvent> events = customAuditEventRepository.find(null, null);
        assertThat(events).hasSize(2);
        assertThat(events).extracting("principal")
            .containsExactlyInAnyOrder("test-user", "other-test-user");
    }

    @Test
    public void findByPrincipalAndType() {
        persistenceAuditEventRepository.save(testUserEvent);
        persistenceAuditEventRepository.save(testOldUserEvent);

        testOtherUserEvent.setEventType(testUserEvent.getEventType());
        persistenceAuditEventRepository.save(testOtherUserEvent);

        PersistentAuditEvent testUserOtherTypeEvent = new PersistentAuditEvent();
        testUserOtherTypeEvent.setUserId(testUserEvent.getUserId());
        testUserOtherTypeEvent.setEventType("test-other-type");
        testUserOtherTypeEvent.setEventDate(testUserEvent.getEventDate());
        persistenceAuditEventRepository.save(testUserOtherTypeEvent);

        List<AuditEvent> events = customAuditEventRepository.find("test-user",
            Date.from(testUserEvent.getEventDate().minusSeconds(3600)), "test-type");
        assertThat(events).hasSize(1);
        AuditEvent event = events.get(0);
        assertThat(event.getPrincipal()).isEqualTo(testUserEvent.getUserId());
        assertThat(event.getType()).isEqualTo(testUserEvent.getEventType());
        assertThat(event.getData()).containsKey("test-key");
        assertThat(event.getData().get("test-key").toString()).isEqualTo("test-value");
        assertThat(event.getTimestamp()).isEqualTo(Date.from(testUserEvent.getEventDate()));
    }

    @Test
    public void addAuditEvent() {
        Map<String, Object> data = new HashMap<>();
        data.put("test-key", "test-value");
        AuditEvent event = new AuditEvent("test-user", "test-type", data);
        customAuditEventRepository.add(event);
        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
        assertThat(persistentAuditEvents).hasSize(1);
        PersistentAuditEvent persistentAuditEvent = persistentAuditEvents.get(0);
        assertThat(persistentAuditEvent.getUserId()).isEqualTo(event.getPrincipal());
        assertThat(persistentAuditEvent.getEventType()).isEqualTo(event.getType());
        assertThat(persistentAuditEvent.getData()).containsKey("test-key");
        assertThat(persistentAuditEvent.getData().get("test-key")).isEqualTo("test-value");
        assertThat(persistentAuditEvent.getEventDate()).isEqualTo(event.getTimestamp().toInstant());
    }

    @Test
    public void addAuditEventTruncateLargeData() {
        Map<String, Object> data = new HashMap<>();
        StringBuilder largeData = new StringBuilder();
        for (int i = 0; i < EVENT_DATA_COLUMN_MAX_LENGTH + 10; i++) {
            largeData.append("a");
        }
        data.put("test-key", largeData);
        AuditEvent event = new AuditEvent("test-user", "test-type", data);
        customAuditEventRepository.add(event);
        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
        assertThat(persistentAuditEvents).hasSize(1);
        PersistentAuditEvent persistentAuditEvent = persistentAuditEvents.get(0);
        assertThat(persistentAuditEvent.getUserId()).isEqualTo(event.getPrincipal());
        assertThat(persistentAuditEvent.getEventType()).isEqualTo(event.getType());
        assertThat(persistentAuditEvent.getData()).containsKey("test-key");
        String actualData = persistentAuditEvent.getData().get("test-key");
        assertThat(actualData.length()).isEqualTo(EVENT_DATA_COLUMN_MAX_LENGTH);
        assertThat(actualData).isSubstringOf(largeData);
        assertThat(persistentAuditEvent.getEventDate()).isEqualTo(event.getTimestamp().toInstant());
    }

    @Test
    public void testAddEventWithWebAuthenticationDetails() {
        HttpSession session = new MockHttpSession(null, "test-session-id");
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setSession(session);
        request.setRemoteAddr("1.2.3.4");
        WebAuthenticationDetails details = new WebAuthenticationDetails(request);
        Map<String, Object> data = new HashMap<>();
        data.put("test-key", details);
        AuditEvent event = new AuditEvent("test-user", "test-type", data);
        customAuditEventRepository.add(event);
        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
        assertThat(persistentAuditEvents).hasSize(1);
        PersistentAuditEvent persistentAuditEvent = persistentAuditEvents.get(0);
        assertThat(persistentAuditEvent.getData().get("remoteAddress")).isEqualTo("1.2.3.4");
        assertThat(persistentAuditEvent.getData().get("sessionId")).isEqualTo("test-session-id");
    }

    @Test
    public void testAddEventWithNullData() {
        Map<String, Object> data = new HashMap<>();
        data.put("test-key", null);
        AuditEvent event = new AuditEvent("test-user", "test-type", data);
        customAuditEventRepository.add(event);
        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
        assertThat(persistentAuditEvents).hasSize(1);
        PersistentAuditEvent persistentAuditEvent = persistentAuditEvents.get(0);
        assertThat(persistentAuditEvent.getData().get("test-key")).isEqualTo("null");
    }

    @Test
    public void addAuditEventWithAnonymousUser() {
        Map<String, Object> data = new HashMap<>();
        data.put("test-key", "test-value");
        AuditEvent event = new AuditEvent(Constants.ANONYMOUS_USER, "test-type", data);
        customAuditEventRepository.add(event);
        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
        assertThat(persistentAuditEvents).hasSize(0);
    }

    @Test
    public void addAuditEventWithAuthorizationFailureType() {
        Map<String, Object> data = new HashMap<>();
        data.put("test-key", "test-value");
        AuditEvent event = new AuditEvent("test-user", "AUTHORIZATION_FAILURE", data);
        customAuditEventRepository.add(event);
        List<PersistentAuditEvent> persistentAuditEvents = persistenceAuditEventRepository.findAll();
        assertThat(persistentAuditEvents).hasSize(0);
    }

}
