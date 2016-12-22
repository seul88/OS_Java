package put.os.memory;

import virtual.device.MainMemory;
import virtual.device.SecondaryMemory;

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
    private int mainMemoryPointer =0;
    private int secondaryMemoryPointer =-1;
    private SecondaryMemory secondaryMemory;
    private MainMemory mainMemory;

    public MemoryManagementUnit(SecondaryMemory secondaryMemory,MainMemory mainMemory) {
        pageTables =  new PageTable[10];
        this.secondaryMemory = secondaryMemory;
        this.mainMemory = mainMemory;
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

            for (int i = 0; i <= finalPage; i++) {
                pageTables[pageTablesPointer].pageTable[i]= new int [] {((secondaryMemoryPointer+3-(secondaryMemoryPointer+3)%4))/pageSize+i,444};
            }
            secondaryMemoryPointer = this.addToMemory(allData,secondaryMemoryPointer+3-(secondaryMemoryPointer+3)%4);
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
    private Address translateAddress (int page, int shift, int pageTablesPointer) {

        return new Address(pageTables[pageTablesPointer].pageTable[page][0],shift);
    }

    /**
     * Tłumaczy adres logiczny uzywany w procesorze na fizyczny
     * @param logicalAddress
     * @return
     */
    private Address translateAddress (Address logicalAddress,int pageTablesPointer) {
        try {
            if (logicalAddress.getShift()>=pageSize) {
                throw new Exception("Shift out of page size");
            }
            if(pageTables[pageTablesPointer].pageTable[logicalAddress.getPage()][1]==444) {
                this.handlePageError(pageTables[pageTablesPointer].pageTable[logicalAddress.getPage()][0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Address(pageTables[pageTablesPointer].pageTable[logicalAddress.getPage()][0],logicalAddress.getShift());
    }

    /**
     * @param page
     * @param shift
     * @return
     */
    public char readFromMemory (int page, int shift, int pageTablesPointer) {
        Address address = translateAddress(page,shift,pageTablesPointer);
        return secondaryMemory.memory[address.getPage()*4+address.getShift()];
    }

    /**
     * @param physicalAddress
     * @return
     */
    public char readFromMemory (Address physicalAddress, int pageTablesPointer) {
        Address address = translateAddress(physicalAddress, pageTablesPointer);
        return secondaryMemory.memory[address.getPage()*4+address.getShift()];
    }

    private int addToMemory(String data, int index) {
        for (char ch : data.toCharArray()) {
            secondaryMemory.memory[index] = ch;
            index++;
        }
        return index;
    }

    private void handlePageError (int secMemoryPointer) {
        char [] pageData = new char[pageSize];
        for (int i = 0; i <pageSize; i++) {
            pageData[i]=secondaryMemory.memory[secMemoryPointer*4+i];
        }
        addToMainMemory(pageData);

    }

    private void addToMainMemory (char [] page){
        if(mainMemoryPointer+4>=mainMemory.memory.length){
            System.out.println("Not enough place in main memory");
        }
        for (int i = 0; i < pageSize; i++) {
            mainMemory.memory[mainMemoryPointer+i]=page[i];
        }
        mainMemoryPointer+=pageSize;
    }

}
