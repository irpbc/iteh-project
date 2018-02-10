package irpbc.iteh.service;

import irpbc.iteh.domain.User;
import irpbc.iteh.repository.AuthorityRepository;
import irpbc.iteh.repository.UserRepository;
import irpbc.iteh.repository.search.UserSearchRepository;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.connect.*;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;

@Service
public class SocialService {

    private final Logger log = LoggerFactory.getLogger(SocialService.class);

    private final UsersConnectionRepository usersConnectionRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final MailService mailService;
    private final UserSearchRepository userSearchRepository;
    private final ConnectionRepository connectionRepository;

    public SocialService(UsersConnectionRepository usersConnectionRepository,
                         AuthorityRepository authorityRepository,
                         PasswordEncoder passwordEncoder,
                         UserRepository userRepository,
                         MailService mailService,
                         UserSearchRepository userSearchRepository,
                         ConnectionRepository connectionRepository) {
        this.usersConnectionRepository = usersConnectionRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userSearchRepository = userSearchRepository;
        this.connectionRepository = connectionRepository;
    }

    @PostConstruct
    private void init() {
        /* Workaround for outdated code in spring-social-facebook.
         * The facebook profile field named "bio" is deprecated
         * and causes an error, so we reassign the PROFILE_FIELDS
         * array by reflection with all the fields except "bio".
         */
        try {
            String[] fieldsToMap = {
                "id", "about", "age_range", "birthday", "context", "cover",
                "currency", "devices", "education", "email", "favorite_athletes",
                "favorite_teams", "first_name", "gender", "hometown", "inspirational_people",
                "installed", "install_type", "is_verified", "languages", "last_name",
                "link", "locale", "location", "meeting_for", "middle_name", "name",
                "name_format", "political", "quotes", "payment_pricepoints", "relationship_status",
                "religion", "security_settings", "significant_other", "sports", "test_group",
                "timezone", "third_party_id", "updated_time", "verified", "viewer_can_send_gift",
                "website", "work"
            };

            Field field = org.springframework.social.facebook.api.UserOperations.class
                .getDeclaredField("PROFILE_FIELDS");
            field.setAccessible(true);

            Field modifiers = field.getClass().getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, fieldsToMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteUserSocialConnection(Long userId) {
        ConnectionRepository connectionRepository = usersConnectionRepository.createConnectionRepository(userId.toString());
        connectionRepository.findAllConnections().keySet().stream()
            .forEach(providerId -> {
                connectionRepository.removeConnections(providerId);
                log.debug("Delete user social connection providerId: {}", providerId);
            });
    }

    public boolean tryConnectSocialAccountToUser(Connection<?> connection, String langKey) {
        if (connection == null) {
            log.error("Cannot create social user because connection is null");
            throw new IllegalArgumentException("Connection cannot be null");
        }
        UserProfile userProfile = connection.fetchUserProfile();
        User user = findUserForSocialAccount(userProfile);
        if (user != null) {
            createSocialConnection(user.getId(), connection);
            return true;
        } else {
            return false;
        }
    }

    private User findUserForSocialAccount(UserProfile userProfile) {
        String email = userProfile.getEmail();
        String userName = userProfile.getUsername();
        if (!StringUtils.isBlank(userName)) {
            userName = userName.toLowerCase(Locale.ENGLISH);
        }
        if (StringUtils.isBlank(email) && StringUtils.isBlank(userName)) {
            log.error("Cannot create social user because email and login are null");
            throw new IllegalArgumentException("Email and login cannot be null");
        }
        if (StringUtils.isBlank(email) && userRepository.findOneByLogin(userName) != null) {
            log.error("Cannot create social user because email is null and login already exist, login -> {}", userName);
            throw new IllegalArgumentException("Email cannot be null with an existing login");
        }
        if (!StringUtils.isBlank(email)) {
            User user = userRepository.findOneByEmailIgnoreCase(email);
            if (user != null) {
                log.info("User already exist associate the connection to this account");
                return user;
            }
        }
        return null;
    }

    private void createSocialConnection(Long userId, Connection<?> connection) {
        ConnectionRepository connectionRepository = usersConnectionRepository
            .createConnectionRepository(userId.toString());
        connectionRepository.addConnection(connection);
    }

    public void postToFacebook(String postText) {
        Facebook facebook = connectionRepository.getPrimaryConnection(Facebook.class).getApi();
        facebook.feedOperations().updateStatus(postText);
    }
}
