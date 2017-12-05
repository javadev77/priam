package fr.sacem.priam.batch.affectation.reader;

import fr.sacem.priam.common.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by benmerzoukah on 01/12/2017.
 */
public class CatalogueOctavZipMultiResourceItemReader<T> extends MultiResourceItemReader<T> {
    private static final Logger LOG = LoggerFactory.getLogger(CatalogueOctavZipMultiResourceItemReader.class);
    private Resource[] archives;
    private ZipFile[] zipFiles;
    private StepExecution stepExecution;


    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

        JobParameters jobParameters = stepExecution.getJobParameters();

        File catalogueOactavDir = new File(jobParameters.getParameters().get("input.catalog.octav").toString());
        File catalogueOactavArchivesDir = new File(jobParameters.getParameters().get("archives.catalog.octav").toString());
        // really used with archives?
        if (catalogueOactavDir != null && catalogueOactavDir.isDirectory()) {
            // overwrite the comparator to use description
            // instead of filename, the itemStream can only
            // have that description
            this.setComparator(new Comparator<Resource>() {

                /** Compares resource descriptions. */
                @Override
                public int compare(Resource r1, Resource r2) {
                    return r1.getDescription().compareTo(r2.getDescription());
                }
            });

            //filter zip files
            File[] filtredFiles = catalogueOactavDir.listFiles((dir, name) -> {
                if (name.startsWith(FileUtils.PREFIX_OCTAV_CATALOGUE_FR) && name.endsWith(".zip")) {
                    return true;
                }
                return false;
            });


            if(filtredFiles != null && filtredFiles.length == 1) {
                File fileToProcess = new File(catalogueOactavArchivesDir.getAbsolutePath() + File.separator + filtredFiles[0].getName());
                boolean isMoveOK = filtredFiles[0].renameTo(fileToProcess);
                // get the inputStreams from all files inside the archives
                zipFiles = new ZipFile[filtredFiles.length];
                List<Resource> extractedResources = new ArrayList<>();
                try {
                    for (int i = 0; i < filtredFiles.length; i++) {
                        // find files inside the current zip resource
                        zipFiles[i] = new ZipFile(fileToProcess);
                        extractFiles(zipFiles[i], extractedResources);
                    }
                } catch (Exception ex) {
                    throw new ItemStreamException(ex);
                }
                // propagate extracted resources
                this.setResources(extractedResources.toArray(new Resource[extractedResources.size()]));
            } else {
                //throw new ItemStreamException("");
                this.setResources(new Resource[0]);
            }



        }
        super.open(executionContext);
    }

    @Override
    public void close() throws ItemStreamException {
        super.close();
        // try to close all used zipfiles
        if (zipFiles != null) {
            for (int i = 0; i < zipFiles.length; i++) {
                try {
                    zipFiles[i].close();
                } catch (IOException ex) {
                    throw new ItemStreamException(ex);
                }
            }
        }
    }

    /**
     * Extract only files from the zip archive.
     *
     * @param currentZipFile
     * @param extractedResources
     * @throws IOException
     */
    private void extractFiles(final ZipFile currentZipFile, final List<Resource> extractedResources) throws IOException {
        Enumeration<? extends ZipEntry> zipEntryEnum = currentZipFile.entries();
        while (zipEntryEnum.hasMoreElements()) {
            ZipEntry zipEntry = zipEntryEnum.nextElement();
            LOG.info("extracting:" + zipEntry.getName());
            // traverse directories
            if (!zipEntry.isDirectory()) {
                // add inputStream
                extractedResources.add(
                        new InputStreamResource(
                                currentZipFile.getInputStream(zipEntry),
                                zipEntry.getName()));
                LOG.info("using extracted file:" + zipEntry.getName());
            }
        }
    }


    @BeforeStep
    public void saveStepExecution(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }
}
