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

        System.out.println("_________________________________\n");

    }

    private static void waitForEnter() {
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Scanner reader = new Scanner(System.in);

    /*
       MODES
     */
    private static void logoMode() {
        displayLogo();

        waitForEnter();

        mode = Mode.MAIN;
    }

    private static void exitMode() {
        run = false;

        System.out.println("[Thanks for using JABBAos!]");
        System.out.print("Credits: \n" +
                "\t- Damian Stube \n" +
                "\t- Maciej Olejnik \n" +
                "\t- Jakub Sobczak \n" +
                "\t- Remigiusz Wroblewski \n" +
                "\t- Erwin Majewski \n" +
                "\t- Arek Kolodynski \n");
    }

    private static void mainMode() {
        System.out.println("[Main menu]");
        System.out.print(
                "\t1 - Filesystem \n" +
                "\t2 - Process management \n" +
                "\t3 - Exit system \n"
        );

        int choose = reader.nextInt();

        switch(choose) {
            case 1:
                mode = Mode.FILESYSTEM;
                break;
            case 2:
                mode = Mode.PROCESS;
                break;
            case 3:
                mode = Mode.EXIT;
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
                "\t1 - Create process \n" +
                "\t2 - Run process \n" +
                "\t3 - Show FCFS Queue \n" +
                "\t4 - Stop process \n" +
                "\t5 - Show tree of processes \n" +
                "\t6 - [Back to Main Menu] \n"
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
                    System.out.println("\t- " + program.getKey());
                }

                // II. Choose program
                System.out.print("Your program: ");
                String chosenProgram = reader.next();

                if(avaiablePrograms.containsKey(chosenProgram))
                {
                    ProcessManager.createProcess( chosenProgram /* Name */, avaiablePrograms.get(chosenProgram)/* memory */);
                    System.out.println("PCB added!");

                    waitForEnter();
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

            // Back to main menu
            case 6: {
                mode = Mode.MAIN;
                break;
            }
        }
    }

    private static void interpreterMode() {
        System.out.println("[Process in progress!]");
        System.out.print(
            "\t1 - Next command \n" +
            "\t2 - Run all commands \n" +
            "\t3 - Show registry \n" +
            "\t4 - Stop process \n" +
            "\t5 - Show this PCB data \n"
        );

        ProcessBlockController activeProcess = ProcessManager.getRunning();
        Interpreter interpreter = new Interpreter(activeProcess);

        int choose = reader.nextInt();

        switch(choose) {

            // Next command
            case 1:
            {
                interpreter.nextLine();
                break;
            }

            // Run all
            case 2: {
                interpreter.runAll();
                break;
            }

            // Stop process
            case 4: {
                ProcessManager.stopRunning();
                System.out.println("Process stopped!");
                mode = Mode.PROCESS;
                waitForEnter();
                break;
            }

            // Show PCB
            case 5: {
                System.out.println(activeProcess);
                waitForEnter();
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
                    clearScreen();
                    filesystemMode();
                    break;
                case MAIN:
                    clearScreen();
                    mainMode();
                    break;
                case PROCESS:
                    clearScreen();
                    processMode();
                    break;
                case INTERPRETER:
                    clearScreen();
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
