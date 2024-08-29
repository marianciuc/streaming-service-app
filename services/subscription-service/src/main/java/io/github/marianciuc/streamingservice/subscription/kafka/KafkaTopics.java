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

package io.github.marianciuc.streamingservice.subscription.kafka;

/**
 * This class provides constants for Kafka topics that are used in the application.
 */
public class KafkaTopics {
    public final static String UNSUBSCRIBE_USER_TOPIC = "unsubscribe-user-topic";
    public final static String ORDER_SUCCESSFULLY_COMPLETED_TOPIC = "order-successfully-completed-topic";
    public final static String RESOLUTION_CREATED_TOPIC = "resolution-created-topic";
    public final static String RESOLUTION_UPDATED_TOPIC = "resolution-updated-topic";
    public final static String RESOLUTION_DELETED_TOPIC = "resolution-deleted-topic";
}
