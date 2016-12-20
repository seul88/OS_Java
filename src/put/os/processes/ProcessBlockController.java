package put.os.processes;

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

	private int A;	// wartosci w rejestrach procesu
	private int B;
	private int C;
	private int D;
	private int E;
	private int F;

	private ProcessBlockController parent;			// rodzic procesu
	private List<ProcessBlockController> children;	// lista dzieci procesu


	public ProcessBlockController(int counter, String name){
		this.PID = counter;
		this.NAME = name;

		this.children = new ArrayList<ProcessBlockController>();

		this.STATE = 0;

		if (this.getParent() != null) this.PPID = this.getParent().getPID();
		else this.PPID = 0;

		this.A = 0;
		this.B = 0;
		this.C = 0;
		this.D = 0;
		this.E = 0;
		this.F = 0;
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
		String result="+ ";
		List<ProcessBlockController> list= this.children;
		for (ProcessBlockController pcb : list )

			result += pcb.getName() +" + ";
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



	// getters & setters

	public int getPID(){
		return this.PID;
	}


	public String getName() {
		return this.NAME;
	}

	public void setName(String name) {
		this.NAME = name;
	}

	public ProcessBlockController getParent() {
		return this.parent;
	}

	public void setParent(ProcessBlockController parent) {
		this.parent = parent;
	}

	public List<ProcessBlockController> getChildren() {
		return this.children;
	}


	public void setPPID(int PPID){
		this.PPID = PPID;
	}

	public void setA(int A){
		this.A = A;
	}

	public void setB(int B){
		this.B = B;
	}

	public void setC(int C){
		this.C = C;
	}

	public void setD(int D){
		this.D = D;
	}

	public void setE(int E){
		this.E = E;
	}

	public void setF(int F){
		this.F = F;
	}

	public int getA(){
		return this.A;
	}

	public int getB(){
		return this.B;
	}

	public int getC(){
		return this.C;
	}

	public int getD(){
		return this.D;
	}

	public int getE(){
		return this.E;
	}

	public int getF(){
		return this.F;
	}

	public int getPPID(){
		return this.PPID;
	}

}