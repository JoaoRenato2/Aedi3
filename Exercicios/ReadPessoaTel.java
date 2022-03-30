import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadPessoaTel {
    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("persontel.dat"));

            PessoaComTelefone[] vetor = new PessoaComTelefone[in.readInt()];

            for (int i = 0; i < vetor.length; i++) {
                vetor[i] = (PessoaComTelefone) in.readObject();
            }
            in.close();

            System.out.println("\nLista de Pessoas");
            System.out.println("-------------------");
            for(Pessoa pessoa: vetor){
                System.out.println(pessoa);
            }

        } catch (IOException e) {
            System.out.println("Erro de E/S!");
        }

    } // fim main ( )
} // fim readPessoaTel class
