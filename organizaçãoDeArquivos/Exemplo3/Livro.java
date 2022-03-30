package organizaçãoDeArquivos.Exemplo3;


import java.io.*;

/*
 * classe com métodos de escrita e leitura de campos com indicador de tamanho
 * (apenas 1 byte – valores de 1 a 255)
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

    public String toString() {
        return titulo + " - " + autor;
    }

    public void writeLen(OutputStream out) throws IOException {

        byte[] campo1 = titulo.trim().getBytes();
        out.write(campo1.length);
        out.write(campo1);

        byte[] campo2 = autor.trim().getBytes();
        out.write(campo2.length);
        out.write(campo2);

    }

    public void readLen(InputStream in) throws IOException {

        int tamCampo1 = in.read();
        byte[] campo1 = new byte[tamCampo1];
        in.read(campo1);

        int tamCampo2 = in.read();
        byte[] campo2 = new byte[tamCampo2];
        in.read(campo2);

        titulo = new String(campo1);
        autor = new String(campo2);
    }
}
