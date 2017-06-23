package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.ParamAppliDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.ProgrammeViewDao;

import fr.sacem.priam.model.domain.*;

import fr.sacem.priam.model.domain.ParamAppli;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.ProgrammeSequence;
import fr.sacem.priam.model.domain.StatutProgramme;

import fr.sacem.priam.model.domain.criteria.ProgrammeCriteria;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.util.MapperConfiguration;
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
        LocalDate today = LocalDate.now();
        if(Long.getLong(paramAppli.getVal())<today.getYear()){
         //update la sequence
            //appdate la table paramAppli
        }
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
    
    @Transactional
	public Programme updateProgramme(ProgrammeDto programmeDto) {
        Programme programme = programmeDao.findOne(programmeDto.getNumProg());
    
        programme.setNom(programmeDto.getNom());
        
        Famille famille = new Famille();
        famille.setCode(programmeDto.getFamille());
        programme.setFamille(famille);
    
        TypeUtilisation typeUtilisation = new TypeUtilisation();
        typeUtilisation.setCode(programmeDto.getTypeUtilisation());
        typeUtilisation.setCodeFamille(programmeDto.getFamille());
        programme.setTypeUtilisation(typeUtilisation);
        
        Rion rion = new Rion();
        rion.setRion(programmeDto.getRionTheorique());
        programme.setRionTheorique(rion);
        
        programme.setTypeRepart(programmeDto.getTypeRepart());
        programme.setDateModfication(new Date());
        
        return programmeDao.save(programme);
        
	}
}
