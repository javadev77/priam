package fr.sacem.priam.model.services;

import fr.sacem.priam.model.dao.jpa.FamilleDao;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.dao.jpa.TypeUtilisationDao;
import fr.sacem.priam.model.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by fandis on 26/07/2017.
 */
@Service
@Transactional(readOnly = true)
public class LigneProgrammeServiceImpl implements LigneProgrammeService {

    LigneProgrammeDao ligneProgrammeDao;
    FichierDao fichierDao;
    TypeUtilisationDao typeUtilisationDao;
    FamilleDao familleDao;

    public void genererLigneProgramme(String nomFichier, Long nbrLignes) {

        Fichier fichier = new Fichier();
        fichier.setDateDebutChargt(new Date());
        fichier.setNomFichier(nomFichier);
        //type utilisation
        TypeUtilisation typeUtilisation = new TypeUtilisation();
        typeUtilisation.setCode("COPRIVSON");
        Example exampleTypeU = Example.of(typeUtilisation);
        TypeUtilisation mytypeU =typeUtilisationDao.findOne(exampleTypeU);
        //famille
        Famille famille = new Famille();
        famille.setCode("COPIEPRIV");
        Example exampleFamille = Example.of(famille);
        Famille myfamille =familleDao.findOne(exampleFamille);
        fichier.setFamille(myfamille);
        fichier.setStatut(Status.CHARGEMENT_OK);
        fichier.setNbLignes(nbrLignes);
        fichier.setDateFinChargt(new Date());
        fichier.setTypeUtilisation(mytypeU);
        fichierDao.save(fichier);

        Long randomUtilisateur = (long) (Math.random() * 350000 + 200000);

        for (int i =0 ; i<nbrLignes;i++){
            Long random = (long) (Math.random() * 350000 + 200000);
            LigneProgramme ligneProgramme = new LigneProgramme();
            ligneProgramme.setFichier(fichier);
            ligneProgramme.setCdeCisac("58");
            ligneProgramme.setCdeFamilTypUtil("COPIEPRIV");
            ligneProgramme.setCdeUtil("COPPRIVE");
            ligneProgramme.setCdeTypUtil("CPRIVSONRD");
            ligneProgramme.setCdeTypIde12("COCV");
            ligneProgramme.setIde12(random);
            ligneProgramme.setDurDif(20l);
            ligneProgramme.setNbrDif(random);
            ligneProgramme.setMt((Double.valueOf(random)));
            ligneProgramme.setCtna("");
            ligneProgramme.setParamCoefHor("");
            ligneProgramme.setDurDifCtna(random);
            ligneProgramme.setCdeLng("");
            ligneProgramme.setIndDoubSsTit("");
            ligneProgramme.setTax(0.0d);
            ligneProgramme.setTypMt("MN");
            ligneProgramme.setCdeGreIde12Cmplx("");
            ligneProgramme.setCdeGreIde12("ML");
            ligneProgramme.setTitreOriCmplx("");
            ligneProgramme.setTitreAltPppalCmplx("");
            ligneProgramme.setTitreOriOeuvPereCmplx("");
            ligneProgramme.setTitreAltOeuvPereCmplx("");
            ligneProgramme.setTitreOeuvre("Titre"+random);
            ligneProgramme.setCdePaysOriIso4NCmplx("");
            ligneProgramme.setRealisateurCmplx("CA");
            ligneProgramme.setRoleParticipant1("nom participant"+random);
            ligneProgramme.setNomParticipant1("participant"+random);
            ligneProgramme.setCdeTypUtilOri("");
            ligneProgramme.setCdeFamilTypUtilOri("");
            ligneProgramme.setUtilisateur("utilisateur " + randomUtilisateur);
            ligneProgramme.setAjout((i % 2 == 0) ? "Manuel" : "Automatique");
            ligneProgramme.setSelection((i % 2 == 0) ? true : false);
            ligneProgrammeDao.save(ligneProgramme);

            if(i%20 == 0) {
                randomUtilisateur = (long) (Math.random() * 350000 + 200000);
            }
        }
    }

    public LigneProgrammeDao getLigneProgrammeDao() {
        return ligneProgrammeDao;
    }

    public void setLigneProgrammeDao(LigneProgrammeDao ligneProgrammeDao) {
        this.ligneProgrammeDao = ligneProgrammeDao;
    }
    public FichierDao getFichierDao() {
        return fichierDao;
    }

    public void setFichierDao(FichierDao fichierDao) {
        this.fichierDao = fichierDao;
    }

    public TypeUtilisationDao getTypeUtilisationDao() {
        return typeUtilisationDao;
    }

    public void setTypeUtilisationDao(TypeUtilisationDao typeUtilisationDao) {
        this.typeUtilisationDao = typeUtilisationDao;
    }

    public FamilleDao getFamilleDao() {
        return familleDao;
    }

    public void setFamilleDao(FamilleDao familleDao) {
        this.familleDao = familleDao;
    }
}
