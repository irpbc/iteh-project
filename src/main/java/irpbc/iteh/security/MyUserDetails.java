package irpbc.iteh.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Created by ivan on 3.1.18..
 */
public class MyUserDetails extends User {

    private long userId;

    public MyUserDetails(long userId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public MyUserDetails(long userId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.userId = userId;
    }

    public final long getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return Long.toString(userId);
    }

    public String getLogin() {
        return super.getUsername();
    }
}
