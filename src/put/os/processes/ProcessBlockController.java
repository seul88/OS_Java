package put.os.processes;

import virtual.device.Processor;

import java.util.List;
import java.util.ArrayList;

public class ProcessBlockController {

    private int PID;    // nr ID procesu
    private String NAME; // nazwa procesu
    private int PPID;    // nr ID rodzica procesu
    private int STATE;

    //  STATE=0 proces NOWY, po utworzeniu procesu.
    //	STATE=1 proces WYKONYWANY, proces jest wykonywany.
    //	STATE=2 proces OCZEKUJACY, proces czeka na przydzial zasobu innego niz procesor.
    //	STATE=3 proces GOTOWY, proces czeka na przydzial procesora.
    //  STATE=4 proces ZAKONCZONY, po zakonczeniu wykonywania procesu.

    private ProcessBlockController parent;            // rodzic procesu
    private List<ProcessBlockController> children;    // lista dzieci procesu

        /*
        TODO VER1 (ERWIN)
        W bloku PCB beda rejestry takie same jak w procesorze, ale beda uzywane jedynie przy wybudzaniu/uruchamianiu
        procesu (wtedy do procesora beda kopiowane rejestry >>tego<< PCB) oraz przy zatrzymywaniu procesu
        kiedy rejestry procesora zostana skopiowane tutaj.

        Najlepiej utworzyc funkcje np. sleep/pause oraz revoke.
     */

    public ProcessBlockController(int counter, String name) {
        this.PID = counter;
        this.NAME = name;

        this.children = new ArrayList<ProcessBlockController>();

        this.STATE = 0;

        if (this.getParent() != null) this.PPID = this.getParent().getPID();
        else this.PPID = 0;

    }


    public void addChild(ProcessBlockController child) {
        child.setParent(this);
        child.setPPID(this.getPID());
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
        Chyba nie powinno moc sie zmieniac nazwy ani PID procesu.
     */

    public void setName(String name) {
        this.NAME = name;
    }

    /*
        TODO VER1 (ERWIN)
        Zmiana rodzica powinna rowniez usuwac >>ten<< proces z listy [children] dawnego rodzica
    */
    public void setParent(ProcessBlockController parent) {
        this.parent = parent;
    }


    /*
        TODO VER1 (ERWIN)
        To powinno byc prywatne i wywolywane przed setParent
        A najlepiej pozbyc sie tej funkcji i wpisywać recznie ppid w tym obiekcie
    */
    public void setPPID(int PPID) {
        this.PPID = PPID;
    }

    public void setSTATE(int STATE) {
        if (STATE >= 0 && STATE <= 4) this.STATE = STATE;
    }


    //		getters		//


    public int getPID() {
        return this.PID;
    }

    public int getPPID() {
        return this.PPID;
    }

    public int getSTATE() {
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

}

