public class DemandPagingEAT{
    static double pageFaultProb = 0.5;
    static double memoryAccess = 200;
    static double pageFaultTime = 8000000;
    //set true if pageFaultTime needs to be caluclated automatically therefore all the values below should be intialised
    static boolean calculatePFT = false;
    //initialise the values
    static double pfOverhead = 0;
    static double swapPageOut = 0;
    static double swapPageIn = 0;
    static double restartOverhead = 0;
    
    public static void main(String[] args){
        if(calculatePFT){
            pageFaultTime = pfOverhead+swapPageOut+swapPageIn+restartOverhead;
        }
        double result = ((1-pageFaultProb)*memoryAccess)+(pageFaultProb*pageFaultTime);
        System.out.println("Demand paging EAT = "+result);
    }
    
}