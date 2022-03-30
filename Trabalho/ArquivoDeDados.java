package Aedi3.Trabalho;

import java.io.*;

public class ArquivoDeDados {
    // Tamanho em bytes do cabecalho de arquivo.
    private static final int TAMANHO_CABECALHO_ARQ = 20; // Ver declaracoes abaixo.

    // Tamanho em bytes do cabecalho de registro.
    private static final int TAMANHO_CABECALHO_REG = 17; // Ver declaracoes abaixo.

    // Marca usada no cabecalho de registro valido.
    private static final byte FLAG_REGISTRO_VALIDO = 0x00; // 0 em decimal;

    // Marca usada no cabecalho de registro marcado para remocao.
    private static final byte FLAG_REGISTRO_REMOVIDO = 0x0F; // 255 em decimal

    // Arquivo lógico utilizado no acesso ao arquivo fisico.
    private RandomAccessFile arquivo;

    // Nome externo do arquivo manipulado.
    private final String nome;

    // Informacoes do cabecalho do arquivo.

    // Numero total de registros (válidos ou não).
    private int numTotalRegistros; // TAM = 4bytes = 1 int

    // Numero de registros válidos.
    private int numRegistrosValidos; // TAM = 4bytes = 1 int

    // Tamanho do registros (dados + cabeçalho).
    private int tamRegistro; // TAM = 4bytes = 1 int

    // Numero do primeiro registro registro da lista de registros marcados.
    // No caso da lista estar vazia, o valor será -1.
    private long primeiroRegistroMarcado;// TAM = 8bytes

    // Numero de registros marcados.
    // Igual a {@link #numTotalRegistros} - {@link #numRegistrosValidos}.
    // Calculado apenas para facilitar a gerencia.
    private int numRegistrosMarcados;

    // Informacoes do cabecalho do registro atual

    // Posição do registro atual
    private long numRegistroAtual;

    // Marca de registro válido ou não.
    // Seu valor deve ser {@link #FLAG_REGISTRO_VALIDO}, caso o registro
    // seja válido, ou {@link #FLAG_REGISTRO_REMOVIDO}, caso contrário.
    private byte marcaValidade; // TAM = 1byte

    // Tamanho total do registro em bytes (dados + cabeçalho).
    private int tamTotalRegistro; // TAM = 4bytes

    // Tamanho da área de dados do registro em bytes.
    private int tamDadosRegistro; // TAM = 4bytes

    // Número do próximo registro da lista de registros marcados.
    // Seu valor indica o número (posição relativa) do próximo registro
    // da lista de registros marcados para remoção. Caso o registro não
    // pertença a lista ou caso ele seja o último registro da lista, seu
    // valor será -1.
    private long proximoRegistroMarcado; // TAM = 8bytes

    // Retorna o número total de registros (válidos ou não).
    public int obtemNumTotalRegistros() {
        return numTotalRegistros;
    }

    // Retorna o número de registros válidos.
    public int obtemNumRegistrosValidos() {
        return numRegistrosValidos;
    }

    // Retorna o tamanho do registro (apenas da área de dados).
    public int obtemTamRegistro() {
        return tamRegistro - TAMANHO_CABECALHO_REG;
    }

    // Inicializa um novo arquivo de dados com um dado nome
    // e um dado tamanho para área de dados do registro (tamDadosReg).
    // Caso o arquivo físico não exista, ele será criado e aberto para leitura e escrita.
    // Se, por ventura ele já existir ele será apenas aberto para leitura e escrita.
    public ArquivoDeDados(String nome, int tamDadosReg) {
        this.nome = nome;
        File arq = new File(nome);
        if (arq.exists())
            abreArquivo();
        else
            criaArquivo(tamDadosReg);
        // Calculado apenas para facilitar a gerencia
        numRegistrosMarcados = numTotalRegistros - numRegistrosValidos;
    }

    // Fecha arquivo de dados.
    // Garante o armazenamento do cabeçalho de arquivo atualizado e fecha o acesso
    // ao arquivo físico. Após a chamada dessa função, nenhuma outra operação sobre
    // o arquivo de dados deverá ser realizada.
    public void fecha() {
        // Armazena cabecalho de arquivo (apenas por seguranca)
        armazenaCabecalhoArquivo();
        try {
            //Fecha arquivo logico
            arquivo.close();
        } catch (IOException e) {
            // Tratar ERROS
        }
    }

