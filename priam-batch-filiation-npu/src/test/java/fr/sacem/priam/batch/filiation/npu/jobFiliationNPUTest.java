package fr.sacem.priam.batch.filiation.npu;

import fr.sacem.priam.batch.filiation.npu.dao.OeuvreFiliationNPURepositoryForTest;
import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class jobFiliationNPUTest {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private JobLauncherTestUtils jobLauncherTestUtils;
    private ApplicationContext context;

    private static final String PATH_FILE_CSV = "src/test/resources/inputCsv/FF_OCTAV_PRIAM_EXTRACTION_NPU.csv";
    private static final String CSV_FILE_NAME = "FF_OCTAV_PRIAM_EXTRACTION_NPU.csv";

    private static final String inputDirectory = "target/inputDirectory/";
    private static final String outputDirectory = "target/outputDirectory/";

    private OeuvreFiliationNPURepositoryForTest oeuvreFiliationNPURepository;

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
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        oeuvreFiliationNPURepository = (OeuvreFiliationNPURepositoryForTest) context.getBean("oeuvreFiliationNPURepositoryForTest");

        // cas nominal
        // 10000111;10000112;PAI OPO MODIFIE
//        String titreOriginal = oeuvreFiliationNPURepository.getTitreOriginal(10000112L);
        CatalogueCms oeuvre = oeuvreFiliationNPURepository.getoeuvre(10000112L);
        Assert.assertEquals("PAI OPO MODIFIE", oeuvre.getTitre());

        // cas ide12 2000013811 non dans le fichier OCTAV Filiation NPU
        oeuvre = oeuvreFiliationNPURepository.getoeuvre(2000013811);
        Assert.assertNotNull(oeuvre);

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
