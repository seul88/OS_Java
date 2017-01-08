package put.os.processes;

import put.os.processorScheduling.Dispatcher;

import java.util.ArrayList;
import java.util.List;


public class ProcessManager {

    private static int counter = 1;
    // Procesy systemowe zaczynaja sie od gwiazdki
    private static ProcessBlockController root = new ProcessBlockController(0, "*ROOT");
    private static ProcessBlockController RUNNING = null;

    public ProcessManager() {
        root.setSTATE(ProcessBlockController.States.GOTOWY);
    }

    /**
     * Remove process
     * @param name Process Name
     */
    public static void removeProcess(String name){
        ProcessBlockController pcb = find(name);
        removeProcess(pcb);
    }

    // Dzieci przenosi do roota
    private static void removeProcess(ProcessBlockController pcb)
    {
        if (!pcb.getChildren().isEmpty())
            for (ProcessBlockController child : pcb.getChildren())
            {
                root.addChild(child);
                child.setParent(root);
            }

        pcb.getParent().removeChild(pcb);
    }

    private static int getNumberOfChildren(ProcessBlockController node) {
        int n = node.getChildren().size();
        for (ProcessBlockController child : node.getChildren())
            n += getNumberOfChildren(child);
        return n;
    }

    /**
     * Sprawdzanie czy proces istnieje w danym nodzie
     * @param node
     * @param keyNode
     * @return
     */
    private static boolean exists(ProcessBlockController node, ProcessBlockController keyNode) {
        boolean result = false;
        if (node.equals(keyNode))
            return true;
        else {
            for (ProcessBlockController child : node.getChildren())
                if (exists(child, keyNode))
                    result = true;
        }
        return result;
    }

    private static boolean exists(ProcessBlockController keyNode) {
        return exists(root, keyNode);
    }

    /**
     * Sprawdzanie czy proces o danej nazwie istnieje
     * @param NAME
     * @return
     */
    public static boolean exists(String NAME) {
        for (ProcessBlockController child : root.getChildren())
            if (child.getName().equals(NAME)) return true;

        return false;
    }


    public static void setState(ProcessBlockController.States STATE, ProcessBlockController pcb) {
        ProcessBlockController pc = find(root, pcb);
        pc.setSTATE(STATE);
    }


    /**
     * WYSZUKIWANIE
     */

    /*
    private ArrayList<ProcessBlockController> returnListOfReadyProcesses(String NAME) {
        ArrayList<ProcessBlockController> lista = new ArrayList<ProcessBlockController>();

        for (ProcessBlockController child : root.getChildren())
            if (child.getSTATE() == ProcessBlockController.States.GOTOWY) lista.add(child);

        return lista;
    }
    */


    public static ProcessBlockController find(String name) {
        return root.find(name);
    }

    private static ProcessBlockController find(ProcessBlockController keyNode) {
        return find(root, keyNode);
    }

    private static ProcessBlockController find(ProcessBlockController node, ProcessBlockController keyNode) {
        if (node == null)
            return null;
        if (node.equals(keyNode))
            return node;
        else {
            ProcessBlockController cnode = null;
            for (ProcessBlockController child : node.getChildren())
                if ((cnode = find(child, keyNode)) != null)
                    return cnode;
        }
        return null;
    }

    /**
     * function adds process to the Tree
     * @param PCB PCB to add
     * @param parent Parent of this PCB
     */
    private static void addProcess(ProcessBlockController PCB, ProcessBlockController parent) {
        parent.addChild(PCB);
        PCB.setParent(parent);
        ++counter;
    }

    public static String getChildrenNames(String name) {
        ProcessBlockController pcb = find(name);

        if(pcb != null)
            return pcb.getChildrenNames();
        else
            return "Nie mozna znalezc tego procesu rodzica!";
    }

    public static String drawTree(String name) {
        ProcessBlockController pcb = find(name);

        if(pcb != null)
            return pcb.drawTree(0);
        else
            return "Nie mozna znalezc tego procesu rodzica!";
    }

    /**
     * CREATE PCB
     * @param name Name of PCB
     * @param program numer tablicy stron do danego programu [of MemoryManagementUnit]
     */
    public static String createProcess(String name, Integer program, ProcessBlockController parent) {

        // I. Tworzymy process
        ProcessBlockController process = new ProcessBlockController(counter, name);

        // Przypisujemy mu program
        process.setProgram(program);

        // II. Dodajemy process do drzewa
        addProcess(process, parent);

        // III. Dodajemy gotowy proces do kolejki FCFS
        process.setSTATE(ProcessBlockController.States.GOTOWY);
        Dispatcher.addPCB(process);

        return name;
    }

    public static String createProcess(String name, Integer program) {
        return createProcess(name, program, root);
    }

    public static String createProcess(Integer program) {
        return createProcess("USERPROG#" + counter, program, root);
    }


    /**
     * Return now running PCB
     * @return ProcessBlockController
     */
    public static ProcessBlockController getRunning() {
        return RUNNING;
    }

    /**
     *  Stop now running PCB
     */
    public static void stopRunning() {
        ProcessBlockController runningPCB = RUNNING;
        Dispatcher.deletePCB(runningPCB);
        RUNNING.sleep();
    }

    public static boolean stopProcess(String name) {
        ProcessBlockController pcb = find(name);

        if(pcb != null)
        {
            Dispatcher.deletePCB(pcb);
            pcb.sleep();
            return true;
        }

        return false;
    }

    public static boolean wakeupProcess(String name) {
        ProcessBlockController pcb = find(name);

        if(pcb != null && pcb.getSTATE() == ProcessBlockController.States.OCZEKUJACY)
        {
            Dispatcher.addPCB(pcb);
            pcb.wakeup();
            return true;
        }

        return false;
    }

    public static boolean runProcess() {
        ProcessBlockController pcb = Dispatcher.pollHead();

        if(pcb != null)
        {
            RUNNING = pcb;
            pcb.setSTATE(ProcessBlockController.States.WYKONYWANY);
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void finishProcess() {
        RUNNING.finish();
        removeProcess(RUNNING);
        RUNNING = null;
    }
}