package fr.sacem.priam.batch.participants.rep;

import fr.sacem.priam.batch.participants.rep.dao.ParticipantRepositoryForTest;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
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
public class JobParticipantsREPTest {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private JobLauncherTestUtils jobLauncherTestUtils;
    private ApplicationContext context;

    private static final String PATH_FILE_CSV = "src/test/resources/inputCsv/FF_PRIAM_PARTICIPANTS_FRA_REP_14102018010155.csv";
    private static final String CSV_FILE_NAME = "FF_PRIAM_PARTICIPANTS_FRA_REP_14102018010155.csv";

    private static final String inputDirectory = "target/inputDirectory/";
    private static final String outputDirectory = "target/outputDirectory/";

    private static final String PATTERN_FILE_NAME = "^FF_PRIAM_PARTICIPANTS_(ANF|FRA)_REP_\\d{14}$";


    private ParticipantRepositoryForTest participantRepository;

    File inDir = null;
    File outDir;


    @Before
    public void setUp() {
        // Create the test configuration for the application from one file
       // ApplicationContext context2 = new AnnotationConfigApplicationContext(DataSourceTestConfiguration.class);
        context = new ClassPathXmlApplicationContext(new String[]{"configuration/job-configuration.xml"});
        // Get the bean to use to invoke the application
        jobLauncherTestUtils = (JobLauncherTestUtils) context.getBean("jobLauncherTestUtils");
        try {
            inDir = new File(inputDirectory);
            FileUtils.copyFile(new File(PATH_FILE_CSV),
                    new File(inputDirectory + CSV_FILE_NAME));
            FileUtils.forceMkdir(inDir);

            outDir = new File(outputDirectory);
            FileUtils.forceMkdir(outDir);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void launchJobTest()throws Exception {

        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("input.archives", new JobParameter(inputDirectory));
        jobParametersMap.put("output.archives", new JobParameter(outputDirectory));
        jobParametersMap.put("pattern.file.name", new JobParameter(PATTERN_FILE_NAME));
        jobParametersMap.put("typeCMS", new JobParameter("FRA"));

        JobParameters jobParameters = new JobParameters(jobParametersMap);

        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        participantRepository = (ParticipantRepositoryForTest) context.getBean("participantRepositoryForTest");

        // cas nominal
        long nbParticipants = participantRepository.getNbParticipants(10000111L);
        Assert.assertEquals(3L, nbParticipants);

        // cas erreur
        nbParticipants = participantRepository.getNbParticipants(2000002411L);
        Assert.assertEquals(1L, nbParticipants);

        // cas warning
        nbParticipants = participantRepository.getNbParticipants(2000012511L);
        Assert.assertEquals(6L, nbParticipants);

    }

    @After
    public void cleanUp() {
        try {
            FileUtils.deleteDirectory(inDir);
            FileUtils.deleteDirectory(outDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
