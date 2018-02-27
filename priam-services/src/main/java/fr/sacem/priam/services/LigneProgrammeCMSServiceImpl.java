package fr.sacem.priam.services;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.common.util.FileUtils;
import fr.sacem.priam.model.dao.jpa.CatalogueOctavDao;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.CatalogueOctav;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.cms.LigneProgrammeCMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.*;

import static fr.sacem.priam.common.util.FileUtils.CATALOGUE_OCTAV_TYPE_CMS_ANF;
import static fr.sacem.priam.common.util.FileUtils.CATALOGUE_OCTAV_TYPE_CMS_FR;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by benmerzoukah on 07/12/2017.
 */
@Service("ligneProgrammeCMSService")
public class LigneProgrammeCMSServiceImpl implements LigneProgrammeService, LigneProgrammeCMSService {
    public static final String IDE_12 = "ide12";
    public static final String CDE_UTIL = "XXX";
    public static final String CDE_CISAC_058 = "058";
    public static final String MANUEL = "MANUEL";
    public static final String AUTOMATIQUE = "AUTOMATIQUE";
    public static final String CORRIGE = "CORRIGE";
    private static final String SOMME = "SOMME";
    public static final Long NBRDIF_SONOFRA = 1L;
    public static final int SELECTION = 1;
    private static final Double MT_SONOANT = 0.0;

    @Autowired
    private LigneProgrammeCMSDao ligneProgrammeCMSDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private FichierDao fichierDao;

    @Autowired
    private CatalogueOctavDao catalogueOctavDao;

