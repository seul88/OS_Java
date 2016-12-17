package put.os.memory;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Damian on 09.12.2016.
 */
public class SecondaryMemory {
    private char[] memory;
    // private int availableMemoryPointer = 0;

    public SecondaryMemory() {
        this.memory = new char[800000];
    }

    /**
     * Zapisuje podany string w pamieci dyskowej
     *
     * @param data
     */
    public int addToMemory(String data, int index) {
        for (char ch : data.toCharArray()) {
            memory[index] = ch;
            index++;
        }
        return index;
    }

    public char ReadFromMemory(int index) {
        return memory[index];
    }
}