import java.util.*;
public class SASeqOrder{
    // initialise values
    static int[] processID;
    static int[][] maximum;
    static int[][] alloc;
    static int[] avail;
    static boolean[] finish;
    static int lastEnteredID = 0;
    static boolean resourceRequest;
    static int requestID;
    static int[] request;
    
    
    public SASeqOrder(int[] pid,int[][] max, int[][] allocation, int[] available, boolean[] fin, boolean rr, int rID, int[] req){
        processID = pid;
        maximum = max;
        alloc = allocation;
        avail = available;
        finish = fin;
        resourceRequest = rr;
        requestID = rID;
        request = req;
    }
    
    public static void main(String[] args){
        if(maximum.length == alloc.length && maximum[0].length == alloc[0].length){
            System.out.println("");
            System.out.println("(order in slide) sequential order execution : ");
            if(resourceRequest){System.out.println("Resource-request : ");}
            System.out.println("execution sequence : ");
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
        if(lastEnteredID > 0){
            for(int i = lastEnteredID ; i < need.length ; i++){
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
                lastEnteredID = i;
                System.out.println("id : "+processID[i]+", new available : "+Arrays.toString(avail));
                return i;
            }
          }
        }
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
                lastEnteredID = i;
                System.out.println("id : "+i+", new available : "+Arrays.toString(avail));
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