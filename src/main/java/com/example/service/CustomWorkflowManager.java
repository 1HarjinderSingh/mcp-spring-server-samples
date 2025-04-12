package com.example.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    * CustomWorkflowManager is a class that manages the workflows in the system.
 */
public class CustomWorkflowManager {

    private Map<String, CustomWorkflow> workflows;

    public CustomWorkflowManager() {
        workflows = new HashMap<>();
    }

    public void loadWorkflows() {
        // Implement the logic to create a new workflow
    }

    public List<CustomWorkflow> getAvailableWorkflows() {
 
        return workflows.values().stream()
                .filter(CustomWorkflow::isActive)
                .toList();
    }
    
    public List<CustomWorkflowStep> getWorkflowSteps(String workflowName) {
        
        return workflows.get(workflowName).workflowSteps().values().stream()
                .filter(CustomWorkflowStep::isActive)
                .toList();
    }

    public CustomWorkflowStep getStepDetails(String workflowName, String stepId) {

        return workflows.get(workflowName).workflowSteps().get(stepId);
        
    }
}
