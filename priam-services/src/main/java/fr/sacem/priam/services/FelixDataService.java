package fr.sacem.priam.services;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.collect.Lists;
import fr.sacem.priam.common.constants.EnvConstants;
import fr.sacem.priam.common.util.csv.*;
import fr.sacem.priam.model.dao.jpa.*;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.dto.FelixData;
import fr.sacem.priam.model.domain.dto.FichierFelixError;
import fr.sacem.priam.services.utils.FelixDataSpringValidator;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.util.Arrays.asList;
import static org.apache.commons.io.IOUtils.LINE_SEPARATOR;

/**
 * Created by benmerzoukah on 18/08/2017.
 */
@Component
public class FelixDataService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FelixDataService.class);
    public static final String CDE_MOD_FAC = "FORFAI";
    public static final String PRINC = "PRINC";
    public static final String SANS = "SANS";
    private static final String DOC_PREFIX ="FF_PRIAM_PREPREP101_" ;
    
    @Autowired
    private LigneProgrammeDao ligneProgrammeDao;
    
    private final static CsvMapper csvMapper;
    private final static ObjectWriter writer;
    private final static Charset CHARSET;
    
    
    static {
        CHARSET = Charsets.toCharset(String.valueOf("UTF-8"));
        csvMapper = new CsvMapper();
        csvMapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new DateSerializer("dd/MM/yyyy"));
        module.addDeserializer(Date.class, new DateDeserializer("dd/MM/yyyy"));
        module.addSerializer(Double.class, new NumberSerializer());
        module.addDeserializer(Double.class, new DoubleDeserializer());
        module.addSerializer(BigDecimal.class, new RepartBigDecimalSerializer());
        csvMapper.registerModule(module);
        
        CsvSchema schema = csvMapper.schemaFor(FelixData.class)
                               .withColumnSeparator(';')
                               .withoutQuoteChar();
        
        writer = csvMapper.writer(schema);
    }
    
    private OutputStream out;
    private File file;
    private long nbLine = 0;
    
    @Autowired
    private ProgrammeDao programmeDao;
    
    @Autowired
    private LignePreprepDao lignePreprepDao;
    
    @Autowired
    private FelixDataSpringValidator validator;
    
    
    @Autowired
    private FichierFelixDao fichierFelixDao;
    
    @Autowired
    private FichierFelixLogDao fichierFelixLogDao;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
    public void generateEtValidateDonneesRepartition(String numProg) {
    
        /*List<LignePreprep> allLignes = lignePreprepDao.findByNumProg(numProg);
        lignePreprepDao.delete(allLignes);

        lignePreprepDao.flush();
    
        List<LignePreprep> lignesSelectionnes = ligneProgrammeDao.findLigneProgrammeSelectionnesForFelix(programme.getNumProg());
        
        Collection<LignePreprep> lignePrepreps = Lists.newArrayList();
        boolean isValidData = true;
        for (LignePreprep lignePreprep : lignesSelectionnes) {
            lignePreprep.setCdeTer(programme.getCdeTer()); // A remplir depuis le programme
            lignePreprep.setRionEffet(programme.getSareftrRionTheorique().getRion());
            lignePreprep.setCdeFamilTypUtil(programme.getSareftrFamiltyputil().getCode());
            lignePreprep.setNumProg(programme.getNumProg());
            lignePreprep.setCdeTypUtil(programme.getSareftrTyputil().getCode());
            lignePreprep.setCdeModFac(CDE_MOD_FAC);
            lignePreprep.setCdeTypProg(PRINC);
            lignePreprep.setCdeCompl(SANS);
            lignePreprep.setLibProg(programme.getNom());
            lignePreprep.setCompLibProg("");
            lignePreprep.setDatDbtProg(programme.getDateDbtPrg()); //TODO A remplir depuis le programme
            lignePreprep.setDatFinProg(programme.getDateFinPrg()); //TODO A remplir depuis le programme

            
            if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(programme.getSareftrTyputil().getCode()) ) {
                lignePreprep.setNbrDif(1L);
            } else {
                lignePreprep.setNbrDif(lignePreprep.getNbrDif());
            }
            
    
            lignePrepreps.add(lignePreprep);

        lignePreprepDao.flush();*/
    
        prepareFelixData(numProg);
    
        //lignePreprepDao.save(lignePrepreps);
        
        /*for(LignePreprep lignePreprep : lignePrepreps) {
            lignePreprep.setKeyLigPenel(lignePreprep.getId() != null  ? lignePreprep.getId().intValue() : null);
        }
    
        lignePreprepDao.save(lignePrepreps);*/
       //lignePreprepDao.flush();
        
        
    }
    
    private List<LignePreprep> prepareFelixData(String numProg) {
        lignePreprepDao.deleteAll(numProg);
        
        List<LignePreprep> lignesSelectionnes = ligneProgrammeDao.findLigneProgrammeSelectionnesForFelix(numProg);
        lignePreprepDao.save(lignesSelectionnes);
        lignePreprepDao.flush();
        
        /*for (LignePreprep lignePreprep : lignesSelectionnes) {
            lignePreprep.setKeyLigPenel(lignePreprep.getId().intValue());
            
        }*/
        
        return lignesSelectionnes;
    }
    
    /*private <T extends LignePreprep> T persistOrMerge(T t) {
        if (t.getId() == null) {
            entityManager.persist(t);
            return t;
        } else {
            return entityManager.merge(t);
        }
    }*/
    
    private void writeFile(List<LignePreprep> data) throws IOException {
        /*createFile();
        for (LignePreprep f : data) {
            writeLine(writer.writeValueAsString(f), false);
            nbLine++;
        }
        finish(file, out);*/
    }
    
    private void createFile() throws IOException {
        
        LOGGER.debug("Création d'un fichier FELIX avec charset = " + CHARSET.displayName());
        file = File.createTempFile("FF_PRIAM_PREPREP101", ".tmp");
        out = new FileOutputStream(file);
        
       // writeLines(head());
    }
    
    private List<String> head() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
        return asList(
            "#--------------------------------------------------------------------------",
            "# Extraction à destination de FELIX pour les calculs de répartition",
            "#--------------------------------------------------------------------------",
            "# JJ/MM/AAAA HH:MM - Auteur - Objet",
            "#--------------------------------------------------------------------------",
            "# " + dateFormat.format(new Date()) + " - PRIAM - Creation",
            "#DEBUT=====================================================================",
            "#cdeCisac;cdeTer;rionEffet;cdeFamilTypUtil;numProg;keyLigPenel;cdeUtil;cdeTypUtil;cdeModFac;cdeTypProg;cdeCompl;libProg;compLibProg;datDbtProg;datFinProg;hrDbt;hrFin;cdeGreDif;cdeModDif;cdeTypIde12;ide12;datDif;hrDif;durDif;nbrDif;mt;ctna;paramCoefHor;durDifCtna;cdeLng;indDoubSsTit;tax");
    }
    
    private List<String> foot(int lignes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
        return asList(
            "#FIN" + dateFormat.format(new Date()),
            "# Nbr lignes de donnees       = " + lignes);
    }
    
    private void writeLine(OutputStream out, String line, boolean withLineSeparator) throws IOException {
        if (line != null) {
            out.write(line.getBytes(CHARSET));
            if (withLineSeparator)
                out.write(LINE_SEPARATOR.getBytes(CHARSET));
        }
    }
    
    private void writeLines(OutputStream out, Collection<String> lines) throws IOException {
        for (String line : lines) {
            writeLine(out, line, true);
        }
    }
    
    public void finish(File file, OutputStream out) throws IOException {
        writeLines(out, foot(0));
        out.flush();
        IOUtils.closeQuietly(out);
    }
    
    @Transactional
    @Async("threadPoolTaskExecutor")
    public void runAsyncCreateFichierFelix(String numProg) {
        LOGGER.info("===> Start Async Method runAsyncCreateFichierFelix() ");
        FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
        try {
            
            FichierFelixError fichierFelixWithErrors = createFichierFelixWithErrors(numProg);
    
            
            ff.setNomFichier(fichierFelixWithErrors.getFilename());
            
            for(String error : fichierFelixWithErrors.getErrors()) {
                FichierFelixLog felixLog = new FichierFelixLog();
                felixLog.setLog(error);
                felixLog.setDateCreation(new Date());
                fichierFelixLogDao.save(felixLog);
                ff.getLogs().add(felixLog);
            }
            
            ff.setStatut(StatutFichierFelix.GENERE);
            ff.setContent(fichierFelixWithErrors.getContent());
            fichierFelixDao.saveAndFlush(ff);
            
        } catch (IOException e) {
            String message = "Erreur lors de la generation du fichier PREPREP";
            LOGGER.error(message);
            createErrorMessage(ff, message);
        }
    
        LOGGER.info("<=== End Async Method runAsyncCreateFichierFelix() ");
    
    }
    
    @Transactional
    public FichierFelixError createFichierFelixWithErrors(String numProg) throws IOException {
        Programme programme = programmeDao.findOne(numProg);
        //List<LignePreprep> lignePrepreps = lignePreprepDao.findByNumProg(numProg);
        List<LignePreprep> lignePrepreps = prepareFelixData(numProg);
        //lignePrepreps = lignePreprepDao.findByNumProg(numProg);
    
        String fileName = DOC_PREFIX
                              + numProg + "_"
                              + programme.getSareftrTyputil().getCode() + "_"
                              + programme.getSareftrRionTheorique().getRion() + "_"
                              + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".csv";
        File tmpFile = File.createTempFile(fileName, ".tmp");
        //OutputStream out = new FileOutputStream(tmpFile);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
    
        writeLines(out, head());
        int line = 1;
        ModelMapper modelMapper = new ModelMapper();
        
        List<String> errorsList = Lists.newArrayList();
        for (LignePreprep lignePreprep: lignePrepreps ) {
            FelixData felixData = modelMapper.map(lignePreprep, FelixData.class);
            //felixData.setKeyLigPenel(lignePreprep.getId().intValue());
            
            BindingResult errors = new BeanPropertyBindingResult(felixData, "lignePreprep-"+line);
            validator.validate(felixData, errors);
            
            if(errors.hasErrors()) {
                for(FieldError fe : errors.getFieldErrors()) {
                    errorsList.add(String.format("Ligne %s: Le champ %s est obligatoire et non renseigné",
                        line, fe.getField()));
                }
            }
    
            writeLine(out, writer.writeValueAsString(felixData), false);
            line++;
        }
    
        writeLines(out, foot(--line));
        out.flush();
        byte[] bytes = out.toByteArray();
        
        
    
        FichierFelixError fichierFelixError = new FichierFelixError();
        fichierFelixError.setTmpFilename(tmpFile.getName());
        fichierFelixError.setFilename(fileName);
        fichierFelixError.setContent(bytes);
        fichierFelixError.setErrors(errorsList);
    
        IOUtils.closeQuietly(out);
        
        return fichierFelixError;
    }
    
    @Transactional
    @Async("threadPoolTaskExecutor")
    public void asyncSendFichierFelix(String numProg) {
        /*List<LignePreprep> allLignes = lignePreprepDao.findByNumProg(numProg);
        lignePreprepDao.delete(allLignes);
        lignePreprepDao.flush();*/
    
        //prepareFelixData(numProg);
        /*lignePreprepDao.save(lignePrepreps);
        lignePreprepDao.flush();*/
    
        List<LignePreprep> lignePrepreps = prepareFelixData(numProg);//lignePreprepDao.findByNumProg(numProg);
        // Envoi du fichier via FTP
        //File felixPreprepDir = new File(System.getProperty("java.io.tmpdir"));
        FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
        File felixPreprep = new File(String.valueOf(EnvConstants.FELIX_PREPREP_DIR) + File.separator + ff.getNomFichier());
        try(FileOutputStream fileOuputStream = new FileOutputStream(felixPreprep)) {
            fileOuputStream.write(ff.getContent());
            majStatut(numProg, StatutProgramme.MIS_EN_REPART);
    
            ff.setStatut(StatutFichierFelix.ENVOYE);
            fichierFelixDao.saveAndFlush(ff);
        } catch (FileNotFoundException e) {
            //Rollback the status
            //majStatut(numProg, StatutProgramme.VALIDE);
            String message = "Impossible de trouver le Fichier Felix Preprep " + felixPreprep.getAbsolutePath();
            LOGGER.error(message, e);
            createErrorMessage(ff, message);
            
        } catch (IOException e) {
            //numProg the status
           // majStatut(numProg, StatutProgramme.VALIDE);
            String message = String.format("Probleme lors de l'envoi du fichier PREPREP  %s à FElix", ff.getNomFichier());
            LOGGER.error(message, e);
            createErrorMessage(ff, message);
            
        }
        
    }
    
    private void createErrorMessage(FichierFelix ff, String message) {
        ff.setStatut(StatutFichierFelix.EN_ERREUR);
        FichierFelixLog felixLog = new FichierFelixLog();
        felixLog.setLog(message);
        felixLog.setDateCreation(new Date());
        ff.getLogs().add(felixLog);
    
        fichierFelixDao.saveAndFlush(ff);
    }
    
    private void majStatut(String numProg, StatutProgramme statutProgramme) {
        Programme prog = programmeDao.findOne(numProg);
        prog.setStatut(statutProgramme);
        programmeDao.save(prog);
        programmeDao.flush();
    }
}

