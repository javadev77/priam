package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.ParamAppliDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeSequnceDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.util.MapperConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 07/06/2017.
 */
@Component
public class ProgrammeService {
    
    @Autowired
    ProgrammeViewDao programmeViewDao;
    @Autowired
    ProgrammeDao programmeDao;
    @Autowired
    ParamAppliDao paramAppliDao;
    @Autowired
    ProgrammeSequnceDao programmeSequnceDao;
    private static final Logger LOG = LoggerFactory.getLogger(ProgrammeService.class);

    @Transactional
    public Page<ProgrammeDto> findProgrammeByCriteria(ProgrammeCriteria criteria, Pageable pageable) {
        
        Page<ProgrammeDto> pageProgramme = programmeViewDao.findAllProgrammeByCriteria(criteria.getNumProg(), criteria.getNom(),
                criteria.getStatut(), criteria.getDateCreationDebut(), criteria.getDateCreationFin(), criteria.getFamille(),
                criteria.getTypeUtilisation(), criteria.getRionTheorique(), criteria.getRionPaiement(), criteria.getTypeRepart(),
                pageable);
        
        return pageProgramme;
    }
    @Transactional
    public Programme addProgramme(ProgrammeDto programmeDto){
        MapperConfiguration mapperConfiguration =new MapperConfiguration();
        ParamAppli paramAppli= paramAppliDao.findOne(1l);
        ProgrammeSequence programmeSequence = new ProgrammeSequence();
        ProgrammeKey programmeKey =new ProgrammeKey();
        LocalDate today = LocalDate.now();
        String year=String.valueOf(today.getYear()).substring(2,4);
        String max_value=programmeSequnceDao.getLastElement(year);
        //cas de la base vide
        if(max_value==null)
            max_value="0";
        Long max_value_to_add=Long.valueOf(max_value)+1;
        String formated_max_value_to_add =StringUtils.leftPad(max_value_to_add.toString(),4,"0");
        programmeKey.setAnnee(year);
        programmeKey.setCodeSequence(max_value_to_add);
        programmeKey.setPrefix("PR");
        programmeSequence.setProgrammeKey(programmeKey);
        programmeSequnceDao.save(programmeSequence);
        programmeDto.setNumProg(programmeKey.getPrefix()+programmeKey.getAnnee()+formated_max_value_to_add);
       // if(Long.getLong(paramAppli.getVal())<today.getYear()){
         //update la sequence
            //appdate la table paramAppli
       // }
        //ajouter une ligne dans la table ProgrammeSequence
        //recuperer la derniere ligne ajouter dans la tavle Programme Sequence
        //construire le Id du programme
        Programme mappedProgramme=mapperConfiguration.convertProgrammeDtoToProgramme(programmeDto);
        mappedProgramme.setDateCreation(new Date());
        mappedProgramme.setStatut(StatutProgramme.CREE);
        if(mappedProgramme !=null) {
            mappedProgramme=programmeDao.save(mappedProgramme);
        }
        return mappedProgramme;
    }
    @Transactional
    public List<Programme> serachProgrammeByNom(String nom){
        List<Programme> resultat= new ArrayList<>();
        if(nom!=null && !nom.equals("")){
            Programme programmeSearcher =new Programme();
            programmeSearcher.setNom(nom);
            Example example = Example.of(programmeSearcher);
            resultat= programmeDao.findAll(example);
        }
        else{
            LOG.info("Nom programme vide ou null");
        }
        return resultat;
    }

}
