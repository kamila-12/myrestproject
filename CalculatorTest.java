package com.dmdev.Calculator.Java;
import java.sql.*;

import java.util.ArrayList;
import java.util.Scanner;
interface operations {
    double running (double a, double b);

}
interface History {
    void addToHistory(String input);
    ArrayList<String> getHistory();
}
abstract class Operation implements operations {
    public double running(double a, double b) {
        return 0.0;
    }
}

class Addition extends Operation {
    public double running(double a, double b) {
        return a + b;
    }

}

class Subtraction extends Operation {
    public double running(double a, double b) {
        return a - b;
    }
}


class Multiplication extends Operation {
    public double running(double a, double b) {
        return a * b;
    }

}
class Division extends Operation{
    public double running(double a, double b) {
        return a / b;
    }

}
class Binary extends Operation {
    public double running(double a, double b) {
        int r = (int) (super.running(a, b));
        return Double.parseDouble(Integer.toBinaryString(r));
    }
}
class Calculator extends Operation implements History {

    private final ArrayList<String> history;
    public Calculator () {
        history = new ArrayList<>();
    }


    public void addToHistory(String input) {
        history.add(input);
        saveToDatabase(input); // Save to the database
    }
    private void saveToDatabase(String input) {
        String url = "jdbc:mysql://localhost:3306/myhistory";
        String username = "root";
        String password = "";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO history(result) VALUES (?);");
            statement.setString(1, input);
            statement.execute();

            connection.close();
        } catch (SQLException e) {
            System.err.println("Error saving to database: " + e.getMessage());
        }
    }





    public ArrayList<String> getHistory() {
        return history;
    }

    public static void main(String[] args) {

        Calculator calculator = new Calculator();

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter 1 integer: ");
        double a = scan.nextDouble();

        System.out.println("Enter 2 integer: ");
        double b = scan.nextDouble();
        scan.nextLine();
        System.out.println("Choose operator: ");
        String op = scan.nextLine();

        switch (op) {
            case "+" -> {
                Operation add = new Addition();
                double sum = add.running(a, b);
                String addInput = a + " + " + b + " = " + sum;
                System.out.println(addInput);
                calculator.addToHistory(addInput);
            }
            case "-" -> {
                Operation sub = new Subtraction();
                double s = sub.running(a, b);
                String subInput = a + " - " + b + " = " + s;
                System.out.println(subInput);
                calculator.addToHistory(subInput);
            }
            case "*" -> {
                Operation mul = new Multiplication();
                double m = mul.running(a, b);
                String mulInput = a + " * " + b + " = " + m;
                System.out.println(mulInput);
                calculator.addToHistory(mulInput);
            }
            case "/" -> {
                Operation div = new Division();
                double d = div.running(a, b);
                String divInput = a + " / " + b + " = " + d;
                System.out.println(divInput);
                calculator.addToHistory(divInput);
                try {
                    if (b == 0) {
                        throw new ArithmeticException("Can't divide by zero");
                    }
                } catch (ArithmeticException e) {
                    System.err.println(e.getMessage());
                }
            }
            case "binary" -> {
                Binary binary = new Binary();
                double bin = binary.running(a, b);
                String binInput = a + " " + b + " = " + bin;
                System.out.println(binInput);
                calculator.addToHistory(binInput);

            }
        }


        System.out.println("History of operation");
        ArrayList<String> history = calculator.getHistory();
        for (String input : history) {
            System.out.println(input);

        }
        String url = "jdbc:mysql://localhost:3306/myhistory";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from history");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1)+" "+resultSet.getString(2));

            }
            connection.close();

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
}



