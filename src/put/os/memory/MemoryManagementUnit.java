package put.os.memory;

/**
 * Created by Damian on 09.12.2016.
 */
public class MemoryManagementUnit {
    public static final int pageSize = 4;
    private char [][] pageTable;
    private char [] TranslationLookasideBuffer;
}
