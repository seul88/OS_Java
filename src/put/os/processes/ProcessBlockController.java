package put.os.processes;
import virtual.device.Processor;

import java.util.List;
import java.util.ArrayList;

public class ProcessBlockController {

	private int PID;    // nr ID procesu
	private String NAME; // nazwa procesu
	private int PPID;	// nr ID rodzica procesu
	private int STATE;

	//  STATE=0 proces NOWY, po utworzeniu procesu.
	//	STATE=1 proces WYKONYWANY, proces jest wykonywany.
	//	STATE=2 proces OCZEKUJACY, proces czeka na przydzial zasobu innego niz procesor.
	//	STATE=3 proces GOTOWY, proces czeka na przydzial procesora.
	//  STATE=4 proces ZAKONCZONY, po zakonczeniu wykonywania procesu.

	private ProcessBlockController parent;			// rodzic procesu
	private List<ProcessBlockController> children;	// lista dzieci procesu


	public ProcessBlockController(int counter, String name){
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


	public String getChildrenNames(){
		String result="";
		List<ProcessBlockController> list= this.children;
		for (ProcessBlockController pcb : list ) {
			result += pcb.getName() + "\n";
			result +=  pcb.getChildrenNames();
		}
		return result;
	}

	public boolean equals(int PID){
		if (PID == this.PID) return true;
		return false;
	}

	public boolean equals(ProcessBlockController PCB){
		if (PCB == this) return true;
		return false;
	}




	// 				setters			//




	public void setName(String name) {
		this.NAME = name;
	}


	public void setParent(ProcessBlockController parent) {
		this.parent = parent;
	}



	public void setPPID(int PPID){
		this.PPID = PPID;
	}

	public void setA(int A){
		Processor.A = A;
	}

	public void setB(int B){
		Processor.B = B;
	}

	public void setC(int C){
		Processor.C = C;
	}

	public void setD(int D){
		Processor.D = D;
	}

	public void setE(int E){
		Processor.E = E;
	}

	public void setF(int F){
		Processor.F = F;
	}

	public void setSTATE(int STATE){
		if (STATE >= 0 && STATE <= 4) this.STATE = STATE;
	}


	//		getters		//





	public int getPID(){
		return this.PID;
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




	public int getA(){
		return Processor.A;
	}

	public int getB(){
		return Processor.B;
	}

	public int getC(){
		return Processor.C;
	}

	public int getD(){
		return Processor.D;
	}

	public int getE(){
		return Processor.E;
	}

	public int getF(){
		return Processor.F;
	}

	public int getPPID(){
		return this.PPID;
	}

	public int getSTATE(){
		return this.STATE;
	}

}

