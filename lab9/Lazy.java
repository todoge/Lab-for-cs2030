import java.util.function.Supplier;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Function;

class Lazy<T> { 

    private final Supplier<Optional<T>> head;
    private final Supplier<Lazy<T>> tail;

    private Lazy(Supplier<Optional<T>> head, Supplier<Lazy<T>> tail) {
        this.head = head;
        this.tail = tail;
    }

    static <T> Lazy<T> ofNullable(T v) {
         return new Lazy<T>(() -> Optional.ofNullable(v),null);
    }

    static <T> Lazy<T> generate(Supplier<? extends T> supplier) {
        return new Lazy<T>(
            () -> Optional.ofNullable(supplier.get()),
            () -> Lazy.generate(supplier)
        );
    }

    <R> Lazy<R> map(Function<? super T, ? extends R> mapper) {
        return new Lazy<R>( 
            ()-> head.get().map(mapper),
            ()->tail.get().map(mapper)
        );
    }

    Lazy<T> filter(Predicate<? super T> predicate) {
        return new Lazy<T>( 
            ()->head.get().filter(predicate),
            ()-> tail.get().filter(predicate)
        );
    }

    Optional<T> get() {
        return head.get();
    }

    @Override 
    public String toString() {
        return tail == null 
            ? head.get().isEmpty() ? "null" : head.get() + "" 
            : "?";
    }

}
