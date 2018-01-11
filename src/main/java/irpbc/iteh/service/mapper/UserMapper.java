package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.Authority;
import irpbc.iteh.domain.User;
import irpbc.iteh.service.dto.UserDTO;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 * <p>
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDTO, User> {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "activationKey", ignore = true)
    @Mapping(target = "resetKey", ignore = true)
    @Mapping(target = "resetDate", ignore = true)
    @Mapping(target = "activated", ignore = true)
    @Mapping(target = "courses", ignore = true)
    @Mapping(target = "fullName",
        expression = "java(userDTO.getFirstName() + \" \" + userDTO.getLastName())")
    @InheritConfiguration
    User toEntity(UserDTO userDTO);

    default User fromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    default Set<Authority> authoritiesFromStrings(Set<String> strings) {
        return strings.stream()
            .map(string -> new Authority().name(string))
            .collect(Collectors.toSet());
    }

    default Set<String> authoritiesToStrings(Set<Authority> authorities) {
        return authorities.stream()
            .map(Authority::getName)
            .collect(Collectors.toSet());
    }
}
