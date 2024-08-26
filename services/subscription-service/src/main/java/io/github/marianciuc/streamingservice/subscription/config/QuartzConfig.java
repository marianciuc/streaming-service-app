package io.github.marianciuc.streamingservice.subscription.config;

import io.github.marianciuc.streamingservice.subscription.jobs.FetchCanceledSubscriptionsJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    @Bean
    public JobDetail cancelledSubscriptionsJobDetail() {
        return JobBuilder.newJob(FetchCanceledSubscriptionsJob.class)
                .withIdentity("cancelledSubscriptionsJob", "group1")
                .storeDurably()
                .build();
    }

    @Bean
    public JobDetail activeSubscriptionsJobDetail() {
        return JobBuilder.newJob(FetchCanceledSubscriptionsJob.class)
                .withIdentity("activeSubscriptionsJob", "group2")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger cancelledSubscriptionsJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(cancelledSubscriptionsJobDetail())
                .withIdentity("cancelledSubscriptionsJobTrigger", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();
    }

    @Bean
    public Trigger activeSubscriptionsJobTrigger() {
        return TriggerBuilder.newTrigger()
                .forJob(cancelledSubscriptionsJobDetail())
                .withIdentity("activedSubscriptionsJobTrigger", "group2")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ?"))
                .build();
    }
}
