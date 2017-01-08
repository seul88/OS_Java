package put.os.memory;

import virtual.device.MainMemory;
import virtual.device.SecondaryMemory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;


/**
 * Created by Damian on 09.12.2016.
 */
public class MemoryManagementUnit {
    public static final int pageSize = 4;
    private static PageTable [] pageTables =  new PageTable[10];
    private static int pageTablesPointer =0;
    private static int mainMemoryPointer =0;
    private static int secondaryMemoryPointer =-1;
    private static LinkedList LRUList = new LinkedList<Integer>();
    private static int [] programSizes = new int[15];

    private static class PageTable {
        private int [][] pageTable;
        private int [] TranslationLookAsideBuffer;

        public PageTable() {
            this.pageTable = new int[100][2];
        }
    }

    /**
     * Zapisuje zawartosc pliku w pamieci dyskowej, linie rozdzielone sa znakiem '/t'
     * Do testowania mozna sobie uzyc path: ".\\resources\\Program1"
     * @param path - sciezka do pliku
     * @return - numer tablicy stron do danego programu
     */
    public static int addToMemoryFromFile(String path) {
        pageTables[pageTablesPointer] = new PageTable();
        int size = 0;
        int finalPage;
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line).append('\n');
                line = br.readLine();
            }
            br.close();
            String allData = sb.toString();
            size = allData.length();
            finalPage = (size)/pageSize;

            programSizes[pageTablesPointer]=size;

            for (int i = 0; i <= finalPage; i++) {
                pageTables[pageTablesPointer].pageTable[i]=
                        new int [] {((secondaryMemoryPointer+3-(secondaryMemoryPointer+3)%4))/pageSize+i,444};
            }
            secondaryMemoryPointer =
                    addToMemory(allData,secondaryMemoryPointer+3-(secondaryMemoryPointer+3)%4);
            pageTablesPointer++;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pageTablesPointer-1;
    }

    public static int sizeOfProgram(int nr){
        return programSizes[nr];
    }


    /**
     * Tłumaczy adres logiczny uzywany w procesorze na fizyczny
     * @param logicalAddress
     * @return
     */
    private static Address translateAddress (Address logicalAddress,int pageTablesPointer) {
        try {
            if (logicalAddress.getShift()>=pageSize) {
                throw new Exception("Shift out of page size");
            }
            if(pageTables[pageTablesPointer].pageTable[logicalAddress.getPage()][1]==444) {
                int mainPointer =
                        handlePageError(pageTables[pageTablesPointer].pageTable[logicalAddress.getPage()][0]);
                pageTables[pageTablesPointer].pageTable[logicalAddress.getPage()][1]=mainPointer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Address(pageTables[pageTablesPointer].
                pageTable[logicalAddress.getPage()][1],logicalAddress.getShift());
    }


    public static byte readFromMemory (int characterPosition, int pageTablesPointer) {
        return readFromMemory(new Address(characterPosition/pageSize,characterPosition%pageSize),pageTablesPointer);
    }

    /**
     * @param physicalAddress
     * @return
     */
    public static byte readFromMemory (Address physicalAddress, int pageTablesPointer) {
        Address address = translateAddress(physicalAddress, pageTablesPointer);


        if(LRUList.contains(address.getPage())){
         LRUList.remove(new Integer (address.getPage()));
        }
        LRUList.push(address.getPage());

        return MainMemory.memory[address.getPage()*4+address.getShift()];
    }

    private static int addToMemory(String data, int index) {
        for (char ch : data.toCharArray()) {
            SecondaryMemory.memory[index] = (byte)ch;
            index++;
        }
        return index;
    }

    private static int handlePageError (int secMemoryPointer) {
        byte[] pageData = new byte[pageSize];
        for (int i = 0; i <pageSize; i++) {
            pageData[i]=SecondaryMemory.memory[secMemoryPointer*4+i];
        }
       return addToMainMemory(pageData);

    }

    private static int addToMainMemory(byte[] page){
        if(mainMemoryPointer+4>MainMemory.memory.length){
            int pointer = (int) LRUList.pollLast();
            for (int i = 0; i < pageSize; i++) {
                MainMemory.memory[pointer*4+i]=page[i];
            }
            for (int i = 0; i <pageTablesPointer ; i++) {
                for (int [] j : pageTables[i].pageTable
                        ) {
                    if (j[1]==pointer){
                        j[1]=444;
                    }
                }
            }

            return pointer;
        } else {
        for (int i = 0; i < pageSize; i++) {
            MainMemory.memory[mainMemoryPointer+i]=page[i];
        }
        mainMemoryPointer+=pageSize;
            return mainMemoryPointer/4-1;
        }

    }

    public static String printMemory (byte [] memory){
        StringBuilder sb= new StringBuilder();
        for (int i = 0; i < memory.length; i++) {
            if(memory[i]=='\n'){
                sb.append("\\n");
            } else
            if(memory[i]!=0){
                sb.append((char)memory[i]);
            } else {
                sb.append('*');
            }

            if(i%4==3){
                sb.append('\n');
            } else {
                sb.append("\t");
            }
        }
        return sb.toString();
    }

    public static String printPageTables(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pageTablesPointer; i++) {
            sb.append("Page Table "+i+":\n");
            for (int j = 0; j <pageTables[i].pageTable.length ; j++) {
                sb.append("["+j+"] \t"+pageTables[i].pageTable[j][0]+","+pageTables[i].pageTable[j][1]+"\n");
            }
        }
        return sb.toString();
    }

    public static String printLRUList (){
        StringBuilder sb = new StringBuilder();
        for (Object i: LRUList
             ) {
            sb.append(i);
            sb.append('\n');
        }
        return sb.toString();
    }
}
