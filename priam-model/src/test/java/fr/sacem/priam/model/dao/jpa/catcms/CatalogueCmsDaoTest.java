package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import fr.sacem.priam.model.domain.dto.KeyValueDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 04/05/2018.
 */
public class CatalogueCmsDaoTest extends AbstractDaoTest {

    @Autowired
    CatalogueCmsDao catalogueRdoDao;

    private static final String TYPE_CMS_FR = "FR";
    private static final String TYPE_CMS_ANF = "ANF";

    @Test
    public void findByCriteriaWithNonEligible() throws Exception {

        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithNonEligible("ANF",
                null, null, null, null, null,
                null, null, null,
                null, null, null, new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(11);
    }

    @Test
    public void findByCriteriaWithoutNonEligible() throws Exception {
        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithoutNonEligible("ANF",
                null, null, null, null, new Date(),
                null, null,
                new Date(), null, null, new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(9);
    }


    @Test
    public void findByTypeCMSFR() throws Exception {

        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithoutNonEligible("FR",
                null, null, null, null, new Date(),
                null, null,
                new Date(),null, null, new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(10);
    }

    @Test
    public void findByPeriodeDebut() throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.MAY, 1);
        Date periodeEntreeDateDebut = calendar.getTime();
        calendar.set(2018, Calendar.MAY, 5);
        Date periodeEntreeDateFin = calendar.getTime();

        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithoutNonEligible("FR",
                null, null, null, periodeEntreeDateDebut, periodeEntreeDateFin,
                null, null,
                new Date(),null,null, new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void findByPeriodeFin() throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.FEBRUARY, 1);
        Date periodeSoriteDateDebut = calendar.getTime();
        calendar.set(2018, Calendar.FEBRUARY, 3);
        Date periodeSortieDateFin = calendar.getTime();

        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithNonEligible("FR",
                null, null, null, null, null,
                null, null,
                periodeSoriteDateDebut, periodeSortieDateFin, null, null, new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(1);

    }

    @Test
    public void findByCriteria_Tri() throws Exception {

        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithNonEligible(TYPE_CMS_FR,
                null, null, null, null, null,
                null, null, null,
                null,null, null, new PageRequest(0, 25, Sort.Direction.DESC, "dateEntree"));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getContent().get(10).getIde12()).isEqualTo(2007279511);
        assertThat(result.getContent().get(0).getIde12()).isEqualTo(2007278711);
    }

    @Test
    public void findByIde12AndTypeCMS() throws Exception {
        long ide12 = 2000163011L;
        CatalogueCms result = catalogueRdoDao.findByIde12AndTypeCMS(ide12, TYPE_CMS_ANF);
        assertThat(result).isNotNull();
    }

    @Test
    public void findTitresByTypeCMS(){
        List<KeyValueDto> titresByTypeCMS = catalogueRdoDao.findTitresByTypeCMS("oeu", "FR");
        assertThat(titresByTypeCMS).isNotNull().isNotEmpty();
        assertThat(titresByTypeCMS.stream().anyMatch(keyValue -> keyValue.getValue().toString().contains("OEU"))).isEqualTo(true);
    }

    @Test
    public void compterNombreTypeInscriptionInclusNonEligible(){
        List<Object> nombreTypeInscription = catalogueRdoDao.compterNombreTypeInscriptionInclusNonEligible("ANF",null,
                null, null, null, null, null
                , null, null, null);
        assertThat(nombreTypeInscription).isNotNull();
    }
    @Test
    public void compterNombreTypeInscriptionExclusNonEligible(){
        List<Object> nombreTypeInscription = catalogueRdoDao.compterNombreTypeInscriptionExclusNonEligible("ANF",null,
                null, null, null, null, null
                , null, null);
        assertThat(nombreTypeInscription).isNotNull();
    }

    @Test
    public void compterNombreTypeUtilisationInclusNonEligible(){
        List<Object> nombreTypeUtilisation= catalogueRdoDao.compterNombreTypeUtilisationInclusNonEligible("ANF",null,
                null, null, null, null, null
                , null, null, null);
        assertThat(nombreTypeUtilisation).isNotNull();
    }
}