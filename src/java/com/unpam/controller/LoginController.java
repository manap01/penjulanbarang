/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Koneksi;
import com.unpam.model.Enkripsi;
import com.unpam.view.MainForm;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author hanif
 */
@WebServlet(name = "LoginController", urlPatterns = {"/LoginController"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String aksi = request.getParameter("aksi");
        MainForm mainForm = new MainForm();
        String konten = "";
        
        if (aksi == null) {
            // Tampilkan form login
            konten = "<div style='max-width: 400px; margin: 50px auto;'>";
            konten += "<h2 style='text-align: center;'>Login</h2>";
            konten += "<form method='post' action='LoginController?aksi=proses'>";
            konten += "<div class='form-group'>";
            konten += "<label>Username</label>";
            konten += "<input type='text' name='username' required autofocus>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Password</label>";
            konten += "<input type='password' name='password' required>";
            konten += "</div>";
            konten += "<button type='submit' class='btn btn-primary' style='width: 100%;'>Login</button>";
            konten += "</form>";
            konten += "<div style='margin-top: 20px; padding: 15px; background: #e3f2fd; border-radius: 4px;'>";
            konten += "<strong>Info Login:</strong><br>";
            konten += "Username: admin<br>";
            konten += "Password: admin123";
            konten += "</div>";
            konten += "</div>";
        }
        else if (aksi.equals("proses")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            Koneksi koneksi = new Koneksi();
            Enkripsi enkripsi = new Enkripsi();
            Connection connection = koneksi.getConnection();
            
            if (connection != null) {
                try {
                    String passwordMD5 = enkripsi.hashMD5(password);
                    String sql = "SELECT * FROM tbpengguna WHERE username=? AND password=?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, username);
                    ps.setString(2, passwordMD5);
                    
                    ResultSet rs = ps.executeQuery();
                    
                    if (rs.next()) {
                        // Login berhasil
                        HttpSession session = request.getSession();
                        session.setAttribute("username", rs.getString("username"));
                        session.setAttribute("nama", rs.getString("nama"));
                        session.setAttribute("level", rs.getString("level"));
                        
                        response.sendRedirect("MainForm");
                        return;
                    } else {
                        // Login gagal
                        konten = "<div style='max-width: 400px; margin: 50px auto;'>";
                        konten += "<div class='alert alert-danger'>Username atau Password salah!</div>";
                        konten += "<a href='LoginController' class='btn btn-primary'>Kembali ke Login</a>";
                        konten += "</div>";
                    }
                    
                    rs.close();
                    ps.close();
                    connection.close();
                } catch (Exception e) {
                    konten = "<div class='alert alert-danger'>Error: " + e.getMessage() + "</div>";
                }
            } else {
                konten = "<div class='alert alert-danger'>Koneksi database gagal!</div>";
            }
        }
        
        mainForm.tampilan(request, response, konten);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}