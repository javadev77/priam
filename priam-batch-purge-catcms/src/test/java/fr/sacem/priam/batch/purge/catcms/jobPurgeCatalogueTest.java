package fr.sacem.priam.batch.purge.catcms;

import fr.sacem.priam.batch.common.domain.CatalogueCms;
import fr.sacem.priam.batch.purge.catcms.dao.CatalogueCMSRepositoryForTest;
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

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class jobPurgeCatalogueTest {

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

        CatalogueCms oeuvre = catalogueCMSRepositoryForTest.getOeuvreByIde12AndTypeCMS(10000111L,"FR");
        Assert.assertNull(oeuvre);

        oeuvre = catalogueCMSRepositoryForTest.getOeuvreByIde12AndTypeCMS(2000014011L,"ANF");
        Assert.assertNull(oeuvre);
    }

}
