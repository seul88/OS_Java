package put.os.memory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Damian on 09.12.2016.
 */
public class MemoryManagementUnit {
    public static final int pageSize = 4;
    private char [][] pageTable;
    private char [] TranslationLookasideBuffer;
    private SecoundaryMemory secoundaryMemory;

    public MemoryManagementUnit(SecoundaryMemory secoundaryMemory) {
        this.secoundaryMemory = secoundaryMemory;
    }

    /**
     * Zapisuje zawartosc pliku w pamieci dyskowej, linie rozdzielone sa znakiem '/t'
     * Do testowania mozna sobie uzyc path: ".\\resources\\Program1"
     * @param path - sciezka do pliku
     * @param logicalAddress - adres logiczny pierwszej komorki do zapisania
     * @return - adres logiczny ostatniej zapisanej komorki
     */
    public Address addToMemoryfromFile(String path, Address logicalAddress) {
        int size =0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            String allData = sb.toString();
            size = allData.length();
            size = 15;
            secoundaryMemory.addToMemory(allData,logicalAddress.asSimpleInt());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Address((logicalAddress.asSimpleInt()+size)/MemoryManagementUnit.pageSize,
                (logicalAddress.asSimpleInt()+size)%MemoryManagementUnit.pageSize);
    }

}
