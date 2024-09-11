/*
 * Copyright (c) 2024  Vladimir Marianciuc. All Rights Reserved.
 *
 * Project: STREAMING SERVICE APP
 * File: CreadtedNewMessage.java
 *
 */

package io.github.marianciuc.streamingservice.moderation.kafka.messages;

import java.util.UUID;

public record CreatedNewMessage (
        String content,
        UUID authorId,
        String authorUsername,
        UUID messageId
){

}
