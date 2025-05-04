package com.example.workflow.utils;

import io.modelcontextprotocol.spec.McpSchema;

public class MCPUtils {

    static McpSchema.LoggingMessageNotification logInfo(String message) {
        return new McpSchema.LoggingMessageNotification(McpSchema.LoggingLevel.INFO, "workflowmcpserver", message);
    }

    static McpSchema.LoggingMessageNotification logError(String message) {
        return new McpSchema.LoggingMessageNotification(McpSchema.LoggingLevel.ERROR, "workflowmcpserver", message);
    }
}
