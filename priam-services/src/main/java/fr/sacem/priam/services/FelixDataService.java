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
import fr.sacem.priam.model.dao.jpa.*;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.dto.FelixData;
import fr.sacem.priam.model.domain.dto.FichierFelixError;
import fr.sacem.priam.services.utils.FelixDataSpringValidator;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
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

import static fr.sacem.priam.common.util.SftpUtil.SftpServer.FELIX;
import static java.util.Arrays.asList;
import static org.apache.commons.io.IOUtils.LINE_SEPARATOR;

/**
 * Created by benmerzoukah on 18/08/2017.
 */
@Component
public class FelixDataService extends FelixDataServiceAbstract{
    

    @Autowired
    private LigneProgrammeCPDao ligneProgrammeCPDao;


    public List<LignePreprep> getListLignesSelectionnees(String numProg) {
       return ligneProgrammeCPDao.findLigneProgrammeSelectionnesForFelix(numProg);
    }
}

