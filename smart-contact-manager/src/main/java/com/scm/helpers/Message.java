package com.scm.helpers;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {

    private String content;

    @Builder.Default
    private MessageType type = MessageType.BLUE;
}
