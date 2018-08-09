package fr.sacem.priam.services.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.util.FamillePriam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AffectationUtil {

    @Autowired
    private FichierDao fichierDao;

    @Autowired
    private ProgrammeDao programmeDao;

    public List<Fichier> getFichiersAffectes(AffectationDto affectationDto){
        List<Fichier> fichiersAffectes = new ArrayList<>();

        Set<Long> fichiersAvantAffectation = new HashSet<>(affectationDto.getFichersAvantAffectation());
        Set<Long> fichiersSelectionnes = affectationDto.getFichiers().stream().map(Fichier::getId).collect(Collectors.toSet());
        Set<Long> fichiersDeselectionnes = affectationDto.getFichiersUnChecked().stream().map(Fichier::getId).collect(Collectors.toSet());

        Sets.SetView<Long> difference = Sets.difference(fichiersAvantAffectation, fichiersDeselectionnes);
        Sets.SetView<Long> result = Sets.union(difference, fichiersSelectionnes);

        /*Programme programme = programmeDao.findByNumProg(affectationDto.getNumProg());

        List<Fichier> listFichierDejaAffecte = new ArrayList<>();
        if(programme.getFamille().getCode().equals(FamillePriam.CMS.getCode())){
            affectationDto.getFichersAvantAffectation()
                    .forEach(id -> listFichierDejaAffecte.add(fichierDao.findOne(id)));
        } else {
            listFichierDejaAffecte.addAll(fichierDao.findFichiersByIdProgramme(programme.getNumProg(), Status.AFFECTE));
        }
        result.addAll(listFichierDejaAffecte);


        List<Fichier> listFichierDto = new ArrayList<>();
        affectationDto.getFichiers().forEach(fichier -> listFichierDto.add(fichierDao.findOne(fichier.getId())));


        List<Fichier> listNouveauFichierAffecte = listFichierDto.stream().filter(elem -> !listFichierDejaAffecte.contains(elem)).collect(Collectors.toList());
        result.addAll(listNouveauFichierAffecte);

        if(listFichierDejaAffecte.size() > listFichierDto.size()){
            List<Fichier> listFichierDesaffecte = listFichierDejaAffecte.stream().filter(elem -> !listFichierDto.contains(elem)).collect(Collectors.toList());
            result.removeAll(listFichierDesaffecte);
        }*/
        result.immutableCopy().forEach(idFichier -> fichiersAffectes.add(fichierDao.findOne(idFichier)));

        return fichiersAffectes;
    }

}
