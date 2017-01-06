package put.os.memory;

import virtual.device.MainMemory;
import virtual.device.SecondaryMemory;

/**
 * Created by Damian on 11.12.2016.
 */

public class ExampleUsage {

    public static void main(String[] args) throws Exception {

        //Inicjalizacja struktur
       // SecondaryMemory secondaryMemory = new SecondaryMemory();
       // MainMemory mainMemory = new MainMemory();
       // MemoryManagementUnit memoryManagementUnit = new MemoryManagementUnit();

        //Wpisanie programow do pamieci
        MemoryManagementUnit.addToMemoryFromFile(".\\resources\\Program1");
        MemoryManagementUnit.addToMemoryFromFile(".\\resources\\Program2");

        MemoryManagementUnit.readFromMemory(new Address(0,2),0); //TODO: Obudowac adres numerem znaku
        MemoryManagementUnit.readFromMemory(new Address(0,0),0);
        MemoryManagementUnit.readFromMemory(new Address(1,2),1);
        MemoryManagementUnit.readFromMemory(new Address(1,1),0);
        MemoryManagementUnit.readFromMemory(new Address(0,2),0);
        MemoryManagementUnit.readFromMemory(new Address(0,0),0);
        MemoryManagementUnit.readFromMemory(new Address(1,2),1);
        MemoryManagementUnit.readFromMemory(new Address(0,2),0);

        MemoryManagementUnit.readFromMemory(0,1);
        MemoryManagementUnit.readFromMemory(1,1);
        MemoryManagementUnit.readFromMemory(2,1);
        MemoryManagementUnit.readFromMemory(3,1);
        MemoryManagementUnit.readFromMemory(4,1);
        MemoryManagementUnit.readFromMemory(5,1);


        System.out.println(MemoryManagementUnit.printMemory(SecondaryMemory.memory));
        System.out.println(MemoryManagementUnit.printPageTables());
        System.out.println(MemoryManagementUnit.printLRUList());
    }
}
