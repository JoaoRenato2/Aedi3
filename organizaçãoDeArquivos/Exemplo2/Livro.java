
package organizaçãoDeArquivos.Exemplo2;

import java.io.*;

/*
 * Classe com métodos de escrita e leitura de campos com indicador de tamanho
 * (dois bytes convertido para caractere – valores de 01 a 99)
 */

class Livro {

    private String titulo;
    private String autor;

    public Livro() {
    }

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    @Override
    public String toString() {
        return titulo + " - " + autor;
    }

    public void writeLenC(OutputStream out) throws IOException {

        byte[] campo1 = titulo.trim().getBytes();
        String tam1 = String.valueOf(campo1.length);
        // Armazena sempre 2 caracteres
        if (tam1.length() < 2) tam1 = "0" + tam1;
        out.write(tam1.getBytes()); // grava o tamanho do campo
        out.write(campo1);          // grava o campo

        byte[] campo2 = autor.trim().getBytes();
        String tam2 = String.valueOf(campo2.length);
        // Armazena sempre 2 caracteres
        if (tam2.length() < 2) tam2 = "0" + tam2;
        out.write(tam2.getBytes()); // grava o tamanho do campo
        out.write(campo2);          // grava o campo
    }

    public void readLenC(InputStream in) throws IOException {

        // instancia vetor de 2 bytes para ler os 2 caracteres
        // que compõem o tamanho do campo
        byte[] tam1 = new byte[2];
        // le o numero inteiro referente ao primeiro caractere
        // do tamanho do campo
        tam1[0] = (byte) in.read();
        // System.out.println(tam1[0]);
        // le o numero inteiro referente ao segundo caractere
        // do tamanho do campo
        tam1[1] = (byte) in.read();
        // System.out.println(tam1[1]);
        String tamanho1 = new String(tam1);
        // System.out.println(tamanho1);
        // le o tamanho total do campo incluindo o tamanho
        // do campo e o conteudo do campo
        byte[] campo1 = new byte[Integer.parseInt(tamanho1)];
        in.read(campo1); // le o campo

        byte[] tam2 = new byte[2];
        tam2[0] = (byte) in.read();
        tam2[1] = (byte) in.read();
        String tamanho2 = new String(tam2);
        byte[] campo2 = new byte[Integer.parseInt(tamanho2)];
        in.read(campo2);

        titulo = new String(campo1);
        autor = new String(campo2);
    }
}
