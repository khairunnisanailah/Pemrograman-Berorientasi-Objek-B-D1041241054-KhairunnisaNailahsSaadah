package UAS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//========= CLASS STATIC =========
class Static {
    public static double pendapatanToko = 0;

    public static void tambahPendapatan(double keuntungan) {
        pendapatanToko += keuntungan;
    }

    public static double getPendapatanToko() {
        return pendapatanToko;
    }

    public static void kurangiPendapatan(double jumlah) {
        pendapatanToko -= jumlah;
        if(pendapatanToko < 0) {
            pendapatanToko = 0;
        }
    }
}

//======= INTERFACE METHOD ======
interface TampilanInfo {
    public void tampilkanInfo();
}

// ========= ABSTRACT CLASS PRODUK =========
abstract class Produk implements TampilanInfo {
    private String kodeProduk;
    private String namaProduk;
    private double hargaProduk;

    public Produk(String kodeProduk, String namaProduk, double hargaProduk) {
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.hargaProduk = hargaProduk;
    }

    public abstract String getTipeProduk();

    public String getKodeProduk() { return kodeProduk; }
    public String getNamaProduk() { return namaProduk; }
    public double getHargaProduk() { return hargaProduk; }

    public void setHargaProduk(double hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public boolean kurangiStok(int jumlah) {
    return true;
}

    public int getStokProduk() {
        return 0;
    }

    public void tambahStok(int jumlah) {
       // default: ndk melakukan apa-apa
    }

    @Override
    public void tampilkanInfo() {
        System.out.printf("%-6s | %-30s | Rp %,8.0f%n",
                kodeProduk, namaProduk, hargaProduk);
    }
}

// ========= SUBCLASS BARANG =========
class Barang extends Produk {
    private int stokProduk;
    //constructor biasa
    public Barang(String kodeProduk, String namaProduk, double hargaProduk, int stokProduk) {
        super(kodeProduk, namaProduk, hargaProduk);
        this.stokProduk = stokProduk;
    }

    //cons overloading utk penambahan barang baru tapi blm diketahui stok
    public Barang(String kodeProduk, String namaProduk, double hargaProduk) {
        this(kodeProduk, namaProduk, hargaProduk, 0);
    }

    @Override
    public String getTipeProduk() {
        return "Barang";
    }

    @Override
    public int getStokProduk() {
        return stokProduk;
    }

    public void setStokProduk(int stokProduk) {
        this.stokProduk = stokProduk;
    }

    @Override
    public void tambahStok(int jumlah) {
        if (jumlah > 0) {
            stokProduk += jumlah;
        }
    }

    @Override
    public boolean kurangiStok(int jumlah) {
        if (jumlah > 0 && stokProduk >= jumlah) {
            stokProduk -= jumlah;
            return true;
        }
        return false;
    }

    @Override
    public void tampilkanInfo() {
        System.out.printf("%-6s | %-30s | Rp %,8.0f | Stok: %d%n",
                getKodeProduk(), getNamaProduk(),
                getHargaProduk(), stokProduk);
    }
}

// ========= SUBCLASS JASA =========
class Jasa extends Produk {
    private String satuanJasa;

    public Jasa(String kodeProduk, String namaProduk, double hargaProduk, String satuanJasa) {
        super(kodeProduk, namaProduk, hargaProduk);
        this.satuanJasa = satuanJasa;
    }

    @Override
    public String getTipeProduk() { return "Jasa"; }

    public String getSatuanJasa() { return satuanJasa; }
    public void setSatuanJasa(String satuanJasa) { this.satuanJasa = satuanJasa; }

    @Override
    public void tampilkanInfo() {
        System.out.printf("%-6s | %-30s | Rp %,8.0f | Satuan: %s%n",
                getKodeProduk(), getNamaProduk(), getHargaProduk(), getSatuanJasa());
    }

}

// ========= CLASS KASIR =========
class Kasir implements TampilanInfo {
    private String idKasir;
    private String namaKasir;

    public Kasir(String idKasir, String namaKasir) {
        this.idKasir   = idKasir;
        this.namaKasir = namaKasir;
    }

