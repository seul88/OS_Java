package put.os;

import put.os.fileSystem.Filesystem;
import put.os.interpreter.Interpreter;
import put.os.memory.MemoryManagementUnit;
import put.os.processes.ProcessBlockController;
import put.os.processes.ProcessManager;
import put.os.processorScheduling.Dispatcher;
import virtual.device.MainMemory;
import virtual.device.Processor;
import virtual.device.SecondaryMemory;

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

    // Is r
    // unning
    private static boolean run = true;

    // Available programs (position on hardDrive)
    private static Map<String, Integer> avaiablePrograms = new HashMap<String, Integer>();

    private static void initSystem() {

        new ProcessManager();

        // Check and load available programs
        File[] programs = new File(".\\resources\\").listFiles();

        for (File file : programs) {
            if (file.isFile()) {

                avaiablePrograms.put(
                        file.getName(),
                        MemoryManagementUnit.addToMemoryFromFile(file.toString())
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

        System.out.println("========================================");
    }

    private static void clearScreen(String text) {
        // Stupid java xd

        int border = (40 - text.length()) / 2;

        System.out.print('\n');

        for(int i = 0; i < border; i++)
            System.out.print('=');

        System.out.print(text);

        for(int i = 0; i < border; i++)
            System.out.print('=');

        System.out.print('\n');
    }

    private static void waitForEnter() {

        System.out.print("\n*click*");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void showRegisters() {
        System.out.println("Registry of Processor");
        System.out.print(
                "\tA: " + Processor.A +
                        "\tB: " + Processor.B +
                        "\tC: " + Processor.C +
                        "\tD: " + Processor.D +
                        "\tE: " + Processor.E +
                        "\tF: " + Processor.F
        );
        waitForEnter();
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
                "\t3 - Show Page Tables \n" +
                "\t4 - Exit system \n"
        );

        int choose = reader.nextInt();

        switch(choose) {
            case 1:
                mode = Mode.FILESYSTEM;
                break;
            case 2:
                mode = Mode.PROCESS;
                break;
            // Show Page Table
            case 3: {
                System.out.println(MemoryManagementUnit.printPageTables());
                break;
            }
            case 4:
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
                "\t5 - Wakeup process \n" +
                "\t6 - Show tree of processes \n" +
                "\t7 - Show registers \n" +
                "\t8 - [Back to Main Menu] \n"
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
                    String process = ProcessManager.createProcess( avaiablePrograms.get(chosenProgram) );
                    System.out.println("PCB " + process +" added!");

                    waitForEnter();
                }

                break;
            }

            // Run process
            case 2: {
                if(ProcessManager.runProcess()) {
                    mode = Mode.INTERPRETER;
                }
                else
                {
                    System.out.println("Kolejka jest pusta!");
                }

                break;
            }

            // Show PCB Queue
            case 3: {
                System.out.println("[FCFS Queue]");
                System.out.println(Dispatcher.drawQueue());
                waitForEnter();
                break;
            }

            // Stop process
            case 4: {
                System.out.println("[Stop process]");
                System.out.println(ProcessManager.drawTree("*ROOT"));

                System.out.print("Choose process: ");
                String process = reader.next();

                if(ProcessManager.stopProcess(process))
                {
                    System.out.println("Zatrzymano proces!");
                }
                else
                {
                    System.out.println("Nie udalo sie zatrzymac procesu.");
                }

                break;
            }

            // Wakeup process
            case 5: {
                System.out.println("[Wakeup process]");
                System.out.println(ProcessManager.drawTree("*ROOT"));

                System.out.print("Choose process: ");
                String process = reader.next();

                if(ProcessManager.wakeupProcess(process))
                {
                    System.out.println("Wybudzono proces!");
                }
                else
                {
                    System.out.println("Nie udalo sie wybudzic procesu.");
                }
                break;
            }

            // Show processes
            case 6: {
                System.out.println("[PCB TREE]");
                System.out.print(ProcessManager.drawTree("*ROOT"));
                waitForEnter();
                break;
            }

            // Registry show
            case 7: {
                showRegisters();
                break;
            }

            // Back to main menu
            case 8: {
                mode = Mode.MAIN;
                break;
            }
        }
    }

    private static void interpreterMode() {
        ProcessBlockController activeProcess = ProcessManager.getRunning();
        Interpreter interpreter = new Interpreter(activeProcess);

        System.out.println("[Process " + activeProcess.getName() + " in progress]");
        System.out.print(
                "\t1 - Next command \n" +
                        "\t2 - Run all commands \n" +
                        "\t3 - Show registry \n" +
                        "\t4 - Stop process and call scheduler \n" +
                        "\t5 - Show this PCB data \n" +
                        "\t6 - Show main memory \n" +
                        "\t7 - Show page tables \n" +
                        "\t8 - Show disc memory \n" +
                        "\t9 - Show LRU list \n"
        );


        int choose = reader.nextInt();

        switch(choose) {

            // Next command
            case 1:
            {
                if(!interpreter.nextLine()) {
                    if(activeProcess.getSTATE() == ProcessBlockController.States.WYKONYWANY)
                    {
                        ProcessManager.finishProcess();
                        System.out.println("Zakonczono proces!");
                    }
                    else
                    {
                        System.out.println("Proces zatrzymany!");
                    }

                    mode = Mode.PROCESS;
                    waitForEnter();
                }

                break;
            }

            // Run all
            case 2: {
                interpreter.runAll();

                if(activeProcess.getSTATE() == ProcessBlockController.States.WYKONYWANY)
                {
                    ProcessManager.finishProcess();
                    System.out.println("Zakonczono proces!");
                }
                else
                {
                    System.out.println("Proces zatrzymany!");
                }

                mode = Mode.PROCESS;
                waitForEnter();
                break;
            }

            // Registry show
            case 3: {
                showRegisters();
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
                break;
            }

            //Print Main Memory (RAM)
            case 6: {
                System.out.println(MemoryManagementUnit.printMemory(MainMemory.memory));
                waitForEnter();
                break;
            }

            //Print page tables
            case 7: {
                System.out.println(MemoryManagementUnit.printPageTables());
                waitForEnter();
                break;
            }

            //Print Secondary Memory (DISC)
            case 8: {
                System.out.println(MemoryManagementUnit.printMemory(SecondaryMemory.memory));
                waitForEnter();
                break;
            }
            //Print LRU List
            case 9: {
                System.out.println(MemoryManagementUnit.printLRUList());
                waitForEnter();
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
                    clearScreen("FILESYSTEM");
                    filesystemMode();
                    break;
                case MAIN:
                    clearScreen("MAIN");
                    mainMode();
                    break;
                case PROCESS:
                    clearScreen("PROCESSES");
                    processMode();
                    break;
                case INTERPRETER:
                    clearScreen("INTERPRETER");
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
