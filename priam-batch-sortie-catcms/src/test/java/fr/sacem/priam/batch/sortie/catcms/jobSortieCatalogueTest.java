package fr.sacem.priam.batch.sortie.catcms;

import fr.sacem.priam.batch.common.domain.CatalogueCms;
import fr.sacem.priam.batch.sortie.catcms.dao.CatalogueCMSRepositoryForTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class jobSortieCatalogueTest {

    private JobLauncherTestUtils jobLauncherTestUtils;
    private ApplicationContext context;

    private CatalogueCMSRepositoryForTest catalogueCMSRepositoryForTest;

    @Before
    public void setUp() {
        // Create the test configuration for the application from one file
        context = new ClassPathXmlApplicationContext(new String[]{"configuration/job-configuration.xml"});
        // Get the bean to use to invoke the application
        jobLauncherTestUtils = (JobLauncherTestUtils) context.getBean("jobLauncherTestUtils");
    }

    @Test
    public void launchJobTest()throws Exception {

        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("param.annee.fr", new JobParameter((Long)context.getBean("paramAnneeFr")));
        jobParametersMap.put("param.annee.anf", new JobParameter((Long)context.getBean("paramAnneeAnf")));

        JobParameters jobParameters = new JobParameters(jobParametersMap);

        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        catalogueCMSRepositoryForTest = (CatalogueCMSRepositoryForTest) context.getBean("catalogueCMSRepositoryForTest");

        // cas nominal expiration oeuvre date entree
        CatalogueCms oeuvre = catalogueCMSRepositoryForTest.getOeuvreByIde12AndTypeCMS(10000111L, "FR");
        Assert.assertEquals(LocalDate.now(), oeuvre.getDateSortie());

        oeuvre = catalogueCMSRepositoryForTest.getOeuvreByIde12AndTypeCMS(2000014011L, "ANF");
        Assert.assertEquals(LocalDate.now(), oeuvre.getDateSortie());

        // cas nominal expiration oeuvre date renouvellement
        oeuvre = catalogueCMSRepositoryForTest.getOeuvreByIde12AndTypeCMS(2000001411L, "FR");
        Assert.assertEquals(LocalDate.now(), oeuvre.getDateSortie());

        oeuvre = catalogueCMSRepositoryForTest.getOeuvreByIde12AndTypeCMS(2632785011L, "ANF");
        Assert.assertEquals(LocalDate.now(), oeuvre.getDateSortie());

        // cas nominal non expiration oeuvre date entree
        oeuvre = catalogueCMSRepositoryForTest.getOeuvreByIde12AndTypeCMS(2000002411L, "FR");
        Assert.assertNull(oeuvre.getDateSortie());

        oeuvre = catalogueCMSRepositoryForTest.getOeuvreByIde12AndTypeCMS(6035521211L, "ANF");
        Assert.assertNull(oeuvre.getDateSortie());

    }

}
