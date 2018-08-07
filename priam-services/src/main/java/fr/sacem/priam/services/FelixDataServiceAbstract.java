package fr.sacem.priam.services;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.collect.Lists;
import fr.sacem.priam.common.constants.EnvConstants;
import fr.sacem.priam.common.exception.TechnicalException;
import fr.sacem.priam.common.util.SftpUtil;
import fr.sacem.priam.common.util.csv.*;
import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.dao.jpa.FichierFelixLogDao;
import fr.sacem.priam.model.dao.jpa.LignePreprepDao;
import fr.sacem.priam.model.dao.jpa.LignePreprepJdbcDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.dto.FelixData;
import fr.sacem.priam.model.domain.dto.FichierFelixError;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.utils.FelixDataSpringValidator;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import static fr.sacem.priam.common.util.SftpUtil.SftpServer.FELIX;
import static java.util.Arrays.asList;
import static org.apache.commons.io.IOUtils.LINE_SEPARATOR;

/**
 * Created by monfleurm on 09/01/2018.
 */
@Component
public abstract class FelixDataServiceAbstract {

    private static final Logger LOGGER = LoggerFactory.getLogger(FelixDataServiceAbstract.class);
    public static final String CDE_MOD_FAC = "FORFAI";
    public static final String PRINC = "PRINC";
    public static final String SANS = "SANS";
    private static final String DOC_PREFIX ="FF_PRIAM_PREPREP101_" ;
    

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

    @Autowired
    ProgrammeService programmeService;

    @Autowired
    @Qualifier(value = "configAdmap")
    Map<String, String> configAdmap;

    @Autowired
    LignePreprepJdbcDao lignePreprepJdbcDao;
    
    public abstract List<LignePreprep> getListLignesSelectionnees(String pNumprog);

