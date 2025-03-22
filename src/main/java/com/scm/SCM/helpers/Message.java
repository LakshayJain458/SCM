package com.scm.SCM.helpers;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
   private String content;
   private MessageType type = MessageType.green;
}
