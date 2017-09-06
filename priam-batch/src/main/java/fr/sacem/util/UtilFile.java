package fr.sacem.util;

import au.com.bytecode.opencsv.CSVReader;
import fr.sacem.domain.Fichier;
import fr.sacem.service.FichierService;
import fr.sacem.service.ZipMultiResourceItemReader;
import fr.sacem.util.exception.PriamValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(ZipMultiResourceItemReader.class);
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
            Reader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while ((input = bufferedReader.readLine()) != null) {
                if (!input.substring(0, 1).equals("#"))
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
            CSVReader csvReader = new CSVReader(reader, SEPARATOR);
            List<String[]> data = new ArrayList<String[]>();

            String[] nextLine = null;
            String[] donnee = null;
            while ((nextLine = csvReader.readNext()) != null && data.size() < 1) {
                int size = nextLine.length;

                // ligne vide
                if (size == 0) {
                    continue;
                }
                String debut = nextLine[0].trim();
                if (debut.length() == 0 && size == 1) {
                    continue;
                }

                // ligne de commentaire
                if (debut.startsWith("#")) {
                    continue;
                }
                data.add(nextLine);
            }
            // on a besoin de charger que la premiere ligne pour recuperer typeUtilisation et la famille
            if (data.size() >= 1) {
                donnee = data.get(0);
                fichier.setTypeUtilisation(donnee[4]);
                fichier.setFamille(donnee[1]);
                fichier.setNom(nomFichier);
                // Date de debout de chargement
                fichier.setDateDebutChargt(UtilFile.getCurrentTimeStamp());
                //Nom du fichier a intégrer en BDD
                fichier.setNom(nomFichier);
                //Nombre de lignes dans le fichier
                Long nb_lignes = UtilFile.nombreDeLignes(inputStream);
                fichier.setNbLignes(nb_lignes);
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
                nomFichier = zipEntry.getName();
                LOG.info("using extracted file:" + zipEntry.getName());
            }
        }
        return idFichier;
    }

    public static void addToZipFile(String fileName, ZipOutputStream zos) throws FileNotFoundException, IOException {

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