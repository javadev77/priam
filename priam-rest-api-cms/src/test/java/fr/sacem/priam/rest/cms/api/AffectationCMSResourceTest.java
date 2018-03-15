package fr.sacem.priam.rest.cms.api;

import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.rest.cms.config.RestResourceTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 17/01/2018.
 */
public class AffectationCMSResourceTest extends RestResourceTest {

    @Autowired
    FichierDao fichierDao;

    List<Fichier> fichierANT = new ArrayList<>();

    List<Fichier> fichierFRA = new ArrayList<>();;

    /*@Override
    public void setup() throws Exception {
        super.setup();
        fichierANT.add(fichierDao.findByNomFichier("Fichier_ANT01"));
        fichierFRA.add(fichierDao.findByNomFichier("Fichier_FRA01"));



    }*/

    @Test
    @Transactional
    public void affecterFichiers() throws Exception {
        AffectationDto affectationDto = createAffectationDto("180001", Collections.emptyList());

        mockMvc.perform(
                put("/app/rest/programme/affectation/")
                        .content(this.json(affectationDto))
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @Ignore
    public void affecterFichiers_CMS_FRA() throws Exception {
        AffectationDto affectationDto = createAffectationDto("180001", Arrays.asList("Fichier_FRA01"));
       // affectationDto.setFichiers(fichierFRA);
        mockMvc.perform(
                put("/app/rest/programme/affectation/")
                        .content(this.json(affectationDto))
                        .contentType(contentType))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.statut", is("AFFECTE")));
    }

    @Test
    @Transactional
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


    @Transactional
    private AffectationDto createAffectationDto(String numProg, List<String> fichiers) {
        AffectationDto affectationDto = new AffectationDto();

        affectationDto.setNumProg(numProg);


        List<Fichier> transform = Lists.transform(fichiers, f -> {
            Fichier fichier = new Fichier();
            fichier.setNomFichier(f);
            fichier.setStatut(Status.CHARGEMENT_OK);
            fichier.setAutomatique(Boolean.TRUE);

            fichierDao.save(fichier);

           // return fichierDao.findByNomFichier(f);
            return fichier;

        });

        affectationDto.setFichiers(transform);
        return  affectationDto;
    }

    @Test
    @Transactional
    public void desaffecterFichiers() throws Exception {

        mockMvc.perform(
                put("/app/rest/programme/toutDesaffecter")
                        .content("180001")
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

}