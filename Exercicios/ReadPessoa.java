import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class ReadPessoa {
    public static void main(String[] args) throws IOException {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("person.dat"));
            Pessoa[] vetor = new Pessoa[in.readInt()];
            for (int i = 0; i<vetor.length;i++){
                vetor[i] = (Pessoa) in.readObject();
            }
            in.close();
            System.out.println("\nLista de Pessoas");
            System.out.println("=============");
            for (Pessoa pessoa: vetor){
                System.out.println(pessoa);
            }
        }catch (IOException | ClassNotFoundException e){
            System.out.println("Erro de E/S...\n");
        }
    }
}
