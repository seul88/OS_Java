package processes;

public class Examples {
	
	
	public static void main(String[] args) {
		
		ProcessManager manager = new ProcessManager();
		
	//	manager.createFirstProcess("TEST");
		
		ProcessBlockController p1 = new ProcessBlockController(manager.getCounter(), "TEST");
		manager.setRoot(p1);		
		
		ProcessBlockController p2 = new ProcessBlockController(manager.getCounter(),"Proces 1");
		manager.createFirstProcess(p2);
		
		ProcessBlockController p3 = new ProcessBlockController(manager.getCounter(),"Proces 2");
		manager.createFirstProcess(p3);
		
		ProcessBlockController p4 = new ProcessBlockController(manager.getCounter(),"Proces 3");
		manager.createFirstProcess(p4);
		
		ProcessBlockController p5 = new ProcessBlockController(manager.getCounter(),"Proces 4");
		manager.addProcess(p2,p5);
		
		ProcessBlockController p6 = new ProcessBlockController(manager.getCounter(),"Proces 5");
		manager.addProcess(p2,p6);
		
		ProcessBlockController p7 = new ProcessBlockController(manager.getCounter(),"Proces 6");
		manager.addProcess(p2,p7);
		
		ProcessBlockController p8 = new ProcessBlockController(manager.getCounter(),"Proces 7");
		manager.createFirstProcess(p8);
		
		ProcessBlockController p9 = new ProcessBlockController(manager.getCounter(),"Proces 8");
		manager.createFirstProcess(p9);
		
		ProcessBlockController p10 = new ProcessBlockController(manager.getCounter(),"Proces 9");
		manager.createFirstProcess(p10);
		
		ProcessBlockController p11 = new ProcessBlockController(manager.getCounter(),"Proces 10");
		manager.createFirstProcess(p11);
		
	//	ProcessBlockController p12 = new ProcessBlockController(manager.getCounter(),"Proces 11");
		
		// krotsze wywolanie metod
		
		manager.createFirstProcess("PROCES X");
		manager.addProcess("PROCES Y", "PROCES X");
		manager.addProcess("PROCES Z", "PROCES X");
		manager.addProcess("PROCES A", "PROCES X");
		System.out.println(manager.findNode("PROCES X").getName()+" HEREEEE");
		System.out.println(manager.getChildrenNames("PROCES X"));
		
		
		System.out.println(manager.getCounter());
		System.out.println(manager.getRoot().getName());
		System.out.println(manager.getRoot().getChildren());
		System.out.println(manager.getRoot().getPID());
		System.out.println(manager.isEmpty());
		System.out.println(manager.getNumberOfProcesses(p1));
		System.out.println(manager.getNumberOfProcesses(p2));
		System.out.println(manager.getChildrenNames(p1));
		if(manager.find(p2, p8)) System.out.println("DA"); 
		p1.removeChild(p2);
		System.out.println(manager.getChildrenNames(p1));
	}

}