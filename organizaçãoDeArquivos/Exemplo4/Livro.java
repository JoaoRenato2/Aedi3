package organizaçãoDeArquivos.Exemplo4;


import java.io.*;

/*
 * Classe com métodos de escrita e leitura de campos com delimitador
 */

class Livro {

    private final char Delimitador = '|';
    private final int MAX = 100;
    private String titulo;
    private String autor;

    public Livro() { }

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    @Override
    public String toString() {
        return titulo + " - " + autor;
    }

    public void writeDel(OutputStream out) throws IOException {

        String campo1 = titulo.trim().concat(String.valueOf(Delimitador));
        out.write(campo1.getBytes());

        String campo2 = autor.trim().concat(String.valueOf(Delimitador));
        out.write(campo2.getBytes());
    }

    public void readDel(InputStream in) throws IOException {

        byte[] campo1 = new byte[MAX];
        byte[] campo2 = new byte[MAX];
        int tam1, tam2, ch;

        for (tam1 = 0; (ch = in.read()) != (int) Delimitador; tam1++)
            campo1[tam1] = (byte) ch;

        for (tam2 = 0; (ch = in.read()) != (int) Delimitador; tam2++)
            campo2[tam2] = (byte) ch;

        titulo = new String(campo1, 0, tam1);
        autor = new String(campo2, 0, tam2);
    }
}
