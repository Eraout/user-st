package com.storage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


class User {
    private int id;
    private String name;
    private String password;

    public User(int id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "UserStorage: " + '\n' +
                "id: " + "#"+id +
                ", name:'" + name + '\'' +
                ", password:'" + password;
    }
}

public class UserStorage implements Iterable<User> {
    private Map<Integer, User> users;  // contains users
    public UserStorage() {
        users = new HashMap<>();
    }
    public void addUser(User user) {
        users.put(user.getId(), user);
    }
    public User getUser(int id) {
        return users.get(id);
    }
    public void removeUser(int id) {
        users.remove(id);
    }
    public void saveToFile(String filename) {   // saving to file
        try (FileWriter writer = new FileWriter(filename)) {
            for (User user : users.values()) {
                writer.write(user.getId() + ": " + user.getName() + ": " + user.getPassword() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadFromFile(String filename) {     // loading information from file
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                String[] parts = line.split(": ");
                if (parts.length == 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String password = parts[2];
                    User user = new User(id, name, password);
                    users.put(id, user);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void printAllUsers() {   // useless method
        for (User user : users.values()) {
            System.out.println(user);
        }
    }
    @Override
    public Iterator<User> iterator() {
        return users.values().iterator();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            UserStorage userStorage = new UserStorage();
            userStorage.loadFromFile("users.txt");
            try {
                new UserManagementGUI(userStorage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
       /* UserStorage userStorage = new UserStorage();   // command UI for this program
        userStorage.loadFromFile("users.txt");

        Scanner scanner = new Scanner(System.in);
        String command;

        do {
            System.out.println("Enter 'Help' to see all list of commands." + '\n'
                    + "Enter command: ");
            command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "help":
                    String[] commandList = {"add", "find", "remove", "showall", "exit", "help"};
                    for (String commandElement : commandList) {
                        System.out.println(commandElement);
                    }
                    break;
                case "add":
                    System.out.println("Enter ID: ");
                    int id = Integer.parseInt(scanner.next());
                    System.out.println("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    User newUser = new User(id, name, password);
                    userStorage.addUser(newUser);
                    break;
                case "find" :
                    System.out.println("Enter ID to find user: ");
                    int searchId = Integer.parseInt(scanner.nextLine());
                    User foundUser = userStorage.getUser(searchId);
                    if (foundUser != null) {
                        System.out.println("Found user: " + foundUser);
                    } else {
                        System.out.println("User not found.");
                    }
                    break;
                case "remove" :
                    System.out.println("Enter ID to remove user: ");
                    int removeId = Integer.parseInt(scanner.nextLine());
                    userStorage.removeUser(removeId);
                    break;
                case "showall" :
                    userStorage.printAllUser();
                    break;
                case "exit" :
                    break;
                default:
                    System.out.println("Invalid command. Try again.");
            }
        } while (!command.equalsIgnoreCase("exit"));
        userStorage.saveToFile("users.txt");
    } */
}
class UserManagementGUI {
    private UserStorage userStorage;
    private JFrame frame;
    private JTextField idField, nameField, passwordField;
    private JTextArea resultArea;

    public UserManagementGUI(UserStorage userStorage) throws IOException {
        this.userStorage = userStorage;
        createAndShowGUI();
    }
    private void createAndShowGUI() throws IOException {    // method create my frame
        Color backgroundColor = new Color(223,201,147);

        BufferedImage iconImage = ImageIO.read(new File("src/main/resources/keyIcon.png"));
        JFrame.setDefaultLookAndFeelDecorated(true);

        frame = new JFrame("User Management");
        frame.setIconImage(iconImage);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setUndecorated(true);
        frame.setSize(400,150);
        frame.setShape(new RoundRectangle2D.Double(0, 0, frame.getWidth(), frame.getHeight(), 30, 30));// size of frame
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(40, 40, 40)); // dark grey background




        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {   // saving changes after close
                userStorage.saveToFile("users.txt");
                System.exit(0);
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(3,2));
        inputPanel.add(new JLabel("ID: "));
        idField = new JTextField();
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Name: "));
        nameField = new JTextField();
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Password: "));
        passwordField = new JTextField();
        inputPanel.add(passwordField);
        inputPanel.setBackground(backgroundColor);



        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(backgroundColor);

        BufferedImage borderImage = ImageIO.read(new File("src/main/resources/border.png"));
        Border border = new ResizableImageBorder(borderImage, 32, 32);

        JButton addButton = new JButton(new ImageIcon("src/main/resources/plus.png"));
        addButton.setToolTipText("<html>" +
                "<span <span style='color: red; background-color: #FFFF00; border: 2px solid blue; padding: 5px solid ;'>Add User</span>" +
                "</html>");
        addButton.setBorder(border);
        addButton.setContentAreaFilled(false);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        JButton findButton = new JButton(new ImageIcon("src/main/resources/search.png"));
        findButton.setToolTipText("Find User");
        findButton.setBorder(border);
        findButton.setContentAreaFilled(false);
        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findUser();
            }
        });

        JButton removeButton = new JButton(new ImageIcon("src/main/resources/minus.png"));
        removeButton.setToolTipText("Remove User");
        removeButton.setBorder(border);
        removeButton.setContentAreaFilled(false);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeUser();
            }
        });

        JButton showAllButton = new JButton(new ImageIcon("src/main/resources/list.png"));
        showAllButton.setToolTipText("Show All Users");
        showAllButton.setBorder(border);
        showAllButton.setContentAreaFilled(false);
        showAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printAllUsers();
            }
        });


        // added buttons
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(findButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(removeButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(showAllButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        frame.add(scrollPane, BorderLayout.SOUTH);
        frame.setVisible(true);

    }
    private void addUser() {
        try {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String password = passwordField.getText();

            User newUser = new User(id, name, password);
            userStorage.addUser(newUser);
            updateResultArea("User added: " + newUser);
        } catch (NumberFormatException ex) {
            updateResultArea("Invalid ID format. Please enter a numeric ID.");
        }
       clearFields();
    }
    private void findUser() {
        try {
            int id = Integer.parseInt(idField.getText());
            User foundUser = userStorage.getUser(id);
            if (foundUser != null) {
                updateResultArea("User found: " + foundUser);
            } else  {
                updateResultArea("User not found.");
            }
        } catch (NumberFormatException ex) {
            updateResultArea("Invalid ID format. Please enter a numeric ID.");
        }
        clearFields();
    }
    private void removeUser() {
        try {
            int id = Integer.parseInt(idField.getText());
            userStorage.removeUser(id);
            updateResultArea("User removed with ID: " + id);
        } catch (NumberFormatException ex) {
            updateResultArea("Invalid ID format. Please enter a numeric ID.");
        }
        clearFields();
    }
    private void printAllUsers() {
       JFrame userListFrame = new JFrame("All Users");
       userListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       userListFrame.setSize(400,300);
       DefaultListModel<User> listModel = new DefaultListModel<>();
       for (User user : userStorage) {
           listModel.addElement(user);
       }
       JList<User> userList = new JList<>(listModel);
       JScrollPane scrollPane = new JScrollPane(userList);
       userListFrame.add(scrollPane);
       userListFrame.setVisible(true);
    }
    private void updateResultArea(String text) {
        resultArea.setText(text);
        int lines = resultArea.getLineCount();
        resultArea.setRows(lines);
    }
    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        passwordField.setText("");
    }
}