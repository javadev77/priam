package fr.sacem.util;

import au.com.bytecode.opencsv.CSVReader;
import fr.sacem.domain.Fichier;
import fr.sacem.service.importPenef.FichierService;
import fr.sacem.util.exception.PriamValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by fandis on 10/05/2017.
 */
public class UtilFile {
    private final static char SEPARATOR = ';';
    private static final Logger LOG = LoggerFactory.getLogger(UtilFile.class);
    private static final String MON_FILTER = "TRACABILITE";
    private static final String STATUT_EN_COURS = "EN_COURS";
    private static final String EXTENTION_CSV = ".csv";
    private FichierService fichierService;

    @Autowired
    public static Long nombreDeLignes(InputStream inputStream) {
        String input ;
        Long count = 0L;
        try {
            InputStream is = inputStream;
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((input = bufferedReader.readLine()) != null) {
                if (!input.startsWith("#"))
                    count++;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Count : " + count);
        return count;
    }

    public static Fichier chargerLesDonnees(InputStream inputStream, String nomFichier) {
        Fichier fichier = new Fichier();
        try {
            //Traitement pour recuperer la famille et le type d'utilisation depuis le contenu du ficiher
            Reader reader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            CSVReader csvReader = new CSVReader(bufferedReader, SEPARATOR);
            List<String[]> data = new ArrayList<String[]>();

            String[] nextLine = null;
            String[] donnee = null;
            Long nbLignes = 0L;
            while ((nextLine = csvReader.readNext()) != null) {
                // ligne vide
                if (nextLine == null || nextLine.length == 0) {
                    continue;
                }
                String debut = nextLine[0].trim();
                if (StringUtils.isBlank(debut) || StringUtils.isEmpty(debut) ) {
                    continue;
                }

                // ligne de commentaire
                if (debut.startsWith("#")) {
                    continue;
                }
                if(donnee == null) {
                    donnee = nextLine;
                }
                
                nbLignes++;
            }
            // on a besoin de charger que la premiere ligne pour recuperer typeUtilisation et la famille
            if (donnee != null) {
                fichier.setTypeUtilisation(donnee[4]);
                fichier.setFamille(donnee[1]);
                fichier.setNom(nomFichier);
                // Date de debout de chargement
                fichier.setDateDebutChargt(UtilFile.getCurrentTimeStamp());
                //Nom du fichier a intégrer en BDD
                fichier.setNom(nomFichier);
                //Nombre de lignes dans le fichier
                fichier.setNbLignes(nbLignes);
                fichier.setStatut(STATUT_EN_COURS);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return fichier;
    }

    public static java.sql.Timestamp getCurrentTimeStamp() {
        java.util.Date today = new java.util.Date();
        return new java.sql.Timestamp(today.getTime());
    }

    /**
     * Extract only files from the zip archive.
     *
     * @param currentZipFile
     * @param extractedResources
     * @throws IOException
     */
    public Long extractFiles(final ZipFile currentZipFile, final List<Resource> extractedResources) throws IOException, PriamValidationException {
        Enumeration<? extends ZipEntry> zipEntryEnum = currentZipFile.entries();

        //Le code support que l'operation traite un seul fichier csv comme demander fonctionnellement
        String nomFichier = "";
        Long idFichier = null;
        while (zipEntryEnum.hasMoreElements()) {
            ZipEntry zipEntry = zipEntryEnum.nextElement();

            LOG.info("extracting:" + zipEntry.getName());
            // traverse directories
            if (!zipEntry.isDirectory() && !zipEntry.getName().contains(MON_FILTER) && zipEntry.getName().contains(EXTENTION_CSV)) {
                // add inputStream
                InputStreamResource inputStreamResourceForExtraction = new InputStreamResource(currentZipFile.getInputStream(zipEntry), zipEntry.getName());
                InputStream csvInputStream = currentZipFile.getInputStream(zipEntry);
                InputStreamResource inputStreamResourceForBDD = new InputStreamResource(csvInputStream,zipEntry.getName());
                idFichier = fichierService.addFichier(inputStreamResourceForBDD.getInputStream(), zipEntry.getName());
                extractedResources.add(inputStreamResourceForExtraction);
                LOG.info("using extracted file:" + zipEntry.getName());
            }
        }
        return idFichier;
    }

    public static void addToZipFile(String fileName, ZipOutputStream zos) throws IOException {

        System.out.println("Writing '" + fileName + "' to zip file");

        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }
        zos.closeEntry();
        fis.close();
    }
    public void deplacerFichier(JobParameter parameterFichierZipEnCours, JobParameter parameterNomFichierOriginal, JobParameter outputDirectory) {
        if (parameterNomFichierOriginal != null && parameterFichierZipEnCours != null && outputDirectory != null) {
            String nomFichierTraiementEnCours = (String) parameterFichierZipEnCours.getValue();
            File fichierTraitementEnCours = new File(nomFichierTraiementEnCours);
            File fichierTraitementOk = new File((String) outputDirectory.getValue() + (String) parameterNomFichierOriginal.getValue());
            if (fichierTraitementOk.exists())
                fichierTraitementOk.delete();
            Boolean deplacementOK = fichierTraitementEnCours.renameTo(fichierTraitementOk);
            if (deplacementOK) {
                fichierTraitementEnCours.delete();
            } else {
                LOG.error("Déplacement de ficiher en cours de traitement KO ");
            }
        }
    }
    public UtilFile(FichierService fichierService) {
        this.fichierService = fichierService;
    }

    public UtilFile() {
    }

    public FichierService getFichierService() {
        return fichierService;
    }

    public void setFichierService(FichierService fichierService) {
        this.fichierService = fichierService;
    }

}