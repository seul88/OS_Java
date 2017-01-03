package put.os.processes;

public class Examples {

    public static void main(String[] args) {

        ProcessManager manager = new ProcessManager();



        ProcessBlockController p1 = new ProcessBlockController(manager.getCounter(), "TEST");
        manager.setRoot(p1);

        ProcessBlockController p2 = new ProcessBlockController(manager.getCounter(),"Proces 1");
        manager.addProcessToRoot(p2);

        ProcessBlockController p3 = new ProcessBlockController(manager.getCounter(),"Proces 2");
        manager.addProcessToRoot(p3);

        manager.setState(3, p3);
        System.out.println( manager.returnReadyProcess().getName());
       if (p3.getSTATE() == 3) System.out.println("Proces dla Arka!");
        if (p2.getSTATE() == 3) System.out.println("Proces dla Arka!");

        ProcessBlockController p4 = new ProcessBlockController(manager.getCounter(),"Proces 3");
        manager.addProcessToRoot(p4);

        ProcessBlockController p5 = new ProcessBlockController(manager.getCounter(),"Proces 4");
        manager.addProcess(p2,p5);

        ProcessBlockController p6 = new ProcessBlockController(manager.getCounter(),"Proces 5");
        manager.addProcess(p2,p6);

        ProcessBlockController p7 = new ProcessBlockController(manager.getCounter(),"Proces 6");
        manager.addProcess(p2,p7);

        ProcessBlockController p8 = new ProcessBlockController(manager.getCounter(),"Proces 7");
        manager.addProcessToRoot(p8);

        ProcessBlockController p9 = new ProcessBlockController(manager.getCounter(),"Proces 8");
        manager.addProcessToRoot(p9);

        ProcessBlockController p10 = new ProcessBlockController(manager.getCounter(),"Proces 9");
        manager.addProcessToRoot(p10);

        ProcessBlockController p11 = new ProcessBlockController(manager.getCounter(),"Proces 10");
        manager.addProcessToRoot(p11);



        // krotsze wywolanie metod
       // manager.setRoot(new ProcessBlockController(manager.getCounter(), "PCB"));
      /*  manager.addProcessToRoot("PROCES X");
        manager.addProcess("PROCES Y", "PROCES X");
        manager.addProcess("PROCES Z", "PROCES X");
        manager.addProcess("PROCES A", "PROCES X");
        manager.addProcess("PROCES 1", "PROCES X");
        manager.addProcess("PROCES 2", "PROCES X");
        System.out.println(manager.getCounter());
        System.out.println(manager.getRoot().getName());

        System.out.println(manager.findNode("PROCES X").getName()+" HEREEEE");
        System.out.println(manager.getChildrenNames("PROCES X"));
        if(manager.find("PROCES X")) System.out.println("JUHU");
*/

/*
        System.out.println(p1.getPPID());
        System.out.println(p7.getPPID());

        System.out.println(manager.getRoot().getChildrenNames());

        manager.removeChild(p8);
        manager.removeChild(p7);
        manager.removeChild(p6);
        manager.removeChild(p5);
        manager.removeChild(p4);
*/
        System.out.println(manager.getCounter());
        System.out.println(manager.getRoot().getName());

        System.out.println(manager.getRoot().getPID());
        System.out.println(manager.isEmpty());
        System.out.println(manager.getNumberOfChildren(p1));
      //  System.out.println(manager.getNumberOfChildren(p2));
        System.out.println(manager.getChildrenNames(p1));
      //  System.out.println(manager.getChildrenNames(p2));
    /*    if(manager.find(p2, p8)) System.out.println("DA");
        p2.removeChild(p5);
        System.out.println(manager.getChildrenNames(p1));
        p1.removeChild(p2);     */
        System.out.println(manager.getChildrenNames(p1));


        }
}