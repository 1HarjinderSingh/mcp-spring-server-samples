package com.example.workflow.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.workflow.model.CustomWorkflow;
import com.example.workflow.model.CustomWorkflowStep;

/*
    * CustomWorkflowManager is a class that manages the workflows in the system.
 */
@Service
public class CustomWorkflowManager {

    private Map<String, CustomWorkflow> workflows;

    public CustomWorkflowManager() {
        workflows = new HashMap<>();
        loadWorkflows();
    }

    public void loadWorkflows() {
        // Implement the logic to create a new workflow
        Map<String, CustomWorkflowStep> workflowSteps = new HashMap<>();

        workflowSteps.put("Upgrade Spring Boot Version", 
                                new CustomWorkflowStep("Upgrade Spring Boot Version", 
                                                    "Description of Step 1", 
                                                    "", 
                                                    "", 
                                                    true
                                                    )
                                );

        workflowSteps.put("Upgrade Gradle version", 
                                new CustomWorkflowStep("Upgrade Gradle version", 
                                                        "Description of Step 2", 
                                                        "", 
                                                        "", 
                                                        true
                                                        )
                                );

        workflows.put("Upgrade Framework", 
                        new CustomWorkflow("Upgrade Framework", 
                                            "Upgrade framework version for my application", 
                                            workflowSteps, 
                                            true
                            )
                        );

    }

    public List<CustomWorkflow> getAvailableWorkflows() {
 
        List<CustomWorkflow> workflowList =  workflows.values().stream()
                                                        .filter(CustomWorkflow::isActive)
                                                        .toList();
        System.out.println("Available workflows: " + workflowList);
        return workflowList;
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
