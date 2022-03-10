-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 14, 2022 at 05:10 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 8.0.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uasdobithdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `tpendudukdobith`
--

CREATE TABLE `tpendudukdobith` (
  `nik` varchar(20) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `jeniskelamin` varchar(20) NOT NULL,
  `tanggal` date NOT NULL,
  `agama` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tpendudukdobith`
--

INSERT INTO `tpendudukdobith` (`nik`, `nama`, `jeniskelamin`, `tanggal`, `agama`) VALUES
('32222', 'Dobith', 'Laki-laki', '2021-12-30', 'Islam'),
('3222', 'Syadad Riyadi', 'Laki-laki', '2022-01-07', 'Islam'),
('43', 'M', 'Laki-laki', '2021-12-29', 'Islam'),
('32', 'M Dob', 'Laki-laki', '2006-01-12', 'Islam');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
