import java.util.*;
public class SJFandSRTFcpuScheduler{
    // process {id, arrival, duration}
    final static int[][] initProcess = {{0,12,14},{1,0,16},{2,6,5},{3,29,18},{4,23,9}};
    
    //don't modify further
    static int[][] process;
    static ArrayList<int[]> complete;
    static ArrayList<int[]> readyq;
    static ArrayList<int[]> sequence; //pid, start time, end time,
    static int time;
    
    public static void main(String[] args){
        System.out.println("Shortest job first [SJF] (non-pre-emptive) :");
        nonPreemtive();
        System.out.println("==============================================================");
        System.out.println("Shortest job first Shortest remaining time first [SRTF] (pre-emptive) :");
        preEmptive();
    }
    
    public static void nonPreemtive(){
        process = new int[initProcess.length][initProcess[0].length];
        for(int i = 0; i < initProcess.length; i++){
            for(int j = 0; j < initProcess[0].length; j++){
                process[i][j] = initProcess[i][j];
            }
        }
        complete = new ArrayList<>();
        readyq = new ArrayList<>();
        sequence = new ArrayList<>();
        time = 0;
        while(!isAllcomplete()){
            updateReadyqnonContineous();
            int[] pexecute = readyq.get(0);
            for(int i = 0; i < readyq.size(); i++){
                if(readyq.get(i)[2] < pexecute[2]){
                    pexecute = readyq.get(i);
                }
            }
            int[] info = {pexecute[0],time,(pexecute[2]+time)};
            sequence.add(info);
            complete.add(pexecute);
            readyq.remove(pexecute);
            time += pexecute[2];
        }
        
        for(int[] each : sequence){
            System.out.println("id : "+each[0]+", time esplased : "+each[1]+" to "+each[2]);
        }
        
        System.out.println("");
        System.out.println("average waiting time : "+avgWaitingTime());
        System.out.println("");
        System.out.println("average turnaround time : "+avgTurnAroundTime());
        System.out.println("");
        System.out.println("throughput : "+throughPut());
        
    }
    
    public static void preEmptive(){
        process = new int[initProcess.length][initProcess[0].length];
        for(int i = 0; i < initProcess.length; i++){
            for(int j = 0; j < initProcess[0].length; j++){
                process[i][j] = initProcess[i][j];
            }
        }
        complete = new ArrayList<>();
        readyq = new ArrayList<>();
        sequence = new ArrayList<>();
        time = 0;
        while(!isAllcomplete()){
            updateReadyq();
            int[] pexecute = readyq.get(0);
            for(int i = 0; i < readyq.size(); i++){
                if(readyq.get(i)[2] < pexecute[2]){
                    pexecute = readyq.get(i);
                }
            }
            if(pexecute[2] == 0){
                complete.add(pexecute);
                readyq.remove(pexecute);
                time--;
            }
            else{
                if(!sequence.isEmpty()){
                        int[] prevProcess = sequence.get(sequence.size()-1);
                        if(prevProcess[0] == pexecute[0]){
                            int[] info = {pexecute[0],prevProcess[1],(prevProcess[2]+1)};
                            sequence.set((sequence.size()-1),info);
                            pexecute[2] -= 1;
                        }
                        else{
                            int[] info = {pexecute[0],time,(time+1)};
                            sequence.add(info);
                            pexecute[2] -= 1;
                        }
                }
                else{
                    int[] info = {pexecute[0],time,(time+1)};
                            sequence.add(info);
                            pexecute[2] -= 1;
                }
            }
            time++;
        }
        for(int[] each : sequence){
            System.out.println("id : "+each[0]+", time esplased : "+each[1]+" to "+each[2]+", duration : "+(each[2]-each[1]));
        }
        
        System.out.println("");
        System.out.println("average waiting time : "+avgWaitingTime());
        System.out.println("");
        System.out.println("average turnaround time : "+avgTurnAroundTime());
        System.out.println("");
        System.out.println("throughput : "+throughPut());
    }
    
    public static double throughPut(){
        double endTime = sequence.get(sequence.size()-1)[2];
        return (((double)process.length)/endTime);
    }
    
    public static double avgWaitingTime(){
        double wt = 0;
        for(int[] each : process){
            wt += waitingTime(each[0]);
            System.out.println("id : "+each[0]+" waiting time : "+waitingTime(each[0]));
        }
        System.out.println("total waiting time : "+wt);
        return (wt/process.length);
    }
    
    public static double avgTurnAroundTime(){
        double tt = 0;
        for(int[] each : process){
            tt += turnAroundTime(each[0]);
            System.out.println("id : "+each[0]+" turnAround time : "+turnAroundTime(each[0]));
        }
        System.out.println("total turnAroundtime : "+tt);
        return (tt/process.length);
    }
    
    public static double waitingTime(int id){
        double wt = 0;
        double startTime = 0;
        //set arrival time
        for(int i = 0; i < process.length; i++){
            if(process[i][0] == id){
                startTime = process[i][1];
                break;
            }
        }
        //calculate waiting time
        for(int i = 0; i < sequence.size(); i++){
            int[] p = sequence.get(i);
            if(p[0] == id){
                wt += (((double)p[1]) - startTime);
                startTime = (double) p[2];
            }
        }
        return wt;
    }
    
    public static double turnAroundTime(int id){
        double arrival = 0;
        double finish = 0;
        for(int i = 0; i < process.length; i++){
            if(process[i][0] == id){
                arrival = (double) process[i][1];
                break;
            }
        }
        for(int i = 0; i < sequence.size(); i++){
            int[] p = sequence.get(i);
            if(p[0] == id){
                finish = (double) p[2];
            }
        }
        return (finish - arrival);
    }
    
    
    
    public static void updateReadyqnonContineous(){
        for(int[] each : process){
            if(!readyq.contains(each) && each[1] <= time && !complete.contains(each)){
                readyq.add(each);
            }
        }
    }
    
    public static void updateReadyq(){
        for(int[] each : process){
            if(!isInReadyQueue(each[0]) && each[1] == time){
                readyq.add(each);
            }
        }
    }
    
    public static boolean isInReadyQueue(int id){
        if(readyq.isEmpty()){return false;}
        for(int[] each : readyq){
            if(each[0] == id){return true;}
        }
        return false;
    }
    
    public static boolean isComplete(int pid){
        if(complete.isEmpty()){return false;}
        for(int[] each : complete){
            if(each[0] == pid){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isAllcomplete(){
        for(int[] each : process){
            if(!isComplete(each[0])){ return false;}
        }
        return true;
    }
    
}