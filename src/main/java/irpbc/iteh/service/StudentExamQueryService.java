package irpbc.iteh.service;

import java.util.List;

import irpbc.iteh.domain.Exam_;
import irpbc.iteh.domain.StudentExam_;
import irpbc.iteh.domain.User_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import irpbc.iteh.domain.StudentExam;
import irpbc.iteh.domain.*; // for static metamodels
import irpbc.iteh.repository.StudentExamRepository;
import irpbc.iteh.repository.search.StudentExamSearchRepository;
import irpbc.iteh.service.dto.StudentExamCriteria;

import irpbc.iteh.service.dto.StudentExamDTO;
import irpbc.iteh.service.mapper.StudentExamMapper;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Service for executing complex queries for StudentExam entities in the database.
 * The main input is a {@link StudentExamCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link StudentExamDTO} or a {@link Page} of {@link StudentExamDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class StudentExamQueryService extends QueryService<StudentExam> {

    private final Logger log = LoggerFactory.getLogger(StudentExamQueryService.class);

    private final StudentExamRepository studentExamRepository;

    private final StudentExamMapper studentExamMapper;

    public StudentExamQueryService(StudentExamRepository studentExamRepository,
                                   StudentExamMapper studentExamMapper) {
        this.studentExamRepository = studentExamRepository;
        this.studentExamMapper = studentExamMapper;
    }

    /**
     * Return a {@link List} of {@link StudentExamDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<StudentExamDTO> findByCriteria(StudentExamCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<StudentExam> specification = createSpecification(criteria);
        return studentExamMapper.toDto(studentExamRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link StudentExamDTO} which matches the criteria from the database
     *
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page     The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<StudentExamDTO> findByCriteria(StudentExamCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<StudentExam> specification = createSpecification(criteria);
        final Page<StudentExam> result = studentExamRepository.findAll(specification, page);
        return result.map(studentExamMapper::toDto);
    }

    /**
     * Function to convert StudentExamCriteria to a {@link Specifications}
     */
    @SuppressWarnings("unchecked")
    private Specifications<StudentExam> createSpecification(StudentExamCriteria criteria) {
        Specifications<StudentExam> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), StudentExam_.id));
            }
            if (criteria.getAttended() != null) {
                specification = specification.and(buildSpecification(criteria.getAttended(), StudentExam_.attended));
            }
            if (criteria.getGrade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrade(), StudentExam_.grade));
            }
            if (criteria.getStudentId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getStudentId(), (SingularAttribute) StudentExam_.student, User_.id));
            }
            if (criteria.getExamId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getExamId(), (SingularAttribute) StudentExam_.exam, Exam_.id));
            }
            if (criteria.getEvaluatedById() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getEvaluatedById(), (SingularAttribute) StudentExam_.evaluatedBy, User_.id));
            }
        }
        return specification;
    }
}
