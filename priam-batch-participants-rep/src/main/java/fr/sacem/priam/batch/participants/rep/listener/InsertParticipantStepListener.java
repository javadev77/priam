package fr.sacem.priam.batch.participants.rep.listener;

import javax.sql.DataSource;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class InsertParticipantStepListener extends StepExecutionListenerSupport {

    @Autowired
    private DataSource dataSource;


    @Override
    public void beforeStep(StepExecution stepExecution) {
        new JdbcTemplate(dataSource).update("TRUNCATE PRIAM_CATCMS_PARTICIPANTS ");
    }
}
