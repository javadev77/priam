package fr.sacem.priam.catcms.api;

import fr.sacem.priam.catcms.api.dto.CatalogueCritereRecherche;
import fr.sacem.priam.catcms.config.RestResourceTest;
import fr.sacem.priam.model.dao.jpa.catcms.CatalogueCmsDao;
import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by benmerzoukah on 04/05/2018.
 */
public class CatalogueResourceTest extends RestResourceTest {

    CatalogueCritereRecherche catalogueCritereRecherche = new CatalogueCritereRecherche();

    Calendar calendar = Calendar.getInstance();

    public static final String APP_REST_CATALOGUE_SEARCH = "/app/rest/catalogue/search";
    public static final String APP_REST_CATALOGUE_DELETE = "/app/rest/catalogue/oeuvre/";
    public static final String APP_REST_CATALOGUE_OEUVRE_EXISTANTE = "/app/rest/catalogue/oeuvre/search";
    public static final String APP_REST_CATALOGUE_OEUVRE_RENEW = "/app/rest/catalogue/oeuvre/";
    public static final String APP_REST_CATALOGUE_OEUVRE_ADD = "/app/rest/catalogue/oeuvre";
    public static final String APP_REST_CATALOGUE_TITRE = "/app/rest/catalogue/titre";
    public static final String APP_REST_CATALOGUE_COMPTEUR = "/app/rest/catalogue/compteur";


    public static final String TYPE_CMS_FR = "FR";
    public static final String TYPE_CMS_ANF = "ANF";

    @Autowired
    CatalogueCmsDao catalogueRdoDao;

//    private MariaDB4jSpringService mariaDB4jSpringService;



