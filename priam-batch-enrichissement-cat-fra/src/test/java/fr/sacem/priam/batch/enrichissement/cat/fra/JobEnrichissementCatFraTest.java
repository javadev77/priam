package fr.sacem.priam.batch.enrichissement.cat.fra;

import fr.sacem.priam.batch.utils.TestUtils;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by benmerzoukah on 25/05/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class JobEnrichissementCatFraTest {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private JobLauncherTestUtils jobLauncherTestUtils;
    private ApplicationContext context;
    private static final String inputDirectory = "target/inputDir";
    private static final String outputDirectory = "target/outDir";

    private static final String CSV_FILE_NAME = "FF_PENEF_CATALOGUE_FR_Test02.csv";
    private static final String ZIP_FILE_PATH = inputDirectory + "/FF_PENEF_CATALOGUE_FR_Test02.zip";
    private static final String CSV_FILE_PATH = "src/test/resources/inputCsv/FF_PENEF_CATALOGUE_FR_Test02.csv";


    @Before
    public void setUp() {
        // Create the test configuration for the application from one file
        context = new ClassPathXmlApplicationContext(new String[]{"configuration/job-configuration.xml"});
        // Get the bean to use to invoke the application
        jobLauncherTestUtils = (JobLauncherTestUtils) context.getBean("jobLauncherTestUtils");
        try {

            FileUtils.forceMkdir( new File(inputDirectory));
            FileUtils.forceMkdir( new File(outputDirectory));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void launchJobTest_Aucun_Fichier()throws Exception {

        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("input.archives", new JobParameter(inputDirectory));
        jobParametersMap.put("output.archives", new JobParameter(outputDirectory));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

    }

    @Test
    public void import_fichier_catalogue_penef_FR_OK() throws Exception {

        TestUtils.csvToZip(ZIP_FILE_PATH, CSV_FILE_PATH, CSV_FILE_NAME);

        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("input.archives", new JobParameter(inputDirectory));
        jobParametersMap.put("output.archives", new JobParameter(outputDirectory));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    @After
    public void cleanUp() {
        try {
            FileUtils.deleteDirectory(new File(inputDirectory));
            FileUtils.deleteDirectory(new File(outputDirectory));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
