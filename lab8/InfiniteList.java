import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.Predicate;

interface InfiniteList<T> {

    public static <T> InfiniteList<T> generate(Supplier<? extends T> s) {
        return InfiniteListImpl.generate(s);
    }

    public static <T> InfiniteList<T> iterate(T seed, Function<T,T> f) {
        return InfiniteListImpl.iterate(seed, f);
    }

    InfiniteList<T> limit(long maxSize);

    void forEach(Consumer<? super T> action);

    Object[] toArray();

    <S> InfiniteList<S> map(Function<? super T, ? extends S> mapper);

    InfiniteList<T> filter(Predicate<? super T> predicate);

    InfiniteList<T> takeWhile(Predicate<? super T> predicate);

    public Optional<T> get();

    long count();

    Optional<T> reduce(BinaryOperator<T> accumulator);

    T reduce(T identity, BinaryOperator<T> accumulator);
}


    
