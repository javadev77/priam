package fr.sacem.priam.services;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeDao;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jbelwidane on 25/07/2017.
 */
@Component
public class LigneProgrammeService {

    public static final String IDE_12 = "ide12";
    public static final String CDE_UTIL = "libAbrgUtil";

    @Autowired
    private LigneProgrammeDao ligneProgrammeDao;

    @Autowired
    private ProgrammeDao programmeDao;

    private static final Logger LOG = LoggerFactory.getLogger(LigneProgrammeService.class);

    @Transactional
    public List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme) {
        return ligneProgrammeDao.findIDE12sByProgramme(ide12, programme);
    }

    @Transactional
    public List<KeyValueDto> getTitresByProgramme(String titre, String programme) {
        return ligneProgrammeDao.findTitresByProgramme(titre.toUpperCase(), programme);
    }

    @Transactional
    public List<String> getUtilisateursByProgramme(String programme) {
        return ligneProgrammeDao.findUtilisateursByProgramme(programme);
    }

    @Transactional
    public Page<SelectionDto> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria, Pageable pageable) {

        Programme programme = programmeDao.findOne(criteria.getNumProg());

        Pageable queryPageable = new Pageable() {

            @Override
            public int getPageNumber() {
                return pageable.getPageNumber();
            }

            @Override
            public int getPageSize() {
                return pageable.getPageSize();
            }

            @Override
            public int getOffset() {
                return pageable.getOffset();
            }

            @Override
            public Sort getSort() {

                Sort sort = pageable.getSort();

                if(sort == null)
                    return sort;

                Sort.Order sortBy = sort.iterator().next();

                if("nbrDif".equals(sortBy.getProperty()) || "durDif".equals(sortBy.getProperty())) {
                    if (TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(programme.getTypeUtilisation().getCode())) {
                        sort = JpaSort.unsafe(sortBy.getDirection(), "sum(nbrDif)");
                    } else if (TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(programme.getTypeUtilisation().getCode())) {
                        sort = JpaSort.unsafe(sortBy.getDirection(), "sum(durDif)");
                    }
                } else if("libAbrgUtil".equals(sortBy.getProperty())) {
                    sort = JpaSort.unsafe(sortBy.getDirection(), "cdeUtil");
                }

                return sort;
            }

            @Override
            public Pageable next() {
                return pageable.next();
            }

            @Override
            public Pageable previousOrFirst() {
                return pageable.previousOrFirst();
            }

            @Override
            public Pageable first() {
                return pageable.first();
            }

            @Override
            public boolean hasPrevious() {
                return pageable.hasPrevious();
            }
        };


        return ligneProgrammeDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
                criteria.getUtilisateur(),
                criteria.getIde12(),
                criteria.getTitre(),
                criteria.getAjout(),
                criteria.getSelection(), queryPageable);
    }

    @Transactional
    public void selectAll(String numProg) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, true);
    }

    @Transactional
    public void selectLigneProgramme(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, false);

        for (Map<String, String>  obj:  idLingesProgrammes) {
            ligneProgrammeDao.updateSelectionByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), obj.get(CDE_UTIL).split(" - ")[0]);
        }


    }

    @Transactional
    public void selectAllLigneProgrammeExcept(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, false);

        for (Map<String, String>  obj:  idLingesProgrammes) {
            ligneProgrammeDao.updateSelectionByNumProgrammeExcept(numProg, Long.parseLong(obj.get(IDE_12)), obj.get(CDE_UTIL).split(" - ")[0]);
        }

    }

    @Transactional
    public void deselectAll(String numProg) {
        ligneProgrammeDao.updateSelectionByNumProgramme(numProg, false);
    }


    public void supprimerLigneProgramme(String numProg, Long ide12) {
        ligneProgrammeDao.deleteLigneProgrammeByIde12AndNumProg(numProg, ide12);
    }
}
