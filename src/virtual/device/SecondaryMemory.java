package virtual.device;


public final class SecondaryMemory {
    public static byte[] memory;

    private SecondaryMemory() {
        memory = new byte[800000];
    }

}