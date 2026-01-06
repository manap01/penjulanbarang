package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.unpam.view.PesanDialog;

/**
 *
 * @author hanif
 */
public class Customer {
    private String idCustomer, namaCustomer, alamat, telepon;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    // Getter dan Setter
    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getPesan() {
        return pesan;
    }

    public Object[][] getList() {
        return list;
    }

    public void setList(Object[][] list) {
        this.list = list;
    }

    // Method Simpan
    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;
        
        if ((connection = koneksi.getConnection()) != null) {
            int jumlahSimpan = 0;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;
            
            try {
                SQLStatemen = "INSERT INTO tbcustomer(id_customer, nama_customer, alamat, telepon) VALUES (?,?,?,?)";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, idCustomer);
                preparedStatement.setString(2, namaCustomer);
                preparedStatement.setString(3, alamat);
                preparedStatement.setString(4, telepon);
                jumlahSimpan = preparedStatement.executeUpdate();
                
                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data customer";
                }
                
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbcustomer\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        
        return !adaKesalahan;
    }

    // Method Ubah
    public boolean ubah() {
        boolean adaKesalahan = false;
        Connection connection;
        
        if ((connection = koneksi.getConnection()) != null) {
            int jumlahUpdate = 0;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;
            
            try {
                SQLStatemen = "UPDATE tbcustomer SET nama_customer=?, alamat=?, telepon=? WHERE id_customer=?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, namaCustomer);
                preparedStatement.setString(2, alamat);
                preparedStatement.setString(3, telepon);
                preparedStatement.setString(4, idCustomer);
                jumlahUpdate = preparedStatement.executeUpdate();
                
                if (jumlahUpdate < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal mengupdate data customer";
                }
                
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbcustomer\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        
        return !adaKesalahan;
    }

    // Method Hapus
    public boolean hapus() {
        boolean adaKesalahan = false;
        Connection connection;
        
        if ((connection = koneksi.getConnection()) != null) {
            int jumlahHapus = 0;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;
            
            try {
                SQLStatemen = "DELETE FROM tbcustomer WHERE id_customer=?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, idCustomer);
                jumlahHapus = preparedStatement.executeUpdate();
                
                if (jumlahHapus < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menghapus data customer";
                }
                
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbcustomer\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        
        return !adaKesalahan;
    }

    // Method GetAll - INI YANG PALING PENTING!
    public void getAll() {
        Connection connection;
        
        if ((connection = koneksi.getConnection()) != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM tbcustomer ORDER BY id_customer",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
                );
                
                ResultSet rset = preparedStatement.executeQuery();
                
                // Hitung jumlah baris
                rset.last();
                int jumlahBaris = rset.getRow();
                rset.beforeFirst();
                
                // Buat array untuk menyimpan data
                list = new Object[jumlahBaris][4];
                int i = 0;
                
                while (rset.next()) {
                    list[i][0] = rset.getString("id_customer");
                    list[i][1] = rset.getString("nama_customer");
                    list[i][2] = rset.getString("alamat");
                    list[i][3] = rset.getString("telepon");
                    i++;
                }
                
                rset.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                pesan = "Tidak dapat membuka tabel tbcustomer\n" + ex;
                list = null;
            }
        } else {
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
            list = null;
        }
    }
}