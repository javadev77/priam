package fr.sacem.priam.batch.participants.req;

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
import static org.junit.Assert.assertTrue;

/**
 * Created by benmerzoukah on 25/05/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class JobParticipantsREQTest {
    public static final String PREFIX_FRA_PART_REQ = "FF_PRIAM_PARTICIPANTS_FRA_REQ";
    public static final String PREFIX_FLAG = "Flag_FF_PRIAM_PARTICIPANTS_FRA_REQ_";
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private JobLauncherTestUtils jobLauncherTestUtils;
    private ApplicationContext context;
    private static final String outputDirectory = "target/outDir";


    @Before
    public void setUp() {
        // Create the test configuration for the application from one file
       // ApplicationContext context2 = new AnnotationConfigApplicationContext(DataSourceTestConfiguration.class);
        context = new ClassPathXmlApplicationContext(new String[]{"configuration/job-configuration.xml"});
        // Get the bean to use to invoke the application
        jobLauncherTestUtils = (JobLauncherTestUtils) context.getBean("jobLauncherTestUtils");
        try {

            FileUtils.forceMkdir( new File(outputDirectory));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void launchJobTest()throws Exception {

        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("outputCsvFile", new JobParameter(outputDirectory));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        //Check the file is generated
        File[] files = new File(outputDirectory).listFiles();
        for(File file : files) {
            assertTrue(file.exists()
                    && (file.getName().startsWith(PREFIX_FRA_PART_REQ) || file.getName().startsWith(PREFIX_FLAG)));
        }

    }

    @After
    public void cleanUp() {
        try {
            FileUtils.deleteDirectory(new File(outputDirectory));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
