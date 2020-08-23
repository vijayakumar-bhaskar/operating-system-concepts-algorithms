import java.util.*;
public class LRUPageFault{
    // initialise seq and frameSize only and run main(String args[])
    static int[] seq = {2,1,2,0,3,2,3,0,1,3,1,2,1,2,1};
    static int[] frame;
    static int frameSize = 3;
    static int pageFault = 0;
    static String pattern = "";
    
    public static void main(String[] args){
        System.out.println("LRU : ");
        System.out.println(Integer.MIN_VALUE+" indicates empty memory array !");
        frame = new int[frameSize];
        for(int i = 0; i < frameSize; i++){
            frame[i] = Integer.MIN_VALUE;
        }
        int index = 0;
        System.out.println("Initial Frame : "+Arrays.toString(frame));
        while(index < seq.length){
            int page = seq[index];
            if(!inFrame(page)){
                int findex = indexToRemove(index);
                pageFault++;
                pattern += "Y";
                frame[findex] = page;
                System.out.println("Frame : "+Arrays.toString(frame));
            }
            else{
                pattern += "N";
            }
            index++;
        }
        System.out.println(System.lineSeparator()+"Final Frame : "+Arrays.toString(frame));
        System.out.println("no of page faults = "+pageFault);
        System.out.println("pattern : "+pattern);
    }
    
    public static int indexToRemove(int currentIndex){
        int[] temp = new int[frame.length];
        for(int i = 0; i < frame.length; i++){ temp[i] = frame[i];}
        for(int i = 0; i < temp.length; i++){
            boolean isAlreadySet = false;
            for(int j = currentIndex-1; j >= 0; j--){
                if(temp[i] == seq[j]){
                    temp[i] = j;
                    isAlreadySet = true;
                    break;
                }
            }
            if(!isAlreadySet){temp[i] = -1;}
        }
        return findIndexOfClosest(temp);
    }
    
    public static int findIndexOfClosest(int[] arr){
        int ret = 0;
        for(int i = 0; i < arr.length; i++){
            if( arr[i] < arr[ret] ){ ret = i;}
        }
        return ret;
    }
    
    public static boolean inFrame(int page){
        for(int each : frame){
            if(each == page){return true;}
        }
        return false;
    }
}