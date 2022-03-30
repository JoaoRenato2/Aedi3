package organizaçãoDeArquivos.Exemplo1;

import java.io.*;
import java.nio.charset.StandardCharsets;

/*
 * classe com métodos de escrita e leitura de campos de tamanho fixo
 */

class Livro {

    private String titulo; // 40 bytes serao usado para título
    private String autor;  // 40 bytes serao usado para autor
    private byte[] buffer = new byte[80]; // buffer fixo de E/S

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

    public void writeFix(OutputStream out) throws IOException {

        String s;

        byte[] campo1 = titulo.trim().getBytes();
       // for (int i = 0; i < campo1.length; i++) buffer[i] = campo1[i];
        System.arraycopy(campo1,0,buffer,0,campo1.length);

        byte[] campo2 = autor.trim().getBytes();
       // for (int i = 0; i < campo2.length; i++) buffer[40 + i] = campo2[i];
        System.arraycopy(campo2,0,buffer,40,campo2.length);

        out.write(buffer);
    }

    public void readFix(InputStream in) throws IOException {

        in.read(buffer);
        titulo = new String(buffer, 0, 40).trim();
        autor = new String(buffer, 40, 40).trim();
    }
}

