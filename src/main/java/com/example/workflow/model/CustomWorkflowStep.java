package com.example.workflow.model;

public record CustomWorkflowStep(
    String stepId,
    String stepAction,
    String stepResult,
    String stepErrorMessage,
    boolean isActive
) {
    // Fields are automatically generated in a record; no need to declare them explicitly.
}
