package organizacaoExterna;

import java.util.Random;

public class Gerador {

    private boolean contains(int n, int[] vet) {
        for (int i = 0; i < vet.length; i++) {
            if (vet[i] == n) return true;
        }
        return false;
    }

    // Gera um numero aleatorio positivo com n digitos
    private int generateRandomDigits(int n) {
        int m = (int) Math.pow(10, n - 1);
        // a soma inicial de m é para garantir que os números sempre
        // tenham n dígitos e não ocorrerem números como 0, 10, etc
        return m + new Random().nextInt(9 * m);
    }

    public int[] gerarVetorNumerosAleatorios(int QTDE_NUMEROS, int NUM_DIGITOS) {
        int[] vet = new int[QTDE_NUMEROS];
        int cont = 0;
        while (cont < QTDE_NUMEROS) {
            int n = generateRandomDigits(NUM_DIGITOS);
            if (!contains(n, vet)) {
                vet[cont] = n;
                cont++;
            }
        }
        return vet;
    }

    public int[] copia(int[] vet) {
        int[] vet_copia = new int[vet.length];
        for (int i = 0; i < vet.length; i++) {
            vet_copia[i] = vet[i];
        }
        return vet_copia;
    }

    public void printVetorNumeros(int[] vet) {
        for (int i = 0; i < vet.length; i++) {
            System.out.print("[" + i + "]:" + vet[i] + " ");
        }
        System.out.println();
    }


}
