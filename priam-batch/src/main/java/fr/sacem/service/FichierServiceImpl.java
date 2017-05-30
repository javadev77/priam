package fr.sacem.service;

import fr.sacem.dao.FichierRepository;
import fr.sacem.domain.Fichier;
import fr.sacem.util.UtilFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * Created by fandis on 17/05/2017.
 */
@Service
public class FichierServiceImpl implements FichierService {
    private FichierRepository fichierRepository;
    private static final Logger LOG = LoggerFactory.getLogger(FichierServiceImpl.class);

    @Override
    public void addFichier(InputStream inputStream, String nomFichier) {

        Fichier fichier = UtilFile.chargerLesDonnees(inputStream, nomFichier);
        fichierRepository.addFichier(fichier);
        LOG.info("Insertion de la ligne ficiher dans la table PRIAM_FICHIER");
    }

    public void updateFichierDate(String nomFichier) {
        fichierRepository.updateFichierDate(nomFichier);
        LOG.info("Mise à jour du fichier en cours de traitement avec la date de fin de chargement");

    }

    public Fichier findByName(String nomFiciher) {
        LOG.info("Recuperation du fichier par le nom:" + nomFiciher);
        return fichierRepository.findByName(nomFiciher);

    }

    public FichierServiceImpl(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }

    public FichierRepository getFichierRepository() {
        return fichierRepository;
    }

    public void setFichierRepository(FichierRepository fichierRepository) {
        this.fichierRepository = fichierRepository;
    }


}
