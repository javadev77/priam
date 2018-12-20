package fr.sacem.priam.batch.common.fv.writer;

/**
 * Created by embouazzar on 19/12/2018.
 */
public abstract class AbstractConfig {

    public abstract String head();

    public abstract String foot(Long nbLignes);

}
