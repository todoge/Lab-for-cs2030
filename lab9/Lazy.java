import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Function;

class Lazy<T> { 

    private final Supplier<? extends T> sup;
    private T evaluated;
    private boolean cached;

    private Lazy(Supplier<? extends T> sup) {
        this.sup = sup;
        evaluated = null;
        cached = false;
    }

    // overloaded method for cached evaluated values
    private Lazy(T evaluated) {
        this.evaluated = evaluated;
        sup = () -> evaluated;
        cached = true;
    }

    static <T> Lazy<T> ofNullable(T v) {
         return new Lazy<T>(v);
    }

    static <T> Lazy<T> generate(Supplier<? extends T> supplier) {
        return new Lazy<T>(supplier);
    }

    <R> Lazy<R> map(Function<? super T, ? extends R> mapper) {
        return Lazy.generate( 
            ()-> this.get().map(mapper).orElse(null)
        );
    }

    Lazy<T> filter(Predicate<? super T> predicate) {
        return Lazy.generate( 
            ()-> this.get().filter(predicate).orElse(null)
        );
    }

    Optional<T> get() {
        if (cached) {
            return Optional.ofNullable(evaluated);
        } else {
            Optional<T> result = Optional.ofNullable(sup.get());
            this.evaluated = result.orElse(null);
            this.cached = true;
            return result; 
        }
    }

    @Override 
    public String toString() {
        return cached ? evaluated + "" : "?";
    }

}
