package io.github.marianciuc.streamingservice.subscription.jobs;

import io.github.marianciuc.streamingservice.subscription.entity.SubscriptionStatus;
import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import io.github.marianciuc.streamingservice.subscription.kafka.KafkaUserSubscriptionProducer;
import io.github.marianciuc.streamingservice.subscription.service.UserSubscriptionService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

/**
 * FetchCanceledSubscriptionsJob is a class that implements the Job interface and is responsible for fetching canceled subscriptions
 * and sending them to a Kafka topic.
 */
@Component
@RequiredArgsConstructor
public class FetchCanceledSubscriptionsJob implements Job {

    private final UserSubscriptionService service;
    private final KafkaUserSubscriptionProducer kafkaProducerService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        LocalDate now = LocalDate.now();
        List<UserSubscriptions> cancelledSubscriptions = service.getAllSubscriptionsByStatusAndEndDate(SubscriptionStatus.CANCELLED, now);

        for (UserSubscriptions subscription : cancelledSubscriptions) {
            kafkaProducerService.sendTopic(subscription);
        }
    }
}
