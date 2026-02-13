package p03NamingOperator;
/*
NAMA: KHAIRUNNISA NAILAH SAADAH
NIM: D1041241054
TANGGAL: 13/02/2026
 */
public class p042OperatorAritmatika {
    public static void main(String[] args) {
        int a = 10;
        int b = 3;
        System.out.println("a = "+a);
        System.out.println("b = "+b);
        System.out.println();

        System.out.println("a + b = "+(a+b));
        System.out.println("a - b = "+(a-b));
        System.out.println("a * b = "+(a*b));
        System.out.println("a / b = "+(a/b));
        System.out.println("a % b = "+(a%b));
        
        //pembagian dengan desimal
        double hasil = (double) a/b;
        System.out.println("a / b = "+hasil);
    }
}
