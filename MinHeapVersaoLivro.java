package Heap;

import java.util.*;

// Heap Mínimo
// versão original do livro:
// Estruturas de Dados
// Fernanda Ascencio e Graziela Araujo
public class MinHeapVersaoLivro {

    static int vet[] = new int[10 + 1];
    static Scanner entrada = new Scanner(System.in);

    public static void main(String[] args) {
        String menu;
        int op;
        int tam = 0, ind, num;

        menu = "\n1 - Inserir elemento na lista" +
                "\n2 - Consultar o elemento de menor prioridade" +
                "\n3 - Extrair o elemento de menor prioridade" +
                "\n4 - Imprimir a lista" +
                "\n5 - Sair";
        do {
            System.out.println(menu);
            op = entrada.nextInt();

            if (op == 1) {
                if (tam < vet.length - 1) {
                    tam++;
                    System.out.print("Digite um número: ");
                    // leitura do número a ser inserido
                    num = entrada.nextInt();
                    ind = tam;
                    while (ind > 1 && vet[Pai(ind)] > num) {
                        vet[ind] = vet[Pai(ind)];
                        ind = Pai(ind);
                    }
                    vet[ind] = num;
                } else
                    System.out.println("Lista de prioridades Lotada!");
            } else if (op == 2) {
                if (tam == 0) System.out.println("Lista vazia!");
                else System.out.println("Elemento de menor prioridade:" + vet[1]);
            } else if (op == 3) {
                if (tam == 0) System.out.println("Lista vazia!");
                else {
                    int menor_prior = vet[1];
                    vet[1] = vet[tam];
                    tam--;
                    heap_fica(1, tam);
                    System.out.println("O elemento removido foi " + menor_prior);
                }
            } else if (op == 4) {
                if (tam == 0) System.out.println("Lista vazia!");
                else {
                    for (int j = 1; j <= tam; j++)
                        System.out.print(vet[j] + " ");
                    System.out.println();
                }
            }
        } while (op != 5);
    }

    public static int Pai(int x) {
        return x / 2;
    }

    public static void heap_fica(int i, int qtde) {
        int f_esq, f_dir, menor, aux;
        menor = i;
        if (2 * i + 1 <= qtde) {
            // o nó que está sendo analisado tem filhos p/
            // esquerda e direita
            f_esq = 2 * i + 1;
            f_dir = 2 * i;
            if (vet[f_esq] <= vet[f_dir] &&
                    vet[f_esq] < vet[i])
                menor = 2 * i + 1;
            else if (vet[f_dir] < vet[f_esq] &&
                    vet[f_dir] < vet[i])
                menor = 2 * i;
        } else if (2 * i <= qtde) {
            // o nó que está sendo analisado tem filho
            // apenas p/ a direita
            f_dir = 2 * i;
            if (vet[f_dir] < vet[i])
                menor = 2 * i;
        }
        if (menor != i) {
            aux = vet[i];
            vet[i] = vet[menor];
            vet[menor] = aux;
            heap_fica(menor, qtde);
        }
    }
}
