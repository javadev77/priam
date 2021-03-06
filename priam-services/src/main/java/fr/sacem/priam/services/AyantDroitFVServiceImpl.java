package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.fv.AyantDroitProgrammeFVDao;
import fr.sacem.priam.model.domain.criteria.AyantDroitCriteria;
import fr.sacem.priam.model.domain.dto.AyantDroitDto;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.services.fv.AyantDroitFVService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;

@Service("ayantDroitFVService")
public class AyantDroitFVServiceImpl implements AyantDroitFVService {

    @Autowired
    AyantDroitProgrammeFVDao ayantDroitProgrammeFVDao;

    @Override
    public Page<? extends AyantDroitDto> findAyantDroitByCriteria(AyantDroitCriteria criteria, Pageable pageable){
        return ayantDroitProgrammeFVDao.findCoadByCriteria(criteria.getNumProg(),
                criteria.getIde12(),
                criteria.getTitre(),
                criteria.getCoad(),
                criteria.getParticipant(), pageable);
    }

    @Override
    public List<KeyValueDto> getListCoadByNumProg(Long coad, String programme) {
        return ayantDroitProgrammeFVDao.findCoadByNumProg(coad, programme);
    }

    @Override
    public List<KeyValueDto> getParticipantByNumProg(String query, String programme) {
        return ayantDroitProgrammeFVDao.findParticipantByNumProg(query, programme);
    }

    @Override
    public List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme) {
        return ayantDroitProgrammeFVDao.findIDE12sByProgramme(ide12, programme);
    }

    @Override
    public List<KeyValueDto> getTitresByProgramme(String query, String programme) {
        return ayantDroitProgrammeFVDao.findTitresByProgramme(query, programme);
    }

    @Override
    public Double calculerPointsByCriteria(AyantDroitCriteria criteria) {
        return ayantDroitProgrammeFVDao.calculerPointsByCriteria(criteria.getNumProg(),
                criteria.getIde12(),
                criteria.getTitre(),
                criteria.getCoad(),
                criteria.getParticipant());
    }

    @Override
    public Page<? extends AyantDroitDto> findByCumulCoad(final AyantDroitCriteria criteria, final Pageable pageable) {
        Sort customSort = createCustomSort(pageable, criteria);
        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), customSort);

        return ayantDroitProgrammeFVDao.findByCumulCoad(criteria.getNumProg(),
            criteria.getCoad(),
            criteria.getParticipant(), pageRequest);
    }

    private Sort createCustomSort(final Pageable pageable, final AyantDroitCriteria criteria) {
        Sort sort = pageable.getSort();

        if(sort == null)
            return sort;

        Sort.Order sortBy = sort.iterator().next();

        String sortProp = sortBy.getProperty();
        if("points".equals(sortProp) ||
            "sum(points)".equals(sortProp)){
            Sort.Direction direction = sortBy.getDirection();
            sort = JpaSort.unsafe(direction, "sum(points)");
        }

        return sort;
    }

    @Override
    public Double calculerPointsByCumulCoad(final AyantDroitCriteria criteria) {
        return ayantDroitProgrammeFVDao.calculerPointsByCumulCoad(criteria.getNumProg(), criteria.getCoad(), criteria.getParticipant());

    }

}
