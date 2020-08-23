import java.util.*;
public class DeadLockDetection{
    static int[] processID = {0,1,2,3,4};
    static int[][] alloc = {{0,1,1},{2,0,0},{0,1,1},{0,0,0},{0,0,1}};
    static int[][] requ = {{2,1,0},{2,2,1},{0,2,2},{1,2,1},{1,0,0}};
    static int[] available = {1,1,0};
    // do not modify further
    static boolean[] finish;
    static boolean deadLock = false;
    public static void main(String[] args){
        initFinish();
        while(!deadLock){
            deadLock = true;
            if(nextExist()){
                deadLock = false;
            }
        }
        boolean isDeadLock = false;
        for(boolean each : finish){
            if(!each){
                isDeadLock = true;
            }
        }
        if(isDeadLock){
            System.out.print("the system is deadlocked, process involved in deadlock : ");
            for(int i = 0; i < finish.length; i++){
                if(!finish[i]){
                    System.out.print("P"+processID[i]+", ");
                }
            }
        }
        else{
            System.out.print("No DeadLock");
        }
        System.out.println("\n Final finish array : "+Arrays.toString(finish));
    }
    
    public static boolean nextExist(){
        for(int i = 0; i < requ.length; i++){
            boolean isSmall = true;
            for(int j = 0 ; j < available.length; j++){
                if(requ[i][j] > available[j]){
                    isSmall = false;
                }
            }
            if(isSmall && !(finish[i])){
                finish[i] = true;
                for(int j = 0; j < available.length; j++){
                    available[j] += alloc[i][j];
                }
                return true;
            }
        }
        return false;
    }
    
    public static void initFinish(){
        finish = new boolean[alloc.length];
        for(int i = 0; i < alloc.length; i++){
            boolean assign = true;
            for(int j = 0; j < alloc[0].length; j++){
                if(alloc[i][j] != 0){
                    assign = false;
                }
            }
            finish[i] = assign;
        }
    }
}