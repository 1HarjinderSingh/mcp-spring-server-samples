package com.example.workflow.utils;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;


@Service
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    @Value("${workflowmcpserver.dir:${user.dir}/.workflowmcpserver}")
    private String workflowDirectory;

    private Path workflowDirPath;

    /**
     * Checks if the given path contains any upward directory traversal segments ("..").
     *
     * @param path the path to check, represented as a {@link Path} object
     * @return {@code true} if the path contains any ".." segments, indicating upward traversal;
     *         {@code false} otherwise
     */
    private static boolean containsUpwardTraversal(Path path) {
        for (Path segment : path) {
            if (segment.toString().equals("..")) {
                return true;
            }
        }
        return false;
    }

    @PostConstruct
    private void init() throws IOException {
        workflowDirPath = Path.of(workflowDirectory);
        if (!Files.exists(workflowDirPath)) {
            Files.createDirectories(workflowDirPath);
        } else {
            logger.debug("Workflow directory already exists {}", workflowDirPath);
        }
    }

 
    public static String readFile(String fileName) throws IOException {
        
        Resource resource = new ClassPathResource(fileName.toString());
        Path filePath = resource.getFile().toPath();

        isValidFile(filePath);

        String template = Files.readString(filePath);

        return template;
    }

    private static boolean isValidFile(Path fileName) {
        if (fileName.isAbsolute()) {
            throw new IllegalArgumentException(String.format("File name must be relative to workflow directory: %s", fileName));
        } else if (containsUpwardTraversal(fileName)) {
            throw new IllegalArgumentException(String.format("File name cannot contain upward traversal: ", fileName));
        }

        return true;
    }

    public static String readPngAsBase64(String filePath) throws IOException {
        // Load the PNG file from the classpath
        ClassPathResource resource = new ClassPathResource(filePath);

        // Read the file content as bytes
        byte[] fileBytes = Files.readAllBytes(resource.getFile().toPath());

        // Convert the bytes to a Base64 encoded string
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    public static String readJSON(String filePath) throws IOException {
        // Load the JSON file from the classpath
        ClassPathResource resource = new ClassPathResource(filePath);

        // Read the file content as bytes
        byte[] fileBytes = Files.readAllBytes(resource.getFile().toPath());

        // Convert the bytes to a string
        return new String(fileBytes);
    }

}