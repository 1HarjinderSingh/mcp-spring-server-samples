package com.example.workflow.utils;

import java.util.Map;

public class PromptUtils {

     public static String parsePrompt(String promptText, Map<String, String> args) {
            if (args == null || args.isEmpty()) {
                return promptText;
            }

            String parsedPrompt = promptText;
            for (Map.Entry<String, String> entry : args.entrySet()) {
                String placeholder = "{" + entry.getKey() + "}";
                parsedPrompt = parsedPrompt.replace(placeholder, entry.getValue());
            }
            return parsedPrompt;
     }

}
