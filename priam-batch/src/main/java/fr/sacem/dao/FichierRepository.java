package fr.sacem.dao;

import fr.sacem.domain.Fichier;

/**
 * Created by fandis on 17/05/2017.
 */
public interface FichierRepository {

    Long addFichier(Fichier fichier);

    void updateFichierDate(String nomFichier);

    Fichier findByName(String nomFichier);
    
    void updateFichierById(Long idFichier);
    
    Fichier findById(Long idFichier);
}