    public String getIdKasir() { return this.idKasir; }
    public String getNamaKasir() { return this.namaKasir; }
    public void setNamaKasir(String namaKasir) { this.namaKasir = namaKasir; }

    @Override
    public void tampilkanInfo() {
        System.out.println("Kasir         : [" + idKasir + "] " + namaKasir);
    }
}

// ========= CLASS DETAIL TRANSAKSI =========
class DetailTransaksi implements TampilanInfo {
    private String kodeDetail;
    private Produk produk;
    private int jumlahBarangDibeli;
    private double subtotal;

    public DetailTransaksi(String kodeDetail, Produk produk, int jumlahBarangDibeli) {
        this.kodeDetail         = kodeDetail;
        this.produk             = produk;
        this.jumlahBarangDibeli = jumlahBarangDibeli;
        this.subtotal           = hitungSubtotal();
    }

    public String getKodeDetail() { return kodeDetail; }
    public Produk getProduk() { return produk; }
    public int getJumlahBarang() { return jumlahBarangDibeli; }
    public double getSubtotal() { return subtotal; }

    public double hitungSubtotal() {
        this.subtotal = jumlahBarangDibeli * produk.getHargaProduk();
        return this.subtotal;
    }

    @Override
    public void tampilkanInfo() {
        System.out.printf("  %-30s %3dx  Rp %,5.0f%n",
                produk.getNamaProduk(), jumlahBarangDibeli, subtotal);
    }
}

// ========= CLASS TRANSAKSI =========
class Transaksi implements TampilanInfo {
    private String kodeTransaksi;
    private LocalDateTime waktuTransaksi;
    private Kasir kasir;
    private ArrayList<DetailTransaksi> listDetail;
    private double totalHarga;
    private double uangDibayar;
    private double kembalian;

    public Transaksi(String kodeTransaksi, Kasir kasir) {
        this.kodeTransaksi  = kodeTransaksi;
        this.waktuTransaksi = LocalDateTime.now();
        this.kasir          = kasir;
        this.listDetail     = new ArrayList<>();
        this.totalHarga     = 0;
    }

    public String getKodeTransaksi() { return kodeTransaksi; }
    public LocalDateTime getWaktuTransaksi() { return waktuTransaksi; }
    public Kasir getKasir() { return kasir; }
    public ArrayList<DetailTransaksi> getListDetail() { return listDetail; }
    public double getTotalHarga() { return totalHarga; }

    public boolean tambahItem(String kodeDetail, Produk produk, int jumlah) {
        
        //pesan eror jika stok tdk ckp
        if (!produk.kurangiStok(jumlah)) {
            System.out.println("[PERINGATAN] Stok " + produk.getNamaProduk()
                    + " tidak mencukupi! Stok tersedia: " + produk.getStokProduk());
            return false;
        }
        DetailTransaksi detail = new DetailTransaksi(kodeDetail, produk, jumlah);

        //menambahkan data item ke dlm array list kyk vector
        listDetail.add(detail);

        //menghitung pendapatan bersih
        hitungTotal();
        if (produk instanceof Barang) {
            Static.tambahPendapatan(produk.getHargaProduk() * jumlah * 0.30);
        } else if (produk instanceof Jasa) {
            Static.tambahPendapatan(produk.getHargaProduk() * jumlah * 0.70);
        }
        return true;
    }

    //menghitung total harga yg hrs dibayar
    public double hitungTotal() {
        totalHarga = 0;
        for (DetailTransaksi d : listDetail) totalHarga += d.getSubtotal();
        return totalHarga;
    }

    //menghitung jumlah produk yg dibeli
    public int getJumlahProdukDibeli(Produk produk) {
        int total = 0;
        for(DetailTransaksi d : listDetail) {
            if (d.getProduk().getKodeProduk().equals(produk.getKodeProduk())){
                total += d.getJumlahBarang();
            }
        } 
        return total;
    }

