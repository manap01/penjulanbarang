/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Transaksi;
import com.unpam.model.Barang;
import com.unpam.model.Customer;
import com.unpam.view.MainForm;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@WebServlet(name = "TransaksiController", urlPatterns = {"/TransaksiController"})
public class TransaksiController extends HttpServlet {

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
        Transaksi transaksi = new Transaksi();
        Barang barang = new Barang();
        Customer customer = new Customer();
        MainForm mainForm = new MainForm();
        String konten = "";
        
        if (aksi == null) {
            aksi = "lihat";
        }
        
        if (aksi.equals("lihat")) {
            transaksi.getAll();
            Object[][] data = transaksi.getList();
            
            konten = "<h2>Data Transaksi Penjualan</h2>";
            konten += "<a href='TransaksiController?aksi=tambah' class='btn btn-success'>Tambah Transaksi</a>";
            konten += "<table>";
            konten += "<tr><th>No Transaksi</th><th>Tanggal</th><th>Customer</th><th>Barang</th><th>Jumlah</th><th>Total Harga</th><th>Aksi</th></tr>";
            
            if (data != null && data.length > 0) {
                for (Object[] row : data) {
                    konten += "<tr>";
                    konten += "<td>" + row[0] + "</td>";
                    konten += "<td>" + row[1] + "</td>";
                    konten += "<td>" + row[2] + " - " + row[3] + "</td>";
                    konten += "<td>" + row[4] + " - " + row[5] + "</td>";
                    konten += "<td>" + row[6] + "</td>";
                    konten += "<td>Rp " + String.format("%,.0f", row[7]) + "</td>";
                    konten += "<td>";
                    konten += "<a href='TransaksiController?aksi=edit&no=" + row[0] + "' class='btn btn-warning'>Edit</a> ";
                    konten += "<a href='TransaksiController?aksi=hapus&no=" + row[0] + "' class='btn btn-danger' onclick='return confirm(\"Yakin ingin menghapus?\")'>Hapus</a>";
                    konten += "</td>";
                    konten += "</tr>";
                }
            } else {
                konten += "<tr><td colspan='7' style='text-align:center'>Tidak ada data</td></tr>";
            }
            
            konten += "</table>";
        }
        else if (aksi.equals("tambah")) {
            // Ambil data customer dan barang untuk dropdown
            customer.getAll();
            Object[][] dataCustomer = customer.getList();
            
            barang.getAll();
            Object[][] dataBarang = barang.getList();
            
            // Generate nomor transaksi otomatis
            String noTransaksi = "TRX" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String tanggalHariIni = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            
            konten = "<h2>Tambah Transaksi Penjualan</h2>";
            konten += "<form method='post' action='TransaksiController?aksi=simpan' id='formTransaksi'>";
            konten += "<div class='form-group'>";
            konten += "<label>No Transaksi</label>";
            konten += "<input type='text' name='no' value='" + noTransaksi + "' readonly>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Tanggal</label>";
            konten += "<input type='date' name='tanggal' value='" + tanggalHariIni + "' required>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Customer</label>";
            konten += "<select name='customer' required>";
            konten += "<option value=''>-- Pilih Customer --</option>";
            if (dataCustomer != null) {
                for (Object[] row : dataCustomer) {
                    konten += "<option value='" + row[0] + "'>" + row[0] + " - " + row[1] + "</option>";
                }
            }
            konten += "</select>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Barang</label>";
            konten += "<select name='barang' id='barang' required onchange='hitungTotal()'>";
            konten += "<option value=''>-- Pilih Barang --</option>";
            if (dataBarang != null) {
                for (Object[] row : dataBarang) {
                    konten += "<option value='" + row[0] + "' data-harga='" + row[2] + "' data-stok='" + row[3] + "'>" 
                            + row[0] + " - " + row[1] + " (Stok: " + row[3] + ")</option>";
                }
            }
            konten += "</select>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Jumlah</label>";
            konten += "<input type='number' name='jumlah' id='jumlah' min='1' required onchange='hitungTotal()' onkeyup='hitungTotal()'>";
            konten += "</div>";
            konten += "<div class='form-group'>";
            konten += "<label>Total Harga</label>";
            konten += "<input type='text' name='total' id='total' readonly>";
            konten += "</div>";
            konten += "<button type='submit' class='btn btn-primary'>Simpan</button> ";
            konten += "<a href='TransaksiController' class='btn btn-danger'>Batal</a>";
            konten += "</form>";
            
            // Script untuk hitung total otomatis
            konten += "<script>";
            konten += "function hitungTotal() {";
            konten += "  var barang = document.getElementById('barang');";
            konten += "  var jumlah = document.getElementById('jumlah').value;";
            konten += "  var total = document.getElementById('total');";
            konten += "  if (barang.selectedIndex > 0 && jumlah > 0) {";
            konten += "    var harga = barang.options[barang.selectedIndex].getAttribute('data-harga');";
            konten += "    var stok = barang.options[barang.selectedIndex].getAttribute('data-stok');";
            konten += "    if (parseInt(jumlah) > parseInt(stok)) {";
            konten += "      alert('Jumlah melebihi stok yang tersedia!');";
            konten += "      document.getElementById('jumlah').value = stok;";
            konten += "      jumlah = stok;";
            konten += "    }";
            konten += "    total.value = parseFloat(harga) * parseInt(jumlah);";
            konten += "  } else {";
            konten += "    total.value = '';";
            konten += "  }";
            konten += "}";
            konten += "</script>";
        }
        else if (aksi.equals("simpan")) {
            String no = request.getParameter("no");
            String tanggal = request.getParameter("tanggal");
            String idCustomer = request.getParameter("customer");
            String kodeBarang = request.getParameter("barang");
            int jumlah = Integer.parseInt(request.getParameter("jumlah"));
            double totalHarga = Double.parseDouble(request.getParameter("total"));
            
            transaksi.setNoTransaksi(no);
            transaksi.setTanggal(tanggal);
            transaksi.setIdCustomer(idCustomer);
            transaksi.setKodeBarang(kodeBarang);
            transaksi.setJumlah(jumlah);
            transaksi.setTotalHarga(totalHarga);
            
            if (transaksi.simpan()) {
                konten = "<div class='alert alert-success'>Data transaksi berhasil disimpan!</div>";
                konten += "<a href='TransaksiController' class='btn btn-primary'>Kembali ke Data Transaksi</a>";
            } else {
                konten = "<div class='alert alert-danger'>Gagal menyimpan data: " + transaksi.getPesan() + "</div>";
                konten += "<a href='TransaksiController?aksi=tambah' class='btn btn-primary'>Kembali</a>";
            }
        }
        else if (aksi.equals("edit")) {
            String no = request.getParameter("no");
            
            // Ambil data transaksi berdasarkan no
            transaksi.getAll();
            Object[][] dataTransaksi = transaksi.getList();
            Object[] transaksiData = null;
            
            for (Object[] row : dataTransaksi) {
                if (row[0].equals(no)) {
                    transaksiData = row;
                    break;
                }
            }
            
            if (transaksiData != null) {
                // Ambil data customer dan barang untuk dropdown
                customer.getAll();
                Object[][] dataCustomer = customer.getList();
                
                barang.getAll();
                Object[][] dataBarang = barang.getList();
                
                konten = "<h2>Edit Transaksi Penjualan</h2>";
                konten += "<form method='post' action='TransaksiController?aksi=update' id='formTransaksi'>";
                konten += "<div class='form-group'>";
                konten += "<label>No Transaksi</label>";
                konten += "<input type='text' name='no' value='" + transaksiData[0] + "' readonly>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Tanggal</label>";
                konten += "<input type='date' name='tanggal' value='" + transaksiData[1] + "' required>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Customer</label>";
                konten += "<select name='customer' required>";
                if (dataCustomer != null) {
                    for (Object[] row : dataCustomer) {
                        String selected = row[0].equals(transaksiData[2]) ? "selected" : "";
                        konten += "<option value='" + row[0] + "' " + selected + ">" + row[0] + " - " + row[1] + "</option>";
                    }
                }
                konten += "</select>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Barang</label>";
                konten += "<select name='barang' id='barang' required onchange='hitungTotal()'>";
                if (dataBarang != null) {
                    for (Object[] row : dataBarang) {
                        String selected = row[0].equals(transaksiData[4]) ? "selected" : "";
                        konten += "<option value='" + row[0] + "' data-harga='" + row[2] + "' data-stok='" + row[3] + "' " + selected + ">" 
                                + row[0] + " - " + row[1] + " (Stok: " + row[3] + ")</option>";
                    }
                }
                konten += "</select>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Jumlah</label>";
                konten += "<input type='number' name='jumlah' id='jumlah' value='" + transaksiData[6] + "' min='1' required onchange='hitungTotal()' onkeyup='hitungTotal()'>";
                konten += "</div>";
                konten += "<div class='form-group'>";
                konten += "<label>Total Harga</label>";
                konten += "<input type='text' name='total' id='total' value='" + transaksiData[7] + "' readonly>";
                konten += "</div>";
                konten += "<button type='submit' class='btn btn-primary'>Update</button> ";
                konten += "<a href='TransaksiController' class='btn btn-danger'>Batal</a>";
                konten += "</form>";
                
                // Script untuk hitung total otomatis
                konten += "<script>";
                konten += "function hitungTotal() {";
                konten += "  var barang = document.getElementById('barang');";
                konten += "  var jumlah = document.getElementById('jumlah').value;";
                konten += "  var total = document.getElementById('total');";
                konten += "  if (barang.selectedIndex >= 0 && jumlah > 0) {";
                konten += "    var harga = barang.options[barang.selectedIndex].getAttribute('data-harga');";
                konten += "    var stok = barang.options[barang.selectedIndex].getAttribute('data-stok');";
                konten += "    if (parseInt(jumlah) > parseInt(stok)) {";
                konten += "      alert('Jumlah melebihi stok yang tersedia!');";
                konten += "      document.getElementById('jumlah').value = stok;";
                konten += "      jumlah = stok;";
                konten += "    }";
                konten += "    total.value = parseFloat(harga) * parseInt(jumlah);";
                konten += "  }";
                konten += "}";
                konten += "</script>";
            }
        }
        else if (aksi.equals("update")) {
            String no = request.getParameter("no");
            String tanggal = request.getParameter("tanggal");
            String idCustomer = request.getParameter("customer");
            String kodeBarang = request.getParameter("barang");
            int jumlah = Integer.parseInt(request.getParameter("jumlah"));
            double totalHarga = Double.parseDouble(request.getParameter("total"));
            
            transaksi.setNoTransaksi(no);
            transaksi.setTanggal(tanggal);
            transaksi.setIdCustomer(idCustomer);
            transaksi.setKodeBarang(kodeBarang);
            transaksi.setJumlah(jumlah);
            transaksi.setTotalHarga(totalHarga);
            
            if (transaksi.ubah()) {
                konten = "<div class='alert alert-success'>Data transaksi berhasil diupdate!</div>";
                konten += "<a href='TransaksiController' class='btn btn-primary'>Kembali ke Data Transaksi</a>";
            } else {
                konten = "<div class='alert alert-danger'>Gagal mengupdate data: " + transaksi.getPesan() + "</div>";
                konten += "<a href='TransaksiController' class='btn btn-primary'>Kembali</a>";
            }
        }
        else if (aksi.equals("hapus")) {
            String no = request.getParameter("no");
            
            transaksi.setNoTransaksi(no);
            
            if (transaksi.hapus()) {
                konten = "<div class='alert alert-success'>Data transaksi berhasil dihapus!</div>";
                konten += "<a href='TransaksiController' class='btn btn-primary'>Kembali ke Data Transaksi</a>";
            } else {
                konten = "<div class='alert alert-danger'>Gagal menghapus data: " + transaksi.getPesan() + "</div>";
                konten += "<a href='TransaksiController' class='btn btn-primary'>Kembali</a>";
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