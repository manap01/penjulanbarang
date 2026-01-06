/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.view;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "MainForm", urlPatterns = {"/MainForm"})
public class MainForm extends HttpServlet {

    public void tampilan(HttpServletRequest request, HttpServletResponse response, String konten)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String nama = (String) session.getAttribute("nama");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Aplikasi Penjualan Barang</title>");
            out.println("<meta charset='UTF-8'>");
            out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<style>");
            out.println("* { margin: 0; padding: 0; box-sizing: border-box; }");
            out.println("body { font-family: Arial, sans-serif; background: #f4f4f4; }");
            out.println(".navbar { background: #2c3e50; color: white; padding: 15px 30px; display: flex; justify-content: space-between; align-items: center; }");
            out.println(".navbar h1 { font-size: 22px; }");
            out.println(".navbar .user-info { font-size: 14px; }");
            out.println(".menu { background: #34495e; padding: 0; display: flex; position: relative; }");
            out.println(".menu a { color: white; padding: 15px 25px; text-decoration: none; display: block; transition: background 0.3s; }");
            out.println(".menu a:hover { background: #1abc9c; }");
            out.println(".menu .dropdown { position: relative; }");
            out.println(".menu .dropdown:hover .dropdown-menu { display: block; }");
            out.println(".dropdown-menu { display: none; position: absolute; background: #2c3e50; min-width: 200px; top: 100%; left: 0; z-index: 1000; box-shadow: 0 2px 5px rgba(0,0,0,0.2); }");
            out.println(".dropdown-menu a { padding: 12px 20px; border-bottom: 1px solid #34495e; }");
            out.println(".dropdown-menu a:last-child { border-bottom: none; }");
            out.println(".dropdown-menu a:hover { background: #1abc9c; }");
            out.println(".container { max-width: 1200px; margin: 30px auto; padding: 20px; background: white; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); }");
            out.println("table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println("table th, table td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }");
            out.println("table th { background: #34495e; color: white; }");
            out.println("table tr:hover { background: #f5f5f5; }");
            out.println(".btn { padding: 8px 15px; margin: 2px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; display: inline-block; }");
            out.println(".btn-primary { background: #3498db; color: white; }");
            out.println(".btn-success { background: #27ae60; color: white; }");
            out.println(".btn-danger { background: #e74c3c; color: white; }");
            out.println(".btn-warning { background: #f39c12; color: white; }");
            out.println(".btn:hover { opacity: 0.8; }");
            out.println(".form-group { margin-bottom: 15px; }");
            out.println(".form-group label { display: block; margin-bottom: 5px; font-weight: bold; }");
            out.println(".form-group input, .form-group select, .form-group textarea { width: 100%; padding: 10px; border: 1px solid #ddd; border-radius: 4px; }");
            out.println(".alert { padding: 15px; margin-bottom: 20px; border-radius: 4px; }");
            out.println(".alert-success { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }");
            out.println(".alert-danger { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }");
            out.println(".welcome { text-align: center; padding: 50px; }");
            out.println(".welcome h2 { color: #2c3e50; margin-bottom: 10px; }");
            out.println(".welcome p { color: #7f8c8d; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            
            // Navbar
            out.println("<div class='navbar'>");
            out.println("<h1>Aplikasi Penjualan Barang</h1>");
            if (username != null) {
                out.println("<div class='user-info'>Login sebagai: <strong>" + nama + "</strong></div>");
            }
            out.println("</div>");
            
            // Menu
            out.println("<div class='menu'>");
            if (username == null) {
                out.println("<a href='LoginController'>Login</a>");
            } else {
                out.println("<a href='MainForm'>Home</a>");
                out.println("<a href='BarangController'>Data Barang</a>");
                out.println("<a href='CustomerController'>Data Customer</a>");
                out.println("<a href='TransaksiController'>Transaksi Penjualan</a>");
                
                // Menu Dropdown Laporan
                out.println("<div class='dropdown'>");
                out.println("  <a href='#'>Laporan ▼</a>");
                out.println("  <div class='dropdown-menu'>");
                out.println("    <a href='LaporanController?jenis=barang' target='_blank'>Laporan Barang</a>");
                out.println("    <a href='LaporanController?jenis=customer' target='_blank'>Laporan Customer</a>");
                out.println("    <a href='LaporanController?jenis=transaksi' target='_blank'>Laporan Transaksi</a>");
                out.println("  </div>");
                out.println("</div>");
                
                out.println("<a href='LogoutController'>Logout</a>");
            }
            out.println("</div>");
            
            // Content
            out.println("<div class='container'>");
            if (konten != null && !konten.isEmpty()) {
                out.println(konten);
            } else {
                out.println("<div class='welcome'>");
                out.println("<h2>Selamat Datang di Aplikasi Penjualan Barang</h2>");
                out.println("<p>Silakan pilih menu di atas untuk memulai</p>");
                out.println("</div>");
            }
            out.println("</div>");
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        tampilan(request, response, "");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        tampilan(request, response, "");
    }
}