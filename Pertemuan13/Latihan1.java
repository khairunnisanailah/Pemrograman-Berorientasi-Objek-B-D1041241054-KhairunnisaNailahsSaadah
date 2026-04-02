package Pertemuan13;
/*
Nama: Khairunnisa Nailah Saadah
NIM: D1041241054
 */
public class Latihan1 {
    public static void main(String[] args) {
        int [] nilai = {85, 90, 78, 92, 88};
        System.out.println("=== DAFTAR NILAI ===");
        for(int i=0;i<nilai.length;i++){
            System.out.println("Nilai "+(i+1)+": "+nilai[i]);
        }
        System.out.println("Total Nilai: "+nilai.length);
    }
}
