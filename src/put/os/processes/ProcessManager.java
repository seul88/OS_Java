package put.os.processes;

public class ProcessManager {


	private int counter;
	private ProcessBlockController root;


	public ProcessManager(ProcessBlockController root) {
		this.root = root;
		this.counter = 0;
	}


	public boolean isEmpty() {
		return root == null;
	}


	public ProcessBlockController getRoot() {
		return root;
	}


	public int getCounter(){
		return this.counter;
	}

	public boolean removeChild(ProcessBlockController child) {
		return child.removeChild(child);
	}


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



	public ProcessBlockController findNode(String NAME){
		// wyszukiwanie procesu po nazwie
		for (ProcessBlockController child : root.getChildren())
			if (child.getName() == NAME) return child;
		return null;
	}

	public boolean find(String NAME){
		// wyszukiwanie procesu po nazwie
		for (ProcessBlockController child : root.getChildren())
			if (child.getName() == NAME) return true;
		return false;
	}



	// function adds process to the Tree
	// it requires 2 parameters - root of the Tree [PCB] and value to add [PCB]

	public void addProcess(ProcessBlockController root, ProcessBlockController PCB){
		root.addChild(PCB);
		this.counter = counter+1;
	}



	//  add process by name [String]

	public void addProcess(String NAME, String root){
		ProcessBlockController parent = findNode(root);
		ProcessBlockController PCB = new ProcessBlockController(this.counter, NAME);
		parent.addChild(PCB);
		this.counter = counter+1;
	}



	// function enables to add root of Tree (root only!)

	public void addProcessToRoot(ProcessBlockController PCB){
		this.root.addChild(PCB);
		this.counter = counter+1;
	}


	public void addProcessToRoot(String NAME){
		ProcessBlockController PCB = new ProcessBlockController(this.counter, NAME);
		this.root.addChild(PCB);
		this.counter = counter+1;
	}

	public ProcessManager(){
		this.counter = 0;
		this.root = null;
	}


	public String getChildrenNames(ProcessBlockController PCB){
		String result ="";
		result = PCB.getChildrenNames();
		return result;
	}


	public String getChildrenNames(String PCB){

		ProcessBlockController pcb = this.findNode(PCB);
		return pcb.getChildrenNames();

	}
}