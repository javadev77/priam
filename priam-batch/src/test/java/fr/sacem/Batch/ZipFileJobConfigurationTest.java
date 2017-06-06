package fr.sacem.Batch;

/**
 * Created by fandis on 19/05/2017.
 */


import fr.sacem.util.UtilFile;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("batch-test")
public class ZipFileJobConfigurationTest {

    /**
     * Logger.
     */
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    /**
     * JobLauncherTestUtils Bean.
     */
    private JobLauncherTestUtils jobLauncherTestUtils;
    private ApplicationContext context;
    private String inputDirectory = "src/test/resources/zipDirectory/";
    private String outputDirectory = "src/test/resources/zipDirectoryVide/";
    private static final String SINGLE_FILE = "src/test/resources/zipDirectory/FF_PENEF_EXTRANA_EXTCMSFRANCE_20170406140540.csv";
    static Properties p = new Properties();

    @Before
    public void setUp() {
        // Create the test configuration for the application from one file
        context = new ClassPathXmlApplicationContext(new String[]{"configuration/job-configuration.xml"});
        // Get the bean to use to invoke the application
        jobLauncherTestUtils = (JobLauncherTestUtils) context.getBean("jobLauncherTestUtils");

    }

    @Test
    public void launchJobOneArchiveVide() throws Exception {

        // Job parameters
        Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
        jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
        jobParametersMap.put("input.archives", new JobParameter(inputDirectory));
        jobParametersMap.put("output.archives", new JobParameter(outputDirectory));
        JobParameters jobParameters = new JobParameters(jobParametersMap);
        //JobExecution execution = jobLauncher.run(job, jobParameters);
        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        // output step summaries
        for (StepExecution step : jobExecution.getStepExecutions()) {
            LOG.debug(step.getSummary());
            assertEquals("Read Count mismatch, changed input?",
                    0, step.getReadCount());
        }
    }
     @Ignore
    @Test
    public void launchJobOneArchiveAvecDixElement() throws Exception {
        // Job parameters
        Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
        jobParametersMap.put("time", new JobParameter(System.currentTimeMillis()));
        FileOutputStream fos = new FileOutputStream(inputDirectory + "\\" + "atest.zip");
        ZipOutputStream zos = new ZipOutputStream(fos);
        UtilFile.addToZipFile(SINGLE_FILE, zos);
        zos.close();
        fos.close();
        jobParametersMap.put("input.archives", new JobParameter(inputDirectory));
        jobParametersMap.put("output.archives", new JobParameter(outputDirectory));
        JobParameters jobParameters = new JobParameters(jobParametersMap);
        //JobExecution execution = jobLauncher.run(job, jobParameters);
        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        // output step summaries
        for (StepExecution step : jobExecution.getStepExecutions()) {
            LOG.debug(step.getSummary());
            assertEquals("Read Count mismatch, changed input?",
                    10, step.getReadCount());
        }
    }


}