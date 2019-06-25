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
public class SaveListener extends StepExecutionListenerSupport {

    @Autowired
    private DataSource dataSource;


    @Override
    public void beforeStep(StepExecution stepExecution) {
        String typeCMS = stepExecution.getJobParameters().getString("typeCMS");
        new JdbcTemplate(dataSource).update("DELETE FROM PRIAM_CATCMS_PARTICIPANTS_SAVE WHERE TYPE_CMS ='" + typeCMS + "'");
    }
}
