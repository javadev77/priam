package fr.sacem.dao;

import fr.sacem.domain.Fichier;
import fr.sacem.util.exception.PriamValidationException;

import java.util.Set;

/**
 * Created by fandis on 17/05/2017.
 */
public interface FichierRepository {

    Long addFichier(Fichier fichier) throws PriamValidationException;

    void updateFichierDate(String nomFichier);

    Fichier findByName(String nomFichier);
    
    void updateFichierById(Long idFichier);
    
    Fichier findById(Long idFichier);

    void rejeterFichier(Long idFichier, Set<String> errors);

    void supprimerLigneProgrammeParIdFichier(Long idFichier);

    void enregistrerLog(Long idFichier, Set<String> errors);

    void clearSelectedFichiers(String numProg, String statut);

    Long addFichierLink(String numProg);

}
