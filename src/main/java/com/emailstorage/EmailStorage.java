package com.emailstorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class EmailStorage {
    private Map<String, String> emailAddresses;

    public EmailStorage() {
        emailAddresses = new LinkedHashMap<>();
    }
    public void addEmailAddress( String name, String email) {
        emailAddresses.put( name, email);
    }
    public String getEmailAddress(String name) {
        return emailAddresses.get(name);
    }
    public void removeEmailAddress(String name) {
        emailAddresses.remove(name);
    }
    public void saveToFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            for (Map.Entry<String, String> entry : emailAddresses.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadFromFile(String filename) {
        try(Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    emailAddresses.put(parts[0], parts[1]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void printAllEmailAddresses() {
        for (Map.Entry<String, String> entry : emailAddresses.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    public static void main(String[] args) {
        EmailStorage emailStorage = new EmailStorage();
        emailStorage.loadFromFile("emails.txt");

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.println("Enter command (help to see all commands): ");
            command = scanner.nextLine();
            switch (command.toLowerCase()) {
                case "help" :
                    String[] commandsList = {"add", "find", "remove","printall", "exit", "help"};
                    for (String commandElement : commandsList) {
                        System.out.println(commandElement);
                    }
                    break;
                case "add" :
                    System.out.println("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter email: ");
                    String email = scanner.nextLine();
                    emailStorage.addEmailAddress(name, email);
                    break;
                case "find" :
                    System.out.println("Enter name to find email: ");
                    String searchName = scanner.nextLine();
                    String foundEmail = emailStorage.getEmailAddress(searchName);
                    if (foundEmail != null) {
                        System.out.println("Found email: " + foundEmail);
                    } else {
                        System.out.println("Email not found.");
                    }
                    break;
                case "remove" :
                    System.out.println("Enter name to remove: ");
                    String removeName = scanner.nextLine();
                    emailStorage.removeEmailAddress(removeName);
                    break;
                case "printall" :
                    emailStorage.printAllEmailAddresses();
                    break;
                case "exit" :
                    break;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        } while (!command.equalsIgnoreCase("exit"));

        emailStorage.saveToFile("emails.txt");
    }
}
