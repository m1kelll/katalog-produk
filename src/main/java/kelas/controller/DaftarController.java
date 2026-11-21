package kelas.controller;

import java.io.IOException;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kelas.Navigator;
import kelas.data.DataProduk;
import kelas.model.Produk;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DaftarController {

    @FXML
    private TableView<Produk> tabel;
    @FXML
    private TableColumn<Produk, String> kodeCol;
    @FXML
    private TableColumn<Produk, String> namaCol;
    @FXML
    private TableColumn<Produk, String> jenisCol;
    @FXML
    private TableColumn<Produk, Long> hargaCol;
    @FXML
    private TextField cariField;
    @FXML
    private Label pesanLabel;

    private Navigator navigator;
    private DataProduk data;

    private final ObservableList<Produk> dataTampilan =
            FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        kodeCol.setCellValueFactory(
                new PropertyValueFactory<>("kode"));
        namaCol.setCellValueFactory(
                new PropertyValueFactory<>("nama"));
        jenisCol.setCellValueFactory(
                new PropertyValueFactory<>("kategori"));
        hargaCol.setCellValueFactory(
                new PropertyValueFactory<>("harga"));

        tabel.setItems(dataTampilan);
        tabel.setPlaceholder(new Label("Belum ada produk."));
    }

    public void setKonteks(Navigator navigator, DataProduk data) {
        this.navigator = navigator;
        this.data = data;
        cari();
    }

    @FXML
    private void cari() {
        if (data == null) {
            return;
        }

        String kata = cariField.getText().trim().toLowerCase(Locale.ROOT);
        dataTampilan.clear();

        for (Produk produk : data.getDaftar()) {
            if (produk.getNama().toLowerCase(Locale.ROOT).contains(kata)) {
                dataTampilan.add(produk);
            }
        }

        pesanLabel.setText("Jumlah produk yang tampil: " + dataTampilan.size());
    }

    @FXML
    private void tambah() {
        try {
            navigator.bukaForm();
        } catch (IOException e) {
            pesanLabel.setText("Form gagal dibuka.");
            e.printStackTrace();
        }
    }

    @FXML
    private void detail() {
        Produk produk = tabel.getSelectionModel().getSelectedItem();

        if (produk == null) {
            pesanLabel.setText("Pilih satu produk dahulu.");
            return;
        }

        try {
            navigator.bukaDetail(produk);
        } catch (IOException e) {
            pesanLabel.setText("Detail gagal dibuka.");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void edit() {
        Produk produk = tabel.getSelectionModel().getSelectedItem();

        if (produk == null) {
            pesanLabel.setText("Pilih satu produk dahulu.");
            return;
        }

        try {
            navigator.bukaForm(produk);
        } catch (IOException e) {
            pesanLabel.setText("Form edit gagal dibuka.");
            e.printStackTrace();
        }
    }
    
    @FXML
    private void hapus() {
        Produk produk = tabel.getSelectionModel().getSelectedItem();

        if (produk == null) {
            pesanLabel.setText("Pilih satu produk dahulu.");
            return;
        }

        Alert konfirmasi = new Alert(Alert.AlertType.CONFIRMATION);
        konfirmasi.setTitle("Hapus Produk");
        konfirmasi.setHeaderText("Hapus " + produk.getNama() + "?");
        konfirmasi.setContentText("Produk yang dihapus tidak dapat dikembalikan.");

        Optional<ButtonType> hasil = konfirmasi.showAndWait();
        if (hasil.isPresent() && hasil.get() == ButtonType.OK) {
            data.hapus(produk.getKode());
            cari();
            pesanLabel.setText("Produk berhasil dihapus.");
        }
    }
}
