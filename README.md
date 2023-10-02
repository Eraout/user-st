# User Management System

## Overview

This **Java-based User Management System** project allows you to **add**, **find**, **remove**, and **list** users. It utilizes Swing for the graphical user interface and has features for both text-based and graphical interaction. The application stores user data in a map and also allows for data persistence by reading and writing to a `users.txt` file.

## Features

- **Add new users**: Specify an ID, name, and password.
- **Find existing users**: Search based on their ID.
- **Remove users**: Delete by ID.
- **List all current users**: View all users.
- **Data Persistence**: Data is stored in a `users.txt` file.

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)**
- **Java Swing library**

### Running the Application

1. **Clone** the repository or download the source files.
2. Open a terminal and **navigate** to the directory containing the source files.
3. Run `javac UserStorage.java` to compile the Java source file.
4. Run `java UserStorage` to execute the compiled Java program.

## Usage

### GUI Version

1. The main window contains input fields for `ID`, `Name`, and `Password`.
2. Use the buttons to perform operations:
    - `Add User`: Adds a new user.
    - `Find User`: Searches for a user by ID and displays the user's details.
    - `Remove User`: Removes a user by ID.
    - `Show All Users`: Opens a new window listing all users.

### Command Line Version

*Uncomment the section in `UserStorage.java` that provides a text-based interface and recompile.*

## Code Structure

- **`User` Class**: Represents a user with attributes `ID`, `Name`, and `Password`.
- **`UserStorage` Class**: Manages a collection of users and provides methods to manipulate them. Also provides file-based data persistence.
- **`UserManagementGUI` Class**: Provides a GUI interface for interacting with the `UserStorage`.

## TODO

- [ ] Implement password encryption for data storage.
- [ ] Add data validation for the input fields.
- [ ] Improve the user interface design.

## Author

*Eraout*
