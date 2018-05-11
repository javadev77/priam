package fr.sacem.priam.batch.common.util;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by benmerzoukah on 06/10/2017.
 */
public class SftpGetRemoteFilesTasklet implements Tasklet, InitializingBean {
    
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
	  return null;
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
	  
    }
}
