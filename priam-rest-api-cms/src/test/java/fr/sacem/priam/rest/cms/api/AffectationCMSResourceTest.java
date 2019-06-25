package fr.sacem.priam.rest.cms.api;

import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.model.domain.dto.DesaffectationDto;
import fr.sacem.priam.rest.cms.config.RestResourceTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by benmerzoukah on 17/01/2018.
 */
public class AffectationCMSResourceTest extends RestResourceTest {

    @Autowired
    FichierDao fichierDao;

    List<Fichier> fichierANT = new ArrayList<>();

    List<Fichier> fichierFRA = new ArrayList<>();

    /*@Override
    public void setup() throws Exception {
        super.setup();
        fichierANT.add(fichierDao.findByNomFichier("Fichier_ANT01"));
        fichierFRA.add(fichierDao.findByNomFichier("Fichier_FRA01"));



    }*/

    @Test
    @Transactional(value="transactionManager")
    public void affecterFichiers() throws Exception {
        AffectationDto affectationDto = createAffectationDto("180001", Collections.emptyList());

        mockMvc.perform(
                put("/app/rest/programme/affectation/")
                        .content(this.json(affectationDto))
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @Ignore
    public void affecterFichiers_CMS_FRA() throws Exception {
        AffectationDto affectationDto = createAffectationDto("180001", Arrays.asList("Fichier_FRA01"));
        mockMvc.perform(
                put("/app/rest/programme/affectation/")
                        .content(this.json(affectationDto))
                        .contentType(contentType))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.statut", is("AFFECTE")));
    }

    @Test
    @Transactional(value="transactionManager")
    @Ignore
    public void affecterFichiers_CMS_ANT() throws Exception {
        AffectationDto affectationDto = createAffectationDto("180002", Arrays.asList("Fichier_ANT01"));
        //affectationDto.setFichiers(fichierANT);
        mockMvc.perform(
                put("/app/rest/programme/affectation")
                        .content(this.json(affectationDto))
                        .contentType(contentType))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.statut", is("AFFECTE")));
    }


    @Transactional(value="transactionManager", propagation = Propagation.REQUIRES_NEW)
    private AffectationDto createAffectationDto(String numProg, List<String> fichiers) {
        AffectationDto affectationDto = new AffectationDto();

        affectationDto.setNumProg(numProg);


        List<Fichier> transform = Lists.transform(fichiers, f -> {
            Fichier fichier = new Fichier();
            fichier.setNomFichier(f);
            fichier.setStatut(Status.CHARGEMENT_OK);
            fichier.setAutomatique(Boolean.TRUE);

            fichier = fichierDao.saveAndFlush(fichier);

           // return fichierDao.findByNomFichier(f);
            return fichier;

        });

        affectationDto.setFichiers(transform);
        return  affectationDto;
    }

    @Test
    @Transactional(value="transactionManager")
    public void desaffecterFichiers() throws Exception {

        DesaffectationDto d = new DesaffectationDto();
        d.setNumProg("180001");
        d.setAllDesaffecte(true);
        mockMvc.perform(
                put("/app/rest/programme/toutDesaffecter")
                        .content(json(d))
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

}