    // Perbaikan validasi: Cek apakah masih ada item yang tersisa untuk direturn
    public boolean validasiJumlahBarang(Return rtn){
        for (DetailTransaksi d: listDetail){
            Produk produk = d.getProduk();
            int jumlahDibeli = getJumlahProdukDibeli(produk);
            int jumlahDireturn = (rtn != null) ? rtn.getJumlahProdukDireturn(produk) : 0;
            if (jumlahDireturn < jumlahDibeli){
                return true; // Masih ada barang yang belum direturn sepenuhnya
            }
        }
        return false;
    }

    public int getJumlahSudahDireturn(Produk produk, ArrayList<Return> daftarReturn) {
    int total = 0;
    for (Return r : daftarReturn) {
            if (r.getTransaksiReferensi().getKodeTransaksi().equals(this.kodeTransaksi)) {
                total += r.getJumlahProdukDireturn(produk);
            }
        }
        return total;
    }

    //method pembayaran
    public boolean prosesPembayaran(double uangDibayar) {
        if (uangDibayar < totalHarga) {
            return false;
        }
        this.uangDibayar = uangDibayar;
        this.kembalian = uangDibayar - totalHarga;
        return true;
    }

    public double getUangDibayar() { return uangDibayar; }

    public double getKembalian() { return kembalian; }

    @Override
    public void tampilkanInfo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
        System.out.println("=====================================================");
        System.out.println("         SISTEM POINT OF SALE - MEDIA CORNER    ");
        System.out.println("               Toko ATK - Pontianak             ");
        System.out.println("=====================================================");
        System.out.println("No. Transaksi : " + kodeTransaksi);
        System.out.println("Tanggal       : " + waktuTransaksi.format(fmt));
        kasir.tampilkanInfo();
        System.out.println("-----------------------------------------------------");
        System.out.println("  ITEM YANG DIBELI:");
        for (DetailTransaksi d : listDetail) d.tampilkanInfo();
        System.out.println("-----------------------------------------------------");
        System.out.printf("  TOTAL BAYAR                          Rp %,5.0f%n", totalHarga);
        System.out.printf("  UANG DIBAYAR                         Rp %,5.0f%n", uangDibayar);
        System.out.printf("  KEMBALIAN                            Rp %,5.0f%n", kembalian);
        System.out.println("=====================================================");
        System.out.println("             Terima kasih telah berbelanja!         ");
        System.out.println("            Toko ATK Media Corner Pontianak.       ");
        System.out.println("=====================================================");
    }
}


// ========= CLASS DETAIL RETURN =========
class DetailReturn implements TampilanInfo {
    private String kodeDetailReturn;
    private Produk produk;
    private int jumlahReturn;
    private int jumlahLayak;
    private int jumlahRusak;

    public DetailReturn(String kodeDetailReturn, Produk produk,int jumlahReturn, int jumlahLayak, int jumlahRusak) {
        this.kodeDetailReturn = kodeDetailReturn;
        this.produk           = produk;
        this.jumlahReturn     = jumlahReturn;
        this.jumlahLayak      = jumlahLayak;
        this.jumlahRusak      = jumlahRusak;
    }

    public String getKodeDetailReturn() { return kodeDetailReturn; }
    public Produk getProduk() { return produk; }
    public int getJumlahReturn() { return jumlahReturn; }
    public int getJumlahLayak(){ return jumlahLayak; }
    public int getJumlahRusak(){ return jumlahRusak; }

    public double getNilaiReturn() {
        return jumlahReturn * produk.getHargaProduk();
    }

    @Override
    public void tampilkanInfo() {
        System.out.printf("  %-25s %3dx | Layak: %d | Rusak: %d | Rp %,8.0f%n",
                produk.getNamaProduk(), jumlahReturn, jumlahLayak, jumlahRusak, getNilaiReturn());
    }
}

// ========= CLASS RETURN =========
class Return implements TampilanInfo {
    private String kodeReturn;
    private String alasanReturn;
    private LocalDateTime waktuReturn;
    private Transaksi transaksiReferensi;
    private ArrayList<DetailReturn> listDetailReturn;
    private double totalUangReturn;
    private boolean returnSudahDiproses = false;

