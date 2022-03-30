import java.io.*;

public class WritePessoaTel {
    public static void main(String[] args) throws IOException {

        int numElem = 2;
        //PessoaComTelefone[] vetor = consoleReadPessoaComTel(numElem);
        PessoaComTelefone[] vetor = readVetorPessoaComTel();

        try {
            // Cria stream para escrita de objetos
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream("persontel.dat"));
            // Escreve tamanho do vetor
            out.writeInt(vetor.length);
            for (Pessoa pessoa : vetor)
                out.writeObject(pessoa); // Escreve objeto da classe
            // PessoaComTelefone juntamente com
            // os objetos da classe Telefone
            out.close(); // Fecha stream
        } catch (IOException e) {
            System.out.println("Erro de E/S !!!\n");
        }


    }

    private static PessoaComTelefone[] readVetorPessoaComTel() throws IOException {
        PessoaComTelefone[] vet = new PessoaComTelefone[5];

        vet[0] = new PessoaComTelefone(10, "Maria Cláudia Santos");
        vet[0].setNumTel(2);
        vet[0].setTel(0, new Telefone("9999-1234"));
        vet[0].setTel(1, new Telefone("9898-7898"));

        vet[1] = new PessoaComTelefone(11, "Ana Paula Souza");
        vet[1].setNumTel(3);
        vet[1].setTel(0, new Telefone("8888-1234"));
        vet[1].setTel(1, new Telefone("7897-2254"));
        vet[1].setTel(2, new Telefone("9988-7788"));

        vet[2] = new PessoaComTelefone(15, "João Fernando Silva");
        vet[2].setNumTel(1);
        vet[2].setTel(0, new Telefone("9977-1515"));

        vet[3] = new PessoaComTelefone(17, "Pedro Cardoso Queiroz");
        vet[3].setNumTel(2);
        vet[3].setTel(0, new Telefone("3375-1234"));
        vet[3].setTel(1, new Telefone("2425-7898"));

        vet[4] = new PessoaComTelefone(21, "Beatriz Miranda de Oliveira");
        vet[4].setNumTel(1);
        vet[4].setTel(0, new Telefone("3444-2784"));

        return vet;
    }

    private static PessoaComTelefone[] consoleReadPessoaComTel(int numElem)
            throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String Nome;
        int Id, numTel;
        PessoaComTelefone[] vet = new PessoaComTelefone[numElem];
        for (int i = 0; i < numElem; i++) {
            System.out.println("\nEntre com dados da " + (i + 1) + "a. pessoa:");
            System.out.print("Id : ");
            Id = Integer.parseInt(in.readLine());
            System.out.print("Nome : ");
            Nome = in.readLine();
            vet[i] = new PessoaComTelefone(Id, Nome);
            System.out.print("Num.Telefones : ");
            numTel = Integer.parseInt(in.readLine());
            vet[i].setNumTel(numTel);
            for (int j = 0; j < numTel; j++) {
                System.out.print("\t" + (j + 1) + "o. Numero : ");
                vet[i].setTel(j, new Telefone(in.readLine()));
            }
        }
        return vet;
    }
}
