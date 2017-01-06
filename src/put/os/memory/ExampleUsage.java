package put.os.memory;

/**
 * Created by Damian on 11.12.2016.
 */

public class ExampleUsage {

    public static void main(String[] args) throws Exception {

        //Inicjalizacja struktur
       // SecondaryMemory secondaryMemory = new SecondaryMemory();
       // MainMemory mainMemory = new MainMemory();
        MemoryManagementUnit memoryManagementUnit = new MemoryManagementUnit();

        //Wpisanie programow do pamieci
        memoryManagementUnit.addToMemoryFromFile(".\\resources\\Program1");
        memoryManagementUnit.addToMemoryFromFile(".\\resources\\Program2");

        memoryManagementUnit.readFromMemory(new Address(0,2),0);
        memoryManagementUnit.readFromMemory(new Address(0,0),0);
        memoryManagementUnit.readFromMemory(new Address(1,2),1);
        memoryManagementUnit.readFromMemory(new Address(1,1),0);
        memoryManagementUnit.readFromMemory(new Address(0,2),0);
        memoryManagementUnit.readFromMemory(new Address(0,0),0);
        memoryManagementUnit.readFromMemory(new Address(1,2),1);
        memoryManagementUnit.readFromMemory(new Address(0,2),0);


    }
}