    // Abre um arquivo de dados já existente.
    // Além de abrir o arquivo físico associado ao arquivo de dados, essa função
    // recupera o cabecalho de arquivo armazenado anteriormente.
    // Essa função só deverá ser utilizada quando o arquivo físico já existir.
    private void abreArquivo() {
        try {
            // Abre arquivo logico
            arquivo = new RandomAccessFile(nome, "rws");
            // Recupera dados do cabecalho de arquivo
            recuperaCabecalhoArquivo();
        } catch (IOException e) {
            // Tratar ERROS
        }
    }

    // Cria um novo arquivo de dados.
    // Além de criar o arquivo físico associado ao arquivo de dados, essa função
    // inicializa e armazena o cabecalho de arquivo.
    // Essa função só deverá ser utilizada quando o arquivo físico não existir.
    private void criaArquivo(int tamDadosReg) {
        try {
            // Abre arquivo logico
            arquivo = new RandomAccessFile(nome, "rws");
            // Inicializa dados do cabecalho de arquivo
            inicializaCabecalhoArquivo(tamDadosReg);
            // Armazena dados do cabecalho de arquivo recem-criado
            armazenaCabecalhoArquivo();
        } catch (IOException e) {
            // Tratar ERROS
        }
    }

    // Inicializa cabecalho de arquivo.
    // Define valores iniciais para os dados do cabeçalho de arquivo.
    private void inicializaCabecalhoArquivo(int tamDadosReg) {
        numTotalRegistros = 0;
        numRegistrosValidos = 0;
        tamRegistro = tamDadosReg + TAMANHO_CABECALHO_REG;
        primeiroRegistroMarcado = -1L;
    }

    // Grava o cabeçalho de arquivo no arquivo físico.
    private void armazenaCabecalhoArquivo() {
        try {
            // Posiciona ponteiro para escrita do cabecalho de arquivo
            arquivo.seek(0L);
            // Armazena dados do cabecalho de arquivo
            arquivo.writeInt(numTotalRegistros);
            arquivo.writeInt(numRegistrosValidos);
            arquivo.writeInt(tamRegistro);
            arquivo.writeLong(primeiroRegistroMarcado);
        } catch (IOException e) {
            // Tratar ERROS
        }
    }

    // Le o cabeçalho de arquivo a partir do arquivo físico.
    private void recuperaCabecalhoArquivo() {
        try {
            // Posiciona ponteiro para leitura do cabecalho de arquivo
            arquivo.seek(0L);
            // Recupera dados do cabecalho de arquivo
            numTotalRegistros = arquivo.readInt();
            numRegistrosValidos = arquivo.readInt();
            tamRegistro = arquivo.readInt();
            primeiroRegistroMarcado = arquivo.readLong();
        } catch (IOException e) {
            // Tratar ERROS
        }
    }

    // Inicializa cabecalho de registro.
    // Define valores iniciais para os dados do cabeçalho de registro.
    private void inicializaCabecalhoRegistro(int tamReg, int tamDados) {
        marcaValidade = FLAG_REGISTRO_VALIDO;
        tamTotalRegistro = tamReg;
        tamDadosRegistro = tamDados;
        proximoRegistroMarcado = -1L;
    }

    // Grava o cabeçalho de registro no arquivo físico.
    private void armazenaCabecalhoRegistro(long numRegistro) {
        try {
            // Calcula posicao de escrita do cabecalho
            long posicaoCabecalhoRegistro = TAMANHO_CABECALHO_ARQ +
                    numRegistro * tamRegistro;
            // Posiciona ponteiro para escrita do cabecalho de registro
            arquivo.seek(posicaoCabecalhoRegistro);
            // Armazena dados do cabecalho de registro
            arquivo.writeByte(marcaValidade);
            arquivo.writeInt(tamTotalRegistro);
            arquivo.writeInt(tamDadosRegistro);
            arquivo.writeLong(proximoRegistroMarcado);
        } catch (IOException e) {
            // Tratar ERROS
        }
    }

    // Le o cabeçalho de registro a partir do arquivo físico.
    private void recuperaCabecalhoRegistro(long numRegistro) {
        try {
            // Calcula posicao de leitura do cabecalho
            long posicaoCabecalhoRegistro = TAMANHO_CABECALHO_ARQ +
                    numRegistro * tamRegistro;
            // Posiciona ponteiro para leitura do cabecalho de registro
            arquivo.seek(posicaoCabecalhoRegistro);
            // Recupera dados do cabecalho de registro
            marcaValidade = arquivo.readByte();
            tamTotalRegistro = arquivo.readInt();
            tamDadosRegistro = arquivo.readInt();
            proximoRegistroMarcado = arquivo.readLong();
        } catch (IOException e) {
            // Tratar ERROS
        }
    }

