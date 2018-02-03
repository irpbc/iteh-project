package irpbc.iteh.service.dto;

import io.github.jhipster.service.filter.Filter;
import irpbc.iteh.domain.enumeration.UserType;

import java.io.Serializable;

/**
 * Criteria class for the User entity. This class is used in UserResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /users?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class UserCriteria implements Serializable {
    private static final long serialVersionUID = 1L;

    private UserTypeFilter userType;

    public UserCriteria() {
    }

    public UserTypeFilter getUserType() {
        return userType;
    }

    public void setUserType(UserTypeFilter userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserCriteria{" +
            (userType != null ? "userType=" + userType + ", " : "") +
            "}";
    }

    public static class UserTypeFilter extends Filter<UserType> {

    }
}
