import java.util.*;
public class FIFOPageReplacement{
    // initialsie seq and frameSize only, run main
    static int[] seq = {1, 2, 3, 4, 2, 3, 4, 1, 2, 1, 1, 3, 1, 4};
    static int[] frame;
    static int frameSize = 3;
    static int pageFault = 0;
    static String pattern = "";
    public static void main(String[] args){
        System.out.println("FIFO : ");
        System.out.println(Integer.MIN_VALUE+" indicates empty memory array !");
        frame = new int[frameSize];
        for(int i = 0; i < frameSize; i++){
            frame[i] = Integer.MIN_VALUE;
        }
        int index = 0;
        int findex = 0;
        System.out.println("Initial Frame : "+Arrays.toString(frame));
        while(index < seq.length){
            int page = seq[index];
            if(!inFrame(page)){
                pageFault++;
                frame[findex] = page;
                pattern += "Y";
                findex = (findex+1)%frameSize;
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
    
    public static boolean inFrame(int page){
        for(int each : frame){
            if(each == page){return true;}
        }
        return false;
    }
}