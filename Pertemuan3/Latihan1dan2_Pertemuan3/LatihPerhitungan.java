package Pertemuan3.Latihan1dan2_Pertemuan3;

public class LatihPerhitungan {
    public static void main(String[] args) {
        //deklarasi dan inisialisasi variable
        int saldo = 1000000;
        //menapilkan saldo awal
        System.err.println("=== SIMULASI TRANSAKSI BANK ===");
        System.err.println("Saldo Awal             : "+saldo);
        //deklarasi dan inisialisasi variable
        int setoran = 500000;
        //deklarasi dan inisialisasi variable dengan menghitung jumlah saldo
        int saldosetelahsetoran = saldo+setoran;
        //menampilkan setoran dan saldo setelah disetor
        System.out.println("Setoran                : "+setoran);
        System.out.println("Saldo Setelah Setoran  : "+saldosetelahsetoran);
        //deklarasi dan inisialisasi variable
        int penarikan = 250000;
        //deklarasi dan inisialisasi variable dengan menghitung jumlah saldo
        int saldoakhir = saldosetelahsetoran-penarikan;
        //menampilkan penarikan dan saldo setelah ditarik
        System.out.println("Penarikan              : "+penarikan);
        System.out.println("Saldo Akhir            : "+saldoakhir);
    }
}
