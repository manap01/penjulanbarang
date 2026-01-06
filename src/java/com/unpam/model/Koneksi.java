package com.unpam.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author hanif
 */
public class Koneksi {
    
    private static final String DEFAULT_DRIVER = "com.mysql.cj.jdbc.Driver";
    private String database;
    private String user;
    private String password;
    private Connection connection;
    private String pesanKesalahan;
    
    public String getPesanKesalahan() {
        return pesanKesalahan;
    }
    
    public Connection getConnection() {
        connection = null;
        pesanKesalahan = "";
        
        // CEK ENVIRONMENT VARIABLE (untuk Railway)
        String envDatabase = System.getenv("DATABASE_URL");
        String envUser = System.getenv("DB_USER");
        String envPassword = System.getenv("DB_PASSWORD");
        
        if (envDatabase != null && !envDatabase.isEmpty()) {
            // MODE RAILWAY (pakai URL lengkap)
            database = envDatabase;
            user = envUser != null ? envUser : "root";
            password = envPassword != null ? envPassword : "";
        } else {
            // MODE Lokal XAMPP (fallback)
            database = "jdbc:mysql://localhost:3306/dbaplikasipenjualanbarang";
            user = "root";
            password = "";
        }
        
        // Load driver
        try {
            Class.forName(DEFAULT_DRIVER);
        } catch (ClassNotFoundException ex) {
            pesanKesalahan = "JDBC Driver tidak ditemukan atau rusak\n" + ex;
        }
        
        // Koneksi ke database
        if (pesanKesalahan.equals("")) {
            try {
                // Jika URL sudah ada parameter, langsung pakai
                if (database.contains("?")) {
                    connection = DriverManager.getConnection(database);
                } else {
                    // Jika belum ada parameter, tambahkan user & password
                    connection = DriverManager.getConnection(database + "?user=" + user + "&password=" + password);
                }
            } catch (SQLException ex) {
                pesanKesalahan = "Koneksi gagal ke " + database + "\nError: " + ex.getMessage();
            }
        }
        
        return connection;
    }
}