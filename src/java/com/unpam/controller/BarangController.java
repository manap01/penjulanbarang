/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Barang;
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
@WebServlet(name = "BarangController", urlPatterns = {"/BarangController"})
public class BarangController extends HttpServlet {

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
        Barang barang = new Barang();
        MainForm mainForm = new MainForm();
        String konten = "";
        
        if (aksi == null) {
            aksi = "lihat";
        }
        
        if (aksi.equals("lihat")) {
            barang.getAll();
            Object[][] data = barang.getList();
            
            konten = "<h2>Data Barang</h2>";
            konten += "<a href='BarangController?aksi=tambah' class='btn btn-success'>Tambah Barang</a>";
            konten += "<table>";
            konten += "<tr><th>Kode Barang</th><th>Nama Barang</th><th>Harga</th><th>Stok</th><th>Aksi</th></tr>";
            
            if (data != null && data.length > 0) {
                for (Object[] row : data) {
                    konten += "<tr>";
                    konten += "<td>" + row[0] + "</td>";
                    konten += "<td>" + row[1] + "</td>";
                    konten += "<td>Rp " + String.format("%,.0f", row[2]) + "</td>";
                    konten += "<td>" + row[3] + "</td>";
                    konten += "<td>";
                    konten += "<a href='BarangController?aksi=edit&kode=" + row[0] + "' class='btn btn-warning'>Edit</a> ";
                    konten += "<a href='BarangController?aksi=hapus&kode=" + row[0] + "' class='btn btn-danger' onclick='return confirm(\"Yakin ingin menghapus?\")'>Hapus</a>";
                    konten += "</td>";
                    konten += "</tr>";
                }
            } else {
                konten += "<tr><td colspan='5' style='text-align:center'>Tidak ada data</td></tr>";
            }
            
            konten += "</table>";
        }
        else if (aksi.equals("tambah")) {
            konten = "<h2>Tambah Barang</h2>";
            konten += "<form method='post' action='BarangController?aksi=simpan'>";
            konten += "<div class='form-group'>";
            konten += "<label>Kode Barang</label>";
            konten += "<input type='text' name='kode' required>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Nama Barang</label>";
            konten += "<input type='text' name='nama' required>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Harga</label>";
            konten += "<input type='number' name='harga' step='0.01' required>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Stok</label>";
            konten += "<input type='number' name='stok' required>";
            konten += "</div>";
            konten += "<button type='submit' class='btn btn-primary'>Simpan</button> ";
            konten += "<a href='BarangController' class='btn btn-danger'>Batal</a>";
            konten += "</form>";
        }
        else if (aksi.equals("simpan")) {
            String kode = request.getParameter("kode");
            String nama = request.getParameter("nama");
            double harga = Double.parseDouble(request.getParameter("harga"));
            int stok = Integer.parseInt(request.getParameter("stok"));
            
            barang.setKodeBarang(kode);
            barang.setNamaBarang(nama);
            barang.setHarga(harga);
            barang.setStok(stok);
            
            if (barang.simpan()) {
                konten = "<div class='alert alert-success'>Data barang berhasil disimpan!</div>";
                konten += "<a href='BarangController' class='btn btn-primary'>Kembali ke Data Barang</a>";
            } else {
                konten = "<div class='alert alert-danger'>Gagal menyimpan data: " + barang.getPesan() + "</div>";
                konten += "<a href='BarangController?aksi=tambah' class='btn btn-primary'>Kembali</a>";
            }
        }
        else if (aksi.equals("edit")) {
            String kode = request.getParameter("kode");
            
            // Ambil data barang berdasarkan kode
            barang.getAll();
            Object[][] data = barang.getList();
            Object[] barangData = null;
            
            for (Object[] row : data) {
                if (row[0].equals(kode)) {
                    barangData = row;
                    break;
                }
            }
            
            if (barangData != null) {
                konten = "<h2>Edit Barang</h2>";
                konten += "<form method='post' action='BarangController?aksi=update'>";
                konten += "<div class='form-group'>";
                konten += "<label>Kode Barang</label>";
                konten += "<input type='text' name='kode' value='" + barangData[0] + "' readonly>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Nama Barang</label>";
                konten += "<input type='text' name='nama' value='" + barangData[1] + "' required>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Harga</label>";
                konten += "<input type='number' name='harga' value='" + barangData[2] + "' step='0.01' required>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Stok</label>";
                konten += "<input type='number' name='stok' value='" + barangData[3] + "' required>";
                konten += "</div>";
                konten += "<button type='submit' class='btn btn-primary'>Update</button> ";
                konten += "<a href='BarangController' class='btn btn-danger'>Batal</a>";
                konten += "</form>";
            }
        }
        else if (aksi.equals("update")) {
            String kode = request.getParameter("kode");
            String nama = request.getParameter("nama");
            double harga = Double.parseDouble(request.getParameter("harga"));
            int stok = Integer.parseInt(request.getParameter("stok"));
            
            barang.setKodeBarang(kode);
            barang.setNamaBarang(nama);
            barang.setHarga(harga);
            barang.setStok(stok);
            
            if (barang.ubah()) {
                konten = "<div class='alert alert-success'>Data barang berhasil diupdate!</div>";
                konten += "<a href='BarangController' class='btn btn-primary'>Kembali ke Data Barang</a>";
            } else {
                konten = "<div class='alert alert-danger'>Gagal mengupdate data: " + barang.getPesan() + "</div>";
                konten += "<a href='BarangController' class='btn btn-primary'>Kembali</a>";
            }
        }
        else if (aksi.equals("hapus")) {
            String kode = request.getParameter("kode");
            
            barang.setKodeBarang(kode);
            
            if (barang.hapus()) {
                konten = "<div class='alert alert-success'>Data barang berhasil dihapus!</div>";
                konten += "<a href='BarangController' class='btn btn-primary'>Kembali ke Data Barang</a>";
            } else {
                konten = "<div class='alert alert-danger'>Gagal menghapus data: " + barang.getPesan() + "</div>";
                konten += "<a href='BarangController' class='btn btn-primary'>Kembali</a>";
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