    public Return(String kodeReturn, String alasanReturn, Transaksi transaksiReferensi) {
        this.kodeReturn           = kodeReturn;
        this.alasanReturn         = alasanReturn;
        this.waktuReturn          = LocalDateTime.now();
        this.transaksiReferensi   = transaksiReferensi;
        this.listDetailReturn     = new ArrayList<>();
        this.totalUangReturn      = 0;
    }

    public String getKodeReturn() { return kodeReturn; }
    public String getAlasanReturn() { return alasanReturn; }
    public LocalDateTime getWaktuReturn() { return waktuReturn; }
    public Transaksi getTransaksiReferensi() { return transaksiReferensi; }
    public ArrayList<DetailReturn> getListDetailReturn() { return listDetailReturn; }
    public double getTotalUangReturn() { return totalUangReturn; }

    //nambahain item yg di return ke dlm array list kyk vector
    public void tambahItemReturn(String kodeDetailReturn, Produk produk, int jumlah, int jumlahLayak, int jumlahRusak) {
        listDetailReturn.add(new DetailReturn(kodeDetailReturn, produk, jumlah, jumlahLayak, jumlahRusak));
    }

    //hitung total uang yg di retur
    public double hitungTotalReturn() {
        totalUangReturn = 0;
        for (DetailReturn dr : listDetailReturn){
            totalUangReturn += dr.getNilaiReturn();
        } 
        return totalUangReturn;
    }

    public void prosesReturn() {
        if(returnSudahDiproses){
            System.out.println("Return sudah diproses!");
            return;
        }
        for (DetailReturn dr : listDetailReturn){
            dr.getProduk().tambahStok(dr.getJumlahLayak());
            if (dr.getProduk() instanceof Barang){
                Static.kurangiPendapatan(dr.getProduk().getHargaProduk() * dr.getJumlahReturn() * 0.30);
            }
        }
        //panggil total uang yg di retur yg dihitung sebelumnya
        hitungTotalReturn();
        returnSudahDiproses = true;
    }

    @Override
    public void tampilkanInfo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd MMMM yyyy, HH:mm");
        System.out.println("===================================================");
        System.out.println("               BUKTI RETURN BARANG               ");
        System.out.println("         Toko ATK Media Corner Pontianak       ");
        System.out.println("===================================================");
        System.out.println("No. Return      : " + kodeReturn);
        System.out.println("Ref. Transaksi  : " + transaksiReferensi.getKodeTransaksi());
        System.out.println("Waktu Return    : " + waktuReturn.format(fmt));
        System.out.println("Alasan Return   : " + alasanReturn);
        System.out.println("---------------------------------------------------");
        System.out.println("  ITEM YANG DIKEMBALIKAN:");
        for (DetailReturn dr : listDetailReturn) dr.tampilkanInfo();
        System.out.println("---------------------------------------------------");
        System.out.printf("  TOTAL UANG KEMBALI    Rp %,10.0f%n", totalUangReturn);
        System.out.println("===================================================");
    }

    public int getJumlahProdukDireturn(Produk produk) {
        int total = 0;
        for (DetailReturn dr : listDetailReturn) {
            if (dr.getProduk().getKodeProduk().equals(produk.getKodeProduk())) {
                total += dr.getJumlahReturn();
            }
        } 
        return total;
    }

    //memastikan jumlah yg di retur tdk melebihi brg yg dibeli
    public boolean validasiReturn(Produk produk, int jumlahBaru, Transaksi trx) {
        int totalDibeli = trx.getJumlahProdukDibeli(produk);
        int sudahReturn = getJumlahProdukDireturn(produk);
        return (sudahReturn + jumlahBaru) <= totalDibeli;
    }
}

//========= CLASS VALIDASI =========
class Validasi {
    public static int inputInt(Scanner inputUser, String pesan) {
        while (true) {
            try {
                System.out.print(pesan);
                return inputUser.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Input harus berupa angka!");
                inputUser.nextLine();
            }
        }
    }