    // Grava registro no arquivo físico.
    private void armazenaRegistro(byte[] dados, long numRegistro) {
        try {
            // Calcula tamanho total do registro
            int tamTotalReg = dados.length + TAMANHO_CABECALHO_REG;
            // Inicializa dados do cabecalho de registro
            inicializaCabecalhoRegistro(tamTotalReg, dados.length);
            // Armazena cabecalho de registro
            armazenaCabecalhoRegistro(numRegistro);
            // Calcula posicao de escrita dos dados do registro
            long posicaoDadosRegistro = TAMANHO_CABECALHO_ARQ +
                    numRegistro * tamRegistro +
                    TAMANHO_CABECALHO_REG;
            // Posiciona ponteiro para escrita dos dados do registro
            arquivo.seek(posicaoDadosRegistro);
            // Armazena dados do registro
            arquivo.write(dados);
        } catch (IOException e) {
            // Tratar ERROS
        }
    }

    // Le registro a partir do arquivo físico.
    private byte[] recuperaRegistro(long numRegistro) {
        byte[] dados = null;
        try {
            // Calcula posicao de leitura dos dados do registro
            long posicaoDadosRegistro = TAMANHO_CABECALHO_ARQ +
                    numRegistro * tamRegistro +
                    TAMANHO_CABECALHO_REG;
            // Posiciona ponteiro para leitura dos dados do registro
            arquivo.seek(posicaoDadosRegistro);
            // Usa informacao do cabecalho para criar buffer de dados
            dados = new byte[tamDadosRegistro];
            // recupera dados do registro
            arquivo.read(dados);
        } catch (IOException e) {
            // Tratar ERROS
        }
        return dados;
    }

    // Obtem os dados do registro que o ponteiro lógico está indicando
    public byte[] obtemRegistro() {

        byte[] dados = null;

        // Recupera cabecalho de registro
        recuperaCabecalhoRegistro(numRegistroAtual);

        // Verifica validade do registro
        if (marcaValidade == FLAG_REGISTRO_VALIDO)
            // Recupera dados do registro
            dados = recuperaRegistro(numRegistroAtual);

        // Retorna dados
        return dados;
    }

    // Obtem um registro do arquivo de dados.
    public byte[] obtemRegistro(long numRegistro) {
        byte[] dados = null;
        // Recupera cabecalho de registro
        recuperaCabecalhoRegistro(numRegistro);
        // Verifica validade do registro
        if (marcaValidade == FLAG_REGISTRO_VALIDO)
            // Recupera dados do registro
            dados = recuperaRegistro(numRegistro);
        // Retorna dados
        return dados;
    }

    // Insere um registro no arquivo de dados.
    // Um registro novo contendo os dados informados será acrescentado ao arquivo de dados
    // Caso não exista espaço disponível (registros previamente marcados),
    // o registro será acrescentado o final do arquivo. Contudo se houver registros
    // na lista de registros marcados para remoção, o espaço (bytes) utilizado por
    // um deles será reaproveitado para armazenar o novo registro. A lista de registros
    // marcados será administrada como uma pilha, portanto o último registro marcado
    // será o primeiro a ser reaproveitado.
    public void insereRegistro(byte[] dados) {

        long numRegistro;

        if (numRegistrosMarcados == 0) { // Nao ha espaco para aproveitar
            // Insere novo registro no final do arquivo
            numRegistro = numTotalRegistros;

            // Atualiza quantidade total de registros


            // Atualiza quantidade de registros validos


            // Armazena cabecalho do arquivo atualizado


        } else { // Existe espaco para aproveitar
            // Reaproveita primeiro registro da lista de marcados
            numRegistro = primeiroRegistroMarcado;

            // Recupera cabecalho do registro a ser reaproveitado


            // Atualiza lista de registros marcados


            // Atualiza quantidade de registros validos


            // Armazena cabecalho do arquivo atualizado


            // Reduz numero de registros marcados


        }

        // Armazena registro na posicao (calculada acima)


    }


    // Marca para remoção um dos registros do arquivo de dados.
    // O registro cujo número (ou posição relativa) for informado será incluído na
    // lista de registros marcados. A lista de registros marcados será administrada
    // como uma pilha, portanto o último registro marcado será o primeiro a ser
    // reaproveitado em uma futura inclusão.
    public void removeRegistro(long numRegistro) {
        // Recupera cabecalho de registro
        recuperaCabecalhoRegistro(numRegistro);
        // Verifica validade do registro
        if (marcaValidade == FLAG_REGISTRO_VALIDO) {
            // Marca registro para remocao
            marcaValidade = FLAG_REGISTRO_REMOVIDO;

            // Atualiza indicador do proximo registro marcado
            proximoRegistroMarcado = primeiroRegistroMarcado;


            // Armazena cabecalho de registro atualizado


            // Atualiza lista de registros marcados
            primeiroRegistroMarcado = numRegistro;


            // Atualiza quantidade de registros validos


            // Armazena cabecalho do arquivo atualizado


            // Incrementa numero de registros marcados


        }
    }

