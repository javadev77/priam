package fr.sacem.priam.services;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.JournalJdbcDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPJdbcDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.criteria.LigneProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
import fr.sacem.priam.model.journal.JournalBuilder;
import fr.sacem.priam.model.util.TypeUtilisationPriam;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.api.LigneProgrammeService;
import fr.sacem.priam.services.cp.LigneProgrammeCPService;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import fr.sacem.priam.services.journal.annotation.TypeLog;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nullable;
import javax.persistence.EntityManager;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by jbelwidane on 25/07/2017.
 */
@Service("ligneProgrammeCPService")
public class LigneProgrammeCPServiceImpl implements LigneProgrammeService, LigneProgrammeCPService {

    private static final String OEUVRE_SELECTIONNEE = "Sélectionnée";
    private static final String OEUVRE_DESELECTIONNEE = "Désélectionnée";


    public static final String IDE_12 = "ide12";
    public static final String CDE_UTIL = "libAbrgUtil";
    public static final String CDE_CISAC_058 = "058";
    public static final String MANUEL = "MANUEL";
    public static final String AUTOMATIQUE = "AUTOMATIQUE";
    public static final String CORRIGE = "CORRIGE";
    private static final String SOMME = "SOMME";
    public static final int SELECTION = 1;
    public static final String DUR_DIF = "durDif";
    public static final String AJOUT = "ajout";
    public static final String NBR_DIF = "nbrDif";


    @Autowired
    private LigneProgrammeCPDao ligneProgrammeCPDao;

    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    JournalDao journalDao;

    @Autowired
    FichierService fichierService;

    @Autowired
    LigneProgrammeCPJdbcDao ligneProgrammeCPJdbcDao;

    @Autowired
    private JournalService journalService;

    @Autowired
    JournalJdbcDao journalJdbcDao;

    EntityManager entityManager;
    
    private static final Logger LOG = LoggerFactory.getLogger(LigneProgrammeCPServiceImpl.class);


    @Transactional
    @Override
    public List<KeyValueDto> getListIDE12ByProgramme(Long ide12, String programme) {
        return ligneProgrammeCPDao.findIDE12sByProgramme(ide12, programme);
    }

    @Transactional
    @Override
    public List<KeyValueDto> getTitresByProgramme(String titre, String programme) {
        return ligneProgrammeCPDao.findTitresByProgramme(titre.toUpperCase(), programme);
    }

    @Transactional
    @Override
    public List<String> getUtilisateursByProgramme(String programme) {
        return ligneProgrammeCPDao.findUtilisateursByProgramme(programme);
    }

