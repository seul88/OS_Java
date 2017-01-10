package put.os.ipc;

import put.os.fileSystem.AllocateMemory;
import put.os.fileSystem.HardDrive;
import virtual.device.MainMemory;
import java.util.Random;

/**
 * Potok nienazwany
 */
public class Pipe {

    // For separate filesystem
    private static HardDrive drive = new HardDrive(MainMemory.size);
    private static int counter = 0;
    private Pipe link;
    private String randomName;

    private boolean f0 = true; //FileHandlerToRead
    private boolean f1 = true; //FileHandlerToWrite

    public Pipe() {
        randomName = "PIPE"+(counter++);
        drive.CreateNewFile(randomName, "0");
    }

    public Pipe(Pipe pipeForLink)
    {
        this.link = pipeForLink;
        pipeForLink.link = this;

        this.randomName = pipeForLink.randomName;
    }

    public void write(String data) throws Exception {
        if(!f1) {
            throw new Exception("Write handler is closed");
        }

        drive.AddContentToFile(randomName, data);
    }

    public String read() throws Exception {
        if(!f0) {
            throw new Exception("Read handler is closed");
        }

        return drive.PrintFileFromMemory(randomName);
    }

    public boolean closeRead() {
        f0 = false;

        return this.link.f1;
    }

    /**
     *  Dla deskryptora zapisu :
        jeśli istnieją inne procesy mające potok otwarty do zapisu nie dzieje się nic
        gdy nie ma więcej procesów a potok jest pusty, procesy, które czekały na odczyt z potoku zostają obudzone a ich funkcje read zwrócą 0 ( wygląda to tak jak osiągnięcie końca pliku)
     * @return
     */
    public boolean closeWrite() {
        f1 = false;

        return this.link.f0;
    }

    public void finish() {
        drive.DeleteFileFromMemory(randomName);
        this.link = null;
    }
}
