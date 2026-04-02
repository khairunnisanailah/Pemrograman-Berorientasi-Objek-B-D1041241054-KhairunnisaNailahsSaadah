package Pertemuan12;
/*
Nama: Khairunnisa Nailah Saadah
NIM: D1041241054
 */
class Sekolah{
    String namaSekolah;
    Sekolah(String namaSekolah) {
        this.namaSekolah = namaSekolah;
    }
    class Kelas{
        String namaKelas;
        Kelas(String namaKelas) {
            this.namaKelas = namaKelas;
        }
        void info(){
            System.out.println("Sekolah: "+namaSekolah);
            System.out.println("Kelas: "+namaKelas);
        }
        
    }
}
public class LatihanSekolah {
    public static void main(String[] args) {
        Sekolah sekolah = new Sekolah ("SMA Negri 1");
        Sekolah.Kelas kelas = sekolah.new Kelas("X-A");
        kelas.info();
    }
}
