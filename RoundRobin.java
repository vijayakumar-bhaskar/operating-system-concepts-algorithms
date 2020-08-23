import java.util.*;
public class RoundRobin{
    // process {id, arrival, duration}
    static int[][] process = {{0,22,14},{1,16,31},{2,49,18},{3,0,37},{4,6,10}};
    static int quantum = 10;
    //don't modify further
    static ArrayList<int[]> complete = new ArrayList<>();
    static LinkedList<int[]> readyq = new LinkedList<>();
    static ArrayList<int[]> sequence = new ArrayList<>(); //pid, start time, end time,
    static int time = 0;
    
    public static void main(String[] args){
        int quantumRemain = quantum;
        updateReadyq();
        while(!isAllcomplete()){
            updateReadyq();
            int[] cProcess = readyq.remove();
            if(quantumRemain == 0){
                if(cProcess[2] == 0){
                    complete.add(cProcess);
                    time--;
                }
                else{
                    readyq.add(cProcess);
                    time--;
                }
                //reset quantum for next process
                quantumRemain = quantum;
            }
            else{
                if(cProcess[2] == 0){
                    complete.add(cProcess);
                    time--;
                    quantumRemain = quantum;
                }
                else{
                    if(!sequence.isEmpty()){
                        int[] prevProcess = sequence.get(sequence.size()-1);
                        if(prevProcess[0] == cProcess[0]){
                            int[] info = {cProcess[0],prevProcess[1],(prevProcess[2]+1)};
                            sequence.set((sequence.size()-1),info);
                            quantumRemain -= 1;
                            cProcess[2] -= 1;
                            readyq.addFirst(cProcess);
                        }
                        else{
                            int[] info = {cProcess[0],time,(time+1)};
                            sequence.add(info);
                            quantumRemain -= 1;
                            cProcess[2] -= 1;
                            readyq.addFirst(cProcess);
                        }
                    }
                    else{
                            int[] info = {cProcess[0],time,(time+1)};
                            sequence.add(info);
                            quantumRemain -= 1;
                            cProcess[2] -= 1;
                            readyq.addFirst(cProcess);
                        }
                }
            }
            time++;
        }
        System.out.println("");
        System.out.println("RoundRobin (RR) :: ");
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
            System.out.println("id : "+each[0]+" waiting time :"+waitingTime(each[0]));
        }
        System.out.println("total waiting time : "+wt);
        return (wt/process.length);
    }
    
    public static double avgTurnAroundTime(){
        double tt = 0;
        for(int[] each : process){
            tt += turnAroundTime(each[0]);
            System.out.println("id : "+each[0]+" turnAround time :"+turnAroundTime(each[0]));
        }
        System.out.println("total turnAround time : "+tt);
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
    
    public static void updateReadyq(){
        for(int[] each : process){
            if(!isInReadyQueue(each[0]) && each[1] == time){
                readyq.add(each);
                System.out.println("adding process : "+each[0]+" at time : "+time);
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