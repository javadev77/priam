package fr.sacem.priam.rest.copieprivee.api;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.rest.copieprivee.config.RestResourceTest;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 16/11/2017.
 */
public class AffectationCPResourceTest extends RestResourceTest {

    @Autowired
    private FichierDao fichierDao;

    @Test
    @Transactional
    public void test_affecterFichiers_all_vide() throws Exception {
        mockMvc.perform(
                put("/app/rest/programme/affectation")
                        .content(this.json(createAffectationDto("170001", Collections.emptyList())))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statut", is("VALIDE")));

    }

    @Test
    @Transactional
    @Ignore
    public void test_affecterFichiers() throws Exception {
        mockMvc.perform(
                put("/app/rest/programme/affectation")
                        .content(this.json(createAffectationDto("170001", Arrays.asList("F01", "F02"))))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statut", is("AFFECTE")));

    }



    @Test
    @Transactional
    public void test_deaffecterFichiers() throws Exception {
        mockMvc.perform(
                put("/app/rest/programme/toutDesaffecter")
                        .content("170001")
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statut", is("CREE")));

    }



    private AffectationDto createAffectationDto(String numProg, List<String> fichiers) {
        AffectationDto affectationDto = new AffectationDto();
        affectationDto.setNumProg(numProg);
        List<Fichier> transform = Lists.transform(fichiers, f -> {
            Fichier fichier = new Fichier();
            fichier.setNomFichier( f);
            fichier.setStatut(Status.CHARGEMENT_OK);
            fichier.setAutomatique(Boolean.TRUE);

            fichierDao.saveAndFlush(fichier);

            return fichier;
        });

        affectationDto.setFichiers(transform);

        return affectationDto;
    }



}