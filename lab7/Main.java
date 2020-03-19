import java.util.stream.IntStream;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
class Main {

    private static boolean isPrime(int x) { 
        return IntStream.range(2,(int) Math.sqrt(x) + 1).noneMatch(y->x%y==0);  
    }

    private static boolean isTwinPrimeNumber(int x){
        return x > 2 
            ? isPrime(x) ? (isPrime(x-2) || isPrime(x+2)) : false 
            : false;
    }

    static int[] twinPrimes(int n){
        IntStream s = IntStream.rangeClosed(0,n).filter(x->isTwinPrimeNumber(x));
        return s.toArray();
    }

    static int gcd(int a, int b){
        Stream<Integer> s = Stream.of(b);
        return s.reduce(a,(x,y)->(x==0) ? y : gcd(y%x,x));
    }

    static long countRepeats(int... array){
       int len = array.length;
       int[] arr = new int[9];
       final int[] count = new int[1];
       IntStream.range(1,len-1).forEach(x->{
            if(array[x] == array[x-1] && array[x] != array[x+1]){
                count[0]++;
            }
       });   
       if(len >= 2 && array[len-1] == array[len-2]){
           count[0]++;
       }
       return count[0];
    }

    static double normalizedMean(Stream<Integer> stream){
        double[] trackers = new double[4];
        stream.forEach(x->{
            trackers[0] = trackers[0] + x;
            trackers[1]++;
            if(trackers[1] == 1){
                trackers[2] = x;
                trackers[3] = x;
            } else {
                trackers[2] = Math.min(trackers[2],x);
                trackers[3] = Math.max(trackers[3],x);
            }
        });
        double average = trackers[0]/trackers[1];
        return trackers[1] == 0 || (trackers[3]-trackers[2]) == 0
                ? 0 
                : ((average-trackers[2])/(trackers[3]-trackers[2]));
    }
}

