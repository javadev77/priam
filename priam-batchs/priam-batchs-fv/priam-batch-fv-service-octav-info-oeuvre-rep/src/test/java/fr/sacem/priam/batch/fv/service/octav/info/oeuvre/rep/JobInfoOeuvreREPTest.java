package fr.sacem.priam.batch.fv.service.octav.info.oeuvre.rep;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.fv.service.octav.info.oeuvre.rep.config.BatchConfigTest;
import fr.sacem.priam.batch.fv.service.octav.info.oeuvre.rep.config.ConfigurationPriamLocalTest;
import fr.sacem.priam.batch.fv.service.octav.info.oeuvre.rep.dao.LigneProgrammeFVDaoTest;
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
import static org.junit.Assert.assertNotNull;

/**
 * Created by embouazzar on 30/12/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes= {ConfigurationPriamLocalTest.class, BatchConfigTest.class})
public class JobInfoOeuvreREPTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(JobInfoOeuvreREPTest.class);

    @Autowired
    public JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    LigneProgrammeFVDaoTest ligneProgrammeFVDaoTest;

    private static final String PATH_FILE_CSV = "src/test/resources/inputCsv/FF_PRIAM_20170218141426_1_INFOS_OEUVRES_REP.csv";
    private static final String CSV_FILE_NAME = "FF_PRIAM_20170218141426_1_INFOS_OEUVRES_REP.csv";

    private static final String inputDirectory = "target/inputDirectory/";
    private static final String outputDirectory = "target/outputDirectory/";

    private String patternFileName = "^FF_PRIAM_\\d{14}_\\d+_INFOS_OEUVRES_REP$";

    File inDir = null;
    File outDir = null;


    @Before
    public void setUp() {

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
    public void launchJobTest()throws Exception{

        Map<String, JobParameter> jobParametersMap = new HashMap<>();
        jobParametersMap.put("input.archives", new JobParameter(inputDirectory));
        jobParametersMap.put("output.archives", new JobParameter(outputDirectory));
        jobParametersMap.put("pattern.file.name", new JobParameter(patternFileName));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        LigneProgrammeFV ligneProgrammeFV = ligneProgrammeFVDaoTest.getLigneProgrammeFVByIde12(2379416911L);
        assertEquals(ligneProgrammeFV.getTitreOeuvre(), "LA CORSE INFOS OEUVRES");
    }

    @After
    public void cleanUp() {
        try {
            FileUtils.forceDeleteOnExit(inDir);
            FileUtils.deleteDirectory(outDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
