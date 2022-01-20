package com.example.demo;

import java.net.*;

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
	
	URL url = new URL("https://www.discord.com");
	HttpURLConnection con = (HttpURLConnection) url.openConnection();
	con.setRequestMethod("POST");
	con.setRequestProperty("Content-Type", "application/json");
	con.setDoOutput(true);
	OutputStream os = con.getOutputStream();
	String payload = "{\"content\":\"" + message.getText() + "\"}";
	os.write(payload.getBytes());
	os.close();
	
	try {
		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);
	} catch (Exception ex) {
		System.out.println("Exception: " + ex);
	}
	
	BufferedReader in = new BufferedReader(new InputStreamReader(
			con.getInputStream()));
	String inputLine;
	StringBuffer response = new StringBuffer();
	while ((inputLine = in.readLine()) != null) {
		response.append(inputLine);
	}
	in.close();
	System.out.println("POST response content: " + response.toString());
	
	
	return new TopicMessage(HtmlUtils.htmlEscape(message.getText()),
			HtmlUtils.htmlEscape(message.getUser()));
  }

}