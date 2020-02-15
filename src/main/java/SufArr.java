import java.util.*;

class SufArr {
    int[] lcp;
    int N;
    int[] suffixArray;
    String s;

    public SufArr(String s) {
        this.s = s;
        N = s.length();
        suffixArray();
        kasai();
    }

    public static class Suffix implements Comparable<Suffix> {
        int index;
        int rank;
        int next;

        Suffix(int ind, int r, int nr) {
            index = ind;
            rank = r;
            next = nr;
        }

        public int compareTo(Suffix s) {
            if (rank != s.rank) return Integer.compare(rank, s.rank);
            return Integer.compare(next, s.next);
        }
    }

    public static class NEWSuffix  implements Comparable<NEWSuffix>{
        int index;
        int c;
        int prev_c;

        NEWSuffix(int ind, int cl) {
            index = ind;
            c = cl;
            prev_c = 0;
        }

        public int compareTo(NEWSuffix s) {
           if (c == s.c) return Integer.compare(prev_c, s.prev_c);
           else return  Integer.compare(c, s.c);
    }

    public void createSuffArr(String str) {
        str += '$';
        int N = str.length();
        NEWSuffix[] arr = new NEWSuffix[N];
        for(int i = 0; i < N ; i++)
            arr[i] = new NEWSuffix(i,str.charAt(i) - '$' + 1);
        Arrays.sort(arr);
        for(int i = 1; i < N; i++)
            arr[i].c = (str.charAt(arr[i].index) > str.charAt(arr[i - 1].index))? arr[i-1].c + 1 : arr[i-1].c;

        int[] new_c = new int[N];
        int[] prev_c = new int[N];
        int cur_len = 1;
        for(int i = 0; i < N ; i++){
            System.out.print(arr[i].c + " ");
        }
        System.out.println( " is c ");
        for(int i = 0; i < N ; i++){
            System.out.print(arr[i].index+" ");
        }
        System.out.println( " is ind ");
        Arrays.sort(arr);
        while(cur_len <= N) {
            System.out.println("============");
            for(int i = 1; i < N; i++)
                prev_c[arr[i].index] = arr[i].c;

            for(int i = 0; i < N; i++) {
                arr[i].c = prev_c[arr[i].index] + prev_c[(arr[i].index + cur_len) % N];
                System.out.print(arr[i].c  + " ");
            }
            System.out.println(" is temp_C");
            Arrays.sort(arr);
            new_c[0] = 1;
            for(int i = 1; i < N ; i++) {
                if(arr[i].c == arr[i - 1].c)
                    new_c[i] = (prev_c[arr[i].index] < prev_c[arr[i - 1].index]) ? new_c[i-1] + 1 : new_c[i - 1];
                else
                    new_c[i] = new_c[i - 1] + 1;
            }
            for(int i = 0; i < N ; i++)
                arr[i].c = new_c[i];
            cur_len *= 2;
            for(int i = 0; i < N ; i++){
                System.out.print(arr[i].c+" ");
            }
            System.out.println(" is c");
            for(int i = 0; i < N ; i++){
                System.out.print(arr[i].index+" ");
            }
            System.out.println( " is ind ");

        }
        for(int i = 0; i < N ; i++)
            System.out.print(arr[i].index + " ");

    }
    }

     private void suffixArray() {
        Suffix[] su = new Suffix[N];

        for (int i = 0; i < N; i++)
            su[i] = new Suffix(i, s.charAt(i) - 'a', 0);
        for (int i = 0; i < N; i++)
            su[i].next = i + 1 < N ? su[i + 1].rank : -1;

        Arrays.sort(su); //O(N logN) в среднем случае. так как у нас всегда есть половина отсортированных значений
                          //худший случай нас не волнует

        int[] inv = new int[N];
        for (int length = 4; length < 2 * N; length *= 2 ) {  //O(logN)
            int rank = 0, prev = su[0].rank;
            su[0].rank = rank;
            inv[su[0].index] = 0;

            for (int i = 1; i < N; i++) {
                if (su[i].rank == prev && su[i].next == su[i - 1].next) {
                    prev = su[i].rank;
                    su[i].rank = rank;
                } else {
                    prev = su[i].rank;
                    su[i].rank = ++rank;
                }
                inv[su[i].index] = i;
            }

            for (int i = 0; i < N; i++) {
                int nextP = su[i].index + length / 2;
                su[i].next = nextP < N ? su[inv[nextP]].rank : -1;
            }
            Arrays.sort(su); //O(N logN) в среднем
        }
        int[] suf = new int[N];
        for (int i = 0; i < N; i++)
            suf[i] = su[i].index;
        suffixArray = suf;
    }

