package irpbc.iteh.security;

import irpbc.iteh.domain.User;
import irpbc.iteh.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements AppUserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;

    public DomainUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase();
        User user = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
        return getUserDetails(lowercaseLogin, user);
    }

    @Override
    @Transactional
    public UserDetails loadUserById(final Long userId) {
        User user = userRepository.findOne(userId);
        return getUserDetails(userId, user);
    }

    private UserDetails getUserDetails(Object logName, User user) {
        if (user != null) {
            if (!user.getActivated()) {
                throw new UserNotActivatedException("User " + logName + " was not activated");
            }

            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());

            return new MyUserDetails(user.getId(), user.getLogin(), user.getPassword(), grantedAuthorities);

        } else {
            throw new UsernameNotFoundException("User " + logName + " was not found in the " + "database");
        }
    }
}
