public class DynamicStorageAllocation{
    static int[] mPartition = {100,500,200,300,600}; // memory partitions
    static int[] processess = {212,417,112,426}; // memory needed for process
    
    public static void main(String[] args){
        firstFit(mPartition, processess);
        bestFit(mPartition, processess);
        worstFit(mPartition, processess);
    }
    
    //first-fit
    public static void firstFit(int[] memory, int[] process){
        int[] mem = new int[memory.length];
        int[] pro = new int[process.length];
        for(int i = 0; i < memory.length; i++){ mem[i] = memory[i];}
        for(int i = 0; i < process.length; i++){ pro[i] = process[i];}
        System.out.println("First-fit dynamic memory allocation : ");
        for(int each : pro){
            boolean stop = true;
            for(int i = 0; i < mem.length; i++){
                if(each <= mem[i]){
                    System.out.println(each+" -> "+mem[i]+" remain : "+(mem[i]-each));
                    mem[i] -= each;
                    stop = false;
                    break;
                }
            }
            if(stop){
                System.out.println("cannot be allocated : "+each);
                break;
            }
        }
    }
    
    //best-fit
    public static void bestFit(int[] memory, int[] process){
        int[] mem = new int[memory.length];
        int[] pro = new int[process.length];
        for(int i = 0; i < memory.length; i++){ mem[i] = memory[i];}
        for(int i = 0; i < process.length; i++){ pro[i] = process[i];}
        System.out.println("Best-fit dynamic memory allocation : ");
        for(int each : pro){
            if(doesFit(mem, each)){
                int memIndex = 0;
                for(int i = 0; i < mem.length; i++){
                    if(each <= mem[i]){memIndex = i; break;}
                }
                for(int i = 0; i < mem.length; i++){
                    if(each <= mem[i] && mem[i] < mem[memIndex]){memIndex = i;}
                }
                System.out.println(each+" -> "+mem[memIndex]+" remain : "+(mem[memIndex]-each));
                mem[memIndex] -= each;
            }
            else{
                System.out.println("cannot be allocated : "+each);
                break;
            }
        }
    }
    
    public static boolean doesFit(int[] memory, int size){
        for(int each : memory){
            if(size <= each) {return true;}
        }
        return false;
    }
    
    //best-fit
    public static void worstFit(int[] memory, int[] process){
        int[] mem = new int[memory.length];
        int[] pro = new int[process.length];
        for(int i = 0; i < memory.length; i++){ mem[i] = memory[i];}
        for(int i = 0; i < process.length; i++){ pro[i] = process[i];}
        System.out.println("worst-fit dynamic memory allocation : ");
        for(int each : pro){
            if(doesFit(mem, each)){
                int mIndex = 0;
                for(int i = 0; i < mem.length; i++){
                    if(each <= mem[i]){mIndex = i; break;}
                }
                for(int i = 0; i < mem.length; i++){
                    if(each <= mem[i] && mem[i] > mem[mIndex]){mIndex = i;}
                }
                System.out.println(each+" -> "+mem[mIndex]+" remain : "+(mem[mIndex]-each));
                mem[mIndex] -= each;
            }
            else{
                System.out.println("cannot be allocated : "+each);
                break;
            }
        }
    }
    
}
