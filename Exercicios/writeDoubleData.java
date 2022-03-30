import java.io.IOException;
import java.io.*;
import java.util.function.DoubleToIntFunction;


public class writeDoubleData {
    public static void main(String[] args) throws IOException {
        double[] data = {1.555550,3.58,6.78897,1.333333,2.8745};
        double[] aux;

        writeData(data, "file.txt");

   }
   public static void writeData(double data[], String nome_arq) throws IOException{
        FileOutputStream fout = new FileOutputStream(nome_arq);
        DataOutputStream out = new DataOutputStream(fout);

        out.writeInt(data.length);

        for (int i =0; i< data.length;i++){
            out.writeDouble(data[i]);

        }
        out.close();
   }



}
