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
    private PageTable [] pageTables;
    private int pageTablesPointer =0;
    private int secondaryMemoryPointer =-1;
    private SecondaryMemory secondaryMemory;

    public MemoryManagementUnit(SecondaryMemory secondaryMemory) {
        pageTables =  new PageTable[10];
        this.secondaryMemory = secondaryMemory;
    }

    private class PageTable {
        private int [][] pageTable;
        private int [] TranslationLookAsideBuffer;

        public PageTable() {
            this.pageTable = new int[100000][2];
        }
    }

    /**
     * Zapisuje zawartosc pliku w pamieci dyskowej, linie rozdzielone sa znakiem '/t'
     * Do testowania mozna sobie uzyc path: ".\\resources\\Program1"
     * @param path - sciezka do pliku
     * @return - numer tablicy stron do danego programu
     */
    public int addToMemoryFromFile(String path) {
        pageTables[pageTablesPointer] = new PageTable();
        int size;
        int finalPage;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            br.close();
            String allData = sb.toString();
            size = allData.length();
            finalPage = (size)/pageSize;

            for (int i = 0; i < finalPage; i++) {
                pageTables[pageTablesPointer].pageTable[i]= new int [] {((secondaryMemoryPointer+3-(secondaryMemoryPointer+3)%4))/pageSize+i,0};
            }
            secondaryMemoryPointer = secondaryMemory.addToMemory(allData,secondaryMemoryPointer+3-(secondaryMemoryPointer+3)%4);
            pageTablesPointer++;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageTablesPointer;
    }

    /**
     * Tłumaczy adres logiczny na fizyczny danej w parametrze tablicy stron
     * @param pageTablesPointer
     * @param page
     * @param shift
     * @return
     */
    public Address translateAddress (int page, int shift, int pageTablesPointer) {
        return new Address(pageTables[pageTablesPointer].pageTable[page][0],shift);
    }

    /**
     * Tłumaczy adres logiczny uzywany w procesorze na fizyczny
     * @param logicalAddress
     * @return
     */
    public Address translateAddress (Address logicalAddress,int pageTablesPointer) {
        return new Address(pageTables[pageTablesPointer].pageTable[logicalAddress.getPage()][0],logicalAddress.getShift());
    }

    /**
     * UWAGA! Parametrami sa pola adresu FIZYCZNEGO, aby przetlumaczyc logiczny uzyj {@link #translateAddress(Address, int)} s(Address)} lub {@link #translateAddress(int, int, int)}
     * @param page
     * @param shift
     * @return
     */
    public char readFromMemory (int page, int shift) {
       return secondaryMemory.ReadFromMemory(page*4+shift);
    }

    /**
     * UWAGA! Parametrem jest adres FIZYCZNY, aby przetlumaczyc logiczny uzyj {@link #translateAddress(int, int, int)} (Address)} lub {@link #translateAddress(Address, int)}
     * @param physicalAddress
     * @return
     */
    public char readFromMemory (Address physicalAddress) {
        return secondaryMemory.ReadFromMemory(physicalAddress.getPage()*4+physicalAddress.getShift());
    }

}
