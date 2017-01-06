/*package put.os.FCFS;

import put.os.LinkedList;

public class FCFS {

	private LinkedList<Process> fcfsProcQueue;
    private LinkedList<Process> finalProcList = new LinkedList<Process>();

    public FCFS(LinkedList<Process> procQueue) {
        this.fcfsProcQueue = procQueue;
    }

    public void execute() {
        Process x = fcfsProcQueue.peek();
        int totaltime = x.getArrival();


        while(!fcfsProcQueue.isEmpty()){
            Process runningProcess = fcfsProcQueue.peek();
            int ioBurst = runningProcess.getNextBurst();
            int runTime = runningProcess.getRunTime();
            int cpuTime = runningProcess.getcpu();
            int id = runningProcess.getId();
            System.out.println("( P" + runningProcess.getId() + " ) - Arrived, Executing");

                while( (runTime != cpuTime) && (ioBurst != runTime) ) {
                    
                    runningProcess.incrementRunTime();
                    runTime = runningProcess.getRunTime();

                    for(Process p: fcfsProcQueue){
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

   public LinkedList<Process> returnFinalProc() {
       return finalProcList;
   }

   public void moveProcess(Process p, int totaltime) {
       boolean flag = false;
                    for(int b = 1; b < fcfsProcQueue.size();b++) {
                        Process c = fcfsProcQueue.get(b);
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

*/