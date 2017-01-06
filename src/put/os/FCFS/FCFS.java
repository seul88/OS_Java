package put.os.FCFS;

import java.util.LinkedList;
import put.os.processes.*;

public class FCFS {

	private LinkedList<ProcessBlockController> fcfsProcQueue;
    private LinkedList<ProcessBlockController> finalProcList = new LinkedList<ProcessBlockController>();

    public FCFS(LinkedList<ProcessBlockController> procQueue) {
        this.fcfsProcQueue = procQueue;
    }
    
    public void execute() {
    	ProcessBlockController x = fcfsProcQueue.peek();
        int totaltime = x.getArrival();

        while(!fcfsProcQueue.isEmpty()){
        	ProcessBlockController runningProcess = fcfsProcQueue.peek();
            int ioBurst = runningProcess.getNextBurst();
            int runTime = runningProcess.getRunTime();
            int cpuTime = runningProcess.getcpu();
            int id = runningProcess.getId();
            System.out.println("( P" + runningProcess.getId() + " ) - Arrived, Executing");

                while( (runTime != cpuTime) && (ioBurst != runTime) ) {
                    
                    runningProcess.incrementRunTime();
                    runTime = runningProcess.getRunTime();

                    for(ProcessBlockController p: fcfsProcQueue){
                    if((p.getId()!= id) && (p.getArrival() <= totaltime))
                        p.incrementWait();
                    }
                    totaltime++;
                }
                if(runTime == cpuTime){
                    System.out.println("( P" + runningProcess.getId() + " ) - Finished, Removed");
                    finalProcList.add(runningProcess);
                    fcfsProcQueue.remove(runningProcess);
                }
                else if(ioBurst == runTime)
                {
                    System.out.println("( P" + runningProcess.getId() + " ) - Switched, IOburst");
                    runningProcess.ioburstList.remove(0);
                    moveProcess(runningProcess,totaltime);
                }
        }
        System.out.println("finished");
   }
    
   public LinkedList<ProcessBlockController> returnFinalProc() {
       return finalProcList;
   }
   
   public void moveProcess(ProcessBlockController p, int totaltime) {
       boolean flag = false;
                    for(int b = 1; b < fcfsProcQueue.size(); b++) {
                    	ProcessBlockController c = fcfsProcQueue.get(b);
                        if(c.getWait() == 0){
                            fcfsProcQueue.add(b - 1, fcfsProcQueue.pop());
                            flag = true;
                            break;
                        }
                    }
                    if(!flag) {
                        fcfsProcQueue.addLast(fcfsProcQueue.pop());
                    }
   }
}