    //время O(n), n = s.length
    // Легко видеть, что теперь у нас есть алгоритм O(n), потому что на каждом шаге наш lcp уменьшается
    // не более чем на 1 (за исключением случая, когда ранг[i] = n - 1).
    //нам не нужно сравнивать все символы, когда мы вычисляем LCP между суффиксом Si и его соседним
    // суффиксом в массиве Suf−1. Чтобы вычислить LCP всех соседних суффиксов в массиве Suf−1 эффективно,
    // будем рассматривать суффиксы по порядку начиная с sa[0] и заканчивая sa[N-1].
    private void kasai() {
        if(N == 0) {
            lcp = new int[0];
            return;
        }
        lcp = new int[N - 1];
        int[] inv = new int[N];
        for (int i = 0; i < N; i++)
            inv[suffixArray[i]] = i;
        for (int i = 0, len = 0; i < N - 1; i++) {
            if (inv[i] > 0) {
                int ind = inv[i];
                final int k = suffixArray[ind- 1];
                while ((i + len < N) && (k + len < N) && s.charAt(i + len) == s.charAt(k + len))
                    len++;
                lcp[inv[i] - 1] = len;
                if (len > 0)
                    len--;
            }
        }
    }


    private static int binsearch(int rawlo, int rawhi, int lessOrNot, int[] sufAr, String str,String quiery) {
        int lo = rawlo;
        int hi = rawhi;
        int mid;
        int suf;
        while (lo < hi) {
            mid = (hi + lo) / 2;
            suf = sufAr[mid];
            int LEN = (suf + quiery.length() < str.length())? quiery.length() : str.length() - suf;
            int i = str.substring(suf, suf + LEN ).compareTo(quiery);
            if (lessOrNot == -1) {
                if(i < 0) lo = mid + 1;
                else hi = mid;
            } else {
                if(i <= 0) lo = mid + 1;
                else hi = mid;
                }
        }
        return lo;
}

//O(quiery.length * log N) не учитывая построения массивов
    public static int[] findAllMatches(String str, String quiery) {
        if(str.length() == 0 || quiery.length() == 0 || str.length() < quiery.length())
            return new int[]{};
        int[] suffixAArr = new SufArr(str).suffixArray;
        int start;
    int  end;
    start = binsearch(0, suffixAArr.length, -1, suffixAArr, str, quiery);
    end = binsearch(start, suffixAArr.length, 0, suffixAArr, str, quiery);

    Set<Integer> temp = new HashSet<>();
    for(int i = start; i < end; i++)
            temp.add(suffixAArr[i]);
    int[] res = new int[temp.size()];
    int i = 0;
    for(int el : temp)
        res[i++] = el;
    return res;
    }

    //сам алгоритм поиска при известных сусф и лср массивах происходит за линейное от длины lcp время
    public static int  numOfDistinctSubstr(String str) {
        int res = 0;
        if(str.length() != 0) {
            SufArr suffObj = new SufArr("str");
            int[] sa = suffObj.suffixArray;
            int[] lcp = suffObj.lcp;
            if (suffObj.N != 0) {
                res = str.substring(sa[0]).length();
                for (int i = 1; i < sa.length; i++) {
                    res += str.substring(sa[i]).length();
                    res -= lcp[i - 1];
                }
            }
        }
        return res;
    }

    static void printArr(int arr[]) {
        for (int i = 0; i < arr.length; i++)
            System.out.print(arr[i] + " ");
        System.out.println();
    }
}
