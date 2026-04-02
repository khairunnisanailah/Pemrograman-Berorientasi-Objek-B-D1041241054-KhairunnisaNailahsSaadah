package Pertemuan12;
/*
Nama: Khairunnisa Nailah Saadah
NIM: D1041241054
 */
class Mahasiswa{
    static int jumlahMahasiswa =0;
    String namaMahasiswa;
    public Mahasiswa(String nama){
        this.namaMahasiswa = nama;
        jumlahMahasiswa++;
        System.out.println("Mahasiswa dibuat: "+nama);
    }
    static int getJumlah(){
        return jumlahMahasiswa;
    }
}
public class LatihanMahasiswa {
    public static void main(String[] args) {
        Mahasiswa m1 = new Mahasiswa("Andi");
        Mahasiswa m2 = new Mahasiswa("Budi");
        Mahasiswa m3 = new Mahasiswa("Citra");
        System.out.println("Total Mahasiswa: "+ Mahasiswa.getJumlah());
        }
}
