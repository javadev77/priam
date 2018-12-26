package fr.sacem.priam.fv.octav.info.req;

import fr.sacem.priam.fv.octav.info.req.config.BatchConfigTest;
import fr.sacem.priam.fv.octav.info.req.config.ConfigurationPriamLocalTest;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by embouazzar on 26/12/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes= {ConfigurationPriamLocalTest.class, BatchConfigTest.class})
public class JobGenerationREQTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    private static final String outputDirectory = "target/outDir";

    File outDir = null;

    @Before
    public void setUp() {
        try {
            outDir = new File(outputDirectory);
            FileUtils.forceMkdir(outDir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void launchJobTest() throws Exception{
        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("outputCsvFile", new JobParameter(outputDirectory));
        jobParametersMap.put("idFichier", new JobParameter(1L));
        JobParameters jobParameters = new JobParameters(jobParametersMap);


        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());
    }

    /*@After
    public void cleanUp() {
        try {

            FileUtils.deleteDirectory(outDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