    private List<LignePreprep> prepareFelixData(String numProg) {
        LOGGER.info(">>>>>> prepare FelixData <<<<<<");
        lignePreprepDao.deleteAll(numProg);
        
        List<LignePreprep> lignesSelectionnes = getListLignesSelectionnees(numProg);

        LOGGER.info(">>>>>> Debut insert en mode Batch Jdbc des LignesPrepreps taille : " + lignesSelectionnes.size());
        lignePreprepJdbcDao.insertLignesPreprep(lignesSelectionnes);

        LOGGER.info("<<<<<<< Fin insert en mode Batch");

        return lignePreprepDao.findByNumProg(numProg);
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
 
    @Transactional
    @Async("threadPoolTaskExecutor")
    public void runAsyncCreateFichierFelix(String numProg) {
        LOGGER.info("===> Start Async Method runAsyncCreateFichierFelix() ");
        FichierFelix ff = null;
        try {
            ff = fichierFelixDao.findByNumprog(numProg);
            List<LignePreprep> lignePrepreps = getListLignesSelectionnees(numProg);
            FichierFelixError fichierFelixWithErrors = createFichierFelixWithErrors(numProg, lignePrepreps);
    
            
            ff.setNomFichier(fichierFelixWithErrors.getFilename());
            
            List<String> errors = fichierFelixWithErrors.getErrors();
            if(errors != null && !errors.isEmpty()) {
                for(String error : errors) {
                    FichierFelixLog felixLog = new FichierFelixLog();
                    felixLog.setLog(error);
                    felixLog.setDateCreation(new Date());
                    fichierFelixLogDao.save(felixLog);
                    ff.getLogs().add(felixLog);
                }
            }
            ff.setStatut(errors == null || errors.isEmpty() ? StatutFichierFelix.GENERE : StatutFichierFelix.EN_ERREUR);
            fichierFelixDao.saveAndFlush(ff);
            
        } catch (IOException e) {
            String message = "Erreur lors de la generation du fichier PREPREP";
            LOGGER.error(message, e);
            createErrorMessage(ff, message);
        } catch (Exception t) {
            String message = "Erreur technique !!!";
            LOGGER.error(message, t);
            createErrorMessage(ff, message);
        }
    
        LOGGER.info("<=== End Async Method runAsyncCreateFichierFelix() ");
    
    }
    
    @Transactional
    public FichierFelixError createFichierFelixWithErrors(String numProg,
                                                          List<LignePreprep> lignePrepreps
                                                          ) throws IOException {
        Programme programme = programmeDao.findOne(numProg);
        if(programme == null) {
            TechnicalException technicalException = new TechnicalException(String.format("Impossible de trouver le programme %s", numProg));
            LOGGER.error(technicalException.getMessage(), technicalException);
            throw technicalException;
        }
    
        String fileName = DOC_PREFIX
                              + numProg + "_"
                              + programme.getTypeUtilisation().getCode() + "_"
                              + programme.getRionTheorique().getRion() + "_"
                              + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".csv";

        File tmpFile = new File(getPreprepDir() + File.separator + fileName);
        OutputStream out = new FileOutputStream(tmpFile);
        
        writeLines(out, head());
        int line = 1;
        ModelMapper modelMapper = new ModelMapper();
        
        List<String> errorsList = Lists.newArrayList();
        for (LignePreprep lignePreprep: lignePrepreps ) {
            FelixData felixData = modelMapper.map(lignePreprep, FelixData.class);
            
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
        
    
        FichierFelixError fichierFelixError = new FichierFelixError();
        fichierFelixError.setTmpFilename(tmpFile.getAbsolutePath());
        fichierFelixError.setFilename(fileName);
        fichierFelixError.setErrors(errorsList);
    
        IOUtils.closeQuietly(out);
        
        return fichierFelixError;
    }

    private String getPreprepDir() {
        return configAdmap.get(EnvConstants.FELIX_PREPREP_DIR.property());
    }

    @Transactional
    @Async("threadPoolTaskExecutor")
    public void asyncSendFichierFelix(ProgrammeDto programmeDto, UserDTO userDTO) {
        String numProg = programmeDto.getNumProg();
        /*programmeDto.setUsermaj(utilisateur);*/
        programmeDto.setUsermaj(userDTO.getUserId());
        try {
            
            // Regeneration du fichier
            List<LignePreprep> lignePrepreps = prepareFelixData(numProg);
            FichierFelixError fichierFelixWithErrors = createFichierFelixWithErrors(numProg, lignePrepreps);
            lignePrepreps.clear();
            
            FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
            ff.setNomFichier(fichierFelixWithErrors.getFilename());
            
            fichierFelixDao.saveAndFlush(ff);
            
            // Envoi du fichier via FTP
            File tempFile = new File(getPreprepDir() + File.separator + fichierFelixWithErrors.getFilename());
            LOGGER.debug("==> Debut Envoi du fichier à FELIX = " + ff.getNomFichier());
            SftpUtil.uploadFile(FELIX, tempFile, ff.getNomFichier());
            LOGGER.debug("<=== Fin Envoi du fichier à FELIX = " + ff.getNomFichier());
            programmeDto.setStatut(StatutProgramme.MIS_EN_REPART);
            programmeService.majStatut(programmeDto, userDTO);
            
            ff.setStatut(StatutFichierFelix.ENVOYE);
            fichierFelixDao.saveAndFlush(ff);
    
            if (tempFile.exists()) {
                FileUtils.forceDelete(tempFile);
            }
            
        } catch (Exception e) {
            FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
            String message = String.format("Probleme lors de l'envoi du fichier PREPREP  %s à FELIX", ff.getNomFichier());
            LOGGER.error(message, e);
            createErrorMessage(ff, message);

        }
        
    }
    
    private void createErrorMessage(FichierFelix ff, String message) {
        if(ff != null) {
            ff.setStatut(StatutFichierFelix.EN_ERREUR);
            FichierFelixLog felixLog = new FichierFelixLog();
            felixLog.setLog(message);
            felixLog.setDateCreation(new Date());
            fichierFelixLogDao.save(felixLog);
            ff.getLogs().add(felixLog);

            fichierFelixDao.saveAndFlush(ff);
        }
    }

    public String getFamilleUtil(String numprog){

        return programmeDao.findByNumProg(numprog).getFamille().getCode();
    }
}

