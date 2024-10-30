package HospitalManagementSystem;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient() {
        System.out.println("Enter Patient Name: ");
        String name = scanner.next();
        System.out.println("Enter Patient Age: ");
        int age = scanner.nextInt();
        System.out.println("Enter Patient Gender: ");
        String gender = scanner.next();

        try {
            String query = "INSERT INTO patients(name, age, gender) VALUES(?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setString(3, gender);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Patient Added Successfully!");
            } else {
                System.out.println("Failed to Add Patient");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewPatient() {
        String query = "SELECT * FROM patients";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Patients:");
            System.out.println("+------------+------------+------+---------+");
            System.out.println("| Patient Id | Name       | Age  | Gender  |");
            System.out.println("+------------+------------+------+---------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name"); // Corrected to String type
                int age = resultSet.getInt("age");
                String gender = resultSet.getString("gender");

                // Pass each variable to printf
                System.out.printf("| %-10d | %-10s | %-4d | %-7s |\n", id, name, age, gender);
            }
            System.out.println("+------------+------------+------+---------+");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientById(int id) {
        String query = "SELECT * FROM patients WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
