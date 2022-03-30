package Exercicios;

import java.io.*;

import java.io.IOException;

public class ContaBytes {
    public static void main(String[] args) throws IOException {

        //FileInputStream in = new  FileInputStream(args[0]);
        FileInputStream in = new FileInputStream("file.txt");


        int value, total = 0;
        while ((value = in.read()) != -1) {
            System.out.printf("%s:%d\n",(char)value, value);
            total ++;
        }
        in.close();
        System.out.printf("Total: %d bytes",total);

    }
}
