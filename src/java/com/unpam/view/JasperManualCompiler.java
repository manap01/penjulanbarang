/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.view;

import net.sf.jasperreports.engine.JasperCompileManager;

/**
 * Program untuk compile file .jrxml menjadi .jasper secara manual
 * Jalankan program ini (Run File) untuk compile semua file .jrxml
 * 
 * @author hanif
 */
public class JasperManualCompiler {
    
    public static void main(String[] args) {
        // Path ke folder reports
        
        String projectPath = "C:\\Users\\hanif\\Documents\\NetBeansProjects\\AplikasiPenjualanBarang\\web\\reports\\";
        
        // Daftar file yang akan di-compile
        String[] files = {
            "LaporanBarang.jrxml",
            "LaporanCustomer.jrxml", 
            "LaporanTransaksi.jrxml"
        };
        
        System.out.println("=== MEMULAI COMPILE JASPER REPORTS ===\n");
        
        for (String fileName : files) {
            try {
                String jrxmlPath = projectPath + fileName;
                String jasperPath = jrxmlPath.replace(".jrxml", ".jasper");
                
                System.out.println("Compiling: " + fileName);
                JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
                System.out.println("✓ SUCCESS: " + fileName + " compiled!\n");
                
            } catch (Exception e) {
                System.err.println("✗ ERROR compiling " + fileName);
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
                System.out.println();
            }
        }
        
        System.out.println("=== SELESAI ===");
        System.out.println("Silakan refresh folder reports di NetBeans");
        System.out.println("File .jasper sudah ter-generate");
    }
}
