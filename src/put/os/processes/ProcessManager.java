package put.os.processes;

public class ProcessManager {

    private static int counter = 1;
    private static ProcessBlockController root = new ProcessBlockController(0, "ROOT");
    private static ProcessBlockController RUNNING = null;

    public ProcessManager() {}

    public static boolean isEmpty() {
        return root == null;
    }

    public static ProcessBlockController getRoot() {
        return root;
    }

    public static int getCounter() {
        return counter;
    }

    public static void removeChild(ProcessBlockController child) {
        child.removeChild(child);
    }

    /*
        TODO VER1 (ERWIN)
        To chyba nie jest poprawne
    */
    private static void setRoot(ProcessBlockController root) {
        ProcessManager.root = root;
        counter++;
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

    /*
        TODO VER1 (ERWIN)
        Przerobic/przeciazyc na wyszukiwanie po nazwie
    */
    public static void setState(ProcessBlockController.States STATE, ProcessBlockController pcb) {
        ProcessBlockController pc = find(root, pcb);
        pc.setSTATE(STATE);
    }


    public static ProcessBlockController returnReadyProcess() {
        for (ProcessBlockController child : root.getChildren())
            if (child.getSTATE() == ProcessBlockController.States.GOTOWY) return child;

        return null;
    }

    /**
     * WYSZUKIWANIE
     */
    private static ProcessBlockController find(String NAME) {
        // wyszukiwanie procesu po nazwie
        for (ProcessBlockController child : root.getChildren())
            if (child.getName().equals(NAME)) return child;

        return null;
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
        ++counter;
    }

    public static String getChildrenNames(String name) {
        ProcessBlockController pcb = find(name);
        return pcb.getChildrenNames();
    }

    /**
     * CREATE PCB
     * @param name Name of PCB
     * @param program numer tablicy stron do danego programu [of MemoryManagementUnit]
     */
    public static void createProcess(String name, Integer program, ProcessBlockController parent) {

        // I. Tworzymy process
        ProcessBlockController process = new ProcessBlockController(counter, name);

        // Przypisujemy mu program
        //process.setMemory(program);

        // II. Dodajemy process do drzewa
        addProcess(process, parent);

        // III. Dodajemy gotowy proces do kolejki FCFS

    }

    public static void createProcess(String name, Integer program) {
        createProcess(name, program, root);
    }


    /**
     * Return now running PCB
     * @return ProcessBlockController
     */
    public static ProcessBlockController getRunning() {
        return RUNNING;
    }

}