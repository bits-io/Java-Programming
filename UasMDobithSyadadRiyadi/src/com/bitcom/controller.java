package com.bitcom;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class controller implements Initializable {
    @FXML private TextField etNIK;
    @FXML private TextField etNama;

    @FXML private Button btncetak;
    @FXML private Button btnkeluar;
    @FXML private Button btntambah;
    @FXML private ComboBox<String> cbAgama;

    @FXML private TableColumn<pendudukModel, String> colagama;
    @FXML private TableColumn<pendudukModel, String> coljk;
    @FXML private TableColumn<pendudukModel, String> colnama;
    @FXML private TableColumn<pendudukModel, String> colnik;
    @FXML private TableColumn<pendudukModel, Date> coltgl;

    @FXML private DatePicker dpTgl;

    @FXML private RadioButton rbJKlaki;

    @FXML private RadioButton rbJKpere;

    @FXML private TableView<pendudukModel> tblPenduduk;

    public Connection koneksiDB(){
        Connection koneksi;
        try {
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost:3306/uasdobithdb", "root", "");
            return koneksi;
        } catch (Exception e){
            System.out.println("Kesalahan " + e.getMessage());
            return null;
        }
    }

    public ObservableList<pendudukModel> ambilDataPenduduk(){
        ObservableList<pendudukModel> dataPenduduk = FXCollections.observableArrayList();
        Connection koneksi = koneksiDB();
        String query = "select * from tpendudukdobith";
        Statement st;
        ResultSet rs;
        try {
            st = koneksi.createStatement();
            rs = st.executeQuery(query);
            pendudukModel isiTabelPenduduk;
            while (rs.next()){
                isiTabelPenduduk = new pendudukModel(
                        rs.getString("nik"),
                        rs.getString("nama"),
                        rs.getString("jeniskelamin"),
                        rs.getDate("tanggal"),
                        rs.getString("agama")
                );
                dataPenduduk.add(isiTabelPenduduk);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataPenduduk;
    }

    public void tampilDataPenduduk(){
        ObservableList<pendudukModel> listData = ambilDataPenduduk();
        colnik.setCellValueFactory(new PropertyValueFactory<>("nik"));
        colnama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        coljk.setCellValueFactory(new PropertyValueFactory<>("jk"));
        coltgl.setCellValueFactory(new PropertyValueFactory<>("tgl"));
        colagama.setCellValueFactory(new PropertyValueFactory<>("agama"));

        FilteredList<pendudukModel> pendudukModelFilteredList = new FilteredList<>(listData, p -> true);
        SortedList<pendudukModel> sortedListPenduduk = new SortedList<>(pendudukModelFilteredList);
        sortedListPenduduk.comparatorProperty().bind(tblPenduduk.comparatorProperty());
        tblPenduduk.setItems(sortedListPenduduk);
    }

    public String getJenisKelamin() {
        final ToggleGroup jeniskel = new ToggleGroup();
        rbJKlaki.setToggleGroup(jeniskel);
        rbJKpere.setToggleGroup(jeniskel);

        RadioButton result = (RadioButton) jeniskel.getSelectedToggle();
        return result.getText();
    }

    public void awalForm() {
        etNama.setText("");
        etNIK.setText("");

        rbJKpere.setSelected(false);
        rbJKlaki.setSelected(false);
        cbAgama.setValue("");
        dpTgl.setValue(null);

        btntambah.setText("Tambah Data");
        btncetak.setText("Cetak Data");
        btnkeluar.setText("Keluar App");

        etNama.setDisable(true);
        etNIK.setDisable(true);
        rbJKpere.setDisable(true);
        rbJKlaki.setDisable(true);
        cbAgama.setDisable(true);
        dpTgl.setDisable(true);

        btntambah.setDisable(false);
        btncetak.setDisable(false);
        btnkeluar.setDisable(false);

    }

    public void awalTambah() {
        etNama.setText("");
        etNIK.setText("");

        rbJKpere.setSelected(false);
        rbJKlaki.setSelected(false);
        cbAgama.setValue("");
        dpTgl.setValue(null);

        btntambah.setText("Simpan");
        btnkeluar.setText("Batal");

        etNama.setDisable(false);
        etNIK.setDisable(false);
        rbJKpere.setDisable(false);
        rbJKlaki.setDisable(false);
        cbAgama.setDisable(false);
        dpTgl.setDisable(false);

        btncetak.setDisable(true);

        etNIK.requestFocus();
        etNIK.requestFocus();
    }

    public void aksiTambah() {
        String tambah = btntambah.getText();
        if (tambah.equals("Tambah Data")) {
            awalTambah();
        } else {
            if (etNIK.getText().trim().isEmpty() || etNama.getText().trim().isEmpty() || coltgl.getText().trim().isEmpty() || cbAgama.getValue().trim().isEmpty()){
                Alert pesan = new Alert(Alert.AlertType.WARNING);
                pesan.setTitle("Data Tidak Lengkap");
                pesan.setHeaderText("Lengkapi Data");
                pesan.showAndWait();
                etNIK.requestFocus();
            } else {
                tambahDataPenduduk();
            }

        }
    }

    public void tambahDataPenduduk() {
        Connection koneksi = koneksiDB();
        String queryCek = "select * from tpendudukdobith where nik = '" + etNIK.getText() + "'";
        Statement st;
        ResultSet rs;
        try {
            st = koneksi.createStatement();
            rs = st.executeQuery(queryCek);
            if (rs.next()){
                Alert pesan = new Alert(Alert.AlertType.WARNING);
                pesan.setTitle("Duplikasi Data");
                pesan.setHeaderText("Data sudah Ada");
                pesan.showAndWait();
                etNIK.requestFocus();
            } else {
                String jeniskelamin = getJenisKelamin();
                String queryTambah = "insert into tpendudukdobith values ('" + etNIK.getText() + "', '" + etNama.getText() + "', '" + jeniskelamin + "', '" + dpTgl.getValue() + "', '" + cbAgama.getValue() + "')";
                System.out.println(queryTambah);
                try {
                    st = koneksi.createStatement();
                    st.executeUpdate(queryTambah);
                    tampilDataPenduduk();
                    awalForm();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aksiKeluar() {
        String keluarApp = btnkeluar.getText();
        if (keluarApp.equals("Keluar App")){
            Alert pesan = new Alert(Alert.AlertType.CONFIRMATION);
            pesan.setTitle("Keluar Aplikasi");
            pesan.setHeaderText("Yakin mau keluar dari Aplikasi ?");
            if (pesan.showAndWait().get() == ButtonType.OK){
                Stage stage = (Stage) btnkeluar.getScene().getWindow();
                stage.close();
            }
        } else {
            awalForm();
        }
    }

    public void aksiCetak(ActionEvent actionEvent) {
        JasperPrint jp;
        Map param = new HashMap<>();
        System.out.println("Cetak Berhasil");
        try {
            jp = JasperFillManager.fillReport("laporan/LaporanDataPenduduk.jasper", param, koneksiDB());
            JasperViewer jv = new JasperViewer(jp, true);
            jv.setTitle("Laporan Data Penduduk");
            jv.setVisible(true);
        } catch (JRException e){
            System.out.println(e);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(colnama);
        System.out.println(colnik);
        System.out.println(colagama);
        System.out.println(coltgl);
        System.out.println(coljk);
        System.out.println(tblPenduduk);
        awalForm();
        tampilDataPenduduk();
    }
}
