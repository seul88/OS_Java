package put.os.processes;

public class ProcessManager {

    /*
        TODO VER1 (ERWIN)
        Czy moglbys sprawic aby wszystkie funkcje tutaj zamiast argumentow o typie PCB przyjmowaly stringa z nazwa bloku PCB?
     */

    private int counter;
    private ProcessBlockController root;

    /*
        TODO VER1 (ERWIN)
        Ten konstruktor mozesz usunac jezeli nie wykorzystujesz go
    */
    public ProcessManager(ProcessBlockController root) {
        this.root = root;
        this.counter = 0;
    }

    public ProcessManager() {
        this.counter = 0;
        this.root = null;

        /*
            TODO VER1 (ERWIN)
            Utworz tutaj blok PCB roota
            bedzie tworzyla nowy blok PCB i dodawala go od razu do roota z tego obiektu
            this.setRoot(new ProcessBlockController(this.getCounter(), pcbName);)
        */
    }


    public boolean isEmpty() {
        return root == null;
    }


    public ProcessBlockController getRoot() {
        return root;
    }


    public int getCounter() {
        return this.counter;
    }

    public void removeChild(ProcessBlockController child) {
        child.removeChild(child);
    }


    /*
        TODO VER1 (ERWIN)
        Moze zrob go prywatny, chyba ze nie mozna bo tego wymaga funkcjonalnosc
    */
    public void setRoot(ProcessBlockController root) {
        this.root = root;
        this.counter++;
    }


    public int getNumberOfChildren(ProcessBlockController node) {
        int n = node.getChildren().size();
        for (ProcessBlockController child : node.getChildren())
            n += getNumberOfChildren(child);
        return n;
    }

    /*
        TODO VER1 (ERWIN)
        To mozna przeniesc do ProcessBlockController
    */
    public String returnProcessStateAsString(ProcessBlockController pcb) {
        if (pcb.getSTATE() == 0) return "Nowy";
        if (pcb.getSTATE() == 1) return "Wykonywany";
        if (pcb.getSTATE() == 2) return "Oczekujący";
        if (pcb.getSTATE() == 3) return "Gotowy";
        if (pcb.getSTATE() == 4) return "Zakonczony";
        return "Error! Nieznany stan procesu!";
    }


    /*
        TODO VER1 (ERWIN)
        argument keyNode domyslnie root
    */
    public boolean find(ProcessBlockController node, ProcessBlockController keyNode) {
        boolean result = false;
        if (node.equals(keyNode))
            return true;

        else {
            for (ProcessBlockController child : node.getChildren())
                if (find(child, keyNode))
                    result = true;
        }

        return result;
    }

    /*
        TODO VER1 (ERWIN)
        argument keyNode domyslnie root
    */
    public ProcessBlockController findNode(ProcessBlockController node, ProcessBlockController keyNode) {
        if (node == null)
            return null;
        if (node.equals(keyNode))
            return node;
        else {
            ProcessBlockController cnode = null;
            for (ProcessBlockController child : node.getChildren())
                if ((cnode = findNode(child, keyNode)) != null)
                    return cnode;
        }
        return null;
    }

    /*
        TODO VER1 (ERWIN)
        Dodac funkcje
    */
    public void setState(int STATE, ProcessBlockController pcb) {
        ProcessBlockController pc = findNode(this.root, pcb);
        pc.setSTATE(STATE);
    }


    public ProcessBlockController returnReadyProcess() {
        for (ProcessBlockController child : root.getChildren())
            if (child.getSTATE() == 3) return child;
        return null;
    }

    public ProcessBlockController findNode(String NAME) {
        // wyszukiwanie procesu po nazwie
        for (ProcessBlockController child : root.getChildren())
            if (child.getName().equals(NAME)) return child;
        return null;
    }

    public boolean find(String NAME) {
        // wyszukiwanie procesu po nazwie
        for (ProcessBlockController child : root.getChildren())
            if (child.getName().equals(NAME)) return true;
        return false;
    }


    // function adds process to the Tree
    // it requires 2 parameters - root of the Tree [PCB] and value to add [PCB]

    public void addProcess(ProcessBlockController root, ProcessBlockController PCB) {
        root.addChild(PCB);
        this.counter = counter + 1;
    }


    //  add process by name [String]

    /*
        TODO VER1 (ERWIN)
        To chyba mozna wyrzucic.
    */
    public void addProcess(String NAME, String root) {
        ProcessBlockController parent = findNode(root);
        ProcessBlockController PCB = new ProcessBlockController(this.counter, NAME);
        parent.addChild(PCB);
        this.counter = counter + 1;
    }


    // function enables to add root of Tree (root only!)

    /*
        TODO VER1 (ERWIN)
        To mozna wyrzucic i w funkcji addProcess(ProcessBlockController root, ProcessBlockController PCB)
        dac jako pierwszy argument
    */
    public void addProcessToRoot(ProcessBlockController PCB) {
        this.root.addChild(PCB);
        this.counter = counter + 1;
    }

    /*
        TODO VER1 (ERWIN)
        Mozna wyrzucic i funkcjonalnosc tego przejmie createPCB(pcbName:string)
    */
    public void addProcessToRoot(String NAME) {
        ProcessBlockController PCB = new ProcessBlockController(this.counter, NAME);
        this.root.addChild(PCB);
        this.counter = counter + 1;
    }


    public String getChildrenNames(ProcessBlockController PCB) {
        String result = "";
        result = PCB.getChildrenNames();
        return result;
    }


    public String getChildrenNames(String PCB) {

        ProcessBlockController pcb = this.findNode(PCB);
        return pcb.getChildrenNames();

    }


    /*
        TODO VER1 (ERWIN)
        Utworz funkcje createPCB(pcbName:string)
        bedzie tworzyla nowy blok PCB i dodawala go od razu do roota, albo jak sie poda drugi argument rodzica - to do niego
        this.addProcessToRoot(new ProcessBlockController(this.getCounter(), pcbName);)
     */
}