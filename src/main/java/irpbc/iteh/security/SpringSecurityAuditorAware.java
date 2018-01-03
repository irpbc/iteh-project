package irpbc.iteh.security;

import irpbc.iteh.config.Constants;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Long getCurrentAuditor() {
        Long userId = SecurityUtils.getCurrentUserId();
        return userId != null ? userId : 1;
    }
}
