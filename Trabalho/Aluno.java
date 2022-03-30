package Aedi3.Trabalho;

import java.io.*;

class Aluno {

    // Declaracao dos atributos do aluno
    private int id;
    private String nome;
    // Declaracao do tamanho (em bytes) de um registro de aluno
    public static final int ALUNO_TAM = 46;

    // Construtor default
    public Aluno() {
    }

    // Construtor
    public Aluno(int id, String nome) {
        this.id = id;
        if (nome.length() > 40) { // Trunca o nome caso tam. seja maior que 40
            this.nome = nome.substring(0, 40);
            System.out.println(nome);
        }
        else
            this.nome = nome;
    }

    public String toString() {
        return "ID : " + id + "\tNome: " + nome;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void getNome(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Empacota os atributos do aluno em um vetor de bytes
    public byte[] getBytes() {

        // Declara stream de bytes auxiliar
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        // Conecta um DataStream ao stream de bytes
        DataOutputStream out = new DataOutputStream(buffer);
        try {
            // Escreve os atributos do aluno no stream
            out.writeInt(id);
            out.writeUTF(nome);
            // Garante que a operacao de escrita nao sera adiada
            out.flush();
        } catch (IOException error) {
            // Tratar ERROS
        }
        // Obtem e retorna vetor de bytes onde os atributos foram armazenados
        return buffer.toByteArray();
    }

    // Desempacota os atributos do aluno de um vetor de bytes
    public void setBytes(byte[] buffer) {

        // Declara stream de bytes auxiliar a partir do vetor dado como parametro
        ByteArrayInputStream buff = new ByteArrayInputStream(buffer);
        // Conecta um DataStream ao stream de bytes
        DataInputStream in = new DataInputStream(buff);
        try {
            // Realiza a leitura dos atributos do aluno a partir do stream
            id = in.readInt();
            nome = in.readUTF();
        } catch (IOException error) {
            // Tratar ERROS
        }
    }
}
