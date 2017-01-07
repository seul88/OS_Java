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

    public static void deletePCB(ProcessBlockController pcb) {
        Dispatcher.processesQueque.remove(pcb);
    }

    public static ProcessBlockController pollHead () {
        return Dispatcher.processesQueque.pollFirst();
    }

    public static String drawQueue() {
        StringBuilder sb = new StringBuilder();

        for (ProcessBlockController pcb: Dispatcher.processesQueque) {
            sb.append("<  ");
            sb.append(pcb.getName());
            sb.append("  ");
        }

        return sb.toString();
    }

    // Not working for static members ;<
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ProcessBlockController pcb: Dispatcher.processesQueque
             ) {
            sb.append(pcb.getName()+"\t");
        }
        return sb.toString();
    }
}