    @Test
    public void findAllByCriteria_TypeCMS_FR_NonEligible() throws Exception {

        List<CatalogueCms> all = catalogueRdoDao.findAll();

        catalogueCritereRecherche.setTypeCMS(TYPE_CMS_FR);
        catalogueCritereRecherche.setDisplayOeuvreNonEligible(false);

        mockMvc.perform(
                post(APP_REST_CATALOGUE_SEARCH)
                        .content(this.json(catalogueCritereRecherche))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(11)));
    }

    @Test
    public void findAllByCriteria_TypeCMS_ANF_Eligible() throws Exception {
        catalogueCritereRecherche.setTypeCMS(TYPE_CMS_ANF);
        catalogueCritereRecherche.setDisplayOeuvreNonEligible(true);

        mockMvc.perform(
                post(APP_REST_CATALOGUE_SEARCH)
                        .content(this.json(catalogueCritereRecherche))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(11)));
    }

    @Test
    public void findAllByCriteria_ide12_inexistant() throws Exception {
        catalogueCritereRecherche.setTypeCMS(TYPE_CMS_ANF);
        catalogueCritereRecherche.setIde12("12121212121");

        mockMvc.perform(
                post(APP_REST_CATALOGUE_SEARCH)
                        .content(this.json(catalogueCritereRecherche))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(0)));
    }

    @Test
    public void findAllByCriteria_ide12_existant() throws Exception {

        String ide12Existant = "2002665711";

        catalogueCritereRecherche.setTypeCMS(TYPE_CMS_ANF);
        catalogueCritereRecherche.setIde12(ide12Existant);

        mockMvc.perform(
                post(APP_REST_CATALOGUE_SEARCH)
                        .content(this.json(catalogueCritereRecherche))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.content[0].ide12", is(2002665711)));
    }

    @Test
    public void findAllByCriteria_ide12_titre() throws Exception {

        String titre = "PRISCA AVEC TOI JE VEUX VOYAGE";

        catalogueCritereRecherche.setTypeCMS(TYPE_CMS_ANF);
        catalogueCritereRecherche.setTitre(titre);

        mockMvc.perform(
                post(APP_REST_CATALOGUE_SEARCH)
                        .content(this.json(catalogueCritereRecherche))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.content[0].titre", is(titre)));
    }

    @Test
    public void findAllByCriteria_periodeEntree() throws Exception {


        calendar.set(2018, Calendar.MAY, 6);
        Date periodeEntreeDateDebut = calendar.getTime();
        calendar.set(2018, Calendar.MAY, 7);
        Date periodeEntreeDateFin = calendar.getTime();

        catalogueCritereRecherche.setTypeCMS(TYPE_CMS_FR);
        catalogueCritereRecherche.setPeriodeEntreeDateDebut(periodeEntreeDateDebut);
        catalogueCritereRecherche.setPeriodeSortieDateDebut(periodeEntreeDateFin);


        mockMvc.perform(
                post(APP_REST_CATALOGUE_SEARCH)
                        .content(this.json(catalogueCritereRecherche))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(3)))
                .andExpect(jsonPath("$.content[0].ide12", is(2002665711)))
                .andExpect(jsonPath("$.content[1].ide12", is(2007278711)));
    }

    @Test
    public void findAllByCriteria_periodeRenouvellement() throws Exception {

        calendar.set(2018, Calendar.MAY, 1);
        Date periodeRenouvellementDateDebut = calendar.getTime();
        calendar.set(2018, Calendar.MAY, 10);
        Date periodeRenouvellementDateFin = calendar.getTime();

        catalogueCritereRecherche.setTypeCMS(TYPE_CMS_FR);
        catalogueCritereRecherche.setPeriodeRenouvellementDateDebut(periodeRenouvellementDateDebut);
        catalogueCritereRecherche.setPeriodeRenouvellementDateFin(periodeRenouvellementDateFin);


        mockMvc.perform(
                post(APP_REST_CATALOGUE_SEARCH)
                        .content(this.json(catalogueCritereRecherche))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(1)))
                .andExpect(jsonPath("$.content[0].ide12", is(2007281411)));
    }

    @Test
    public void findAllByCriteria_tri_dateEntree() throws Exception {

        List<CatalogueCms> all = catalogueRdoDao.findAll();

        catalogueCritereRecherche.setTypeCMS(TYPE_CMS_FR);
        catalogueCritereRecherche.setDisplayOeuvreNonEligible(false);


        mockMvc.perform(
                post(APP_REST_CATALOGUE_SEARCH + "?page=0&size=25&sort=dateEntree,DESC")
                        .content(this.json(catalogueCritereRecherche))
                        .contentType(contentType))

                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.content[9].ide12", is(2007282611)))
                .andExpect(jsonPath("$.numberOfElements", is(11)));
    }

    @Test
    public void findAllByCriteria_periodeEntree_periodeSortie_null() throws Exception {

        catalogueCritereRecherche.setTypeCMS(TYPE_CMS_FR);
        catalogueCritereRecherche.setPeriodeEntreeDateFin(null);
        catalogueCritereRecherche.setPeriodeSortieDateFin(null);

        mockMvc.perform(
                post(APP_REST_CATALOGUE_SEARCH + "?page=0&size=25&sort=dateEntree,DESC")
                        .content(this.json(catalogueCritereRecherche))
                        .contentType(contentType))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numberOfElements", is(11)));
    }


    @Test
    public void deleteOeuvreWithRaison() throws Exception {
        CatalogueCms deletedOeuvre = new CatalogueCms();
        deletedOeuvre.setRaisonSortie("raison sortie");

        mockMvc.perform(
                delete(APP_REST_CATALOGUE_DELETE + "22")
                .content(this.json(deletedOeuvre))
                .contentType(contentType))

                .andExpect(status().isOk())
                .andExpect(jsonPath("ide12", is(2007282611)))
                .andExpect(jsonPath("raisonSortie", is("raison sortie")))
                .andExpect(jsonPath("typeSortie", is("Manuelle")));
    }

    @Test
    public void findOeuvreExistanteCatalogue() throws Exception {
        CatalogueCms catalogueCms = new CatalogueCms();
        catalogueCms.setIde12(2000163011L);
        catalogueCms.setTypeCMS(TYPE_CMS_ANF);
        mockMvc.perform(
                post(APP_REST_CATALOGUE_OEUVRE_EXISTANTE)
                        .content(json(catalogueCms))
                        .contentType(contentType))
                .andExpect(status().isOk());
    }

    @Test
    public void renouvelerOeuvre() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        mockMvc.perform(
                put(APP_REST_CATALOGUE_OEUVRE_RENEW + 22)
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("ide12", is(2007282611)))
                .andExpect(jsonPath("typeCMS", is(TYPE_CMS_FR)))
                .andExpect(jsonPath("dateRenouvellement", is(sdf.format(new Date()))));
    }

    @Test
    public void ajouterOeuvreFromMipsa() throws Exception {
        CatalogueCms oeuvreToAdd = new CatalogueCms();
        oeuvreToAdd.setIde12(2632785011L);
        oeuvreToAdd.setTitre("LA BOHEME");
        //oeuvreToAdd.setTypUtilGen("PHONOFR");
        oeuvreToAdd.setTypeCMS(TYPE_CMS_FR);
        //oeuvreToAdd.setDateEntree(new Date());
        oeuvreToAdd.setParticipants("GHIAUROV KARAJA ITUNES PAN");
        oeuvreToAdd.setRoles("CA");
        oeuvreToAdd.setTypeInscription("Manuelle");

        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.post(APP_REST_CATALOGUE_OEUVRE_ADD)
                        .content(this.json(oeuvreToAdd))
                        .contentType(MediaType.APPLICATION_JSON_VALUE);

        mockMvc.perform(builder)
                //.content(this.json(oeuvreToAdd)))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("typeCMS", is(TYPE_CMS_FR)));

    }

    @Test
    public void getTitresByProgramme() throws Exception {

        String titre = "wap";
        mockMvc.perform(get(APP_REST_CATALOGUE_TITRE + "?q=" + titre +
                "&typeCMS=" + TYPE_CMS_FR +
                "&displayOeuvreNonEligible="+false
        )
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].value", is("WAP")));
    }

    @Test
    public void compteurByCriteria() throws Exception {

        catalogueCritereRecherche.setTypeCMS(TYPE_CMS_ANF);
        catalogueCritereRecherche.setDisplayOeuvreNonEligible(false);

        mockMvc.perform(post(APP_REST_CATALOGUE_COMPTEUR)
                .content(this.json(catalogueCritereRecherche))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['TYPE_INSCRIPTION'].[0]['code']", is("Automatique")))
                .andExpect(jsonPath("$['TYPE_INSCRIPTION'].[0]['value']", is(9)))
                .andExpect(jsonPath("$['TYPE_UTILISATION'].[0]['code']", is("PHONOFR")))
                .andExpect(jsonPath("$['TYPE_UTILISATION'].[0]['value']", is(9)));

        catalogueCritereRecherche.setDisplayOeuvreNonEligible(true);

        mockMvc.perform(post(APP_REST_CATALOGUE_COMPTEUR)
                .content(this.json(catalogueCritereRecherche))
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$['TYPE_INSCRIPTION'].[0]['code']", is("Automatique")))
                .andExpect(jsonPath("$['TYPE_INSCRIPTION'].[0]['value']", is(11)))
                .andExpect(jsonPath("$['TYPE_UTILISATION'].[0]['code']", is("PHONOFR")))
                .andExpect(jsonPath("$['TYPE_UTILISATION'].[0]['value']", is(11)));

    }

}