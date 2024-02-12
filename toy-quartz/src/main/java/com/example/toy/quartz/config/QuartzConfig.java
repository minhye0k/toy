package com.example.toy.quartz.config;

import com.example.toy.quartz.factory.AutowiringSpringBeanJobFactory;
import com.example.toy.quartz.listener.JobListener;
import com.example.toy.quartz.listener.TriggerListener;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
public class QuartzConfig {
    private final QuartzProperties quartzProperties;
    private final TriggerListener triggerListener;
    private final JobListener jobsListener;
    private final DataSource dataSource;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ApplicationContext applicationContext, PlatformTransactionManager transactionManager){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setApplicationContext(applicationContext);

        Properties properties = new Properties();
        properties.putAll(quartzProperties.getProperties());

//        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        schedulerFactoryBean.setGlobalTriggerListeners(triggerListener);
        schedulerFactoryBean.setGlobalJobListeners(jobsListener);
        schedulerFactoryBean.setTransactionManager(transactionManager);
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setQuartzProperties(properties);
        return schedulerFactoryBean;
    }
}
