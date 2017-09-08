package fr.sacem.service;

import fr.sacem.domain.Fichier;
import fr.sacem.util.exception.PriamValidationException;

import java.io.InputStream;
import java.util.Set;

/**
 * Created by fandis on 17/05/2017.
 */
public interface FichierService {
    Long addFichier(InputStream inputStream, String nomFichier) throws PriamValidationException;

    void updateFichierDate(String nomFichier);

    Fichier findByName(String nomFiciher);
    
    Fichier findById(Long idFichier);

    void rejeterFichier(Long idFichier, Set<String> logs);

    void creerlog(Long idFichier, String log);
}
