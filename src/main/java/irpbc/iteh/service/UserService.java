package irpbc.iteh.service;

import irpbc.iteh.config.Constants;
import irpbc.iteh.domain.Authority;
import irpbc.iteh.domain.User;
import irpbc.iteh.repository.AuthorityRepository;
import irpbc.iteh.repository.UserRepository;
import irpbc.iteh.repository.search.UserSearchRepository;
import irpbc.iteh.security.AuthoritiesConstants;
import irpbc.iteh.security.SecurityUtils;
import irpbc.iteh.service.dto.UserDTO;
import irpbc.iteh.service.mapper.UserMapper;
import irpbc.iteh.service.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private static final String USERS_CACHE = "users";

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final SocialService socialService;
    private final UserSearchRepository userSearchRepository;
    private final AuthorityRepository authorityRepository;
    private final CacheManager cacheManager;

    public UserService(UserRepository userRepository,
                       UserMapper userMapper,
                       PasswordEncoder passwordEncoder,
                       SocialService socialService,
                       UserSearchRepository userSearchRepository,
                       AuthorityRepository authorityRepository,
                       CacheManager cacheManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.socialService = socialService;
        this.userSearchRepository = userSearchRepository;
        this.authorityRepository = authorityRepository;
        this.cacheManager = cacheManager;
    }

    public User activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        User user = userRepository.findOneByActivationKey(key);

        // activate given user for the registration key.
        user.setActivated(true);
        user.setActivationKey(null);
        userSearchRepository.save(user);
        cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
        log.debug("Activated user: {}", user);
        return user;
    }

    public User completePasswordReset(String newPassword, String key) {
        log.debug("Reset user password for reset key {}", key);

        User user = userRepository.findOneByResetKey(key);
        if (user.getResetDate().isAfter(Instant.now().minusSeconds(86400))) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user.setResetKey(null);
            user.setResetDate(null);
            cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
        }
        return user;
    }

    public User requestPasswordReset(String mail) {
        User user = userRepository.findOneByEmailIgnoreCase(mail);
        if (user.getActivated()) {
            user.setResetKey(RandomUtil.generateResetKey());
            user.setResetDate(Instant.now());
            cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
        }
        return user;
    }

    public User createUser(UserDTO userDTO) {

        Set<String> authSet = new HashSet<>();
        switch (userDTO.getUserType()) {
            case ST:
                authSet.add(AuthoritiesConstants.STUDENT);
                authSet.add(AuthoritiesConstants.USER);
                break;
            case LC:
                authSet.add(AuthoritiesConstants.LECTURER);
                authSet.add(AuthoritiesConstants.USER);
                break;
            case SR:
                authSet.add(AuthoritiesConstants.SERVICE);
                authSet.add(AuthoritiesConstants.LECTURER);
                authSet.add(AuthoritiesConstants.STUDENT);
                authSet.add(AuthoritiesConstants.USER);
                break;
            case AD:
                authSet.add(AuthoritiesConstants.ADMIN);
                authSet.add(AuthoritiesConstants.LECTURER);
                authSet.add(AuthoritiesConstants.SERVICE);
                authSet.add(AuthoritiesConstants.STUDENT);
                authSet.add(AuthoritiesConstants.USER);
                break;
        }
        userDTO.setAuthorities(authSet);

        User user = new User();
        user.setUserType(userDTO.getUserType());
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.fullName(userDTO.getFirstName() + ' ' + userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey(Constants.DEFAULT_LANGUAGE); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = userDTO.getAuthorities().stream()
                .map(authorityRepository::findOne)
                .collect(Collectors.toSet());
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(Instant.now());
        user.setActivated(true);
        userRepository.save(user);
        userSearchRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName  last name of user
     * @param email     email id of user
     * @param langKey   language key
     * @param imageUrl  image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        User user = userRepository.findOne(SecurityUtils.getCurrentUserId());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setLangKey(langKey);
        user.setImageUrl(imageUrl);
        userSearchRepository.save(user);
        cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
        log.debug("Changed Information for User: {}", user);
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return Optional.of(userRepository
            .findOne(userDTO.getId()))
            .map(user -> {
                user.setUserType(userDTO.getUserType());
                user.setLogin(userDTO.getLogin());
                user.setFirstName(userDTO.getFirstName());
                user.setLastName(userDTO.getLastName());
                user.fullName(userDTO.getFirstName() + ' ' + userDTO.getLastName());
                user.setEmail(userDTO.getEmail());
                user.setImageUrl(userDTO.getImageUrl());
                user.setActivated(userDTO.isActivated());
                user.setLangKey(userDTO.getLangKey());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                userDTO.getAuthorities().stream()
                    .map(authorityRepository::findOne)
                    .forEach(managedAuthorities::add);
                userSearchRepository.save(user);
                cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(userMapper::toDto);
    }

    public void deleteUser(String login) {
        User user = userRepository.findOneByLogin(login);
        socialService.deleteUserSocialConnection(user.getLogin());
        userRepository.delete(user);
        userSearchRepository.delete(user);
        cacheManager.getCache(USERS_CACHE).evict(login);
        log.debug("Deleted User: {}", user);
    }

    public void changePassword(String password) {
        User user = userRepository.findOne(SecurityUtils.getCurrentUserId());
        String encryptedPassword = passwordEncoder.encode(password);
        user.setPassword(encryptedPassword);
        cacheManager.getCache(USERS_CACHE).evict(user.getLogin());
        log.debug("Changed password for User: {}", user);
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository
            .findAll(pageable)
            .map(userMapper::toDto);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserWithAuthoritiesByLogin(String login) {
        return userMapper.toDto(userRepository.findOneWithAuthoritiesByLogin(login));
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public UserDTO getUserWithAuthorities() {
        return userMapper.toDto(userRepository
            .findOneWithAuthoritiesById(SecurityUtils.getCurrentUserId()));
    }

    /**
     * @return a list of all the authorities
     */
    public List<String> getAuthorities() {
        return authorityRepository.findAll().stream()
            .map(Authority::getName)
            .collect(Collectors.toList());
    }
}
