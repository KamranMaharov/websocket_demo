package com.example.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.io.*;

@Controller
public class ChatController {
  @MessageMapping("/postmessage")
  @SendTo("/topic/chat")
  public TopicMessage greeting(ChatMessage message) throws Exception {
	BufferedWriter writer = new BufferedWriter(new FileWriter("chat-log.txt", true));
	writer.write(message.getText() + " " + message.getUser() + "\n");
	writer.close();

	return new TopicMessage(HtmlUtils.htmlEscape(message.getText()),
			HtmlUtils.htmlEscape(message.getUser()));
  }

}