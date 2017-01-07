package put.os.processes;

import put.os.memory.MemoryManagementUnit;
import virtual.device.Processor;

import java.util.List;
import java.util.ArrayList;

public class ProcessBlockController {

    public enum States {
        NOWY,           // proces NOWY, po utworzeniu procesu.
        WYKONYWANY,     // proces WYKONYWANY, proces jest wykonywany.
        OCZEKUJACY,     // proces OCZEKUJACY, wykonywanie procesu zostało zatrzymane.
        GOTOWY,         // proces GOTOWY, proces czeka na przydzial procesora.
        ZAKONCZONY      // proces ZAKONCZONY, po zakonczeniu wykonywania procesu.
    }

    private int PID;     // nr ID procesu
    private String NAME; // nazwa procesu
    private int PPID;    // nr ID rodzica procesu
    private States STATE;

    private int numberOfProgram;		// numer programu
    private int pointer;				// wskaźnik na znak czytany z dysku
    private int sizeOfProgram;			// rozmiar programu, porównywany z pointerem

    // INTERRUPT_SAVE_AREA
    private int A, B, C, D, E, F;   // wartości w rejestrach procesora

    private ProcessBlockController parent;            // rodzic procesu
    private List<ProcessBlockController> children;    // lista dzieci procesu

        /*
        TODO VER1 (ERWIN)
        W bloku PCB beda rejestry takie same jak w procesorze, ale beda uzywane jedynie przy wybudzaniu/uruchamianiu
        procesu (wtedy do procesora beda kopiowane rejestry >>tego<< PCB) oraz przy zatrzymywaniu procesu
        kiedy rejestry procesora zostana skopiowane tutaj.

        Najlepiej utworzyc funkcje np. sleep/pause oraz revoke.
     */

        /*
        TODO
        Do tego potrzebujemy przytrzymac inta z numerem page -> Memory management information (z wikipedii)


        Dodałem te 3 pola do pracy z pamięcią:
        private int numberOfProgram;		// numer programu
        private int pointer;				// wskaźnik na znak czytany z dysku
        private int sizeOfProgram;			// rozmiar programu, porównywany z pointerem

        to wystarczy?
         */

    public ProcessBlockController(int counter, String name) {
        this.PID = counter;
        this.NAME = name;

        this.children = new ArrayList<ProcessBlockController>();

        this.STATE = States.NOWY;

        if (this.getParent() != null) this.PPID = this.getParent().getPID();
        else this.PPID = 0;
    }


    /**
     * Adding Child
     * @param child
     */
    public void addChild(ProcessBlockController child) {
        children.add(child);
    }

    /**
     * Set parrent
     * @param parent
     */
    public void setParent(ProcessBlockController parent) {
        this.parent = parent;
        this.PPID = parent.getPID();
    }

    /**
     * Set program
     * @param program
     */
    public void setProgram(Integer program) {
        this.numberOfProgram = program;
        this.sizeOfProgram = MemoryManagementUnit.sizeOfProgram(program);
        this.pointer = 0;
    }

    /**
     * Delete child
     * @param child
     * @return result of deleting
     */
    public boolean removeChild(ProcessBlockController child) {
        return this.children.remove(child);
    }


    public String getChildrenNames() {
        String result = "";

        for (ProcessBlockController pcb : this.children) {
            result += pcb.getName() + "\n";
            result += pcb.getChildrenNames();
        }

        return result;
    }

    public String drawTree(int level) {
        String result = "";

        for(int i = 0; i<level; i++)
            result += '\t';

        result += "-" + this.NAME + " [" + this.getSTATEName() + "]\n";

        for (ProcessBlockController pcb : this.children) {
            result += pcb.drawTree(level+1);
        }

        return result;
    }

    /**
     * Check pcb
     * @param PID
     * @return
     */
    public boolean equals(int PID) {
        if (PID == this.PID) return true;
        return false;
    }

    public boolean equals(ProcessBlockController PCB) {
        if (PCB == this) return true;
        return false;
    }

    public States getSTATE() {
        return this.STATE;
    }

    public String getSTATEName() {
        switch(this.STATE) {
            case NOWY:
                return "Nowy";
            case WYKONYWANY:
                return "Wykonywany";
            case OCZEKUJACY:
                return "Oczekujacy";
            case GOTOWY:
                return "Gotowy";
            case ZAKONCZONY:
                return "Zakonczony";
            default:
                return "Unknown!";
        }
    }

    public void setSTATE(States STATE) {
        this.STATE = STATE;
    }

    public int getPID() {
        return this.PID;
    }

    public int getPPID() {
        return this.PPID;
    }

    public String getName() {
        return this.NAME;
    }

    public ProcessBlockController getParent() {
        return this.parent;
    }

    public List<ProcessBlockController> getChildren() {
        return this.children;
    }

    /**
     * Return description of PCB
     * @return
     */
    public String toString() {
        StringBuilder desc = new StringBuilder();

        desc.append("===== PCB =====\n");

        desc.append("Name: ");
        desc.append(NAME);

        desc.append("\nPID: ");
        desc.append(PID);

        desc.append("\nState: ");
        desc.append(getSTATEName());

        desc.append("\n===============");

        return desc.toString();
    }

    /**
     * Return next value from memory depends on memory pointer
     * @return Next char of program
     */
    public byte readNextFromMemory() throws Exception {
        if(pointer >= sizeOfProgram)
        {
            throw new Exception("End of program!");
        }
        else
        {
            byte mem = MemoryManagementUnit.readFromMemory(pointer, numberOfProgram);
            ++pointer;
            return mem;
        }
    }


    /**
     *  Function change state of Process for Waiting
     *  and save values from Processor to ProcessBlockController
     */
    public void sleep(){
        this.A = Processor.A;
        this.B = Processor.B;
        this.C = Processor.C;
        this.D = Processor.D;
        this.E = Processor.E;
        this.F = Processor.F;
        this.STATE = States.OCZEKUJACY;
    }


    /**
     *  Function recovers last step of executed program
     *  and change state for Ready
     */
    public void wakeup(){
        Processor.A = this.A;
        Processor.B = this.B;
        Processor.C = this.C;
        Processor.D = this.D;
        Processor.E = this.E;
        Processor.F = this.F;
        this.STATE = States.GOTOWY;
    }
}