    @Override
    public List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme) {
        return ligneProgrammeCMSDao.findIDE12sByProgramme(ide12, programme);
    }

    @Override
    public List<KeyValueDto> getTitresByProgramme(String titre, String programme) {
        return ligneProgrammeCMSDao.findTitresByProgramme(titre.toUpperCase(), programme);
    }

    @Override
    @Transactional
    public Page<SelectionCMSDto> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria, Pageable pageable) {
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

                if("points".equals(sortBy.getProperty()) ||
                        "sum(nbrDif)".equals(sortBy.getProperty()) ||
                        "sum(mt)".equals(sortBy.getProperty())||
                        "mt".equals(sortBy.getProperty())){
                    if (TypeUtilisationEnum.CMS_FRA.getCode().equals(programme.getTypeUtilisation().getCode())) {
                        sort = JpaSort.unsafe(sortBy.getDirection(), "sum(mt)");
                    }

                    if (TypeUtilisationEnum.CMS_ANT.getCode().equals(programme.getTypeUtilisation().getCode())) {
                        sort = JpaSort.unsafe(sortBy.getDirection(), "sum(nbrDif)");
                    }
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


        Page<SelectionCMSDto> ligneProgrammeByCriteria = ligneProgrammeCMSDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
                criteria.getIde12(),
                criteria.getTitre(),
                criteria.getAjout(),
                criteria.getSelection(), queryPageable);

        return ligneProgrammeByCriteria;
    }

    @Override
    @Transactional
    public void selectLigneProgramme(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {
                ligneProgrammeCMSDao.updateSelectionTemporaireByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), 1);
            }
        }

    }

    @Override
    @Transactional
    public void supprimerLigneProgramme(String numProg, Long ide12, SelectionDto selectedLigneProgramme) {
        LigneProgrammeCMS oeuvreManuelFound = new LigneProgrammeCMS();
        if(selectedLigneProgramme.getAjout().equals(MANUEL)){
            oeuvreManuelFound = ligneProgrammeCMSDao.findOeuvreManuelByIde12(numProg, ide12);
        } else if(selectedLigneProgramme.getAjout().equals(CORRIGE)) {
            oeuvreManuelFound = ligneProgrammeCMSDao.findOeuvreCorrigeByIde12(numProg, ide12);
        }

        deleteOeuvreManuel(oeuvreManuelFound);

    }

    @Override
    public void deleteOeuvreManuel(LigneProgrammeCMS oeuvreManuelFound) {
        if(oeuvreManuelFound != null) {

            List<LigneProgrammeCMS> oeuvresAutoByIdOeuvreManuel = ligneProgrammeCMSDao.findOeuvresAutoByIdOeuvreManuel(oeuvreManuelFound.getId());
            for (LigneProgrammeCMS oeuvreAuto : oeuvresAutoByIdOeuvreManuel) {
                oeuvreAuto.setSelection(FALSE);
                oeuvreAuto.setSelectionEnCours(TRUE);
                oeuvreAuto.setOeuvreManuel(null);
                ligneProgrammeCMSDao.save(oeuvreAuto);
            }

            ligneProgrammeCMSDao.delete(oeuvreManuelFound);
            ligneProgrammeCMSDao.flush();
        }
    }

    @Override
    @Transactional
    public void deselectLigneProgramme(String numProg, Set<Map<String, String>> unselected) {
        for (Map<String, String>  obj:  unselected) {
            if(obj != null && !obj.isEmpty()) {
                ligneProgrammeCMSDao.updateSelectionTemporaireByNumProgramme(numProg, Long.parseLong(obj.get(IDE_12)), 0);
            }
        }

    }

    @Override
    @Transactional
    public void enregistrerEdition(String numProg) {
        ligneProgrammeCMSDao.updateSelection(numProg, TRUE);
        ligneProgrammeCMSDao.updateSelection(numProg, FALSE);

        ligneProgrammeCMSDao.flush();
    }

    @Override
    @Transactional
    public void annulerEdition(String numProg) {
        List<LigneProgrammeCMS> oeuvresManuelsEnCoursEdition = ligneProgrammeCMSDao.findOeuvresManuelsEnCoursEdition(numProg, FALSE);
        oeuvresManuelsEnCoursEdition.forEach( oeuvreManuel -> deleteOeuvreManuel(oeuvreManuel));

        ligneProgrammeCMSDao.updateSelectionTemporaire(numProg, FALSE);
        ligneProgrammeCMSDao.updateSelectionTemporaire(numProg, TRUE);

        ligneProgrammeCMSDao.flush();
    }

    @Override
    @Transactional
    public void annulerSelection(String numProg) {
        List<LigneProgrammeCMS> allOeuvresManuelsByNumProg = ligneProgrammeCMSDao.findAllOeuvresManuelsByNumProg(numProg);
        allOeuvresManuelsByNumProg.forEach( oeuvreManuel -> deleteOeuvreManuel(oeuvreManuel));

        ligneProgrammeCMSDao.updateSelectionTemporaireByNumProgramme(numProg, TRUE);
        ligneProgrammeCMSDao.updateSelection(numProg, TRUE);

        ligneProgrammeCMSDao.flush();
    }

    @Override
    public void modifierDurDifTemporaire(String numProg, Set<Map<String, String>> idLingesProgrammes) {

    }

    @Override
    public Map<String,Object> calculerCompteurs(String numProg, String statut) {
        Map<String, Object> result = new HashMap<>();
        result.put(AUTOMATIQUE, 0L);
        result.put(CORRIGE, 0L);
        result.put(MANUEL, 0L);
        result.put(SOMME, 0.0d);

        Integer selection = SELECTION;

        List<Object> prog = ligneProgrammeCMSDao.compterOuvres(numProg, selection);

        for (Object indicateur : prog) {
            Object[] indObjects = (Object[]) indicateur;
            result.put((String) indObjects[1], ((BigInteger) indObjects[0]).longValue());
        }
           result.put(SOMME, ligneProgrammeCMSDao.calculerPointsMontantOeuvres(numProg, selection));

        return result;
    }

    @Transactional
    @Override
    public void ajouterOeuvreManuel(LigneProgrammeCMS input) {
        Programme programme = programmeDao.findOne(input.getNumProg());

        List<LigneProgrammeCMS> founds = ligneProgrammeCMSDao.findOeuvresAutoByIde12AndCdeUtil(input.getNumProg(), input.getIde12());
        if(founds != null && !founds.isEmpty()) {
            input.setAjout(CORRIGE);
            LigneProgrammeCMS oeuvreManuel = createOeuvreManuel(input, programme);
            founds.forEach( found -> {
                found.setOeuvreManuel(oeuvreManuel);
                found.setSelection(FALSE);
                ligneProgrammeCMSDao.save(found);
            });
        } else {
            LigneProgrammeCMS oeuvreCorrigeFound = ligneProgrammeCMSDao.findOeuvreCorrigeByIde12(input.getNumProg(), input.getIde12());
            if(oeuvreCorrigeFound != null) {
                oeuvreCorrigeFound.setCdeCisac(CDE_CISAC_058);
                oeuvreCorrigeFound.setCdeFamilTypUtil(programme.getFamille().getCode());
                oeuvreCorrigeFound.setCdeTypUtil(programme.getTypeUtilisation().getCode());
                oeuvreCorrigeFound.setAjout(CORRIGE);
                oeuvreCorrigeFound.setNbrDif(input.getNbrDif());
                oeuvreCorrigeFound.setMt(input.getMt());
                oeuvreCorrigeFound.setOeuvreManuel(null);
                oeuvreCorrigeFound.setDateInsertion(new Date());
                oeuvreCorrigeFound.setCdeTypIde12(input.getCdeTypIde12());
                ligneProgrammeCMSDao.save(oeuvreCorrigeFound);
            } else {
                LigneProgrammeCMS oeuvreManuelFound = ligneProgrammeCMSDao.findOeuvreManuelByIde12AndCdeUtil(input.getNumProg(), input.getIde12());
                if(oeuvreManuelFound != null) {
                    oeuvreManuelFound.setCdeCisac(CDE_CISAC_058);
                    oeuvreManuelFound.setCdeFamilTypUtil(programme.getFamille().getCode());
                    oeuvreManuelFound.setCdeTypUtil(programme.getTypeUtilisation().getCode());
                    oeuvreManuelFound.setAjout(MANUEL);
                    oeuvreManuelFound.setNbrDif(input.getNbrDif());
                    oeuvreManuelFound.setMt(input.getMt());
                    oeuvreManuelFound.setOeuvreManuel(null);
                    oeuvreManuelFound.setDateInsertion(new Date());
                    oeuvreManuelFound.setCdeTypIde12(input.getCdeTypIde12());
                    ligneProgrammeCMSDao.save(oeuvreManuelFound);
                    // oeuvreManuelFound.setSelectionEnCours(true);
                } else {
                    input.setAjout(MANUEL);
                    createOeuvreManuel(input, programme);
                    /*if(isEligible(input.getIde12())) {
                        createOeuvreManuel(input, programme);
                    }*/
                }
            }
        }
        /*LigneProgrammeCMS oeuvreManuelFound = ligneProgrammeCMSDao.findOeuvreManuelByIde12AndCdeUtil(input.getNumProg(), input.getIde12());
        if(oeuvreManuelFound != null) {
            oeuvreManuelFound.setCdeCisac(CDE_CISAC_058);
            oeuvreManuelFound.setCdeFamilTypUtil(programme.getFamille().getCode());
            oeuvreManuelFound.setCdeTypUtil(programme.getTypeUtilisation().getCode());
            oeuvreManuelFound.setAjout(MANUEL);
            oeuvreManuelFound.setNbrDif(input.getNbrDif());
            oeuvreManuelFound.setMt(input.getMt());
            oeuvreManuelFound.setOeuvreManuel(null);
            oeuvreManuelFound.setDateInsertion(new Date());
            oeuvreManuelFound.setCdeTypIde12(input.getCdeTypIde12());
            // oeuvreManuelFound.setSelectionEnCours(true);
        } else {
            List<LigneProgrammeCMS> founds = ligneProgrammeCMSDao.findOeuvresAutoByIde12AndCdeUtil(input.getNumProg(), input.getIde12());
            if(founds != null && !founds.isEmpty()) {
                input.setAjout(CORRIGE);
                LigneProgrammeCMS oeuvreManuel = createOeuvreManuel(input, programme);
                founds.forEach( found -> {
                    found.setOeuvreManuel(oeuvreManuel);
                    found.setSelection(FALSE);
                    ligneProgrammeCMSDao.save(found);
                });

            } else {
                input.setAjout(MANUEL);
                createOeuvreManuel(input, programme);
                *//*if(isEligible(input.getIde12())) {
                    createOeuvreManuel(input, programme);
                }*//*
            }*/
    }

    @Override
    public LigneProgrammeCMS createOeuvreManuel(LigneProgrammeCMS input, Programme programme) {
        Fichier probe = new Fichier();
        probe.setAutomatique(false);
        Programme programme1 = new Programme();
        programme1.setNumProg(input.getNumProg());
        probe.setProgramme(programme1);

        Example<Fichier> of = Example.of(probe);
        Fichier f = fichierDao.findOne(of);

        if(f == null) {
            f = new Fichier();

            f.setProgramme(programme);
            f.setAutomatique(false);

            fichierDao.saveAndFlush(f);
        }

        input.setFichier(f);
        input.setCdeCisac(CDE_CISAC_058);
        input.setCdeFamilTypUtil(programme.getFamille().getCode());
        input.setCdeUtil(CDE_UTIL);
        input.setCdeTypUtil(programme.getTypeUtilisation().getCode());
        if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOFRA.toString())) {
            input.setNbrDif(NBRDIF_SONOFRA);
        }
        if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOANT.toString())) {
            input.setMt(MT_SONOANT);
        }
        /*input.setAjout(MANUEL);*/

        input.setSelection(TRUE);
        input.setDateInsertion(new Date());
        input.setCdeTypIde12(input.getCdeTypIde12());
        input.setSelectionEnCours(TRUE);
        input.setSelection(FALSE);
        //input.setLibelleUtilisateur(sareftjLibutilDao.find);

        return ligneProgrammeCMSDao.saveAndFlush(input);
    }

    @Override
    @Transactional
    public boolean isEligible(Long ide12, String typeCMS){
        boolean result = false;
        CatalogueOctav oeuvreCatalogueOctav = null;
        if(CATALOGUE_OCTAV_TYPE_CMS_ANF.equals(typeCMS)){
            oeuvreCatalogueOctav = catalogueOctavDao.findByIde12(ide12, CATALOGUE_OCTAV_TYPE_CMS_ANF);
        } else {
            oeuvreCatalogueOctav = catalogueOctavDao.findByIde12(ide12, CATALOGUE_OCTAV_TYPE_CMS_FR);
        }
        if (oeuvreCatalogueOctav!=null){
            result = true;
        }
        return result;
    }
}
