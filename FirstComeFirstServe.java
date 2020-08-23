import java.util.*;
public class FCFS{
    // process {id, arrival, duration}
    static int[][] process = {{1,0,5},{2,4,3},{3,2,4},{4,12,2}};
    static ArrayList<int[]> complete = new ArrayList<>();

    public static void main(String[] args){
        while(!isAllcomplete()){
            complete.add(getNextProcess());
        }
        System.out.println("FCFS CPU SCHEDULING : ");
        int startTime = complete.get(0)[1];
        for(int i = 0 ; i < complete.size(); i++){
            int[] p = complete.get(i);
            System.out.println(i+" :: process id : "+p[0]+", arrival : "+p[1]+", duration : "+p[2]
                                +", time esplased : "+startTime+" to "+(startTime+p[2])
                                +", waiting time : "+(startTime-p[1])+", turnaround time : "+((startTime+p[2])-p[1]));
            startTime += p[2];
        }
        System.out.println("");
        System.out.println("Average Waiting Time = "+getAvgWaitingTime());
        System.out.println("");
        System.out.println("Average turnAround Time = "+getAvgTurnAroundTime());
        System.out.println("");
        System.out.println("throughput = "+getThroughput());
    }

    public static float getAvgWaitingTime(){
        float startTime = complete.get(0)[1];
        float sumofwait = 0;
        for(int i = 0; i < complete.size(); i++){
            int[] p = complete.get(i);
            sumofwait += (startTime - p[1]);
            startTime += p[2];
        }
        System.out.println("total wait time : "+sumofwait);
        return (sumofwait/(process.length));
    }

    public static float getAvgTurnAroundTime(){
        float startTime = complete.get(0)[1];
        float sumofturnAround = 0;
        for(int i = 0; i < complete.size(); i++){
            int[] p = complete.get(i);
            sumofturnAround += ((startTime+p[2]) - p[1]);
            startTime += p[2];
        }
        System.out.println("total turnAroundTime : "+sumofturnAround);
        return (sumofturnAround/(process.length));
    }

    public static float getThroughput(){
        float sumOfDuration = 0;
        for (int[] each : process){
            sumOfDuration += each[2];
        }
        return (process.length/sumOfDuration);
    }

    public static int[] getNextProcess(){
        int pid = 0;
        for(int i = 0; i < process.length; i++){ if(!complete.contains(process[i])){ pid = i; break;}}
        for(int i = 0; i < process.length; i++){
            if(!complete.contains(process[i])){
                if(process[i][1] < process[pid][1]){
                    pid = i;
                }
            }
        }
        return process[pid];
    }

    public static boolean isAllcomplete(){
        boolean isComplete = true;
        for(int[] each : process){
            if(!complete.contains(each)){ isComplete = false;}
        }
        return isComplete;
    }
}
