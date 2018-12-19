package fr.sacem.priam.batch.fv;

import fr.sacem.priam.batch.fv.config.BatchConfigurationTest;
import fr.sacem.priam.batch.fv.config.ConfigurationPriamLocalTest;
import fr.sacem.priam.batch.fv.dao.LigneProgrammeFVDaoTest;
import fr.sacem.priam.batch.utils.TestUtils;

import org.apache.commons.io.FileUtils;

import org.junit.*;

import org.junit.runner.RunWith;
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
public class JobImportPenefFVCat_01Test {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private LigneProgrammeFVDaoTest ligneProgrammeFVDaoTest;

    private String inputDirectory = "target/inputDirectoryCat_01/";
    private String outputDirectory = "target/outputDirectory/";
    private String patternFileName = "^FF_PENEF_EXTRANA_FONDS(0[1-7]|0[9]|1[0-3])_\\d{14}$";

    private static final String CSV_FILE_NAME_6 = "FF_PENEF_EXTRANA_FONDS06_20181211161117.csv";
    private static final String CSV_FILE_PATH_6 = "src/test/resources/inputCsvCat_01/FF_PENEF_EXTRANA_FONDS06_20181211161117.csv";
    private static final String ZIP_FILE_PATH_6 = "target/inputDirectoryCat_01/FF_PENEF_EXTRANA_FONDS06_20181211161117.zip";

    File inDir = null;
    File outDir = null;


    @Before
    public void setUp() {

        try {

            inDir = new File(inputDirectory);
            if(inDir.exists()){
                FileUtils.cleanDirectory(inDir);
            }
            FileUtils.forceMkdir(inDir);

            outDir = new File(outputDirectory);
            FileUtils.forceMkdir(outDir);

            TestUtils.csvToZip(ZIP_FILE_PATH_6, CSV_FILE_PATH_6, CSV_FILE_NAME_6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Test
    public void launchJobTestFV6()throws Exception{
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
        Assert.assertEquals(2L, nbLignes);

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