    // Elimina todos os registros marcados para remoção.
    // Um novo arquivo de dados e, consequentemente, um novo arquivo físico serão
    // gerados e apenas os registros válidos serão mantidos. O arquivo físico antigo
    // será removido ao final da eliminação.
    public void eliminaRegistrosMarcados() {
        // Elimina arquivo temporario caso ele exista
        File arq = new File("dados.tmp");
        if (arq.exists()) {
            arq.delete();
        }
        // Cria arquivo temporario
        ArquivoDeDados tmp = new ArquivoDeDados("dados.tmp",
                tamRegistro - TAMANHO_CABECALHO_REG);
        long numRegistro = 0;
        while (numRegistro < numTotalRegistros) {
            // Recupera cabecalho de registro
            recuperaCabecalhoRegistro(numRegistro);
            // Verifica validade do registro
            if (marcaValidade == FLAG_REGISTRO_VALIDO) {

                // Recupera dados do registro


                // Insere registro no arquivo temporario


            }
            numRegistro++;
        }
        // Fecha arquivo atual
        fecha();
        // Fecha arquivo temporario
        tmp.fecha();
        // Elimina arquivo atual
        File arq1 = new File(nome);
        if (arq1.exists())
            arq1.delete();
        // Altera nome do arquivo temporario
        arq.renameTo(arq1);
        // Reabre arquivo de dados
        abreArquivo();
        // Reinicializa numero de registros marcados
        numRegistrosMarcados = 0;
    }

    // Posiciona o ponteiro lógico no primeiro registro válido
    public void movePonteiroParaInicio() {
        numRegistroAtual = 0;
        recuperaCabecalhoRegistro(numRegistroAtual);
        while (!existeRegistroValido() && numRegistroAtual < numTotalRegistros)
            numRegistroAtual++;
    }

    // Verifica se o ponteiro lógico está apontando para um registro válido
    public boolean existeRegistroValido() {
        return obtemRegistro() != null && numRegistroAtual < numTotalRegistros;
    }

    // Posiciona o ponteiro no próximo registro válido do arquivo de dados.
    public void movePonteiroRegistroSeguinte() {

        numRegistroAtual++;
        recuperaCabecalhoRegistro(numRegistroAtual);

        while (!existeRegistroValido() && numRegistroAtual < numTotalRegistros) {
            numRegistroAtual++;
        }
    }

    // Restaura um registro previamente marcado para remoção tornando-o novamente um registro válido
    // do arquivo de dados. Caso o registro informado seja válido a função deve simplesmente encerrar
    // sem realizar nenhuma outra operação. Caso o registro informado tenha sido marcado para remoção
    // a função deve, além de remover a marcação do registro, realizar as alterações necessárias na
    // lista de registros marcados para remoção.
    public void restauraRegistro(long numRegistro) {
        // Recupera cabecalho de registro numRegistro
        recuperaCabecalhoRegistro(numRegistro);
        // Verifica se o registro é válido
        if (marcaValidade == FLAG_REGISTRO_REMOVIDO) {
            // Armazena o proximo registro marcado
            long proximo = proximoRegistroMarcado;
            // Aponta n para o primeiro registro marcado
            long n = primeiroRegistroMarcado;
            if (numRegistro != primeiroRegistroMarcado) {
                do {
                    // Recupera cabecalho do primeiro registro marcado
                    recuperaCabecalhoRegistro(n);
                    if (proximoRegistroMarcado != numRegistro)
                        // avança com n
                        n = proximoRegistroMarcado;
                    // avanca até que o próximo de n seja o numRegistro
                } while (proximoRegistroMarcado != numRegistro);
            }
            // faz com que o próximo de n seja o proximo do numRegistro
            proximoRegistroMarcado = proximo;
            // atualiza o cabeçalho do registro
            armazenaCabecalhoRegistro(n);
            // inicializa um cabeçalho válido
            inicializaCabecalhoRegistro(tamRegistro, tamDadosRegistro);
            // Armazena o cabecalho validando novamente o registro
            armazenaCabecalhoRegistro(numRegistro);
            // Atualiza quantidade de registros validos
            numRegistrosValidos++;
            // Reduz numero de registros marcados
            numRegistrosMarcados--;
            // Armazena cabecalho do arquivo atualizado
            armazenaCabecalhoArquivo();
        }
    }

}


