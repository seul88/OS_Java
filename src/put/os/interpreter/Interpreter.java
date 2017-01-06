package put.os.interpreter;

import put.os.processes.ProcessBlockController;

public class Interpreter {

    private ProcessBlockController pcb;

    public Interpreter(ProcessBlockController pcb) {
        this.pcb = pcb;
    }

    public boolean nextLine() {

        return true;
    }

    public void runAll() {
        while(nextLine()) {}
    }

}
