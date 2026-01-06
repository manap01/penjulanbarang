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
public class Transaksi {
    private String noTransaksi, tanggal, idCustomer, kodeBarang;
    private int jumlah;
    private double totalHarga;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getNoTransaksi() {
        return noTransaksi;
    }

    public void setNoTransaksi(String noTransaksi) {
        this.noTransaksi = noTransaksi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(String idCustomer) {
        this.idCustomer = idCustomer;
    }

    public String getKodeBarang() {
        return kodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        this.kodeBarang = kodeBarang;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(double totalHarga) {
        this.totalHarga = totalHarga;
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
                // Sesuaikan nama kolom dengan database Anda
                SQLStatemen = "INSERT INTO tbtransaksi(no_transaksi, tanggal, id_customer, kode_barang, jumlah, total_harga) VALUES (?,?,?,?,?,?)";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, noTransaksi);
                preparedStatement.setString(2, tanggal);
                preparedStatement.setString(3, idCustomer);
                preparedStatement.setString(4, kodeBarang);
                preparedStatement.setInt(5, jumlah);
                preparedStatement.setDouble(6, totalHarga);
                jumlahSimpan = preparedStatement.executeUpdate();
                
                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data transaksi";
                }
                
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbtransaksi\n" + ex + "\n" + SQLStatemen;
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
                SQLStatemen = "UPDATE tbtransaksi SET tanggal=?, id_customer=?, kode_barang=?, jumlah=?, total_harga=? WHERE no_transaksi=?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, tanggal);
                preparedStatement.setString(2, idCustomer);
                preparedStatement.setString(3, kodeBarang);
                preparedStatement.setInt(4, jumlah);
                preparedStatement.setDouble(5, totalHarga);
                preparedStatement.setString(6, noTransaksi);
                jumlahUpdate = preparedStatement.executeUpdate();
                
                if (jumlahUpdate < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal mengupdate data transaksi";
                }
                
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbtransaksi\n" + ex + "\n" + SQLStatemen;
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
                SQLStatemen = "DELETE FROM tbtransaksi WHERE no_transaksi=?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, noTransaksi);
                jumlahHapus = preparedStatement.executeUpdate();
                
                if (jumlahHapus < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menghapus data transaksi";
                }
                
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbtransaksi\n" + ex + "\n" + SQLStatemen;
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
                // Query JOIN yang BENAR - sesuaikan dengan nama kolom di database
                PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT t.no_transaksi, t.tanggal, t.id_customer, c.nama_customer, " +
                    "t.kode_barang, b.nama_barang, t.jumlah, t.total_harga " +
                    "FROM tbtransaksi t " +
                    "LEFT JOIN tbcustomer c ON t.id_customer = c.id_customer " +
                    "LEFT JOIN tbbarang b ON t.kode_barang = b.kode_barang " +
                    "ORDER BY t.tanggal DESC",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
                );
                
                ResultSet rset = preparedStatement.executeQuery();
                
                // Hitung jumlah baris
                rset.last();
                int jumlahBaris = rset.getRow();
                rset.beforeFirst();
                
                // Buat array untuk menyimpan data
                list = new Object[jumlahBaris][8];
                int i = 0;
                
                while (rset.next()) {
                    list[i][0] = rset.getString("no_transaksi");
                    list[i][1] = rset.getString("tanggal");
                    list[i][2] = rset.getString("id_customer");
                    list[i][3] = rset.getString("nama_customer");
                    list[i][4] = rset.getString("kode_barang");
                    list[i][5] = rset.getString("nama_barang");
                    list[i][6] = rset.getInt("jumlah");
                    list[i][7] = rset.getDouble("total_harga");
                    i++;
                }
                
                rset.close();
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                pesan = "Tidak dapat membuka tabel tbtransaksi\n" + ex;
                list = null;
            }
        } else {
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
            list = null;
        }
    }
}