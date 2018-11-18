package com.emprovise.service.dataservice.batch;

import com.emprovise.service.dataservice.dto.StockDetail;
import com.emprovise.service.dataservice.resource.StockResource;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Random;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class JobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private SimpleJobLauncher jobLauncher;
    @Autowired
    private StockResource stockResource;
    private static Logger logger = LoggerFactory.getLogger(JobConfiguration.class);

    @Bean
    public ResourcelessTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public MapJobRepositoryFactoryBean mapJobRepositoryFactory(ResourcelessTransactionManager txManager) throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean(txManager);
        factory.afterPropertiesSet();
        return factory;
    }

    @Bean
    public JobRepository jobRepository(MapJobRepositoryFactoryBean factory) throws Exception {
        return factory.getObject();
    }

    @Bean
    public SimpleJobLauncher jobLauncher(JobRepository jobRepository) {
        SimpleJobLauncher launcher = new SimpleJobLauncher();
        launcher.setJobRepository(jobRepository);
        return launcher;
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
                .start(startStep())
                .build();
    }

    @Bean
    public Step startStep() {
        return stepBuilderFactory.get("startStep")
                .tasklet(addRandomStocksTasklet()).build();
    }

    @Bean
    public Tasklet addRandomStocksTasklet() {
        return (contribution, chunkContext) -> {
            StockDetail stock = new StockDetail();
            stock.setStockName(RandomStringUtils.randomAlphabetic(10));
            Random random = new Random();
            stock.setStockName(RandomStringUtils.randomAlphabetic(10));
            stock.setHigh(random.doubles(0, (99999 + 1)).findFirst().getAsDouble());
            stock.setLow(random.doubles(0, (99999 + 1)).findFirst().getAsDouble());
            stock.setOpen(random.doubles(0, (99999 + 1)).findFirst().getAsDouble());
            stock.setClose(random.doubles(0, (99999 + 1)).findFirst().getAsDouble());
            stock.setDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            stockResource.add(stock).subscribe(System.out::println);
            logger.info(String.format("Stock %s Added", stock.getStockName()));
            return RepeatStatus.FINISHED;
        };
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void jobRunner() throws Exception {
        JobParameters param = new JobParametersBuilder()
                .addString("JobID", String.valueOf(System.currentTimeMillis())).toJobParameters();
        JobExecution execution = jobLauncher.run(job(), param);
        System.out.println("Job finished with status :" + execution.getStatus());
    }
}
