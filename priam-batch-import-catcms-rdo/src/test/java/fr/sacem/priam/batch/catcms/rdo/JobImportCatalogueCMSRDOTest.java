package fr.sacem.priam.batch.catcms.rdo;

import fr.sacem.priam.batch.catcms.rdo.dao.FichierCatcmsRepositoryForTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
public class JobImportCatalogueCMSRDOTest {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private JobLauncherTestUtils jobLauncherTestUtils;
    private ApplicationContext context;
    private String inputDirectory = "src/test/resources/inputDirectory/";
    private String outputDirectory = "src/test/resources/outputDirectory/";

    private static final String CSV_FILE_NAME = "FF_OCTAV_CATALOGUE_FR.csv";
    private static final String CSV_FILE_PATH = "src/test/resources/inputCsv/FF_OCTAV_CATALOGUE_FR.csv";
    private static final String ZIP_FILE_PATH = "src/test/resources/inputDirectory/FF_OCTAV_CATALOGUE_FR.zip";

    private FichierCatcmsRepositoryForTest fichierCatcmsRepository;



    @Before
    public void setUp() {
        // Create the test configuration for the application from one file
        context = new ClassPathXmlApplicationContext(new String[]{"configuration/job-configuration.xml"});
        // Get the bean to use to invoke the application
        jobLauncherTestUtils = (JobLauncherTestUtils) context.getBean("jobLauncherTestUtils");
        fichierCatcmsRepository = (FichierCatcmsRepositoryForTest) context.getBean("fichierRepositoryForTest");
        csvToZip();
    }

    @Test
    public void launchJobTest()throws Exception{

        Map<String, JobParameter> jobParametersMap = new HashMap<String, JobParameter>();
        jobParametersMap.put("input.archives", new JobParameter(inputDirectory));
        jobParametersMap.put("output.archives", new JobParameter(outputDirectory));
        JobParameters jobParameters = new JobParameters(jobParametersMap);

        // launch the job
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        // assert job run status
        assertEquals(BatchStatus.COMPLETED, jobExecution.getStatus());

        // output step summaries
        for (StepExecution step : jobExecution.getStepExecutions()) {
            LOG.debug(step.getSummary());
            long idFichier = fichierCatcmsRepository.getLastIdFichier();
            long nbLignes = fichierCatcmsRepository.countNbLignesByIdFichier(idFichier);
            Assert.assertEquals(2L, nbLignes);
        }

    }

    private void csvToZip() {
        byte[] buffer = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream(ZIP_FILE_PATH);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(CSV_FILE_NAME);
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(CSV_FILE_PATH);

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();

            //remember close it
            zos.close();

            System.out.println("Done");

        }
        catch(IOException ex){
                ex.printStackTrace();
            }
        }
}
