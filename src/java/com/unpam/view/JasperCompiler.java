/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.view;

import net.sf.jasperreports.engine.JasperCompileManager;
import javax.servlet.ServletContext;
import java.io.File;

/**
 *
 * @author hanif
 */
public class JasperCompiler {
    
    public static void compileAllReports(ServletContext context) {
        try {
            String reportsPath = context.getRealPath("/reports");
            File reportsFolder = new File(reportsPath);
            
            if (reportsFolder.exists()) {
                File[] files = reportsFolder.listFiles((dir, name) -> name.endsWith(".jrxml"));
                
                if (files != null) {
                    for (File file : files) {
                        String jrxmlPath = file.getAbsolutePath();
                        String jasperPath = jrxmlPath.replace(".jrxml", ".jasper");
                        
                        // Cek apakah file .jasper sudah ada
                        File jasperFile = new File(jasperPath);
                        if (!jasperFile.exists()) {
                            System.out.println("Compiling: " + file.getName());
                            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
                            System.out.println("Success: " + file.getName() + " compiled!");
                        } else {
                            System.out.println("Already compiled: " + file.getName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error compiling reports: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
