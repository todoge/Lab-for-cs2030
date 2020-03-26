import java.util.stream.IntStream;
import java.util.function.Function;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;
class Main {

    final static class Pair{
        private final int a;
        private final int b;
        Pair(int a, int b){
            this.a = a;
            this.b = b;
        }

        public int getKey(){
            return a;
        }

        public int getValue(){
            return b;
        }
    }

    static int gcd(int a, int b){
        return Stream.iterate(new Pair(a,b),x->{if(x.getKey() == 0){
                return x;
            } else {
                final Pair temp =  new Pair(x.getValue()%x.getKey(),x.getKey());
                return temp;
            }
        }).filter(x->x.getKey() == 0).findFirst().get().getValue();
    }
}
