package organizacaoExterna;

import java.util.Arrays;

public class MinHeap {
    private int[] vet;
    private int tam, ind;

    public MinHeap(int[] vet) {
        this.setVet(vet);
        this.setTam(vet.length);
        this.setTam(0);
        for (int i = 1; i < vet.length; i++) {
            this.insere(vet[i]);
        }

    }


    public int Pai(int x) {
        return x / 2;
    }

    public  void imprimirLista() {
        if (tam == 00) {
            System.out.println("Lista vazia!");
        }
        else{
            for (int j = 1; j <= tam; j++)
                System.out.print(vet[j] + " ");
            System.out.println("  ");
        }
    }

    public int menorElemento() {
        return vet[1];
    }

    public void retiraMenor() {
        if (tam == 0) System.out.println("Lista vazia!");
        else {
            int menor_prior = vet[1];
            vet[1] = vet[tam];
            tam--;
            heap_fica(1, tam);
            System.out.println("O elemento removido foi " + menor_prior);
        }
    }

    public void insere(int num) {
        if (tam < vet.length - 1) {
            tam++;
            ind = tam;
            while (ind > 1 && vet[Pai(ind)] > num) {
                vet[ind] = vet[Pai(ind)];
                ind = Pai(ind);
            }
            vet[ind] = num;
        } else
            System.out.println("Lista de prioridades Lotada!");
    }


    public void heap_fica(int i, int qtde) {
        int f_esq, f_dir, menor, aux;
        menor = i;
        if (2 * i + 1 <= qtde) {
            f_esq = 2 * i + 1;
            f_dir = 2 * i;
            if (vet[f_esq] <= vet[f_dir] &&
                    vet[f_esq] < vet[i])
                menor = 2 * i + 1;
            else if (vet[f_dir] < vet[f_esq] &&
                    vet[f_dir] < vet[i])
                menor = 2 * i;
        } else if (2 * i <= qtde) {
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


    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public int getInd() {
        return ind;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }

    public int[] getVet() {
        return vet;
    }

    public void setVet(int[] vet) {
        this.vet = vet;
    }

    @Override
    public String toString() {
        return "Heap{" +
                "vet=" + Arrays.toString(vet) +
                ", tam=" + tam +
                ", ind=" + ind +
                '}';
    }


}
