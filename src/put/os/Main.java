package put.os;

import put.os.fileSystem.Filesystem;
import put.os.interpreter.Interpreter;
import put.os.memory.MemoryManagementUnit;
import put.os.processes.ProcessBlockController;
import put.os.processes.ProcessManager;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {

    private enum Mode {
        LOGO,
        MAIN,
        FILESYSTEM,
        PROCESS,
        INTERPRETER,
        EXIT
    }

    // Actual mode
    private static Mode mode = Mode.LOGO;

    // Is running
    private static boolean run = true;

    // Available programs (position on hardDrive)
    private static Map<String, Integer> avaiablePrograms = new HashMap<String, Integer>();

    private static void initSystem() {
        // TODO make static or singleton
        MemoryManagementUnit memoryManagementUnit = new MemoryManagementUnit();

        // Check and load available programs
        File[] programs = new File(".\\resources\\").listFiles();

        for (File file : programs) {
            if (file.isFile()) {

                avaiablePrograms.put(
                        file.getName(),
                        memoryManagementUnit.addToMemoryFromFile(file.toString())
                );

            }
        }

    }

    // Logo
    private static void displayLogo() {
        System.out.print(
                "########################################################## \n" +
                "        __       ___      .______   .______        ___      \n" +
                "       |  |     /   \\     |   _  \\  |   _  \\      /   \\     \n" +
                "       |  |    /  ^  \\    |  |_)  | |  |_)  |    /  ^  \\    \n" +
                " .--.  |  |   /  /_\\  \\   |   _  <  |   _  <    /  /_\\  \\   \n" +
                " |  `--'  |  /  _____  \\  |  |_)  | |  |_)  |  /  _____  \\  \n" +
                "  \\______/  /__/     \\__\\ |______/  |______/  /__/     \\__\\ \n" +
                "                                                            \n" +
                "######################## - OS - ##########################"
        );
    }

    private static void clearScreen() {
        // Stupid java xd

        System.out.println("\n_________________________________\n");

    }

    private static Scanner reader = new Scanner(System.in);

    /*
       MODES
     */
    private static void logoMode() {
        displayLogo();

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mode = Mode.MAIN;
    }

    private static void exitMode() {
        run = false;

        System.out.println("Thanks for using JABBAos!");
        System.out.print("Credits: \n" +
                "- Damian Stube \n" +
                "- Maciej Olejnik \n" +
                "- Jakub Sobczak \n" +
                "- Remigiusz Wroblewski \n" +
                "- Erwin Majewski \n" +
                "- Arek Kolodynski \n");

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mainMode() {
        System.out.println("[Main menu]");
        System.out.print(
                "1 - Filesystem \n" +
                "2 - Process management \n"
        );

        int choose = reader.nextInt();

        switch(choose) {
            case 1:
                mode = Mode.FILESYSTEM;
                break;
            case 2:
                mode = Mode.PROCESS;
                break;
        }

    }

    private static void filesystemMode() {
        System.out.println("[Filesystem menu]");

        Filesystem.run();

        mode = Mode.MAIN;
    }

    private static void processMode() {
        System.out.println("[Process menu]");
        System.out.print(
                "1 - Create process \n" +
                "2 - Run process \n" +
                "3 - Show FCFS Queue \n"
        );

        int choose = reader.nextInt();

        switch(choose) {

            // Create process
            case 1:
            {
                // I. List all names of available programs
                System.out.println("Available programs: ");
                for(Map.Entry<String, Integer> program : avaiablePrograms.entrySet())
                {
                    System.out.println(program.getKey());
                }

                // II. Choose program
                String chosenProgram = reader.next();

                if(avaiablePrograms.containsKey(chosenProgram))
                {
                    ProcessManager.createProcess( chosenProgram /* Name */, avaiablePrograms.get(chosenProgram)/* memory */);
                    System.out.println("PCB added!");
                }

                break;
            }

            // Run process
            case 2: {

                // Wywolaj funkcje z FCFS
                // Wlacz i zwroc wlaczony PCB z kolejki

                // Albo wywolaj funkcje w ProcessManager ktora wywola funkcje wlaczajaca PCB z kolejki
                // a potem wpisze ja do RUNNING i zwroci tutaj

                if(/* zostal uruchomiony*/ true) {
                    mode = Mode.INTERPRETER;
                }

                break;
            }
        }
    }

    public static void interpreterMode() {
        System.out.println("[Process in progress!]");
        System.out.print(
            "1 - Next command \n" +
            "2 - Run all process \n" +
            "3 - Show registry \n" +
            "4 - Stop process \n"
        );

        ProcessBlockController activeProcess = ProcessManager.getRunning();
        Interpreter interpeter = new Interpreter(activeProcess);

        int choose = reader.nextInt();

        switch(choose) {

            // Next command
            case 1:
            {
                interpeter.nextLine();
                break;
            }

            // Run all
            case 2: {
                interpeter.runAll();

                break;
            }

            // Stop process
            case 4: {
                ProcessManager.stopRunning();
                mode = Mode.PROCESS;
                break;
            }
        }
    }

    /*
        MAIN FUNCTION
     */
    public static void main(String[] args) {
        initSystem();

        while(run) {
            switch(mode)
            {
                case LOGO:
                    logoMode();
                    break;
                case FILESYSTEM:
                    filesystemMode();
                    break;
                case MAIN:
                    mainMode();
                    break;
                case PROCESS:
                    processMode();
                    break;
                case INTERPRETER:
                    interpreterMode();
                    break;
                case EXIT:
                    clearScreen();
                    exitMode();
                    break;
            }
        }
    }
}
