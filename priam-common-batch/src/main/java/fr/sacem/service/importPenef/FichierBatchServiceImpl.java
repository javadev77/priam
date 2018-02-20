package fr.sacem.service.importPenef;

import au.com.bytecode.opencsv.CSVReader;
import fr.sacem.dao.FichierRepository;
import fr.sacem.domain.Fichier;
import fr.sacem.util.DateTimeUtils;
import fr.sacem.util.UtilFile;
import fr.sacem.util.exception.PriamValidationException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by fandis on 17/05/2017.
 */
@Service
public class FichierBatchServiceImpl implements FichierBatchService {
    private FichierRepository fichierRepository;
    private static final Logger LOG = LoggerFactory.getLogger(FichierBatchServiceImpl.class);
    private static final String STATUT_EN_COURS = "EN_COURS";
    private final static char SEPARATOR = ';';

    @Override
    public Long addFichier(InputStream inputStream, String nomFichier) throws PriamValidationException {
        LOG.info("Insertion de la ligne ficiher dans la table PRIAM_FICHIER");
        Fichier fichier = chargerLesDonnees(inputStream, nomFichier);
        
        return fichierRepository.addFichier(fichier);
        
    }

    protected Fichier chargerLesDonnees(InputStream inputStream, String nomFichier) {
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
                fichier.setDateDebutChargt(new DateTimeUtils().getCurrentTimeStamp());
                //Nom du fichier a intégrer en BDD
                fichier.setNom(nomFichier);
                //Nombre de lignes dans le fichier
                fichier.setNbLignes(nbLignes);
                fichier.setStatut(STATUT_EN_COURS);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
        return fichier;
    }

    @Override
    public void updateFichierById(Long idFichier) {
        fichierRepository.updateFichierById(idFichier);
    }

    @Override
    public void updateFichierDate(String nomFichier) {
        fichierRepository.updateFichierDate(nomFichier);
        LOG.info("Mise à jour du fichier en cours de traitement avec la date de fin de chargement");

    }

    @Override
    public Fichier findByName(String nomFiciher) {
        LOG.info("Recuperation du fichier par le nom:" + nomFiciher);
        return fichierRepository.findByName(nomFiciher);

    }
    
    @Override
    public Fichier findById(Long idFichier) {
        LOG.info("Recuperation du fichier par le idFichier:" + idFichier);
        return fichierRepository.findById(idFichier);
    }

    @Override
    public void rejeterFichier(Long idFichier, Set<String> errors) {
        fichierRepository.rejeterFichier(idFichier, errors);
        fichierRepository.supprimerLigneProgrammeParIdFichier(idFichier);
        fichierRepository.enregistrerLog(idFichier, errors);
    }

    public FichierBatchServiceImpl(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }

    public void setFichierRepository(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }


    @Override
    public void creerlog(Long idFichier, String log) {
        fichierRepository.enregistrerLog(idFichier, Stream.of(log).collect(Collectors.toSet()));
    }

    @Override
    @Transactional
    public void clearSelectedFichiers(String numProg, String statut) {
        fichierRepository.clearSelectedFichiers(numProg, statut);
    }
}
