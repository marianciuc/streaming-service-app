/*
 * Copyright (c) 2024 Vladimir Marianciuc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in
 *    all copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *      THE SOFTWARE.
 */

package io.github.marianciuc.streamingservice.subscription.jobs;

import io.github.marianciuc.streamingservice.subscription.entity.SubscriptionStatus;
import io.github.marianciuc.streamingservice.subscription.entity.UserSubscriptions;
import io.github.marianciuc.streamingservice.subscription.service.impl.UserSubscriptionServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.*;

public class FetchActiveSubscriptionsJobTest {
    @MockBean
    private JobExecutionContext jobExecutionContext;
    
    @MockBean
    private UserSubscriptionServiceImpl service;
    
    @SneakyThrows
    @Test
    public void testExecute() throws JobExecutionException {
        FetchActiveSubscriptionsJob aSJob = new FetchActiveSubscriptionsJob(service);
            
        UserSubscriptions sub1 = new UserSubscriptions();
        UserSubscriptions sub2 = new UserSubscriptions();
        
        when(service.getAllUserSubscriptionsByStatusAndEndDate(any(), any())).thenReturn(Arrays.asList(sub1, sub2));
        
        aSJob.execute(jobExecutionContext);

        verify(service, times(1)).unsubscribeUser(sub1);
        verify(service, times(1)).unsubscribeUser(sub2);
        verify(service, times(1)).getAllUserSubscriptionsByStatusAndEndDate(SubscriptionStatus.ACTIVE, LocalDate.now());
    }
}