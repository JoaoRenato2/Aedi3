package organizacaoExterna;

import java.util.Scanner;

public class TesteHeap {
    public static void main(String[] args) {
        Gerador t1 = new Gerador();
        int[] vet = t1.gerarVetorNumerosAleatorios(5,2);
        MinHeap heap = new MinHeap(vet);
        heap.imprimirLista();



    }
}
