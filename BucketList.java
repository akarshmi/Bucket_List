package BucketList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class BucketList {

    static String NAME;
    static String USERNAME;
    static Scanner scInput = new Scanner(System.in);
    static BufferedReader brInput = new BufferedReader(new InputStreamReader(System.in));

    public static void newUser(String Name, String UserName, String Password) throws SQLException {

            Connection con = DatabaseConnection.getConnection();
            System.err.println("Connection Established Successfully.");

            String createUserQuery = "INSERT INTO users (name,username,password) VALUES (?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(createUserQuery);

            pstmt.setString(1, Name);
            pstmt.setString(2, UserName);
            pstmt.setString(3, Password);

            int rowsAffected = pstmt.executeUpdate();

            // Check if the insert was successful
            if (rowsAffected > 0) {
                System.out.println("Registered successfully!");
            } else {
                System.out.println("Registration failed, Try again later.");
            }

    }


    public static void regularUser(String userName, String password) {
        try {

            Connection con = DatabaseConnection.getConnection();
//            System.err.println("Connection Established Successfully.");

            // Step 3: Define the SQL query to find the user by username and password
            String getUserQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = con.prepareStatement(getUserQuery);

            // Step 4: Set the parameters for the query
            pstmt.setString(1, userName);
            pstmt.setString(2, password);

            // Step 5: Execute the query
            ResultSet rs = pstmt.executeQuery();

            // Step 6: Check if a user with the given username and password exists
            if (rs.next()) {

                // If the query returns a result, user exists
                //System.out.println("User Authenticated successfully!");
                BucketList.NAME = rs.getString("name");
                BucketList.USERNAME = rs.getString("username");
                System.out.println("Welcome Mr. " + NAME + "  to BuckyLog.");
                userInterface();


            } else {
                // If no user is found
                System.out.println("Invalid username or password.");

            }

        }
        catch (SQLException | IOException e) {
            System.err.println("SQL error: " + e.getMessage());
        }
    }

    public static void userInterface() throws IOException, SQLException {


//        System.out.println("Welcome to your Bucket List!");



        while (true) {
            System.out.println("Here’s what you can do:");
            System.out.println("1. View your Bucket List");
            System.out.println("2. Add a new goal");
            System.out.println("3. Update the status of a goal");
            System.out.println("4. Remove a goal");
            System.out.println("5. Exit");
            System.out.print("---=== Pick an option (1-5) ===-- \n");

            int choice = scInput.nextInt(); // Get user input

            // Perform action based on the user's choice
            switch (choice) {
                case 1:
                    // View Bucket List
                    viewBucketList();
                    break;

                case 2:
                    // Add a New Goal
                    addGoal();
                    break;

                case 3:
                    // Update the Status of a Goal
                    updateGoalStatus();
                    break;

                case 4:
                    // Remove a Goal
                    removeGoal();
                    break;

                case 5:
                    // Exit the program
                    System.out.println("Thanks for using your BuckyLog. Goodbye!");
                    scInput.close(); // Close the scanner resource
                    return;

                default:
                    // Handle invalid input
                    System.out.println("Oops! Please choose a valid option (1-5).");
            }

        }
    }


    public static void viewBucketList() throws SQLException {
        System.out.println("\n=== Your Bucket List ===");
        Connection con = DatabaseConnection.getConnection();

        String getAllListQuery = "SELECT * FROM bucket_list_items WHERE username = ? ORDER BY date_added DESC";
        PreparedStatement pstmt = con.prepareStatement(getAllListQuery);

        pstmt.setString(1,BucketList.USERNAME);
        ResultSet rs = pstmt.executeQuery();

        boolean hasItems = false;

        while (rs.next()) {
            hasItems = true;
            int itemId = rs.getInt("item_id");
            String itemName = rs.getString("item_name");
            String description = rs.getString("description");
            Timestamp dateAdded = rs.getTimestamp("date_added");
            String status = rs.getString("status");

            // Print the details of each bucket list item
            System.out.println("Item ID: " + itemId);
            System.out.println("Item Name: " + itemName);
            System.out.println("Description: " + description);
            System.out.println("Date Added: " + dateAdded);
            System.out.println("Status: " + status);
            System.out.println("-------------------------------------------------");
        }

        // If no items were found, print a message
        if (!hasItems) {
            System.out.println("You have not added any goals to your bucket list yet.");
        }



    }

    // Method to add a new goal
    public static void addGoal() throws IOException, SQLException {
        Connection con = DatabaseConnection.getConnection();
        System.out.println("\n=== Add a New Goal ===");
        System.out.println("What’s your new goal?");

        System.out.println("Topic: ");
        String topic = brInput.readLine();
        System.out.println("Description: ");
        String description = brInput.readLine();

        String insertQuery = "INSERT INTO bucket_list_items (username, item_name, description, date_added, status) " + "VALUES (?, ?, ?, NOW(), ?)";

        PreparedStatement pstmt = con.prepareStatement(insertQuery);

        pstmt.setString(1, USERNAME);
        pstmt.setString(2, topic);
        pstmt.setString(3, description);
        pstmt.setString(4, "pending");

        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Bucket list item added successfully!");
        } else {
            System.out.println("Failed to add the item.");
        }

      System.out.println("Your goal has been added to: " + topic);
        System.out.println();
    }

    public static void updateGoalStatus() throws IOException, SQLException {
        System.out.println("\n=== Update Goal Status ===");
        viewBucketList();

        System.out.print("Enter the goal ID: ");
        int goalId = scInput.nextInt();
        scInput.nextLine(); // Clear the newline character

        System.out.print("What's the new status? (1/Completed, 2/Pending): ");
        String inputStatus = brInput.readLine().trim().toLowerCase();

        String newStatus;
        switch(inputStatus) {
            case "1":
            case "completed":
                newStatus = "Completed";
                break;
            case "2":
            case "pending":
                newStatus = "Pending";
                break;
            default:
                System.out.println("Invalid status. Using default 'Pending'.");
                newStatus = "Pending";
        }

        Connection con = DatabaseConnection.getConnection();
        String updateStatusQuery = "UPDATE bucket_list_items SET status = ? WHERE username = ? AND item_id = ?";
        PreparedStatement pstmt = con.prepareStatement(updateStatusQuery);
        pstmt.setString(1, newStatus);
        pstmt.setString(2, USERNAME);
        pstmt.setInt(3, goalId);

        int rowsUpdated = pstmt.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Goal #" + goalId + " status updated successfully to: " + newStatus);
        } else {
            System.out.println("Failed to update the goal status. Make sure the goal ID is correct.");
        }
    }


    public static void removeGoal() throws SQLException {
        System.out.println("\n=== Remove a Goal ===");

        viewBucketList();

        System.out.print("Enter the goal number you want to remove: ");
        int goalNumber = scInput.nextInt();
        scInput.nextLine();

        Connection con = DatabaseConnection.getConnection();

        String deleteGoalQuery = "DELETE FROM bucket_list_items WHERE username = ? AND item_id = ?";
        PreparedStatement pstmt = con.prepareStatement(deleteGoalQuery);

        pstmt.setString(1, USERNAME);
        pstmt.setInt(2, goalNumber);

        int rowsDeleted = pstmt.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Goal #" + goalNumber + " has been successfully removed from your Bucket List.");
        } else {
            System.out.println("Failed to remove the goal. Please ensure the goal number is correct.");
        }

        System.out.println();
    }
}

