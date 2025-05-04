package com.example.workflow.resource;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema.TextResourceContents;
import io.modelcontextprotocol.spec.McpSchema.BlobResourceContents;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.Resource;
import io.modelcontextprotocol.spec.McpSchema.Annotations;
import io.modelcontextprotocol.spec.McpSchema.LoggingMessageNotification;
import io.modelcontextprotocol.spec.McpSchema.LoggingLevel;
import io.modelcontextprotocol.spec.McpSchema.ReadResourceResult;

import com.example.workflow.utils.FileUtils;

import java.io.IOException;
import java.util.List;

@Component
public class WorkflowResources {

    private final Logger logger = LoggerFactory.getLogger(WorkflowResources.class);

    @Bean
    List<McpServerFeatures.SyncResourceSpecification> loadWorkflowResource() {

        // Define the resource using McpSchema.Resource
        Resource resource = new Resource("file://MCPResource.json",
                "MCPResource.json", "Workflow Resource. A resource that provides a workflow overview and steps.",
                "application/json",
                new Annotations(List.of(Role.ASSISTANT), 1.0));

        McpServerFeatures.SyncResourceSpecification reg =
                new McpServerFeatures.SyncResourceSpecification(
                        resource,
                        (exchange, request) -> {

                            logger.debug("JournalMCPResource called with request {}", request);

                            String textResource = "", blobResource = "";

                            try {
                                textResource = FileUtils.readJSON(resource.name());
                                blobResource = FileUtils.readPngAsBase64("images/MCP.png");

                            } catch (IOException e) {
                                exchange.loggingNotification(new LoggingMessageNotification(LoggingLevel.ERROR, "workflowserver", e.getMessage()));
                                logger.error("Error reading DM Journal file. Returning empty journal.", e);
                            }

                            TextResourceContents textContent = new TextResourceContents(
                                                                            resource.uri(),
                                                                            resource.mimeType(),
                                                                            textResource
                                                );
                            
                            BlobResourceContents blobContent = new BlobResourceContents(
                                                                            resource.uri(),
                                                                            resource.mimeType(),
                                                                            blobResource
                                            );

                            return new ReadResourceResult(List.of(textContent, blobContent));
                        });

        return List.of(reg);
    }
}
