import java.util.*;
public class SafteyAlgorithm{
    // initialise values e.g {p0; max : 2,3,2 ; alloc : 1,0,0} => processID = {0,....}, maximum = {{2,3,2},...}, alloc = {{1,0,0},...}
    static int[] processID = {0,1,2,3,4};
    static int[][] maximum = {{0,2,3},{8,0,0},{8,1,0},{4,0,4},{0,0,1}};
    static int[][] alloc = {{0,0,0},{3,0,0},{4,0,0},{0,0,2},{0,0,0}};
    static int[] avail = {1,2,2};
    static boolean[] finish = {false,false,false,false,false}; //no of false = no of process ; initially set all values to false
    // initialise further if resource-request
    final static boolean resourceRequest = false;
    final static int requestID = 1; // process id not index number
    final static int[] request = {1,0,2};
    // is need given
    final static boolean isNeedGiven = false;
    final static int[][] needGiven = {};
    
    
    public static void main(String[] args){
        if(isNeedGiven){
            maximum = new int[alloc.length][alloc[0].length];
            for(int i = 0; i < needGiven.length; i++){
                for(int j = 0; j < needGiven[i].length; j++){
                    maximum[i][j] = alloc[i][j]+needGiven[i][j];
                }
            }
        }
        int[] processID2 = new int[processID.length];
        int[][] maximum2 = new int[maximum.length][maximum[0].length];
        int[][] alloc2 = new int[alloc.length][alloc[0].length];
        int[] avail2 = new int[avail.length];
        boolean[] finish2 = new boolean[finish.length];
        for(int i = 0 ; i < processID.length; i++){processID2[i] = processID[i];}
        for(int i = 0 ; i < maximum.length; i++){ for (int j = 0; j < maximum[0].length; j++){ maximum2[i][j] = maximum[i][j]; }}
        for(int i = 0 ; i < alloc.length; i++){ for (int j = 0; j < alloc[0].length; j++){ alloc2[i][j] = alloc[i][j]; }}
        for(int i = 0 ; i < avail.length; i++){ avail2[i] = avail[i];}
        for(int i = 0 ; i < finish.length; i++){ finish2[i] = finish[i];}
        SASeqOrder objTwo = new SASeqOrder(processID2,maximum2, alloc2, avail2, finish2,resourceRequest, requestID, request);
        if(maximum.length == alloc.length && maximum[0].length == alloc[0].length){
            System.out.println("(lowest id first order) restart order execution : ");
            if(resourceRequest){System.out.println("Resource-request : ");}
            System.out.println("execution sequence : ");
            System.out.println("");
            System.out.println("Initial available : "+Arrays.toString(avail));
            System.out.println("");
            int[][] need = calculateNeed(maximum,alloc);
            if(resourceRequest){
                // available = available - request
                for(int i = 0; i < avail.length; i++){
                    avail[i] -= request[i];
                }
                int indexToChange = 0;
                for(int i = 0 ; i < processID.length; i++){
                    if(processID[i] == requestID){ indexToChange = i; break;}
                }
                // allocation(i) = allocation(i) + request
                for(int i = 0; i < alloc[indexToChange].length; i++){
                    alloc[indexToChange][i] += request[i];
                }
                // need(i) = need(i) - request
                for(int i = 0; i < need[indexToChange].length; i++){
                    need[indexToChange][i] -= request[i];
                }
            }
            int[] executionOrder = new int[maximum.length];
            int orderCount = 0;
            int nextProcessID = findNext(need, avail);
            boolean toStop = false;
            while(!isFinished() && !toStop){
                if(nextProcessID == -1){
                    executionOrder[orderCount] = nextProcessID;
                    System.out.println(" DeadLock");
                    toStop = true;
                }
                else{
                    executionOrder[orderCount] = nextProcessID;
                    orderCount++;
                    nextProcessID = findNext(need,avail);
                }
            }
        }
        objTwo.main(null);
    }
    
    public static int[][] calculateNeed(int[][] max, int[][]alloc){
        int[][] need = new int[max.length][max[0].length];
        for(int i = 0; i < max.length; ++i){
            int[] a = max[i];
            int[] b = alloc[i];
            for(int j = 0; j < a.length; ++j){
                need[i][j] = max[i][j] - alloc[i][j];
            }
        }
        return need;
    }
    
    public static int findNext(int[][] need, int[] avail){
        for(int i = 0 ; i < need.length ; i++){
            boolean isFound = true;
            for(int j = 0; j < avail.length; j++){
                if(need[i][j] > avail[j]){
                    isFound = false;
                }
            }
            if(isFound && !(finish[i])){
                finish[i] = true;
                for(int j = 0; j < avail.length; j++){
                    avail[j] += alloc[i][j];
                }
                System.out.println("id : "+processID[i]+", new available : "+Arrays.toString(avail));
                return i;
            }
          }
        return -1;
    }
    
    public static boolean isFinished(){
        boolean ret = true;
        for(boolean each : finish){
            if(!each){
                ret = false;
            }
        }
        return ret;
    }
}