package com.unpam.controller;

import com.unpam.model.Koneksi;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author hanif
 */
@WebServlet(name = "LaporanController", urlPatterns = {"/LaporanController"})
public class LaporanController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String jenis = request.getParameter("jenis");
        
        if (jenis == null) {
            jenis = "barang";
        }
        
        // Set response untuk PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=laporan_" + jenis + ".pdf");
        
        try {
            // Buat dokumen PDF
            Document document = new Document(PageSize.A4);
            OutputStream out = response.getOutputStream();
            PdfWriter.getInstance(document, out);
            
            document.open();
            
            // Font untuk judul dan isi
            Font fontJudul = new Font(Font.HELVETICA, 18, Font.BOLD);
            Font fontSubjudul = new Font(Font.HELVETICA, 12, Font.NORMAL);
            Font fontHeader = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);
            Font fontIsi = new Font(Font.HELVETICA, 9, Font.NORMAL);
            
            // Judul Laporan
            Paragraph judul = null;
            if (jenis.equals("barang")) {
                judul = new Paragraph("LAPORAN DATA BARANG", fontJudul);
            } else if (jenis.equals("customer")) {
                judul = new Paragraph("LAPORAN DATA CUSTOMER", fontJudul);
            } else if (jenis.equals("transaksi")) {
                judul = new Paragraph("LAPORAN TRANSAKSI PENJUALAN", fontJudul);
            }
            judul.setAlignment(Element.ALIGN_CENTER);
            judul.setSpacingAfter(10);
            document.add(judul);
            
            // Tanggal cetak
            String tanggalCetak = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(new Date());
            Paragraph tanggal = new Paragraph("Tanggal Cetak: " + tanggalCetak, fontSubjudul);
            tanggal.setAlignment(Element.ALIGN_CENTER);
            tanggal.setSpacingAfter(20);
            document.add(tanggal);
            
            // Koneksi database
            Koneksi koneksi = new Koneksi();
            Connection conn = koneksi.getConnection();
            
            if (conn != null) {
                Statement stmt = conn.createStatement();
                ResultSet rs = null;
                PdfPTable table = null;
                
                // LAPORAN BARANG
                if (jenis.equals("barang")) {
                    rs = stmt.executeQuery("SELECT * FROM tbbarang ORDER BY kode_barang");
                    
                    // Buat tabel dengan 4 kolom
                    table = new PdfPTable(4);
                    table.setWidthPercentage(100);
                    table.setWidths(new float[]{2, 4, 3, 2});
                    
                    // Header tabel
                    PdfPCell cell;
                    
                    cell = new PdfPCell(new Phrase("Kode Barang", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Nama Barang", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Harga", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Stok", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    // Isi tabel
                    int no = 1;
                    while (rs.next()) {
                        cell = new PdfPCell(new Phrase(rs.getString("kode_barang"), fontIsi));
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase(rs.getString("nama_barang"), fontIsi));
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase("Rp " + String.format("%,.0f", rs.getDouble("harga")), fontIsi));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase(String.valueOf(rs.getInt("stok")), fontIsi));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        no++;
                    }
                }
                
                // LAPORAN CUSTOMER
                else if (jenis.equals("customer")) {
                    rs = stmt.executeQuery("SELECT * FROM tbcustomer ORDER BY id_customer");
                    
                    // Buat tabel dengan 4 kolom
                    table = new PdfPTable(4);
                    table.setWidthPercentage(100);
                    table.setWidths(new float[]{2, 4, 5, 3});
                    
                    // Header tabel
                    PdfPCell cell;
                    
                    cell = new PdfPCell(new Phrase("ID Customer", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Nama Customer", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Alamat", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Telepon", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    // Isi tabel
                    while (rs.next()) {
                        cell = new PdfPCell(new Phrase(rs.getString("id_customer"), fontIsi));
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase(rs.getString("nama_customer"), fontIsi));
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase(rs.getString("alamat"), fontIsi));
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase(rs.getString("telepon"), fontIsi));
                        cell.setPadding(5);
                        table.addCell(cell);
                    }
                }
                
                // LAPORAN TRANSAKSI
                else if (jenis.equals("transaksi")) {
                    rs = stmt.executeQuery(
                        "SELECT t.no_transaksi, t.tanggal, c.nama_customer, b.nama_barang, " +
                        "t.jumlah, t.total_harga " +
                        "FROM tbtransaksi t " +
                        "LEFT JOIN tbcustomer c ON t.id_customer = c.id_customer " +
                        "LEFT JOIN tbbarang b ON t.kode_barang = b.kode_barang " +
                        "ORDER BY t.tanggal DESC"
                    );
                    
                    // Buat tabel dengan 6 kolom
                    table = new PdfPTable(6);
                    table.setWidthPercentage(100);
                    table.setWidths(new float[]{3, 2.5f, 3, 3, 2, 3});
                    
                    // Header tabel
                    PdfPCell cell;
                    
                    cell = new PdfPCell(new Phrase("No Transaksi", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Tanggal", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Customer", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Barang", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Jumlah", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Total Harga", fontHeader));
                    cell.setBackgroundColor(new Color(52, 73, 94));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(8);
                    table.addCell(cell);
                    
                    // Isi tabel
                    double grandTotal = 0;
                    while (rs.next()) {
                        cell = new PdfPCell(new Phrase(rs.getString("no_transaksi"), fontIsi));
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase(rs.getString("tanggal"), fontIsi));
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase(rs.getString("nama_customer"), fontIsi));
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase(rs.getString("nama_barang"), fontIsi));
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        cell = new PdfPCell(new Phrase(String.valueOf(rs.getInt("jumlah")), fontIsi));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setPadding(5);
                        table.addCell(cell);
                        
                        double total = rs.getDouble("total_harga");
                        grandTotal += total;
                        cell = new PdfPCell(new Phrase("Rp " + String.format("%,.0f", total), fontIsi));
                        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                        cell.setPadding(5);
                        table.addCell(cell);
                    }
                    
                    // Total keseluruhan
                    cell = new PdfPCell(new Phrase("GRAND TOTAL", new Font(Font.HELVETICA, 10, Font.BOLD)));
                    cell.setColspan(5);
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setPadding(8);
                    cell.setBackgroundColor(new Color(236, 240, 241));
                    table.addCell(cell);
                    
                    cell = new PdfPCell(new Phrase("Rp " + String.format("%,.0f", grandTotal), new Font(Font.HELVETICA, 10, Font.BOLD)));
                    cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                    cell.setPadding(8);
                    cell.setBackgroundColor(new Color(236, 240, 241));
                    table.addCell(cell);
                }
                
                // Tambahkan tabel ke dokumen
                if (table != null) {
                    document.add(table);
                }
                
                // Tutup koneksi
                if (rs != null) rs.close();
                stmt.close();
                conn.close();
            }
            
            // Tutup dokumen
            document.close();
            out.flush();
            out.close();
            
        } catch (Exception e) {
            throw new ServletException("Error generating PDF: " + e.getMessage(), e);
        }
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