package com.example.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Util {

    // Map to store student ID and its details
    // This is a mock implementation. In a real-world scenario, this data might be fetched from a database or an external service.
    Map<String, String> studentDetails = new HashMap<>();

    public Util() {
        // Constructor
    }

    public  String getStudentDetails(String studentId) {
        if (studentDetails.containsKey(studentId)) {
            return studentDetails.get(studentId);
        }
        try {
            File file = new File("StudentRecords.csv");
            java.util.Scanner scanner = new java.util.Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",\\s*");

                if (parts[0].equals(studentId)) {
                    String studentDetail = String.format("Student ID: %s\nName: %s\nAge: %s\nGPA: %s",
                            parts[0], parts[1], parts[2], parts[3]);
                    studentDetails.put(studentId, studentDetail);
                    return studentDetail;
                }
            }
            scanner.close();
            return "Student not found with ID: " + studentId;

        } catch (java.io.FileNotFoundException e) {
            return "Error: Student records file not found";
        }
    }

    public static void main(String[] args) {
        Util tools = new Util();
        String[] ids = tools.getStudentIds().split(",");
        for (String id : ids) {
            System.out.println("Student ID: " + id);
            System.out.println(tools.getStudentDetails(id));
        }
    }

    public String getStudentIds() {
        if (studentDetails.isEmpty()) {
            try {
                File file = new File("StudentRecords.csv");
                java.util.Scanner scanner = new java.util.Scanner(file);

                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(",\\s*");
                    String studentDetail = String.format("Student ID: %s\nName: %s\nAge: %s\nGPA: %s",
                            parts[0], parts[1], parts[2], parts[3]);
                    studentDetails.put(parts[0], studentDetail);
                }
                scanner.close();
            } catch (java.io.FileNotFoundException e) {
                return "Error: Student records file not found";
            }
        }

        return String.join(",", studentDetails.keySet());
    }

}
