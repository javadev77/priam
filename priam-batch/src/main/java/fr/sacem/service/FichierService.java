package fr.sacem.service;

import fr.sacem.domain.Fichier;

import java.io.InputStream;

/**
 * Created by fandis on 17/05/2017.
 */
public interface FichierService {
    void addFichier(InputStream inputStream, String nomFichier);

    void updateFichierDate(String nomFichier);

    Fichier findByName(String nomFiciher);

}
