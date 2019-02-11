package fr.sacem.priam.batch.common.fv.config;

import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class AsyncBatchConfig {

    @Bean
    public ResourcelessTransactionManager transactionManagerRss() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository jobRepositoryAsync(ResourcelessTransactionManager transactionManagerRss) throws Exception {
        MapJobRepositoryFactoryBean mapJobRepositoryFactoryBean = new MapJobRepositoryFactoryBean(transactionManagerRss);
        mapJobRepositoryFactoryBean.setTransactionManager(transactionManagerRss);
        return mapJobRepositoryFactoryBean.getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncherAsync(JobRepository jobRepositoryAsync) {
        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(jobRepositoryAsync);
        simpleJobLauncher.setTaskExecutor(createTaskExecutor());
        return simpleJobLauncher;
    }


    @Bean
    public TaskExecutor createTaskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }
}
