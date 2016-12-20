package virtual.device;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Damian on 09.12.2016.
 */
public class SecondaryMemory {
    public char[] memory;

    public SecondaryMemory() {
        this.memory = new char[800000];
    }

}