    public static String inputYorN(Scanner inputUser, String pesan) {
        String input;
        do {
            System.out.print(pesan);
            input = inputUser.next().toLowerCase();
            if (!input.equals("y") && !input.equals("n")) {
                System.out.println("[ERROR] Input hanya boleh y atau n!");
            }
        } while (!input.equals("y") && !input.equals("n"));
        return input;
    }

    public static double inputDouble(Scanner inputUser, String pesan) {
        while (true) {
            try {
                System.out.print(pesan);
                return inputUser.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("[ERROR] Input harus berupa angka!");
                inputUser.nextLine();
            }
        }
    }
}

// ========= MAIN PROGRAM =========
public class SistemPOSMediaCorner {
    public static void main(String[] args) {
        Scanner inputUser = new Scanner(System.in);
        
        System.out.println("           ==========================================");
        System.out.println("           |        SISTEM POINT OF SALE (PoS)      |");
        System.out.println("           |         Media Corner - Pontianak       |");
        System.out.println("           ==========================================");
        System.out.println();

        String[][] daftarKasir = {
            {"K001", "Zaza"},
            {"K002", "Zizi"}
        };
        Kasir[] kasir = new Kasir[daftarKasir.length];
        
        int index = 0;
        for (String[] dataKasir : daftarKasir) {
            kasir[index++] = new Kasir(dataKasir[0], dataKasir[1]);
        }
        
        Barang[] daftarBarang = new Barang[7];
        daftarBarang[0] = new Barang("BR001", "BALLPOINT STANDARD E-7",     2000, 50);
        daftarBarang[1] = new Barang("BR002", "PENSIL 2B JOYKO R-88",       2000, 30);
        daftarBarang[2] = new Barang("BR003", "PENSIL MEKANIK JOYKO MP-39", 3000, 20);
        daftarBarang[3] = new Barang("BR004", "PENGGARIS BESI 30 CM",       4000, 15);
        daftarBarang[4] = new Barang("BR005", "BUKU SIDU ISI 32",           2000, 40);
        daftarBarang[5] = new Barang("BR006", "FILE BINDER BINDEX",         35000); 
        daftarBarang[6] = new Barang("BR007", "SPIDOL SNOWMAN HITAM",       3000);

        Jasa[] daftarJasa = new Jasa[4];
        daftarJasa[0] = new Jasa("FC001", "Fotocopy",          500, "Per Lembar");
        daftarJasa[1] = new Jasa("PR001", "Print Hitam Putih", 500, "Per Lembar");
        daftarJasa[2] = new Jasa("PR002", "Print Warna",      1000, "Per Lembar");
        daftarJasa[3] = new Jasa("JL001", "Jilid",            5000, "Unit");

        int pilihanMenu;
        boolean jalan = true;
        ArrayList<Return> daftarReturn = new ArrayList<>();
        ArrayList<Transaksi> daftarTransaksi = new ArrayList<>();
        Transaksi trx = null;
        Return rtn = null;
        int nomorTransaksi = 1;
        int nomorReturn = 1;

        while (jalan) {
            System.out.println("\nSelamat Datang di Media Corner!\nAda yang Bisa Saya Bantu?");
            System.out.println("\n=================================");
            System.out.println("         MENU UTAMA");
            System.out.println("=================================");
            System.out.println("1. Transaksi Baru");
            System.out.println("2. Return Barang");
            System.out.println("3. Lihat Pendapatan Bersih Toko");
            System.out.println("4. Lihat Stok");
            System.out.println("5. Keluar");

            pilihanMenu = Validasi.inputInt(inputUser,"\nSilahkan masukkan menu yang diinginkan (1-5): ");
            
            switch (pilihanMenu) {
                case 1:
                    String lanjutTransaksi = "y";
                    String kodeTrx = String.format("TRX-%03d", nomorTransaksi++);
                    trx = new Transaksi(kodeTrx, kasir[0]);
                    daftarTransaksi.add(trx);
                    rtn = null; // Reset data return setiap transaksi baru dibuat
                    //agar transaksi baru dimulai tanpa membawa data return dari transaksi sebelumnya
                    
                    boolean inputTrxSelesai = false;
                    do {
                        System.out.println("\n========== SILAHKAN MASUKKAN TRANSAKSI ANDA ==========");
                        System.out.println("1. Barang");
                        System.out.println("2. Jasa");
                        System.out.println("0. Kembali ke Menu Utama");
                        int tipe = Validasi.inputInt(inputUser,"Masukkan tipe produk (1 atau 2): ");


                        if (tipe == 0) {
                            if (trx.getListDetail().isEmpty()) {
                                daftarTransaksi.remove(trx);
                                nomorTransaksi--;
                            }
                            inputTrxSelesai = true;
                            break; 
                        }
                        
                        if(tipe == 1){
                            System.out.println("=======================================================================");
                            System.out.println("                          DAFTAR BARANG                                ");
                            System.out.println("=======================================================================");
                            System.out.printf("%-3s | %-6s | %-30s | %-12s | %s%n","NO", "KODE", "NAMA PRODUK", "HARGA", "STOK");
                            System.out.println("------------------------------------------------------------------------");
                            int no = 1;
                            for (Barang b : daftarBarang) {
                                System.out.printf("%-3d | ", no++);
                                b.tampilkanInfo();
                            }
                            System.out.println("=======================================================================");
                            System.out.println();
                            
                            int pilihBarang = Validasi.inputInt(inputUser, "Pilih barang (1 - 7) (atau ketik 0 untuk kembali): ");

                            if(pilihBarang == 0){
                                break;
                            }

                            if(pilihBarang < 1 || pilihBarang > 7){
                                System.out.println("MAAF, PILIHAN ANDA TIDAK VALID!");
                                continue;
                            }

                            int jumlahBarang = Validasi.inputInt(inputUser,"Jumlah: ");
                            if(jumlahBarang <= 0){
                                System.out.println("MAAF, JUMLAH BARANG YANG DIBELI HARUS LEBIH DARI 0!");
                                continue;
                            }

                            boolean berhasil = trx.tambahItem("DT00" + pilihBarang, daftarBarang[pilihBarang - 1], jumlahBarang);
                            if(!berhasil){
                               continue;
                            }
                            
                        } else if (tipe == 2){
                            System.out.println("==================================================================================");
                            System.out.println("                               DAFTAR JASA");
                            System.out.println("==================================================================================");
                            System.out.printf("%-3s | %-6s | %-30s | %-12s | %s%n","NO", "KODE", "NAMA PRODUK", "HARGA", "SATUAN");
                            System.out.println("----------------------------------------------------------------------------------");
                            int no = 1;
                            for (Jasa j : daftarJasa) {
                                System.out.printf("%-3d | ", no++);
                                j.tampilkanInfo();
                            }
                            System.out.println("==================================================================================");
                            System.out.println();
                            
                            int pilihJasa =  Validasi.inputInt(inputUser,"Pilih jasa (1-4) atau 0 untuk kembali : ");

                            if(pilihJasa == 0){
                                break;
                            }

                            if(pilihJasa < 1 || pilihJasa > 4){
                                System.out.println("MAAF, PILIHAN ANDA TIDAK VALID!");
                                continue;
                            }

                            int jumlahJasa = Validasi.inputInt(inputUser,"Jumlah: ");
                            if(jumlahJasa <= 0){
                                System.out.println("MAAF, JUMLAH JASA YANG DIBELI HARUS LEBIH DARI 0!");
                                continue;
                            }
                            boolean berhasil = trx.tambahItem("JS00" + pilihJasa, daftarJasa[pilihJasa-1], jumlahJasa);
                            if(!berhasil){
                                continue;
                            }
                        } else {
                            System.out.println("MAAF, TIPE PRODUK YANG ANDA PILIH TIDAK SESUAI!");
                            continue;
                        }

                        lanjutTransaksi = Validasi.inputYorN(inputUser, "Tambah Transaksi? (y/n): ");
                    } while (lanjutTransaksi.equalsIgnoreCase("y"));
                    if(trx.getListDetail().isEmpty()){
                        System.out.println("Transaksi kosong!");
                        daftarTransaksi.remove(trx);
                        break;
                    }
                    System.out.println("\n----------------------------------");
                    System.out.printf("TOTAL BELANJA : Rp %,10.0f%n", trx.getTotalHarga());
                    System.out.println("-----------------------------------");
                    double uangBayar;
                    while (true) {
                        uangBayar = Validasi.inputDouble(inputUser,"Masukkan uang pembayaran: ");
                        if (trx.prosesPembayaran(uangBayar)) {
                            break;
                        }
                        System.out.println("Uang yang diberikan kurang!");
                    }
                    trx.tampilkanInfo();
                    break;
                    
                case 2:
                    if(daftarTransaksi.isEmpty()){
                        System.out.println("Belum ada transaksi!\n");
                        break;
                    }
                    System.out.println("\nDAFTAR TRANSAKSI");
                    for(int i = 0; i < daftarTransaksi.size(); i++){
                        System.out.printf(
                            "%d. %s%n", i + 1, daftarTransaksi.get(i).getKodeTransaksi()
                        );
                    }
                    int pilihTrx = Validasi.inputInt(inputUser,"Pilih transaksi (atau ketik 0 untuk kembali): ");
                    if(pilihTrx == 0) break;

                    if(pilihTrx < 1 || pilihTrx > daftarTransaksi.size()){
                        System.out.println("Pilihan tidak valid!");
                        break;
                    }
                    trx = daftarTransaksi.get(pilihTrx - 1);
                    boolean masihBisaReturn = false;
                    for (Barang b : daftarBarang) {
                        int dibeli = trx.getJumlahProdukDibeli(b);
                        int direturn = trx.getJumlahSudahDireturn(b, daftarReturn);
                        if (dibeli > direturn) {
                            masihBisaReturn = true;
                            break;
                        }
                    }

                    if (!masihBisaReturn) {
                        System.out.println("Semua barang pada transaksi ini sudah direturn.");
                        break;
                    }

                    //memastikan alasan return berupa teks, bukan hanya angka atau kosong
                    String alasanReturn;
                    do {
                        System.out.print("Alasan return: ");
                        alasanReturn = inputUser.nextLine().trim();

                        if (alasanReturn.isEmpty()) {
                            System.out.println("Alasan return tidak boleh kosong!");
                        } else if (alasanReturn.matches("\\d+")) {
                            System.out.println("Alasan return tidak boleh hanya berupa angka!");
                        }

                    } while (alasanReturn.isEmpty() || alasanReturn.matches("\\d+"));

                    String kodeRtn = String.format("RTN-%03d", nomorReturn++);
                    rtn = new Return(kodeRtn, alasanReturn, trx);
                    int pilihReturn = 0;
                    String lanjutReturn = "y";
                    do {
                        System.out.println("=====================================================================");
                        System.out.println("                       DAFTAR BARANG DI TRANSAKSI                    ");
                        System.out.println("=====================================================================");
                        
                        boolean adaBarangBisaReturn = false;
                        for (int i = 0; i < daftarBarang.length; i++) {
                            Barang b = daftarBarang[i];
                            int jumlahDibeli = trx.getJumlahProdukDibeli(b);
                            int jumlahDireturnLama = trx.getJumlahSudahDireturn(b, daftarReturn);
                            int jumlahDireturnSekarang = rtn.getJumlahProdukDireturn(b);
                            int totalDireturn = jumlahDireturnLama + jumlahDireturnSekarang;
                            int sisaReturn = jumlahDibeli - totalDireturn;
                            if (jumlahDibeli > 0 && sisaReturn > 0) {
                                System.out.printf("%d. %s (Dibeli: %d, Sudah Return: %d, Sisa: %d)%n", 
                                        (i + 1), b.getNamaProduk(), jumlahDibeli, totalDireturn, sisaReturn);
                                adaBarangBisaReturn = true;
                            }
                        }
                        System.out.println("=====================================================================");
                        
                        if(!adaBarangBisaReturn) {
                            System.out.println("Semua item belanjaan sudah sukses direturn.");
                            break;
                        }
                        
                        //memastikan inputan sesuai range opsi yg ada
                        pilihReturn = Validasi.inputInt(inputUser,"Pilih nomor barang sesuai daftar di atas: ");
                        if(pilihReturn < 1 || pilihReturn > 7){
                            System.out.println("MAAF, PILIHAN ANDA TIDAK VALID!");
                            continue;
                        }
                        
                        Produk produkDipilih = daftarBarang[pilihReturn - 1];
                        int jumlahDibeli = trx.getJumlahProdukDibeli(produkDipilih);
                        
                        //memastikan return utk brg yg sdh dibeli sblmnya
                        if(jumlahDibeli == 0){
                            System.out.println("Barang tidak ada pada transaksi!");
                            continue;
                        }
                        int jumlahSudahDireturnLama = trx.getJumlahSudahDireturn(produkDipilih, daftarReturn);
                        int jumlahSudahDireturnSekarang = rtn.getJumlahProdukDireturn(produkDipilih);
                        int sisaReturn = jumlahDibeli - (jumlahSudahDireturnLama + jumlahSudahDireturnSekarang);
                        
                        //memastikan jumlah brg yg di retur sesuai
                        int jumlahReturn = Validasi.inputInt(inputUser, "Jumlah yang ingin dikembalikan: ");
                        if (jumlahReturn <= 0) {
                            System.out.println("Jumlah return harus lebih dari 0!");
                            continue;
                        }
                        
                        if (jumlahReturn > sisaReturn) {
                            System.out.println("Jumlah return melebihi sisa barang yang bisa direturn! Sisa yang bisa direturn: " + sisaReturn);
                            continue;
                        }
                        
                        int jumlahLayak;
                        int jumlahRusak;
                        while (true) {
                            jumlahLayak = Validasi.inputInt(inputUser, "Jumlah barang layak jual: ");
                            jumlahRusak = Validasi.inputInt(inputUser, "Jumlah barang rusak: ");
                            if (jumlahLayak < 0 || jumlahRusak < 0) {
                                System.out.println("Jumlah tidak boleh negatif!");
                                continue;
                            }
                            if (jumlahLayak + jumlahRusak != jumlahReturn) {
                                System.out.println("Jumlah layak + rusak harus = " + jumlahReturn);
                                continue;
                            }
                            break;
                        }
                        
                        rtn.tambahItemReturn("DR00" + pilihReturn, produkDipilih, jumlahReturn, jumlahLayak, jumlahRusak);
                        lanjutReturn = Validasi.inputYorN(inputUser, "Apakah ada barang lain yang ingin direturn? (y/n): ");
                        
                    } while (lanjutReturn.equalsIgnoreCase("y"));
                    if (!rtn.getListDetailReturn().isEmpty()) {
                        rtn.prosesReturn();
                        rtn.tampilkanInfo();
                        daftarReturn.add(rtn);
                    } else {
                        System.out.println("Return dibatalkan karena tidak ada item yang dipilih.");
                        nomorReturn--; 
                    }
                    break;
                    
                case 3:
                    System.out.println();
                    System.out.println("---------------------------------------------------------------------");
                    System.out.printf("                 TOTAL PENDAPATAN BERSIH TOKO: Rp %,5.0f%n", Static.getPendapatanToko());
                    System.out.println("---------------------------------------------------------------------");
                    break;
                    
                case 4:
                    System.out.println("=====================================================================");
                    System.out.println("                       STOK BARANG SEKARANG                          ");
                    System.out.println("=====================================================================");
                    for (Barang b : daftarBarang) b.tampilkanInfo();
                    System.out.println("=====================================================================");
                    break;
                    
                case 5:
                    jalan = false;
                    System.out.println("\n==================================");
                    System.out.println("Terima kasih telah menggunakan");
                    System.out.println("Sistem Point of Sale");
                    System.out.println("Media Corner Pontianak");
                    System.out.println("==================================");
                    break;
                    
                default:
                    System.out.println("\n[ERROR] Menu tidak tersedia! Silakan pilih menu 1 sampai 5.");
                    break;
            }
        }
        inputUser.close(); 
    }
}