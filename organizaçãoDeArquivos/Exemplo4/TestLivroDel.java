package organizaçãoDeArquivos.Exemplo4;

import java.io.*;

class TestLivroDel {
    public static void main(String[] args) throws IOException {

        Livro[] livros = createDummyData();
        String FILE_NAME = "livrodel.dat";
        writeLivros(livros, FILE_NAME);
        readLivros(FILE_NAME);
    }

    public static void writeLivros(Livro[] livros, String FILE_NAME) throws IOException {

        FileOutputStream out = new FileOutputStream(FILE_NAME);

        out.write(livros.length);
        for (int i = 0; i < livros.length; i++)
            livros[i].writeDel(out);

        out.close();
    }

    public static void readLivros(String FILE_NAME) throws IOException {

        FileInputStream in = new FileInputStream(FILE_NAME);

        int totalLivros = in.read();
        for (int i = 0; i < totalLivros; i++) {
            Livro liv = new Livro();
            liv.readDel(in);
            System.out.println(liv);
        }
        in.close();
    }

    public static Livro[] createDummyData() {

        Livro[] livros = new Livro[3];

        livros[0] = new Livro("File Structures", "Michael Folk");
        livros[1] = new Livro("Managing Gigabytes", "Ian witten");
        livros[2] = new Livro("Clean Architecture", "Uncle Bob");

        return livros;
    }
}

