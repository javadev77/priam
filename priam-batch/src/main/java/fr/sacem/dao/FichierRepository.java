package fr.sacem.dao;

import fr.sacem.domain.Fichier;

import java.util.Set;

/**
 * Created by fandis on 17/05/2017.
 */
public interface FichierRepository {

    Long addFichier(Fichier fichier);

    void updateFichierDate(String nomFichier);

    Fichier findByName(String nomFichier);
    
    void updateFichierById(Long idFichier);
    
    Fichier findById(Long idFichier);

    void rejeterFichier(Long idFichier, Set<String> errors);

    void supprimerLigneProgrammeParIdFichier(Long idFichier, Set<String> errors);

    void enregistrerLog(Long idFichier, Set<String> errors);
}
