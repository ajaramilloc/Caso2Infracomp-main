import java.io.*;
import java.util.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Calculator {

    static String fileName;
    static BufferedReader reader2;

    static Lock lock = new ReentrantLock();

    static int hits;
    static int misses;
    static int numberOfFrames;
    static HashMap<String, Reference> references = new HashMap<String, Reference>();
    static ArrayList<Page> realMemory = new ArrayList<Page>();

    public void loadReferences() throws FileNotFoundException, IOException {

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the number of page frames:");
            numberOfFrames = scanner.nextInt();
            System.out.println("Enter the name of the references file:");
            fileName = scanner.next();

            hits = 0;
            misses = 0;

            File file = new File(fileName);
            BufferedReader reader = new BufferedReader(new FileReader(file));

            reader.readLine(); // tp
            reader.readLine(); // nf
            reader.readLine(); // nc
            reader.readLine(); // filter

            int numRecords = Integer.parseInt(reader.readLine().split("=")[1]);
            
            reader.readLine(); // num_pages

            for (int i = 0; i < numRecords; i++) {
                String reference = reader.readLine();
                String[] parts = reference.split(",");
                Reference ref = new Reference(Integer.parseInt(parts[1]), parts[3], 0);
                references.put(parts[0], ref);
            }

            reader.close();
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        File file = new File(fileName);
        reader2 = new BufferedReader(new FileReader(file));

        reader2.readLine(); // tp
        reader2.readLine(); // nf
        reader2.readLine(); // nc
        reader2.readLine(); // filter
        reader2.readLine(); // num_records  
        reader2.readLine(); // num_pages

        // start threads
        ReplacementManagerT t1 = new ReplacementManagerT();
        BitRManagerT t2 = new BitRManagerT();
        t1.start();
        t2.start();
    }

    public static synchronized void NRU() throws Exception {

        lock.lock();
        try {

            String reference = reader2.readLine();

            if (reference != null) {

                String[] parts = reference.split(",");
                int pageNumber = Integer.parseInt(parts[1]);

                int index = searchPage(pageNumber);
                
                if (index != -1) {
                    hits++;

                    if (references.get(parts[0]).getBit().equals("R")) {
                        if (realMemory.get(index).getCategory() == 0) {
                            realMemory.get(index).setCategory(2);
                        }
                        else if (realMemory.get(index).getCategory() == 1) {
                            realMemory.get(index).setCategory(3);
                        }
                    }
                    else {
                        if (realMemory.get(index).getCategory() == 0) {
                            realMemory.get(index).setCategory(1);
                        }
                        else if (realMemory.get(index).getCategory() == 2) {
                            realMemory.get(index).setCategory(3);
                        }
                    }
                }
                else {
                    misses++;

                    if (realMemory.size() < numberOfFrames) {
                        if (references.get(parts[0]).getBit().equals("R")) {
                            realMemory.add(new Page(pageNumber, 2));
                        }
                        else {
                            realMemory.add(new Page(pageNumber, 1));
                        }
                    }
                    else {

                        int pageIndex = 0;
                        int min = 4;

                        for (int j = 0; j < realMemory.size(); j++) {
                            int currentPageCategory = realMemory.get(j).getCategory();
                            if (currentPageCategory < min) {
                                min = currentPageCategory;
                                pageIndex = j;
                            }
                        }

                        realMemory.remove(pageIndex);

                        if (references.get(parts[0]).getBit().equals("R")) {
                            realMemory.add(pageIndex, new Page(pageNumber, references.get(parts[0]).getCategory()));
                        }
                        else {
                            realMemory.add(pageIndex, new Page(pageNumber, references.get(parts[0]).getCategory()));
                        }
                    }
                }
            }
            else {
                System.out.println();
                System.out.println("Hits: " + hits);
                System.out.println("Misses: " + misses);
                System.out.printf("Success rate: %,.2f%%", ((double) hits / (hits + misses) * 100));
                System.out.println();
                reader2.close();
                System.exit(0);
            }
        }finally {
            lock.unlock();
        }
    }

    public static synchronized void resetBitR() {
        lock.lock();

        try {
            for (Page page : realMemory) {
                int category = page.getCategory();

                if (category == 2) {
                    page.setCategory(0);
                } 
                else if (category == 3){
                    page.setCategory(1);
                }
            }
        } finally {
            lock.unlock();
        }
    }

    public static synchronized int searchPage(int numPage) {
        for (int i = 0; i < realMemory.size(); i++){
            if (realMemory.get(i).getNumber() == numPage)
                return i;
        }
        return -1;
    }
}
