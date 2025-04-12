package com.example.service;

import java.util.Map;

public record CustomWorkflow(
                String workflowName, 
                String workflowDescription, 
                Map<String, CustomWorkflowStep> workflowSteps, 
                boolean isActive
                ) {

    // Fields are automatically generated in a record; no need to declare them explicitly.

}
