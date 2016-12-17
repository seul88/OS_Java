package put.os.memory;
import put.os.memory.Address;
import put.os.memory.MemoryManagementUnit;
import put.os.memory.SecondaryMemory;
/**
 * Created by Damian on 11.12.2016.
 */

public class ExampleUsage {

    public static void main(String[] args) {

        //Inicjalizacja struktur
        SecondaryMemory secondaryMemory = new SecondaryMemory();
        MemoryManagementUnit memoryManagementUnit = new MemoryManagementUnit(secondaryMemory);

        //Wpisanie programow do pamieci
        memoryManagementUnit.addToMemoryFromFile(".\\resources\\Program1");
        memoryManagementUnit.addToMemoryFromFile(".\\resources\\Program2");

        memoryManagementUnit.readFromMemory(memoryManagementUnit.translateAddress(new Address(3,2),1)); // UWAGA! Parametr musi byc adresem fizycznym, stad uzycie translateAdress (w dokumentacjach metod szczegoly)
        memoryManagementUnit.readFromMemory(memoryManagementUnit.translateAddress(new Address(0,0),0));
        memoryManagementUnit.readFromMemory(memoryManagementUnit.translateAddress(new Address(0,0),1));
        memoryManagementUnit.readFromMemory(memoryManagementUnit.translateAddress(new Address(7,4),0));
        memoryManagementUnit.readFromMemory(memoryManagementUnit.translateAddress(new Address(1,3),0));
        memoryManagementUnit.readFromMemory(3,1);
    }
}