    @Transactional
    @Override
    public Page<SelectionDto> findLigneProgrammeByCriteria(LigneProgrammeCriteria criteria, Pageable pageable) {
        long before = System.currentTimeMillis();
        Programme programme = programmeDao.findOne(criteria.getNumProg());

        Sort customSort = createCustomSort(pageable, programme);

        PageRequest pageRequest = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), customSort);
        Page<SelectionDto> result = ligneProgrammeCPDao.findLigneProgrammeByCriteria(criteria.getNumProg(),
                criteria.getUtilisateur(),
                criteria.getIde12(),
                criteria.getTitre(),
                criteria.getAjout(),
                criteria.getSelection(), pageRequest);

        /*Iterable<SelectionDto> selectionDtos = Iterables.transform(result, new Function<Object[], SelectionDto>() {
            @Nullable
            @Override
            public SelectionDto apply(@Nullable Object[] objects) {
                SelectionDto selectionDto = new SelectionDto();

                selectionDto.setIde12(((BigInteger) objects[0]).longValue());
                Byte selectionEnCours = (Byte) objects[7];
                selectionDto.setSelection(selectionEnCours.intValue() == 1);
                selectionDto.setCdeUtil((String) objects[9]);
                selectionDto.setLibAbrgUtil((String) objects[8]);

                return selectionDto;
            }
        });
        List<SelectionDto> dtos = Lists.newArrayList(selectionDtos);*/
        long after = System.currentTimeMillis();
        LOG.info(String.format("Execution of findLigneProgrammeByCriteria() takes %s ms", (after-before)));

       // return new PageImpl<>(dtos, pageRequest, result.getTotalElements());
        return result;
    }

    protected Sort createCustomSort(Pageable pageable, Programme programme) {

        Sort sort = pageable.getSort();

        if(sort == null)
            return sort;

        Sort.Order sortBy = sort.iterator().next();

        if("sum(nbrDifEdit)".equals(sortBy.getProperty()) ||
                "nbrDifEdit".equals(sortBy.getProperty()) ||
                "sum(durDif)".equals(sortBy.getProperty()) ||
                "durDifEdit".equals(sortBy.getProperty()) ||
                "durDif".equals(sortBy.getProperty())) {
            if (TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(programme.getTypeUtilisation().getCode())) {
                sort = JpaSort.unsafe(sortBy.getDirection(), "sum(nbrDifEdit)");
            } else if (TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(programme.getTypeUtilisation().getCode())) {
                sort = JpaSort.unsafe(sortBy.getDirection(), "sum(durDifEdit)");
            }
        } else if("libAbrgUtil".equals(sortBy.getProperty())) {
            sort = JpaSort.unsafe(sortBy.getDirection(), "cdeUtil");
        }

        return sort;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void selectLigneProgramme(String numProg, Set<Map<String, String>> idLingesProgrammes) {
        List<LigneProgrammeCP> records = populateRecords(numProg, idLingesProgrammes, TRUE);

        ligneProgrammeCPJdbcDao.batchpdateSelectionTemporaireByNumProgramme(records);
    }

    private List<LigneProgrammeCP> populateRecords(String numProg, Set<Map<String, String>> idLingesProgrammes, Boolean isSelection) {
        List<LigneProgrammeCP> records = new ArrayList<>();
        for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {

                Long ide12 = Long.parseLong(obj.get(IDE_12));
                String libUtil = obj.get(CDE_UTIL);
                String cdeUtil = libUtil !=null ? libUtil.split(" - ")[0] : "";

                //ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, ide12, cdeUtil, 1);
                LigneProgrammeCP ligneProgrammeCP = new LigneProgrammeCP();
                ligneProgrammeCP.setNumProg(numProg);
                ligneProgrammeCP.setIde12(ide12);
                ligneProgrammeCP.setCdeUtil(cdeUtil);
                ligneProgrammeCP.setSelectionEnCours(isSelection);

                records.add(ligneProgrammeCP);
            }
        }

        return records;
    }

    @Transactional
    @Override
    public void modifierDurOrNbrDifTemporaire(String numProg, Set<Map<String, String>> selected, Set<Map<String, String>> unselected, UserDTO userDTO) {
        Programme prog = programmeDao.findByNumProg(numProg);

        //List<LigneProgrammeCP> ligneProgrammeCPs = populateRecords(numProg, idLingesProgrammes, isSelected);

         List<LigneProgrammeCP> workingList = new ArrayList<>();
        List<Journal> journaux = new ArrayList<>();

        prepareInputList(selected, prog, workingList, TRUE);
        prepareInputList(unselected, prog, workingList, FALSE);

        workingList.forEach(lcp -> {
            String ajout = lcp.getAjout();
            if(AUTOMATIQUE.equals(ajout)) {
                List<LigneProgrammeCP> oeuvresAuto = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(numProg, lcp.getIde12(), lcp.getCdeUtil());

                if(!oeuvresAuto.isEmpty()) {
                    if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode())) {
                        Long sumTotal = sumOfNbrDif(oeuvresAuto);
                        if( !lcp.getNbrDifEdit().equals(sumTotal)) {
                            lcp.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());
                            ajouterOeuvreManuel(lcp, userDTO);
                        }
                    }  else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode())) {
                        Long sumTotal = sumOfDurDif(oeuvresAuto);
                        if( !lcp.getDurDifEdit().equals(sumTotal)) {
                            lcp.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());
                            ajouterOeuvreManuel(lcp, userDTO);
                        }
                    }
                }
            } else if(CORRIGE.equals(ajout)) {
                correctionOeuvreCorrige(numProg, lcp, userDTO, journaux);
            } else {

                //ligneProgrammeCPDao.updateNbrDifTemporaireByNumProgramme(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), nbrDifEdit);
                //oeuvresToUpdate.add(createEntityForUpdate(numProg, lcp.getIde12(), lcp.getCdeUtil(), lcp.getNbrDifEdit()));
            }


        });


        /*for (Map<String, String>  obj:  idLingesProgrammes) {
            if (obj != null && !obj.isEmpty()) {
                String ajout = obj.get("ajout");

                LigneProgrammeCP inputLigneCP = createLigneProgrammeCPFromInput(prog, obj, isSelected);


                if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode())) {
                    String nbrDifValue = obj.get(NBR_DIF);
                    Long nbrDifEdit = nbrDifValue != null && !nbrDifValue.equals("") ? Long.valueOf(nbrDifValue) : 0L;
                    inputLigneCP.setNbrDifEdit(nbrDifEdit);
                    if(AUTOMATIQUE.equals(ajout)) {

                        List<LigneProgrammeCP> oeuvresAuto = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil());

                        if(!oeuvresAuto.isEmpty()) {
                            Long sumTotal = sumOfNbrDif(oeuvresAuto);
                            if( !nbrDifEdit.equals(sumTotal)) {

                                inputLigneCP.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());

                                ajouterOeuvreManuel(inputLigneCP, userDTO);
                            }
                        }
                    } else if(CORRIGE.equals(ajout)) {
                        correctionOeuvreCorrige(numProg, inputLigneCP, userDTO, oeuvresToUpdate);
                    } else {

                        //ligneProgrammeCPDao.updateNbrDifTemporaireByNumProgramme(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), nbrDifEdit);
                        oeuvresToUpdate.add(createEntityForUpdate(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), nbrDifEdit));
                    }
                }
                else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode())) {
                    String durDifStringValue = obj.get(DUR_DIF);
                    Long durDifEdit = durDifStringValue != null && !durDifStringValue.equals("") ? Long.valueOf(durDifStringValue) : 0L;
                    inputLigneCP.setDurDifEdit(durDifEdit);
                    if(AUTOMATIQUE.equals(ajout)) {
                        List<LigneProgrammeCP> oeuvresAuto = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil());

                        if (!oeuvresAuto.isEmpty()) {
                            Long sumTotal = sumOfDurDif(oeuvresAuto);
                            if (!durDifEdit.equals(sumTotal)) {


                                inputLigneCP.setCdeTypIde12(oeuvresAuto.get(0).getCdeTypIde12());

                                ajouterOeuvreManuel(inputLigneCP, userDTO);
                            }

                        }
                    } else if(CORRIGE.equals(ajout)){
                        correctionOeuvreCorrige(numProg, inputLigneCP, userDTO, oeuvresToUpdate);
                    } else {
                        ligneProgrammeCPDao.updateDurDifTemporaireByNumProgramme(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), durDifEdit);
                    }

                }

            }
        }*/


        List<LigneProgrammeCP> collected = workingList.stream().filter(p -> !p.getAjout().equals(AUTOMATIQUE)).collect(Collectors.toList());
        collected.forEach(lp -> {
            if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode())) {
               // ligneProgrammeCPJdbcDao.batchUpdateNbrDifTemporaireByNumProgramme(collected);
                ligneProgrammeCPDao.updateNbrDifTemporaireByNumProgramme(numProg, lp.getIde12(), lp.getCdeUtil(), lp.getNbrDifEdit(), lp.isSelectionEnCours());
            }  else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode())) {
                ligneProgrammeCPDao.updateDurDifTemporaireByNumProgramme(numProg, lp.getIde12(), lp.getCdeUtil(), lp.getDurDifEdit(),  lp.isSelectionEnCours());
                //ligneProgrammeCPJdbcDao.updateDurDifTemporaireByNumProgramme(collected);
            }
        });

        ligneProgrammeCPDao.flush();


        journalJdbcDao.bathInsertJournal(journaux);
    }

    private void prepareInputList(Set<Map<String, String>> input, Programme prog, List<LigneProgrammeCP> workingList, Boolean aTrue) {
        for (Map<String, String> obj : input) {
            if (obj != null && !obj.isEmpty()) {

                LigneProgrammeCP inputLigneCP = createLigneProgrammeCPFromInput(prog, obj, aTrue);
                workingList.add(inputLigneCP);
            }
        }
    }

    public <T extends LigneProgrammeCP> Collection<T> bulkSave(Collection<T> entities) {
        final List<T> savedEntities = new ArrayList<>(entities.size());
        int i = 0;
        for (T t : entities) {
            savedEntities.add(persistOrMerge(t));
            i++;
            if (i % 50 == 0) {
                // Flush a batch of inserts and release memory.
                entityManager.flush();
                entityManager.clear();
            }
        }
        return savedEntities;
    }

    private <T extends LigneProgrammeCP> T persistOrMerge(T t) {
        if (t.getId() == null) {
            entityManager.persist(t);
            return t;
        } else {
            return entityManager.merge(t);
        }
    }

    private LigneProgrammeCP createEntityForUpdate(String numProg, Long ide12, String cdeUtil, Long nbrDifEdit) {
        LigneProgrammeCP entity = new LigneProgrammeCP();

        entity.setNumProg(numProg);
        entity.setIde12(ide12);
        entity.setCdeUtil(cdeUtil);
        entity.setNbrDifEdit(nbrDifEdit);

        return entity;

    }

    private void correctionOeuvreCorrige(String numProg, LigneProgrammeCP inputLigneCP, UserDTO userDTO, List<Journal> journaux) {
        Programme programme = programmeDao.findByNumProg(numProg);
        List<LigneProgrammeCP> oeuvresAutoLinkCorrige = ligneProgrammeCPDao.findOeuvresAutoLinkCorrigeByIde12AndCdeUtil(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil());
        Long sumTotal;

        LigneProgrammeCP currentOeuvreCorrige = ligneProgrammeCPDao.findOeuvreCorrigeByIde12AndCdeUtil(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil());
        if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode())){
            if(!oeuvresAutoLinkCorrige.isEmpty()) {
                sumTotal = sumOfNbrDif(oeuvresAutoLinkCorrige);
                Long inputNbrDifEdit = inputLigneCP.getNbrDifEdit();
                if(!inputNbrDifEdit.equals(currentOeuvreCorrige.getNbrDifEdit())) {
                    Journal journal = saveJournal(inputLigneCP, userDTO);
                    SituationApres situationApres = new SituationApres();
                    situationApres.setSituation(getNbrDifOrDurDifAsString(programme, inputLigneCP));
                    journal.getListSituationApres().add(situationApres);

                    if( !inputNbrDifEdit.equals(sumTotal)) {

                        //ligneProgrammeCPDao.updateNbrDifTemporaireByNumProgramme(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), inputNbrDifEdit);
                        //LigneProgrammeCP entityForUpdate = createEntityForUpdate(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), inputNbrDifEdit);
                        //currentOeuvreCorrige.setNbrDifEdit(inputNbrDifEdit);
                        //oeuvresToUpdate.add(entityForUpdate);
                        inputLigneCP.setNbrDifEdit(inputNbrDifEdit);


                    } else {

                        deleteOeuvreCorrigeLinkAuto(numProg, inputLigneCP);
                    }

                    SituationAvant situationAvant = new SituationAvant();
                    situationAvant.setSituation(getNbrDifOrDurDifAsString(programme, currentOeuvreCorrige));
                    journal.getListSituationAvant().add(situationAvant);
                   // journalDao.save(journal);
                    journaux.add(journal);
                }

            }
        } else if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode())){
            if(!oeuvresAutoLinkCorrige.isEmpty()) {
                sumTotal = sumOfDurDif(oeuvresAutoLinkCorrige);
                Long inputDurDifEdit = inputLigneCP.getDurDifEdit();
                if(!inputDurDifEdit.equals(currentOeuvreCorrige.getDurDifEdit())) {
                    Journal journal = saveJournal(inputLigneCP, userDTO);
                    SituationApres situationApres = new SituationApres();
                    situationApres.setSituation(getNbrDifOrDurDifAsString(programme, inputLigneCP));
                    journal.getListSituationApres().add(situationApres);

                    if( !inputDurDifEdit.equals(sumTotal)) {

                        //ligneProgrammeCPDao.updateDurDifTemporaireByNumProgramme(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), inputDurDifEdit);
                       // LigneProgrammeCP entityForUpdate = createEntityForUpdate(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil(), inputDurDifEdit);
                       // oeuvresToUpdate.add(entityForUpdate);

                        inputLigneCP.setDurDifEdit(inputDurDifEdit);

                    } else {
                        deleteOeuvreCorrigeLinkAuto(numProg, inputLigneCP);
                    }

                    SituationAvant situationAvant = new SituationAvant();
                    situationAvant.setSituation(getNbrDifOrDurDifAsString(programme, currentOeuvreCorrige));
                    journal.getListSituationAvant().add(situationAvant);

                    journaux.add(journal);

                }

            }
        }
    }

    private void deleteOeuvreCorrigeLinkAuto(String numProg, LigneProgrammeCP inputLigneCP) {
        LigneProgrammeCP oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreCorrigeByIde12AndCdeUtil(numProg, inputLigneCP.getIde12(), inputLigneCP.getCdeUtil());
        doDeleteOeuvreManuel(oeuvreManuelFound);
    }

    private long sumOfDurDif(List<LigneProgrammeCP> oeuvresAuto) {
        return oeuvresAuto.stream().mapToLong(lcp -> lcp.getDurDif()).sum();
    }

    private long sumOfNbrDif(List<LigneProgrammeCP> oeuvresAuto) {
        return oeuvresAuto.stream().mapToLong(LigneProgrammeCP::getNbrDif).sum();
    }

    private LigneProgrammeCP createLigneProgrammeCPFromInput(Programme prog, Map<String, String> input, boolean isSelected) {
        LigneProgrammeCP ligneProgrammeCP = new LigneProgrammeCP();


        String ide12Value = input.get(IDE_12);
        Long ide12 = StringUtils.isNotEmpty(ide12Value) ? Long.parseLong(ide12Value) : 0L;
        String libUtil = input.get(CDE_UTIL);
        String cdeUtil = StringUtils.isNotEmpty(libUtil)  ? libUtil.split(" - ")[0] : "";

        ligneProgrammeCP.setTitreOeuvre(input.get("titreOeuvre"));
        ligneProgrammeCP.setRoleParticipant1(input.get("roleParticipant1"));
        ligneProgrammeCP.setNomParticipant1(input.get("nomParticipant1"));
        ligneProgrammeCP.setNumProg(prog.getNumProg());
        ligneProgrammeCP.setIde12(ide12);
        ligneProgrammeCP.setCdeUtil(cdeUtil);
        ligneProgrammeCP.setLibelleUtilisateur(libUtil);
        ligneProgrammeCP.setSelectionEnCours(isSelected);
        ligneProgrammeCP.setAjout(input.get("ajout"));

        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode())){
            String nbrDifValue = input.get(NBR_DIF);
            Long nbrDifEdit = nbrDifValue != null &&!nbrDifValue.equals("") ? Long.valueOf(nbrDifValue): 0L;
            ligneProgrammeCP.setNbrDifEdit(nbrDifEdit);
        }
        else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode())) {
            String durDifStringValue = input.get(DUR_DIF);
            Long durDifEdit = durDifStringValue != null && !durDifStringValue.equals("") ? Long.valueOf(durDifStringValue) : 0L;
            ligneProgrammeCP.setDurDifEdit(durDifEdit);
        }


        return ligneProgrammeCP;
    }


    @Transactional
    @Override
    public void supprimerLigneProgramme(String numProg, Long ide12, SelectionDto selectedLigneProgramme) {
    
        String cdeUtil = selectedLigneProgramme.getCdeUtil();
        LigneProgrammeCP oeuvreManuelFound = new LigneProgrammeCP();
        if(selectedLigneProgramme.getAjout().equals(MANUEL)){
            oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreManuelByIde12AndCdeUtil(numProg, ide12, cdeUtil);
        } else if(selectedLigneProgramme.getAjout().equals(CORRIGE)){
            oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreCorrigeByIde12AndCdeUtil(numProg, ide12, cdeUtil);
        }
        doDeleteOeuvreManuel(oeuvreManuelFound);
    }

    private void doDeleteOeuvreManuel(LigneProgrammeCP oeuvreManuelFound) {
        if(oeuvreManuelFound != null) {
    
            List<LigneProgrammeCP> oeuvresAutoByIdOeuvreManuel = ligneProgrammeCPDao.findOeuvresAutoByIdOeuvreManuel(oeuvreManuelFound.getId());
            oeuvresAutoByIdOeuvreManuel.forEach( oeuvreAuto -> {

                oeuvreAuto.setSelectionEnCours(TRUE);
                oeuvreAuto.setOeuvreManuel(null);
                oeuvreAuto.setDurDifEdit(oeuvreAuto.getDurDif());
                oeuvreAuto.setNbrDifEdit(oeuvreAuto.getNbrDif());

                ligneProgrammeCPDao.saveAndFlush(oeuvreAuto);
            });
            ligneProgrammeCPDao.delete(oeuvreManuelFound);
            ligneProgrammeCPDao.flush();
        }
    }
    
    
    @Transactional
    @Override
    public void ajouterOeuvreManuel(LigneProgrammeCP input, UserDTO userDTO) {
        Programme programme = programmeDao.findOne(input.getNumProg());

        Journal journal = createJournal(input);
        journal.setUtilisateur(userDTO.getUserId());
        SituationAvant situationAvant = new SituationAvant();
        SituationApres situationApres = new SituationApres();

        situationApres.setSituation(getNbrDifOrDurDifAsString(programme, input));

        List<LigneProgrammeCP> founds = ligneProgrammeCPDao.findOeuvresAutoByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
        if(founds != null && !founds.isEmpty()) {
            if(!isBackToEtatAuto(programme, input, founds)){
                input.setAjout(CORRIGE);

                LigneProgrammeCP oeuvreManuel = createOeuvreManuel(input, programme);
                founds.forEach( found -> {
                    found.setOeuvreManuel(oeuvreManuel);
                    ligneProgrammeCPDao.saveAndFlush(found);
                });

                //ligneProgrammeCPJdbcDao.batchUpdateOeuvreAuto(founds);
            }
            SareftrTyputil typeUtilisation = programme.getTypeUtilisation();
            if(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(typeUtilisation.getCode())) {
                situationAvant.setSituation(String.valueOf(sumOfNbrDif(founds)));
            } else if(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(typeUtilisation.getCode())) {
                situationAvant.setSituation(String.valueOf(sumOfDurDif(founds)));
            }

            journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());

        } else {
            LigneProgrammeCP oeuvreCorrigeFound = ligneProgrammeCPDao.findOeuvreCorrigeByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
            List<LigneProgrammeCP> oeuvresAutoLinkCorrige = ligneProgrammeCPDao.findOeuvresAutoLinkCorrigeByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
            if(oeuvreCorrigeFound != null) {
                situationAvant.setSituation(getNbrDifOrDurDifAsString(programme, oeuvreCorrigeFound));
                journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());
                if(!isBackToEtatAuto(programme, input, oeuvresAutoLinkCorrige)){
                    oeuvreCorrigeFound.setAjout(CORRIGE);
                    updateOeuvreManuelOuCorrige(input, programme, oeuvreCorrigeFound);
                } else {
                    deleteOeuvreCorrigeLinkAuto(input.getNumProg(), input);
                }
            } else {
                LigneProgrammeCP oeuvreManuelFound = ligneProgrammeCPDao.findOeuvreManuelByIde12AndCdeUtil(input.getNumProg(), input.getIde12(), input.getCdeUtil());
                if(oeuvreManuelFound != null) {
                    oeuvreManuelFound.setAjout(MANUEL);
                    situationAvant.setSituation(getNbrDifOrDurDifAsString(programme, oeuvreManuelFound));
                    updateOeuvreManuelOuCorrige(input, programme, oeuvreManuelFound);


                    journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());
                } else {
                    input.setAjout(MANUEL);
                    input.setDurDifEdit(input.getDurDif());
                    input.setNbrDifEdit(input.getNbrDif());

                    createOeuvreManuel(input, programme);

                    situationAvant.setSituation("0");
                    journal.setEvenement(TypeLog.AJOUT_OEUVRE.getEvenement());

                }
            }
        }

        journal.getListSituationAvant().add(situationAvant);
        journal.getListSituationApres().add(situationApres);

        if(LOG.isInfoEnabled()) {
            LOG.info(journal.toString());
        }
        //journalDao.saveAndFlush(journal);
        List<Journal> journaux = new ArrayList<>();
        journaux.add(journal);
        journalJdbcDao.bathInsertJournal(journaux);
        
    }

    private Journal createJournal(LigneProgrammeCP input) {
        JournalBuilder jb = new JournalBuilder(input.getNumProg(), input.getIde12(), input.getUtilisateur());

        return jb.build();
    }

    private String getNbrDifOrDurDifAsString(Programme prog, LigneProgrammeCP input) {
        SareftrTyputil typeUtilisation = prog.getTypeUtilisation();
        if(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(typeUtilisation.getCode())) {
            return String.valueOf(input.getNbrDifEdit());
            //return String.valueOf(input.getNbrDif()!= null ? input.getNbrDif() : input.getNbrDifEdit());
        } else if(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(typeUtilisation.getCode())) {
            return  String.valueOf(input.getDurDifEdit());
            //return  String.valueOf(input.getDurDif()!= null ? input.getDurDif() : input.getDurDifEdit());
        }
        return "";
    }

    private void updateOeuvreManuelOuCorrige(LigneProgrammeCP input, Programme programme, LigneProgrammeCP oeuvreToMdify) {

        oeuvreToMdify.setCdeCisac(CDE_CISAC_058);
        oeuvreToMdify.setCdeFamilTypUtil(programme.getFamille().getCode());
        oeuvreToMdify.setCdeTypUtil(programme.getTypeUtilisation().getCode());
        oeuvreToMdify.setOeuvreManuel(null);

        oeuvreToMdify.setDurDifEdit(input.getDurDifEdit());

        oeuvreToMdify.setNbrDifEdit(input.getNbrDifEdit());
        oeuvreToMdify.setDateInsertion(new Date());
        oeuvreToMdify.setUtilisateur(input.getUtilisateur());
        oeuvreToMdify.setCdeTypIde12(input.getCdeTypIde12());

        ligneProgrammeCPDao.saveAndFlush(oeuvreToMdify);
    }

    private LigneProgrammeCP createOeuvreManuel(LigneProgrammeCP input, Programme programme) {

        input.setFichier(fichierService.getOrCreateFichierLink(input.getNumProg()));
        input.setCdeCisac(CDE_CISAC_058);
        input.setCdeFamilTypUtil(programme.getFamille().getCode());
        input.setCdeTypUtil(programme.getTypeUtilisation().getCode());

        input.setDateInsertion(new Date());

        //input.setSelectionEnCours(TRUE);
        //input.setSelection(TRUE);
        
        return ligneProgrammeCPDao.saveAndFlush(input);
    }
    
    @Transactional @Override
    public void deselectLigneProgramme(String numProg, Set<Map<String, String>> unselected) {
        /*for (Map<String, String>  obj:  unselected) {
            if(obj != null && !obj.isEmpty()) {
                ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg,
                        Long.parseLong(obj.get(IDE_12)),
                        obj.get(CDE_UTIL).split(" - ")[0],
                        0);
            }
        }*/

        List<LigneProgrammeCP> records = populateRecords(numProg, unselected, FALSE);

        ligneProgrammeCPJdbcDao.batchpdateSelectionTemporaireByNumProgramme(records);
    }

    @Override
    public void enregistrerEdition(String numProg) {

    }


    @Transactional
    @Override
    public void enregistrerEdition(ValdierSelectionProgrammeInput input, UserDTO userDTO) {
        String numProg = input.getNumProg();
        //this.modifierDurOrNbrDifTemporaire(numProg, input.getSelected(), input.getUnselected(), userDTO);
        //journalService.logJournalOeuvre(input, userDTO, TypeLog.SELECTION);

        Programme prog = programmeDao.findByNumProg(numProg);

        ligneProgrammeCPDao.updateSelection(numProg, TRUE);
        ligneProgrammeCPDao.updateSelection(numProg, FALSE);

        /*List<LigneProgrammeCP> ligneProgrammeSelectionChanged = ligneProgrammeCPDao.findLigneProgrammeSelectionChanged(numProg);
        if(ligneProgrammeSelectionChanged != null && !ligneProgrammeSelectionChanged.isEmpty()) {
            List<LigneProgrammeCP> records = new ArrayList<>();
            ligneProgrammeSelectionChanged.forEach( l -> {
                LigneProgrammeCP ligneProgrammeCP = new LigneProgrammeCP();
                ligneProgrammeCP.setId(l.getId());
                ligneProgrammeCP.setSelection(l.isSelectionEnCours());
                records.add(ligneProgrammeCP);

            });

            ligneProgrammeCPJdbcDao.batchUpdateSelection(records);
        }*/

        /*List<Long> ligneProgrammeNbrDifChanged = ligneProgrammeCPDao.findLigneProgrammeNbrDifChanged(numProg);
        if(ligneProgrammeNbrDifChanged != null && !ligneProgrammeNbrDifChanged.isEmpty()) {
            ligneProgrammeCPJdbcDao.updateNbrDif(ligneProgrammeNbrDifChanged);
        }*/


        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode())) {
            ligneProgrammeCPDao.updateDurDif(numProg);
        } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode())) {
            ligneProgrammeCPDao.updateNbrDif(numProg);
        }

        //ligneProgrammeCPDao.flush();

    }
    
    @Transactional
    @Override
    public void annulerEdition(String numProg, String utilisateur) {
        Programme prog = programmeDao.findByNumProg(numProg);

        List<LigneProgrammeCP> oeuvresManuelsEnCoursEdition = ligneProgrammeCPDao.findOeuvresManuelsEnCoursEdition(numProg);
        oeuvresManuelsEnCoursEdition.forEach( oeuvreManuel -> doDeleteOeuvreManuel(oeuvreManuel));

        ligneProgrammeCPDao.updateSelectionTemporaire(numProg, FALSE);
        ligneProgrammeCPDao.updateSelectionTemporaire(numProg, TRUE);

        if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode())) {
            ligneProgrammeCPDao.updateDurDifTemporaire(numProg, FALSE);
            ligneProgrammeCPDao.updateDurDifTemporaire(numProg, TRUE);
        } else if(prog.getTypeUtilisation().getCode().equals(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode())) {
            ligneProgrammeCPDao.updateNbrDifTemporaire(numProg, FALSE);
            ligneProgrammeCPDao.updateNbrDifTemporaire(numProg, TRUE);
        }

        ligneProgrammeCPDao.flush();
    }


    @Transactional
    @Override
    public void annulerSelection(String numProg, String utilisateur) {
        List<LigneProgrammeCP> allOeuvresManuelsByNumProg = ligneProgrammeCPDao.findAllOeuvresManuelsByNumProg(numProg);

        allOeuvresManuelsByNumProg.forEach(this::doDeleteOeuvreManuel);


        //allOeuvresManuelsByNumProg.forEach( oeuvreManuel -> doDeleteOeuvreManuel(oeuvreManuel, utilisateur));

        ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, TRUE);
        ligneProgrammeCPDao.updateSelection(numProg, TRUE);
        
        ligneProgrammeCPDao.flush();
    }

    @Override
    public Map<String, Long> getDurDifProgramme(String numProg, String statut){

        Map<String, Long> result = new HashMap<>();
        result.put(AUTOMATIQUE, 0L);
        result.put(CORRIGE, 0L);
        result.put(MANUEL, 0L);
        result.put(SOMME, 0L);

        Programme programme = programmeDao.findOne(numProg);

        long start = System.currentTimeMillis();
        List<Map<String, Long>> prog = ligneProgrammeCPJdbcDao.compterOuvres(numProg);
//        Long auto = ligneProgrammeCPDao.compterOuvres(numProg, AUTOMATIQUE);
//        Long corrige = ligneProgrammeCPDao.compterOuvres(numProg, CORRIGE);
//        Long manuel = ligneProgrammeCPDao.compterOuvres(numProg, MANUEL);
        long end = System.currentTimeMillis();
        LOG.info("count takes in ms =" + (end-start));

//        result.put(CORRIGE, corrige);
//        result.put(MANUEL, manuel);
//        result.put(AUTOMATIQUE, auto);
        for (Map<String, Long> indicateur : prog) {
            for(String key : indicateur.keySet()) {
                result.put(key, indicateur.get(key));
            }

        }

        if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(programme.getTypeUtilisation().getCode())) {
            result.put(SOMME, ligneProgrammeCPJdbcDao.calculerQuantiteOeuvres(numProg));
        } else if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(programme.getTypeUtilisation().getCode())) {
            //result.put(SOMME, ligneProgrammeCPJdbcDao.calculerDureeOeuvres(numProg));
        }

        return result;
    }

    private boolean isBackToEtatAuto(Programme programme, LigneProgrammeCP ligneProgrammeCP, List<LigneProgrammeCP> oeuvresAutoLink){
        boolean result = false;
        if(programme.getTypeUtilisation().getCode().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode())){
            if(ligneProgrammeCP.getNbrDif() != null){
                if (ligneProgrammeCP.getNbrDif().equals(sumOfNbrDif(oeuvresAutoLink))) {
                    result = true;
                }
            } else {
                if(ligneProgrammeCP.getNbrDifEdit().equals(sumOfNbrDif(oeuvresAutoLink))){
                    result = true;
                }
            }
        } else {
            if(ligneProgrammeCP.getDurDif() != null){
                if(ligneProgrammeCP.getDurDif().equals(sumOfDurDif(oeuvresAutoLink))){
                    result = true;
                }
            } else {
                if(ligneProgrammeCP.getDurDifEdit().equals(sumOfDurDif(oeuvresAutoLink))){
                    result = true;
                }
            }
        }
        return result;
    }


    private Journal saveJournal(LigneProgrammeCP ligneProgrammeCP, UserDTO userDTO){
        Journal journal = createJournal(ligneProgrammeCP);
        journal.setUtilisateur(userDTO.getUserId());
        journal.setEvenement(TypeLog.MODIFIER_OEUVRE.getEvenement());
        return journal;
    }
}
