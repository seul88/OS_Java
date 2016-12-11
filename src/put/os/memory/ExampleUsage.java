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
        Address firstFreeAddressAfterProgram1 = memoryManagementUnit.addToMemoryFromFile(".\\resources\\Program1",new Address(0,0));
        Address firstFreeAddressAfterProgram2 = memoryManagementUnit.addToMemoryFromFile(".\\resources\\Program2",firstFreeAddressAfterProgram1);

        //Odczytanie pierwszego znaku po programie pierwszym(czyli pierwszego prrogramu drugiego w tym przypadku)
        memoryManagementUnit.readFromMemory(memoryManagementUnit.translateAdress(firstFreeAddressAfterProgram1)); // UWAGA! Parametr musi byc adresem fizycznym, stad uzycie translateAdress (w dokumentacjach metod szczegoly)
        memoryManagementUnit.readFromMemory(3,1);
    }
}
