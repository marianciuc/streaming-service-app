package io.github.marianciuc.streamingservice.subscription.config;

import io.github.marianciuc.streamingservice.subscription.jobs.FetchActiveSubscriptionsJob;
import io.github.marianciuc.streamingservice.subscription.jobs.FetchCanceledSubscriptionsJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This configuration class builds and configures all the necessary Quartz jobs and triggers for subscription management.
 */
@Configuration
public class QuartzConfig {

    private static final String CRON_SCHEDULE = "0 0 0 * * ?";
    private static final String CANCELLED_SUBSCRIPTION_GROUP = "CancelledSubscriptionGroup";
    private static final String ACTIVE_SUBSCRIPTION_GROUP = "ActiveSubscriptionGroup";

    /**
     * Creates the job detail for the cancelled subscriptions.
     *
     * @return The job detail for cancelled subscriptions.
     */
    @Bean
    public JobDetail cancelledSubscriptionsJobDetail() {
        return createJobDetail(FetchCanceledSubscriptionsJob.class, "cancelledSubscriptionsJob", CANCELLED_SUBSCRIPTION_GROUP);
    }

    /**
     * Creates the trigger that fires the job for cancelled subscriptions.
     *
     * @return The trigger for the cancelled subscriptions job.
     */
    @Bean
    public Trigger cancelledSubscriptionsJobTrigger() {
        return createTrigger(cancelledSubscriptionsJobDetail(), "cancelledSubscriptionsJobTrigger", CANCELLED_SUBSCRIPTION_GROUP);
    }

    /**
     * Creates the job detail for the active subscriptions.
     *
     * @return The job detail for active subscriptions.
     */
    @Bean
    public JobDetail activeSubscriptionsJobDetail() {
        return createJobDetail(FetchActiveSubscriptionsJob.class, "activeSubscriptionsJob", ACTIVE_SUBSCRIPTION_GROUP);
    }

    /**
     * Creates the trigger that fires the job for active subscriptions.
     *
     * @return The trigger for the active subscriptions job.
     */
    @Bean
    public Trigger activeSubscriptionsJobTrigger() {
        return createTrigger(activeSubscriptionsJobDetail(), "activeSubscriptionsJobTrigger", ACTIVE_SUBSCRIPTION_GROUP);
    }

    /**
     * Creates a job detail for a given job class, identity, and group.
     *
     * @param jobClass The job class.
     * @param identity The job identity.
     * @param group The job group.
     *
     * @return A constructed job detail.
     */
    private JobDetail createJobDetail(Class<? extends Job> jobClass, String identity, String group) {
        return JobBuilder.newJob(jobClass)
                .withIdentity(identity, group)
                .storeDurably()
                .build();
    }

    /**
     * Creates a trigger for a given job detail, identity, and group.
     *
     * @param jobDetail The job detail.
     * @param identity The trigger identity.
     * @param group The trigger group.
     *
     * @return A constructed trigger.
     */
    private Trigger createTrigger(JobDetail jobDetail, String identity, String group) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(identity, group)
                .withSchedule(CronScheduleBuilder.cronSchedule(CRON_SCHEDULE))
                .build();
    }
}
