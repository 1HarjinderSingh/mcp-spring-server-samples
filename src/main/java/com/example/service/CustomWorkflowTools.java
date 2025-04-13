package com.example.service;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
public class CustomWorkflowTools {

    private CustomWorkflowManager workflowManager;

    public CustomWorkflowTools(CustomWorkflowManager workflowManager) {
        this.workflowManager = workflowManager;
    }

    @Tool(description = """
						Gives the list of avilable workflows and their description. 
						Workflow description will define the goal of the workflow, like "Upgarde Orchestra Framework version". 
						Calling getWorkflowSteps(workflowName) will give you a list of steps in the workflow.
						""")
    public List<CustomWorkflow> getWorkflowList() {

		System.out.println("Fetching available workflows...");
		System.out.println("Available workflows: " + workflowManager.getAvailableWorkflows());
		return workflowManager.getAvailableWorkflows();
	}


	@Tool(description = """
						Gives list of the Steps to be pefromed to exectute given workflow, like Step 1, Step 2.
						Calling getStepDetails(stepId) will give you a list of workflow steps.
						""")
	public List<CustomWorkflowStep> getWorkflowSteps(@ToolParam(description = "Workflow name") String workflowName) {
        
		if (workflowName == null || workflowName.isEmpty()) {
            return List.of(new CustomWorkflowStep("Error", "Workflow name cannot be null or empty.", "", "", true));
        }
        
		return workflowManager.getWorkflowSteps(workflowName);
        
	}

	@Tool(description = """
		Given workflow name and step id, returns workflow step deatils. Each workflow step will contain following details:
			stepId - unique ID of the step
			stepAction - action to be performed in the step, like "Upgrade Orchestra Framework version"
			stepResult - result of the action performed in the step, like "Upgrade successful"
			stepErrorMessage - error message if any error occurred during the action, like "Upgrade failed due to network issue"
			isActive - true if the step is active, false if the step is inactive
			Example:
			Step ID: 1
			Step Action: Upgrade Orchestra Framework version
			Step Result: Upgrade successful
			Step Error Message: null
			Is Active: true
		If isActive is true, execute stepAction and move to next step or else skip the step and move to the next step.
		""")
	public CustomWorkflowStep getStepDetails(@ToolParam(description = "Workflow name") String workflowName, @ToolParam(description = "Step ID") String stepId) {

		if (workflowName == null || workflowName.isEmpty()) {
            return new CustomWorkflowStep("Error", "Workflow name cannot be null or empty.", "", "", true);
        }

		if (stepId == null || stepId.isEmpty()) {
			return new CustomWorkflowStep("Error", "Step ID cannot be null or empty. Rerun this step", "", "", true);
		}

		return workflowManager.getStepDetails(workflowName, stepId);
	}

}
