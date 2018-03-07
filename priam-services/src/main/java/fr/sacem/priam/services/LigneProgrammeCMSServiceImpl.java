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
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.cms.LigneProgrammeCMSService;
import org.apache.commons.lang3.StringUtils;
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
    public static final String POINTS_MONTANT = "pointsMontant";
    public static final String AJOUT = "ajout";
    public static final String MT = "mt";

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

                String sortProp = sortBy.getProperty();
                if("pointsMontant".equals(sortProp) ||
                        "sum(nbrDifEdit)".equals(sortProp) ||
                        "sum(mtEdit)".equals(sortProp)||
                        "mt".equals(sortProp)){
                    String typeUtilCode = programme.getTypeUtilisation().getCode();
                    Sort.Direction direction = sortBy.getDirection();
                    if (TypeUtilisationEnum.CMS_FRA.getCode().equals(typeUtilCode)) {
                        sort = JpaSort.unsafe(direction, "sum(mtEdit)");
                    }

                    if (TypeUtilisationEnum.CMS_ANT.getCode().equals(typeUtilCode)) {
                        sort = JpaSort.unsafe(direction, "sum(nbrDifEdit)");
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
        Programme prog = programmeDao.findByNumProg(numProg);

        ligneProgrammeCMSDao.updateSelection(numProg, TRUE);
        ligneProgrammeCMSDao.updateSelection(numProg, FALSE);

        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOFRA.getCode())) {
            ligneProgrammeCMSDao.updatePointsMt(numProg);
        } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOANT.getCode())) {
            ligneProgrammeCMSDao.updatePoints(numProg);
        }

        ligneProgrammeCMSDao.flush();
    }

    @Override
    @Transactional
    public void annulerEdition(String numProg) {
        /*List<LigneProgrammeCMS> oeuvresManuelsEnCoursEdition = ligneProgrammeCMSDao.findOeuvresManuelsEnCoursEdition(numProg, FALSE);
        oeuvresManuelsEnCoursEdition.forEach( oeuvreManuel -> deleteOeuvreManuel(oeuvreManuel));*/

        Programme prog = programmeDao.findByNumProg(numProg);

        ligneProgrammeCMSDao.updateSelectionTemporaire(numProg, FALSE);
        ligneProgrammeCMSDao.updateSelectionTemporaire(numProg, TRUE);

        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOFRA.getCode())) {
            ligneProgrammeCMSDao.updatePointsMtTemporaire(numProg, TRUE);
            ligneProgrammeCMSDao.updatePointsMtTemporaire(numProg, FALSE);
        } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOANT.getCode())) {
            ligneProgrammeCMSDao.updateNbrDifTemporaire(numProg, FALSE);
            ligneProgrammeCMSDao.updateNbrDifTemporaire(numProg, TRUE);
        }

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
                    input.setNbrDifEdit(input.getNbrDif());
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

    @Override
    public void modifierPointsTemporaire(String numProg, Set<Map<String, String>> idLingesProgrammes, boolean isSelected) {
        Programme prog = programmeDao.findByNumProg(numProg);

        for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {
                String ajout = obj.get(AJOUT);

                LigneProgrammeCMS inputLigneCMS = createLigneProgrammeCMSFromInput(numProg, obj);
                inputLigneCMS.setSelectionEnCours(isSelected);

                if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOANT.getCode())) {
                    String nbrDifValue = obj.get(POINTS_MONTANT);
                    Long nbrDifEdit = nbrDifValue != null && !nbrDifValue.equals("") ? Long.valueOf(nbrDifValue) : 0L;


                    if(AUTOMATIQUE.equals(ajout)) {
                        List<LigneProgrammeCMS> oeuvresAuto = ligneProgrammeCMSDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneCMS.getIde12());
                        Long sumTotal = oeuvresAuto.stream().mapToLong(lcp -> lcp.getNbrDif()).sum();
                        if(oeuvresAuto != null && !oeuvresAuto.isEmpty()) {
                            if( !nbrDifEdit.equals(sumTotal)) {

                                inputLigneCMS.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());
                                inputLigneCMS.setNbrDifEdit(nbrDifEdit);

                                ajouterOeuvreManuel(inputLigneCMS);
                            }

                        }
                    } else {
                        ligneProgrammeCMSDao.updatePointsTemporaireByNumProgramme(numProg, inputLigneCMS.getIde12(), nbrDifEdit);
                    }
                }
                else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.SONOFRA.getCode())) {
                    String mtValue = obj.get(POINTS_MONTANT);
                    Double mtEdit = mtValue != null && !mtValue.equals("") ? Double.valueOf(mtValue) : 0.0d;

                    if(AUTOMATIQUE.equals(ajout)) {
                        List<LigneProgrammeCMS> oeuvresAuto = ligneProgrammeCMSDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneCMS.getIde12());
                        Double sumTotal = oeuvresAuto.stream().mapToDouble(lcp -> lcp.getMt()).sum();
                        if(oeuvresAuto != null && !oeuvresAuto.isEmpty()) {
                            if( !mtEdit.equals(sumTotal)) {

                                inputLigneCMS.setMtEdit(mtEdit);

                                ajouterOeuvreManuel(inputLigneCMS);
                            }

                        }
                    } else {
                        ligneProgrammeCMSDao.updatePointsMtTemporaireByNumProgramme(numProg, inputLigneCMS.getIde12(), mtEdit);
                    }

                }

            }
        }
    }

    private LigneProgrammeCMS createLigneProgrammeCMSFromInput(String numProg, Map<String, String> input) {
        LigneProgrammeCMS ligneProgrammeCMS = new LigneProgrammeCMS();


        String ide12Value = input.get(IDE_12);
        Long ide12 = StringUtils.isNotEmpty(ide12Value) ? Long.parseLong(ide12Value) : 0L;

        ligneProgrammeCMS.setTitreOeuvre(input.get("titreOeuvre"));
        ligneProgrammeCMS.setRoleParticipant1(input.get("roleParticipant1"));
        ligneProgrammeCMS.setNomParticipant1(input.get("nomParticipant1"));
        ligneProgrammeCMS.setNumProg(numProg);
        ligneProgrammeCMS.setIde12(ide12);
        ligneProgrammeCMS.setNumProg(numProg);




        return ligneProgrammeCMS;
    }
}
