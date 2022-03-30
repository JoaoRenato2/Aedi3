import java.io.*;

public class WritePessoaConsole {
    public static void main(String[] args) throws IOException {
        int numElem = 2;
        Pessoa[] vetor = consoleReadPessoa(numElem);


    }

    private static Pessoa[] consoleReadPessoa(int numElem) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int id;
        Pessoa[] vet = new Pessoa[numElem];

        for (int i = 0; i < numElem; i++){
            String nome;
            System.out.println("\nEntre com os dados da " +
                    (i+1) + "a. pessoa");
            System.out.print("Id: ");
            id = Integer.parseInt(in.readLine());
            System.out.print("Nome:");
            nome = in.readLine();
            vet[i] = new Pessoa(id,nome);

        }
        return vet;
    }
}
