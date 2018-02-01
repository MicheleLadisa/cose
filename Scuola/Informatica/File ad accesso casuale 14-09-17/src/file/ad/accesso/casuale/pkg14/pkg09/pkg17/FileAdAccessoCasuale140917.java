package file.ad.accesso.casuale.pkg14.pkg09.pkg17;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class FileAdAccessoCasuale140917 {

    public static void main(String[] args) throws IOException {
        String r = "";
        String s = "";
        long vnome = 0;
        for (int i = 0; i < 99; i++) {
            r = r + "*";
        }
        r = r + "\n";
        RandomAccessFile op = new RandomAccessFile("output.txt", "rw");
        op.setLength(50000);
        for (int i = 0; i < 500; i++) {
            op.writeBytes(r);
        }
        Scanner scanner = new Scanner(new FileReader("agenda_telefonica_100.txt"));
        while (scanner.hasNextLine()) {
            int p = 0;
            s = scanner.nextLine();
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {
                    p = i - 1;
                    break;
                }
            }
            String support = s.substring(0, p);
            String nome = "";
            int a = 0;
            for (int i = 0; i < support.length(); i++) {
                if (support.charAt(i) == '\t') {
                    nome = nome + support.substring(a, i) + " ";
                    a = i + 1;
                }
            }
            nome = nome + support.substring(a, support.length());
            vnome = Math.abs(nome.hashCode()) % 500;
            op.seek(vnome * 100);
            op.write(s.getBytes());
        }
        while(true)
        {
            System.out.println("inserisci il nome da cercare in maiuscolo: ");
            Scanner input=new Scanner(System.in);
            String nome=input.nextLine();
            vnome=Math.abs(nome.hashCode()%500);
            op.seek(vnome*100);
            System.out.println("il nome inserito dovrebbe stare alla riga: "+(vnome+1));
            System.out.println(op.readLine());
        }
    }
}
