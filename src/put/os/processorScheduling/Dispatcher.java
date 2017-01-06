package put.os.processorScheduling;

import put.os.processes.ProcessBlockController;

import java.util.LinkedList;

/**
 * Created by Damian on 06.01.2017.
 */
public class Dispatcher {
    private static LinkedList<ProcessBlockController> processesQueque = new LinkedList<ProcessBlockController>();

    public static void addPCB(ProcessBlockController pcb) {
        Dispatcher.processesQueque.add(pcb);
    }
}
