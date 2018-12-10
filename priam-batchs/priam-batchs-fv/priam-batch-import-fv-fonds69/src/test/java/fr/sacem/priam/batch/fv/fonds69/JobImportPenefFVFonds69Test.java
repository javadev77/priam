package fr.sacem.priam.batch.fv.fonds69;

import fr.sacem.priam.batch.fv.fonds69.config.BatchConfigurationTest;
import fr.sacem.priam.batch.fv.fonds69.config.ConfigurationPriamLocalTest;
import fr.sacem.priam.batch.fv.fonds69.dao.LigneProgrammeFVDaoTest;
import fr.sacem.priam.batch.utils.TestUtils;
import org.apache.commons.io.FileUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
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
 * Created by embouazzar on 29/11/2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@ContextConfiguration(classes= {ConfigurationPriamLocalTest.class, BatchConfigurationTest.class})
public class JobImportPenefFVFonds69Test {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

//    private ApplicationContext context;

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private LigneProgrammeFVDaoTest ligneProgrammeFVDaoTest;

    private String inputDirectory = "target/inputDirectory/";
    private String outputDirectory = "target/outputDirectory/";
    /*private String inputDirectory = "src/test/resources/inputDirectory/";
    private String outputDirectory = "src/test/resources/outputDirectory/";*/
    private String patternFileName = "^FF_PENEF_EXTRANA_PAF_FONDS_\\d{14}$";

    private static final String CSV_FILE_NAME = "FF_PENEF_EXTRANA_PAF_FONDS06_20180110134459.csv";
    private static final String CSV_FILE_PATH = "src/test/resources/inputCsv/FF_PENEF_EXTRANA_PAF_FONDS06_20180110134459.csv";
    private static final String ZIP_FILE_PATH = "target/inputDirectory/FF_PENEF_EXTRANA_PAF_FONDS06_20180110134459.zip";
    //private static final String ZIP_FILE_PATH = "src/test/resources/inputDirectory/FF_PENEF_EXTRANA_PAF_FONDS06_20180110134459.zip";

//    File inDir = null;
    File inDir = null;
    File outDir = null;


    @Before
    public void setUp() {

        try {

            inDir = new File(inputDirectory);
            if(inDir.exists() && inDir.list().length > 0 ) {
                for(String s: inDir.list()){
                    File currentFile = new File(inDir.getPath(),s);
                    currentFile.delete();
                }
            } else {
                FileUtils.forceMkdir(inDir);
            }
            outDir = new File(outputDirectory);
            FileUtils.forceMkdir(outDir);

            TestUtils.csvToZip(ZIP_FILE_PATH, CSV_FILE_PATH, CSV_FILE_NAME);
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

        long nbLignes = ligneProgrammeFVDaoTest.countNbLignesByIdFichier(14L);
        Assert.assertEquals(1L, nbLignes);

    }

    /*@After
    public void cleanUp() {
        try {
            FileUtils.deleteDirectory(inDir);
            //FileUtils.deleteDirectory(outDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
