import java.io.*;

public class ReadDoubleData {
    public static void main(String[] args) throws IOException {
        double[] data = {1.555550,3.58,6.78897,1.333333,2.8745};
        double[] aux;


        aux = readData("file.txt");

        for (double valor : aux){
            System.out.printf("%.2f\n", valor);
        }

    }
    public static double[] readData(String nome_arq) throws IOException{
        FileInputStream fin = new FileInputStream(nome_arq);
        DataInputStream in = new DataInputStream(fin);

        double[] data = new double[in.readInt()];
        for (int i = 0; i<data.length;i++){
            data[i] = in.readDouble();

        }
        in.close();

        return data;
    }


}

