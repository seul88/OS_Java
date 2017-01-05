package put.os;

import java.io.IOException;


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

        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
