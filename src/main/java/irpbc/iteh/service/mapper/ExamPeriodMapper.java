package irpbc.iteh.service.mapper;

import irpbc.iteh.domain.*;
import irpbc.iteh.service.dto.ExamPeriodDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ExamPeriod and its DTO ExamPeriodDTO.
 */
@Mapper(componentModel = "spring", config = EntityMapperConfig.class,
    uses = {SchoolYearMapper.class})
public interface ExamPeriodMapper extends EntityMapper<ExamPeriodDTO, ExamPeriod> {

    @Mapping(source = "year.id", target = "yearId")
    ExamPeriodDTO toDto(ExamPeriod examPeriod); 

    @Mapping(source = "yearId", target = "year")
    ExamPeriod toEntity(ExamPeriodDTO examPeriodDTO);

    default ExamPeriod fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExamPeriod examPeriod = new ExamPeriod();
        examPeriod.setId(id);
        return examPeriod;
    }
}
