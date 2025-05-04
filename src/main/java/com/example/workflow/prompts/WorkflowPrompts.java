package com.example.workflow.prompts;

import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpServerFeatures.SyncPromptSpecification;
import io.modelcontextprotocol.spec.McpSchema.EmbeddedResource;
import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.ImageContent;
import io.modelcontextprotocol.spec.McpSchema.TextResourceContents;
import io.modelcontextprotocol.spec.McpSchema.Prompt;
import io.modelcontextprotocol.spec.McpSchema.PromptArgument;
import io.modelcontextprotocol.spec.McpSchema.PromptMessage;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import io.modelcontextprotocol.spec.McpSchema.Role;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.example.workflow.utils.FileUtils;
import java.io.IOException;

import java.util.List;

@Service
public class WorkflowPrompts {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowPrompts.class);

    @Bean
    public List<SyncPromptSpecification> myPrompts() {
        var prompt = new Prompt("greeting", "A friendly greeting prompt",
            List.of(new PromptArgument("name", "The name to greet", true)));

        var promptSpecification = new McpServerFeatures.SyncPromptSpecification(prompt, (exchange, getPromptRequest) -> {
            String nameArgument = (String) getPromptRequest.arguments().get("name");
            if (nameArgument == null) { nameArgument = "friend"; }
            var userMessage = new PromptMessage(Role.USER, new TextContent("Hello " + nameArgument + "! How can I assist you today?"));
            return new GetPromptResult("A personalized greeting message", List.of(userMessage));
        });

        logger.debug("Prompt specification created");
  
        return List.of(promptSpecification);
    }

    @Bean
    public List<SyncPromptSpecification> conversationPrompts() {
        var prompt = new Prompt("conversation", "A friendly conversational prompt", null);

        var promptSpecification = new McpServerFeatures.SyncPromptSpecification(prompt, (exchange, getPromptRequest) -> {
            var userMessage = new PromptMessage(Role.USER, new TextContent("I am ready to assit you with anything you need!"));
            var userMessage2 = new PromptMessage(Role.USER, new TextContent("Here is my first tip: Always ask questions!"));
            var userMessage3 = new PromptMessage(Role.USER, new TextContent("Here is my second tip: Always take notes!"));
            // Convert the file content to Base64 and update the ImageContent
            String base64Content;
            try {
                base64Content = FileUtils.readPngAsBase64("images/MCP.png");
            } catch (IOException e) {
                logger.error("Failed to read PNG file as Base64", e);
                base64Content = "No Image Found"; 
            }
            var imageContent = new PromptMessage(Role.USER, new ImageContent(List.of(Role.USER), 0d, base64Content, "image/jpeg"));

            // Create an EmbeddedResource object with the image content
            var embeddedResource = new PromptMessage(Role.USER, 
                                                        new EmbeddedResource(
                                                            List.of(Role.ASSISTANT), 
                                                            0d, 
                                                            new TextResourceContents(
                                                                "images/MCP.png", 
                                                                "image/png", 
                                                                "MCP flow diagram")
                                                        )
                                                    );

            return new GetPromptResult("A friendly convresation", List.of(userMessage, userMessage2, userMessage3, imageContent, embeddedResource));
        });

        return List.of(promptSpecification);
    }

}
