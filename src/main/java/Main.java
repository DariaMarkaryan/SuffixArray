import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        String txt = "banana";
//        SufArr suff_arr = new SufArr(txt);
//        System.out.println("Following is suffix array for banana: ");
//        SufArr.printArr(suff_arr.suffixArray);
//        System.out.println("Following is lcp[] for banana: ");
//        SufArr.printArr(suff_arr.lcp);
//        SufArr suffixes = new SufArr("sip");
//        int result[] = suffixes.suffixArray;
//        SufArr.subStringSearch(txt, "nan");
//        System.out.println("--------5 3 1 0 4 2---------");
////        suff_arr.createSuffArr(txt);
        // a,ana, anana,banana,na,nana
        int[] resss = SufArr.findAllMatches(txt, "na");
        for(Integer e : resss)
            System.out.print(e + " ");

    }
}