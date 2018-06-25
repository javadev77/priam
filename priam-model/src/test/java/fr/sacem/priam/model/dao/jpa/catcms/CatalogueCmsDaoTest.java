package fr.sacem.priam.model.dao.jpa.catcms;

import fr.sacem.priam.model.dao.AbstractDaoTest;
import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by benmerzoukah on 04/05/2018.
 */
public class CatalogueCmsDaoTest extends AbstractDaoTest {

    @Autowired
    CatalogueCmsDao catalogueRdoDao;

    @Test
    public void findByCriteriaWithNonEligible() throws Exception {

        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithNonEligible("ANF",
                null, null, null, null, null,
                null, null, null,
                null, new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(11);
    }

    @Test
    public void findByCriteriaWithoutNonEligible() throws Exception {
        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithoutNonEligible("ANF",
                null, null, null, null, new Date(),
                null, null,
                new Date(), new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(9);
    }


    @Test
    public void findByTypeCMSFR() throws Exception {

        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithoutNonEligible("FR",
                null, null, null, null, new Date(),
                null, null,
                new Date(), new PageRequest(0, 5));

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
                new Date(), new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    @Test
    public void findByPeriodeFin() throws Exception {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, Calendar.MAY, 1);
        Date periodeSoriteDateDebut = calendar.getTime();
        calendar.set(2018, Calendar.MAY, 3);
        Date periodeSortieDateFin = calendar.getTime();

        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithNonEligible("FR",
                null, null, null, null, null,
                null, null,
                periodeSoriteDateDebut, periodeSortieDateFin, new PageRequest(0, 5));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(1);

    }

    @Test
    public void findByCriteria_Tri() throws Exception {

        Page<CatalogueCms> result = catalogueRdoDao.findByCriteriaWithNonEligible("FR",
                null, null, null, null, null,
                null, null, null,
                null, new PageRequest(0, 25, Sort.Direction.DESC, "dateEntree"));

        assertThat(result).isNotNull().isNotEmpty();
        assertThat(result.getContent().get(10).getIde12()).isEqualTo(2007279511);
        assertThat(result.getContent().get(0).getIde12()).isEqualTo(2007278711);
    }


}