package irpbc.iteh.service;


import java.util.List;

import irpbc.iteh.domain.User_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import irpbc.iteh.domain.User;
import irpbc.iteh.domain.*; // for static metamodels
import irpbc.iteh.repository.UserRepository;
import irpbc.iteh.repository.search.UserSearchRepository;
import irpbc.iteh.service.dto.UserCriteria;

import irpbc.iteh.service.dto.UserDTO;
import irpbc.iteh.service.mapper.UserMapper;

/**
 * Service for executing complex queries for User entities in the database.
 * The main input is a {@link UserCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserDTO} or a {@link Page} of {@link UserDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserQueryService extends QueryService<User> {

    private final Logger log = LoggerFactory.getLogger(UserQueryService.class);


    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final UserSearchRepository userSearchRepository;

    public UserQueryService(UserRepository userRepository, UserMapper userMapper, UserSearchRepository userSearchRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userSearchRepository = userSearchRepository;
    }

    /**
     * Return a {@link List} of {@link UserDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserDTO> findByCriteria(UserCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<User> specification = createSpecification(criteria);
        return userMapper.toDto(userRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link UserDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserDTO> findByCriteria(UserCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<User> specification = createSpecification(criteria);
        final Page<User> result = userRepository.findAll(specification, page);
        return result.map(userMapper::toDto);
    }

    /**
     * Function to convert UserCriteria to a {@link Specifications}
     */
    private Specifications<User> createSpecification(UserCriteria criteria) {
        Specifications<User> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getUserType() != null) {
                specification = specification.and(buildSpecification(criteria.getUserType(), User_.userType));
            }
        }
        return specification;
    }
}
