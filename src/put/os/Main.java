package put.os;

import put.os.fileSystem.Filesystem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
        System.out.print("1 - Filesystem \n");

        int choose = reader.nextInt();

        switch(choose) {
            case 1:
            {
                mode = Mode.FILESYSTEM;
                break;
            }
        }

    }

    private static void filesystemMode() {
        System.out.println("[Filesystem menu]");

        Filesystem.run();

        mode = Mode.MAIN;
    }


    /*
        MAIN FUNCTION
     */
    public static void main(String[] args) {
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
                case EXIT:
                    clearScreen();
                    exitMode();
                    break;
            }
        }
    }
}
