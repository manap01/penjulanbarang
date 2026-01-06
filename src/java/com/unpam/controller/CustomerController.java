/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Customer;
import com.unpam.view.MainForm;
import java.io.IOException;
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
@WebServlet(name = "CustomerController", urlPatterns = {"/CustomerController"})
public class CustomerController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            response.sendRedirect("LoginController");
            return;
        }
        
        String aksi = request.getParameter("aksi");
        Customer customer = new Customer();
        MainForm mainForm = new MainForm();
        String konten = "";
        
        if (aksi == null) {
            aksi = "lihat";
        }
        
        if (aksi.equals("lihat")) {
            customer.getAll();
            Object[][] data = customer.getList();
            
            konten = "<h2>Data Customer</h2>";
            konten += "<a href='CustomerController?aksi=tambah' class='btn btn-success'>Tambah Customer</a>";
            konten += "<table>";
            konten += "<tr><th>ID Customer</th><th>Nama Customer</th><th>Alamat</th><th>Telepon</th><th>Aksi</th></tr>";
            
            if (data != null && data.length > 0) {
                for (Object[] row : data) {
                    konten += "<tr>";
                    konten += "<td>" + row[0] + "</td>";
                    konten += "<td>" + row[1] + "</td>";
                    konten += "<td>" + row[2] + "</td>";
                    konten += "<td>" + row[3] + "</td>";
                    konten += "<td>";
                    konten += "<a href='CustomerController?aksi=edit&id=" + row[0] + "' class='btn btn-warning'>Edit</a> ";
                    konten += "<a href='CustomerController?aksi=hapus&id=" + row[0] + "' class='btn btn-danger' onclick='return confirm(\"Yakin ingin menghapus?\")'>Hapus</a>";
                    konten += "</td>";
                    konten += "</tr>";
                }
            } else {
                konten += "<tr><td colspan='5' style='text-align:center'>Tidak ada data</td></tr>";
            }
            
            konten += "</table>";
        }
        else if (aksi.equals("tambah")) {
            konten = "<h2>Tambah Customer</h2>";
            konten += "<form method='post' action='CustomerController?aksi=simpan'>";
            konten += "<div class='form-group'>";
            konten += "<label>ID Customer</label>";
            konten += "<input type='text' name='id' required>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Nama Customer</label>";
            konten += "<input type='text' name='nama' required>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Alamat</label>";
            konten += "<textarea name='alamat' rows='3' required></textarea>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Telepon</label>";
            konten += "<input type='text' name='telepon' required>";
            konten += "</div>";
            konten += "<button type='submit' class='btn btn-primary'>Simpan</button> ";
            konten += "<a href='CustomerController' class='btn btn-danger'>Batal</a>";
            konten += "</form>";
        }
        else if (aksi.equals("simpan")) {
            String id = request.getParameter("id");
            String nama = request.getParameter("nama");
            String alamat = request.getParameter("alamat");
            String telepon = request.getParameter("telepon");
            
            customer.setIdCustomer(id);
            customer.setNamaCustomer(nama);
            customer.setAlamat(alamat);
            customer.setTelepon(telepon);
            
            if (customer.simpan()) {
                konten = "<div class='alert alert-success'>Data customer berhasil disimpan!</div>";
                konten += "<a href='CustomerController' class='btn btn-primary'>Kembali ke Data Customer</a>";
            } else {
                konten = "<div class='alert alert-danger'>Gagal menyimpan data: " + customer.getPesan() + "</div>";
                konten += "<a href='CustomerController?aksi=tambah' class='btn btn-primary'>Kembali</a>";
            }
        }
        else if (aksi.equals("edit")) {
            String id = request.getParameter("id");
            
            // Ambil data customer berdasarkan id
            customer.getAll();
            Object[][] data = customer.getList();
            Object[] customerData = null;
            
            for (Object[] row : data) {
                if (row[0].equals(id)) {
                    customerData = row;
                    break;
                }
            }
            
            if (customerData != null) {
                konten = "<h2>Edit Customer</h2>";
                konten += "<form method='post' action='CustomerController?aksi=update'>";
                konten += "<div class='form-group'>";
                konten += "<label>ID Customer</label>";
                konten += "<input type='text' name='id' value='" + customerData[0] + "' readonly>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Nama Customer</label>";
                konten += "<input type='text' name='nama' value='" + customerData[1] + "' required>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Alamat</label>";
                konten += "<textarea name='alamat' rows='3' required>" + customerData[2] + "</textarea>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Telepon</label>";
                konten += "<input type='text' name='telepon' value='" + customerData[3] + "' required>";
                konten += "</div>";
                konten += "<button type='submit' class='btn btn-primary'>Update</button> ";
                konten += "<a href='CustomerController' class='btn btn-danger'>Batal</a>";
                konten += "</form>";
            }
        }
        else if (aksi.equals("update")) {
            String id = request.getParameter("id");
            String nama = request.getParameter("nama");
            String alamat = request.getParameter("alamat");
            String telepon = request.getParameter("telepon");
            
            customer.setIdCustomer(id);
            customer.setNamaCustomer(nama);
            customer.setAlamat(alamat);
            customer.setTelepon(telepon);
            
            if (customer.ubah()) {
                konten = "<div class='alert alert-success'>Data customer berhasil diupdate!</div>";
                konten += "<a href='CustomerController' class='btn btn-primary'>Kembali ke Data Customer</a>";
            } else {
                konten = "<div class='alert alert-danger'>Gagal mengupdate data: " + customer.getPesan() + "</div>";
                konten += "<a href='CustomerController' class='btn btn-primary'>Kembali</a>";
            }
        }
        else if (aksi.equals("hapus")) {
            String id = request.getParameter("id");
            
            customer.setIdCustomer(id);
            
            if (customer.hapus()) {
                konten = "<div class='alert alert-success'>Data customer berhasil dihapus!</div>";
                konten += "<a href='CustomerController' class='btn btn-primary'>Kembali ke Data Customer</a>";
            } else {
                konten = "<div class='alert alert-danger'>Gagal menghapus data: " + customer.getPesan() + "</div>";
                konten += "<a href='CustomerController' class='btn btn-primary'>Kembali</a>";
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