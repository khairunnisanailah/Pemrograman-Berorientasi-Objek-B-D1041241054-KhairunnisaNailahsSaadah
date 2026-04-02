package Pertemuan14;
/*
Nama: Khairunnisa Nailah Saadah
NIM: D1041241054
 */
public class Latihan1 {
    public static void main(String[] args) {
        int [][] angka = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("=== TABEL 3x3 ===");
        for(int i=0;i<angka.length;i++){
            for(int j=0;j<angka[i].length;j++){
                System.out.print(angka[i][j]+"   ");
            }
            System.out.println();
        }
    }
}
