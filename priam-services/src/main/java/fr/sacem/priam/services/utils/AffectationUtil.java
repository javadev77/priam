package fr.sacem.priam.services.utils;

import com.google.common.collect.Sets;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AffectationUtil {

    @Autowired
    private FichierDao fichierDao;

    public List<Fichier> getFichiersAffectes(AffectationDto affectationDto){
        List<Fichier> fichiersAffectes = new ArrayList<>();

        Set<Long> fichiersAvantAffectation = new HashSet<>(affectationDto.getFichersAvantAffectation());
        Set<Long> fichiersSelectionnes = affectationDto.getFichiers().stream().map(Fichier::getId).collect(Collectors.toSet());
        Set<Long> fichiersDeselectionnes = affectationDto.getFichiersUnChecked().stream().map(Fichier::getId).collect(Collectors.toSet());

        Sets.SetView<Long> difference = Sets.difference(fichiersAvantAffectation, fichiersDeselectionnes);
        Sets.SetView<Long> result = Sets.union(difference, fichiersSelectionnes);

        result.immutableCopy().forEach(idFichier -> fichiersAffectes.add(fichierDao.findOne(idFichier)));

        return fichiersAffectes;
    }

}
