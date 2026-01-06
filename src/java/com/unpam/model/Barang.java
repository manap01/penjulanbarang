package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.unpam.view.PesanDialog;

/**
 *
 * @author hanif
 */
public class Barang {
    private String kodeBarang, namaBarang;
    private double harga;
    private int stok;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public int getStok() {
        return stok;
    }

    public void setStok(int stok) {
        this.stok = stok;
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

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;
        
        if ((connection = koneksi.getConnection()) != null) {
            int jumlahSimpan = 0;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;
            
            try {
                SQLStatemen = "INSERT INTO tbbarang(kode_barang, nama_barang, harga, stok) VALUES (?,?,?,?)";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, kodeBarang);
                preparedStatement.setString(2, namaBarang);
                preparedStatement.setDouble(3, harga);
                preparedStatement.setInt(4, stok);
                jumlahSimpan = preparedStatement.executeUpdate();
                
                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data barang";
                }
                
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbbarang\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        
        return !adaKesalahan;
    }

    public boolean ubah() {
        boolean adaKesalahan = false;
        Connection connection;
        
        if ((connection = koneksi.getConnection()) != null) {
            int jumlahUpdate = 0;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;
            
            try {
                SQLStatemen = "UPDATE tbbarang SET nama_barang=?, harga=?, stok=? WHERE kode_barang=?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, namaBarang);
                preparedStatement.setDouble(2, harga);
                preparedStatement.setInt(3, stok);
                preparedStatement.setString(4, kodeBarang);
                jumlahUpdate = preparedStatement.executeUpdate();
                
                if (jumlahUpdate < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal mengupdate data barang";
                }
                
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbbarang\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        
        return !adaKesalahan;
    }

    public boolean hapus() {
        boolean adaKesalahan = false;
        Connection connection;
        
        if ((connection = koneksi.getConnection()) != null) {
            int jumlahHapus = 0;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;
            
            try {
                SQLStatemen = "DELETE FROM tbbarang WHERE kode_barang=?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, kodeBarang);
                jumlahHapus = preparedStatement.executeUpdate();
                
                if (jumlahHapus < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menghapus data barang";
                }
                
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbbarang\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        
        return !adaKesalahan;
    }

    public void getAll() {
        Connection connection;
        
        if ((connection = koneksi.getConnection()) != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM tbbarang ORDER BY kode_barang",
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
                    list[i][0] = rset.getString("kode_barang");
                    list[i][1] = rset.getString("nama_barang");
                    list[i][2] = rset.getDouble("harga");
                    list[i][3] = rset.getInt("stok");
                    i++;
                }
                
                rset.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                pesan = "Tidak dapat membuka tabel tbbarang\n" + ex;
                list = null;
            }
        } else {
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
            list = null;
        }
    }
}