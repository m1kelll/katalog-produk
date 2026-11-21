package kelas.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import kelas.model.Produk;

public class DataProduk {

    private final ObservableList<Produk> daftar =
            FXCollections.observableArrayList();
    private int urutan = 1;

    public ObservableList<Produk> getDaftar() {
        return FXCollections.unmodifiableObservableList(daftar);
    }

    public Produk tambah(String nama, String kategori, long harga) {
        String kode = String.format("PR%03d", urutan);
        Produk produk = new Produk(kode, nama, kategori, harga);
        daftar.add(produk);
        urutan++;
        return produk;
    }
    
    public Produk ubah(String kode, String nama, String kategori, long harga) {
        for (int i = 0; i < daftar.size(); i++) {
            if (daftar.get(i).getKode().equals(kode)) {
                Produk produkBaru = new Produk(kode, nama, kategori, harga);
                daftar.set(i, produkBaru);
                return produkBaru;
            }
        }
        throw new IllegalArgumentException("Produk tidak ditemukan.");
    }
    
    public boolean hapus(String kode) {
        return daftar.removeIf(produk -> produk.getKode().equals(kode));
    }
}

