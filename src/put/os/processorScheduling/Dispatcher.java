package put.os.processorScheduling;

import put.os.processes.ProcessBlockController;

import java.util.LinkedList;

/**
 * Created by Damian on 06.01.2017.
 */
public class Dispatcher {
    private static LinkedList<ProcessBlockController> processesQueque;

    public static void addPCB(ProcessBlockController pcb) {
        Dispatcher.processesQueque.add(pcb);
    }

    public static void deletePCB(ProcessBlockController pcb) {
        Dispatcher.processesQueque.remove(pcb);
    }

    public static void pollHead () {
        Dispatcher.processesQueque.pollFirst();
    }

}
