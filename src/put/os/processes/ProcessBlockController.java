package put.os.processes;

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


    public void addChild(ProcessBlockController child) {
        child.setParent(this);
        child.PPID = this.getPID();
        children.add(child);
    }

    public boolean removeChild(ProcessBlockController child) {
        List<ProcessBlockController> list = getChildren();
        return list.remove(child);
    }


    public String getChildrenNames() {
        String result = "";
        List<ProcessBlockController> list = this.children;
        for (ProcessBlockController pcb : list) {
            result += pcb.getName() + "\n";
            result += pcb.getChildrenNames();
        }
        return result;
    }

    public boolean equals(int PID) {
        if (PID == this.PID) return true;
        return false;
    }

    public boolean equals(ProcessBlockController PCB) {
        if (PCB == this) return true;
        return false;
    }


    // 				setters			//



    /*
        TODO VER1 (ERWIN)
        Zmiana rodzica powinna rowniez usuwac >>ten<< proces z listy [children] dawnego rodzica

        Tak chyba będzie OK ~Erwin
    */
    public void setParent(ProcessBlockController parent) {
        removeChild(this.parent);
        this.parent = parent;
    }




    public void setSTATE(States STATE) {
        this.STATE = STATE;
    }


    //		getters		//


    public int getPID() {
        return this.PID;
    }

    public int getPPID() {
        return this.PPID;
    }

    public States getSTATE() {
        return this.STATE;
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

        desc.append("PID: ");
        desc.append(PID);

        desc.append("\nState: ");
        switch(this.STATE) {
            case NOWY:
                desc.append("Nowy");
                break;
            case WYKONYWANY:
                desc.append("Wykonywany");
                break;
            case OCZEKUJACY:
                desc.append("Oczekujący");
                break;
            case GOTOWY:
                desc.append("Gotowy");
                break;
            case ZAKONCZONY:
                desc.append("Zakonczony");
                break;
            default:
                desc.append("Unknown!");
                break;
        }

        desc.append("\n===============");

        return desc.toString();
    }

    /**
     * Return next value from memory depends on memory pointer
     * @return Next char of program
     */
    public byte readNextFromMemory() {

        //return MemoryManagementUnit.getInstance().readFromMemory();

        return 'p'; // palceholder
    }
}

