package com.example.feedback;

public class TestConnection {
    public static void main(String[] args) {
        try {
            DBUtil.getConnection();
            System.out.println("Database connection successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}