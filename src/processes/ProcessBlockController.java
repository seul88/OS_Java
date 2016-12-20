package processes;

import java.util.ArrayList;
import java.util.List;

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
	
	int A;	// wartosci w rejestrach procesu
	int B;
	int C;
	int D;
	int E;
	int F;
	
	private ProcessBlockController parent;			// rodzic procesu 
	private List<ProcessBlockController> children;	// lista dzieci procesu


	public ProcessBlockController(int counter, String name){
		this.PID = counter;
		this.NAME = name;
		
		this.children = new ArrayList<ProcessBlockController>();
		
		this.STATE = 0;
		
		if(this.getParent() != null)this.PPID = this.getParent().getPID();
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
        children.add(child);
    }
	
	 public boolean removeChild(ProcessBlockController child) {
	        List<ProcessBlockController> list = getChildren();
	        return list.remove(child);
	    }
	
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
}