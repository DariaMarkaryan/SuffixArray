import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class SufArrTest {
    String s1 = "";
    String s3 = "banana";
    String s4 = "ananas";
    String s5 = "ACTGGCG";

    String sub34 = "na";
    String sub5 = "TGG";

    @Test
    void searchSubstringTest() {
        int[] empty = new int[]{};
        assertArrayEquals(empty, SufArr.findAllMatches(s3,s1));
        assertArrayEquals(empty, SufArr.findAllMatches(s3,sub5));
        assertArrayEquals(empty, SufArr.findAllMatches(sub34,s4));
        int[] temp = {2, 4};
        assertArrayEquals(temp, SufArr.findAllMatches(s3,sub34));
    }

    @Test
    void creatingTest() {
        SufArr o1 = new SufArr(s3);
        assertArrayEquals(new int[]{5, 3, 1, 0, 4, 2}, o1.suffixArray);
        assertArrayEquals( new int[]{1, 3, 0, 0, 2}, o1.lcp);
        SufArr o2 = new SufArr(s1);
        assertArrayEquals( new int[0], o2.suffixArray);
        assertArrayEquals(new int[0], o2.lcp);
    }

    @Test
    void numOfDistSubstrTest() {
        assertEquals(15, SufArr.numOfDistinctSubstr(s3));
        assertEquals(0, SufArr.numOfDistinctSubstr(s1));
    }
}