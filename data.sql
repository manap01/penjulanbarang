-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 06 Jan 2026 pada 20.05
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbaplikasipenjualanbarang`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbbarang`
--

CREATE TABLE `tbbarang` (
  `kode_barang` varchar(20) NOT NULL,
  `nama_barang` varchar(100) NOT NULL,
  `harga` decimal(15,2) NOT NULL,
  `stok` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbbarang`
--

INSERT INTO `tbbarang` (`kode_barang`, `nama_barang`, `harga`, `stok`) VALUES
('BRG001', 'Laptop Dell Inspiron', 7500000.00, 11),
('BRG002', 'Mouse Wireless Logitech', 150000.00, 25),
('BRG003', 'Keyboard Mechanical RGB', 450000.00, 15),
('BRG004', 'Monitor 24 inch', 2200000.00, 8),
('BRG005', 'USB Flashdisk 64GB', 125000.00, 50),
('PKJ001', 'pengki', 3000000.00, 50),
('PKJ009', 'kipas', 20000.00, 20);

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbcustomer`
--

CREATE TABLE `tbcustomer` (
  `id_customer` varchar(20) NOT NULL,
  `nama_customer` varchar(100) NOT NULL,
  `alamat` text NOT NULL,
  `telepon` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbcustomer`
--

INSERT INTO `tbcustomer` (`id_customer`, `nama_customer`, `alamat`, `telepon`) VALUES
('CST004', 'pt sintoroyo', 'tanggerang', '083821829119'),
('CUS001', 'PT. Teknologi Jaya', 'Jl. Sudirman No.45 Jakarta', '021-5551234'),
('CUS002', 'CV. Global Sejahtera', 'Jl. Thamrin No.12 Surabaya', '031-7778888'),
('CUS003', 'Toko Elektronik Maju Bersama', 'Jl. Merdeka No.8 Bandung', '022-3335555');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbpengguna`
--

CREATE TABLE `tbpengguna` (
  `username` varchar(50) NOT NULL,
  `password` varchar(32) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `level` varchar(20) DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbpengguna`
--

INSERT INTO `tbpengguna` (`username`, `password`, `nama`, `level`) VALUES
('admin', '0192023a7bbd73250516f069df18b500', 'Administrator', 'admin');

-- --------------------------------------------------------

--
-- Struktur dari tabel `tbtransaksi`
--

CREATE TABLE `tbtransaksi` (
  `no_transaksi` varchar(50) NOT NULL,
  `tanggal` date NOT NULL,
  `id_customer` varchar(20) NOT NULL,
  `kode_barang` varchar(20) NOT NULL,
  `jumlah` int(11) NOT NULL,
  `total_harga` decimal(15,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `tbtransaksi`
--

INSERT INTO `tbtransaksi` (`no_transaksi`, `tanggal`, `id_customer`, `kode_barang`, `jumlah`, `total_harga`) VALUES
('TRX20260107010534', '2026-01-07', 'CUS002', 'BRG002', 2, 300000.00),
('TRX20260107010750', '2026-01-07', 'CUS002', 'PKJ009', 3, 60000.00),
('TRX20260107011130', '2026-01-07', 'CST004', 'PKJ009', 20, 400000.00);

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `tbbarang`
--
ALTER TABLE `tbbarang`
  ADD PRIMARY KEY (`kode_barang`);

--
-- Indeks untuk tabel `tbcustomer`
--
ALTER TABLE `tbcustomer`
  ADD PRIMARY KEY (`id_customer`);

--
-- Indeks untuk tabel `tbpengguna`
--
ALTER TABLE `tbpengguna`
  ADD PRIMARY KEY (`username`);

--
-- Indeks untuk tabel `tbtransaksi`
--
ALTER TABLE `tbtransaksi`
  ADD PRIMARY KEY (`no_transaksi`),
  ADD KEY `fk_transaksi_customer` (`id_customer`),
  ADD KEY `fk_transaksi_barang` (`kode_barang`);

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `tbtransaksi`
--
ALTER TABLE `tbtransaksi`
  ADD CONSTRAINT `fk_transaksi_barang` FOREIGN KEY (`kode_barang`) REFERENCES `tbbarang` (`kode_barang`),
  ADD CONSTRAINT `fk_transaksi_customer` FOREIGN KEY (`id_customer`) REFERENCES `tbcustomer` (`id_customer`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
