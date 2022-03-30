package Aedi3.Trabalho;

import java.io.*;

class ArqDadosTest {

    public static void main(String[] args) {

        File arq = new File("aluno.dat");
        if (arq.exists()) arq.delete();

        criaArquivoDeTeste();
        exibeConteudoDoArquivo();

        removeRegistroDoArquivo(2);
        removeRegistroDoArquivo(4);
        exibeConteudoDoArquivo();

        insereRegistroNoArquivo(40, "Juliana");
        exibeConteudoDoArquivo();

        insereRegistroNoArquivo(14, "Marcos");
        exibeConteudoDoArquivo();

        insereRegistroNoArquivo(57, "Roberto");
        exibeConteudoDoArquivo();

        removeRegistroDoArquivo(3);
        exibeConteudoDoArquivo();

        compactaArquivo();
        exibeConteudoDoArquivo();
        testaAcessoSequencial();

        removeRegistroDoArquivo(1);
        exibeConteudoDoArquivo();
        testaAcessoSequencial();

        restauraRegistroDoArquivo(1);
        exibeConteudoDoArquivo();

    }

    private static void criaArquivoDeTeste() {

        // Cria arquivo de dados para armazenar registros da classe finalizada.Aluno
        ArquivoDeDados arqAlunos = new ArquivoDeDados("aluno.dat", Aluno.ALUNO_TAM);

        // Cria alguns registros
        Aluno[] aluno = new Aluno[5];
        aluno[0] = new Aluno(10, "Maria");
        aluno[1] = new Aluno(11, "Ana");
        aluno[2] = new Aluno(15, "Joao");
        aluno[3] = new Aluno(17, "Pedro");
        aluno[4] = new Aluno(21, "Beatriz");

        // Armazena registro no arquivo de dados
        for (int i = 0; i < aluno.length; i++)
            arqAlunos.insereRegistro(aluno[i].getBytes());
        arqAlunos.fecha();
    }

    private static void exibeConteudoDoArquivo() {

        // Cria arquivo de dados para armazenar registros da classe finalizada.Aluno
        ArquivoDeDados arqAlunos = new ArquivoDeDados("aluno.dat", Aluno.ALUNO_TAM);

        System.out.println("\nConteudo do Arquivo:");

        // Armazena registro no arquivo de dados
        for (int i = 0; i < arqAlunos.obtemNumTotalRegistros(); i++) {
            Aluno aluno = new Aluno();
            byte[] dados = arqAlunos.obtemRegistro(i);
            System.out.print("REGISTRO[" + i + "] :");
            if (dados == null)
                System.out.println("REGISTRO MARCADO !");
            else {
                aluno.setBytes(dados);
                System.out.println(aluno);
            }
        }

        // Exibe informacoes sobre o arquivo de dados
        System.out.println("\nNum.Total Registros : " +
                arqAlunos.obtemNumTotalRegistros());
        System.out.println("Num.Registros Validos : " +
                arqAlunos.obtemNumRegistrosValidos());
        System.out.println("Tamanho do Registro : " +
                arqAlunos.obtemTamRegistro());

        arqAlunos.fecha();

    }

    private static void removeRegistroDoArquivo(long numReg) {

        // Cria arquivo de dados para armazenar registros da classe finalizada.Aluno
        ArquivoDeDados arqAlunos = new ArquivoDeDados("aluno.dat", Aluno.ALUNO_TAM);
        // Marca registro para remocao
        arqAlunos.removeRegistro(numReg);
        arqAlunos.fecha();
    }

    private static void insereRegistroNoArquivo(int id, String nome) {

        // Cria arquivo de dados para armazenar registros da classe finalizada.Aluno
        ArquivoDeDados arqAlunos = new ArquivoDeDados("aluno.dat", Aluno.ALUNO_TAM);

        // Cria novo aluno
        Aluno aluno = new Aluno(id, nome);

        // Insere aluno novo no arquivo de dados
        arqAlunos.insereRegistro(aluno.getBytes());
        arqAlunos.fecha();
    }

    private static void compactaArquivo() {

        // Cria arquivo de dados para armazenar registros da classe finalizada.Aluno
        ArquivoDeDados arqAlunos = new ArquivoDeDados("aluno.dat", Aluno.ALUNO_TAM);

        // Elimina registros marcados para remocao
        arqAlunos.eliminaRegistrosMarcados();
        arqAlunos.fecha();
    }

    private static void testaAcessoSequencial() {

        // Cria arquivo de dados para armazenar registros da classe finalizada.Aluno
        ArquivoDeDados arqAlunos = new ArquivoDeDados("aluno.dat", Aluno.ALUNO_TAM);
        System.out.println("\nTeste de acesso sequencial:");

        // Posiciona no primeiro registro válido
        arqAlunos.movePonteiroParaInicio();
        while (arqAlunos.existeRegistroValido()) {
            Aluno aluno = new Aluno();
            byte[] dados = arqAlunos.obtemRegistro();
            aluno.setBytes(dados);
            System.out.println(aluno);
            arqAlunos.movePonteiroRegistroSeguinte();
        }
        arqAlunos.fecha();
    }

    private static void restauraRegistroDoArquivo(long numReg) {

        // Cria arquivo de dados para armazenar registros da classe finalizada.Aluno
        ArquivoDeDados arqAlunos = new ArquivoDeDados("aluno.dat", Aluno.ALUNO_TAM);

        // Marca registro para remocao
        arqAlunos.restauraRegistro(numReg);
        arqAlunos.fecha();
    }
}

