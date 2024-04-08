import java.util.*;
import java.io.*;

public class App {
    public static ArrayList<List<Integer>[][]> referencesGenerator(int pageSize, int matrixSize) {
        List<Integer>[][] data = new List[matrixSize][matrixSize];
        List<Integer>[][] filter = new List[3][3];
        List<Integer>[][] result = new List[matrixSize][matrixSize];

        int pageInt = pageSize / 4;
        int pageCount = 0;
        int referenceCount = 0;

        for (int i = 0; i < filter.length; i++) {
            for (int j = 0; j < filter[i].length; j++) {
                if(pageInt  == referenceCount) {
                    referenceCount = 0;
                    pageCount++;
                }
                filter[i][j] = new ArrayList<Integer>();
                filter[i][j].add(pageCount);
                filter[i][j].add(referenceCount * 4);
                referenceCount++;
            }
        }

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if(pageInt  == referenceCount) {
                    referenceCount = 0;
                    pageCount++;
                }
                data[i][j] = new ArrayList<Integer>();
                data[i][j].add(pageCount);
                data[i][j].add(referenceCount * 4);
                referenceCount++;
            }
        }

        for(int i = 0; i < result.length; i++) {
            for(int j = 0; j < result[i].length; j++) {
                if(pageInt  == referenceCount) {
                    referenceCount = 0;
                    pageCount++;
                }
                result[i][j] = new ArrayList<Integer>();
                result[i][j].add(pageCount);
                result[i][j].add(referenceCount * 4);
                referenceCount++;
            }
        }

        ArrayList<List<Integer>[][]> temp = new ArrayList<List<Integer>[][]>();
        temp.add(filter);
        temp.add(data);
        temp.add(result);

        return temp;
    }

    public static ArrayList<String> fuckingProcess(int matrixSize, ArrayList<List<Integer>[][]> matrices) {

        List<Integer>[][] filter = matrices.get(0);
        List<Integer>[][] data = matrices.get(1);
        List<Integer>[][] result = matrices.get(2);

        int[][] mat2 = new int[3][3];
        int[][] mat1 = new int[matrixSize][matrixSize];
        int[][] mat3 = new int[matrixSize][matrixSize];

        ArrayList<String> references = new ArrayList<>();

        for (int i = 1; i < matrixSize - 1; i++){
            for (int j = 1; j < matrixSize - 1; j++){
                int acum = 0;
                for (int a = -1; a <= 1; a++){
                    for (int b = -1; b <= 1; b++){
                        int i2 = i+a;
                        int j2 = j+b;
                        int i3 = 1+a;
                        int j3 = 1+b;
                        acum += (mat2[i3][j3] * mat1[i2][j2]);
                        references.add("M["+i2+"]["+j2+"],"+data[i2][j2].get(0)+","+data[i2][j2].get(1)+",R");
                        references.add("F["+i3+"]["+j3+"],"+filter[i3][j3].get(0)+","+filter[i3][j3].get(1)+",R");
                    }
                }

                if (acum >= 0 && acum <= 255)
                    mat3[i][j] = acum;
                else if (acum < 0)
                    mat3[i][j] = 0;
                else
                    mat3[i][j] = 255;
                references.add("R["+i+"]["+j+"],"+result[i][j].get(0)+","+result[i][j].get(1)+",W");
            }
        }

        for (int i = 0; i < matrixSize; i++){
            mat3[0][i] = 0;
            references.add("R["+0+"]["+i+"],"+result[0][i].get(0)+","+result[0][i].get(1)+",W");
            mat3[matrixSize-1][i] = 255;
            references.add("R["+(matrixSize-1)+"]["+i+"],"+result[matrixSize-1][i].get(0)+","+result[matrixSize-1][i].get(1)+",W");
        }

        for (int i = 1; i < matrixSize - 1; i++){
            mat3[i][0] = 0;
            references.add("R["+i+"]["+0+"],"+result[i][0].get(0)+","+result[i][0].get(1)+",W");
            mat3[i][matrixSize-1] = 255;
            references.add("R["+i+"]["+(matrixSize-1)+"],"+result[i][matrixSize-1].get(0)+","+result[i][matrixSize-1].get(1)+",W");
        }

        return references;
    }

    public static void writeToFile(ArrayList<String> references, int matrixSize, int pageSize, int maxPage, String archiveName) {

        String fileName = archiveName+".txt";

        String content = "";

        int totalReferences = references.size();

        int totalPages = maxPage + 1;

        content += "TP=" + pageSize + "\nNF=" + matrixSize + "\nNC=" + matrixSize + "\nNF_NC_Filtro= 3\nNR=" + totalReferences + "\nNP=" + totalPages;

        for (int i = 0; i < references.size(); i++) {
            content += "\n" + references.get(i);
        }

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(content);
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }

    public static void main(String[] args) throws Exception { 
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Menú de Opciones: ");
            System.out.println("1. Generar Referencias");
            System.out.println("2. Procesar Referencias");
            System.out.println("Selecciona una opción: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Ingresa el tamaño de las páginas: ");
                    int pageSize = scanner.nextInt();
                    System.out.println("Ingresa el número de columnas: ");
                    int nc = scanner.nextInt();
                    System.out.println("Ingresa el número de filas: ");
                    int nf = scanner.nextInt();

                    ArrayList<List<Integer>[][]> matrices = referencesGenerator(pageSize, nc);
                    ArrayList<String> references = fuckingProcess(nc, matrices);
                    List<Integer>[][] result = matrices.get(2);
                    List<Integer> maxPage = result[3][3];

                    System.out.println("Ingresa el nombre del archivo sin la extensión: ");
                    String archiveName = scanner.next();

                    writeToFile(references, nc, pageSize, maxPage.get(0), archiveName);

                    break;

                case 2:
                    Calculator datos = new Calculator();
                    datos.loadReferences();

                    break;
            }
        }
    }
}
