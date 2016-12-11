package put.os.memory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*"There are two ways of constructing a software design:
 One way is to make it so simple that there are obviously no deficiencies,
 and the other way is to make it so complicated that there are no obvious deficiencies. "
~ Tony Hoare*/

/**
 * Created by Damian on 09.12.2016.
 */
public class MemoryManagementUnit {
    public static final int pageSize = 4;
    private int [][] pageTable;
    private char [] TranslationLookasideBuffer;
    private SecondaryMemory secondaryMemory;

    public MemoryManagementUnit(SecondaryMemory secondaryMemory) {
        pageTable = new int[800000][2];
        this.secondaryMemory = secondaryMemory;
    }

    /**
     * Zapisuje zawartosc pliku w pamieci dyskowej, linie rozdzielone sa znakiem '/t'
     * Do testowania mozna sobie uzyc path: ".\\resources\\Program1"
     * @param path - sciezka do pliku
     * @param logicalAddress - adres logiczny pierwszej komorki do zapisania
     * @return - adres logiczny pierwszego wolnego adresu po zapisanym programie
     */
    public Address addToMemoryFromFile(String path, Address logicalAddress) {
        int size =0;
        int finalPage = 0;
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
            secondaryMemory.addToMemory(allData,logicalAddress.asSimpleInt()+pageSize);
            finalPage = (logicalAddress.asSimpleInt()+size)/pageSize;

            for (int i = logicalAddress.getPage(); i < finalPage+1; i++) {
                pageTable[i]= new int [] {i+1,0};
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Address(finalPage,
                (logicalAddress.asSimpleInt()+size)%MemoryManagementUnit.pageSize);
    }

    /**
     * Tłumaczy adres logiczny uzywany w procesorze na fizyczny
     * @param page
     * @param shift
     * @return
     */
    public Address translateAdress (int page, int shift) {
        return new Address(pageTable[page][0],shift);
    }

    /**
     * Tłumaczy adres logiczny uzywany w procesorze na fizyczny
     * @param logicalAdress
     * @return
     */
    public Address translateAdress (Address logicalAdress) {
        return new Address(pageTable[logicalAdress.getPage()][0],logicalAdress.getShift());
    }

    /**
     * UWAGA! Parametrami sa pola adresu FIZYCZNEGO, aby przetlumaczyc logiczny uzyj {@link #translateAdress(Address)} lub {@link #translateAdress(int, int)}
     * @param page
     * @param shift
     * @return
     */
    public char readFromMemory (int page, int shift) {
       return secondaryMemory.ReadFromMemory(page*4+shift);
    }

    /**
     * UWAGA! Parametrem jest adres FIZYCZNY, aby przetlumaczyc logiczny uzyj {@link #translateAdress(Address)} lub {@link #translateAdress(int, int)}
     * @param physicalAddress
     * @return
     */
    public char readFromMemory (Address physicalAddress) {
        return secondaryMemory.ReadFromMemory(physicalAddress.getPage()*4+physicalAddress.getShift());
    }

}
