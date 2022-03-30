package Exercicios;

import java.io.*;

public class ContaLinhas {
    public static void main(String[] args) throws IOException {
        BufferedReader arq = new BufferedReader(new FileReader("file.txt"));

        int numchar = 0;
        int contLinha = 0;
        String linha = arq.readLine();

        while (linha != null){
            contLinha ++;
            numchar += linha.length();
            linha = arq.readLine();
        }
        arq.close();
        System.out.println("Num. Linhas: " + contLinha);
        System.out.println("Num. caracteres: " + numchar);
    }
}
