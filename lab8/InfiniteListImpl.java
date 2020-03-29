import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.Function;
import java.util.function.Consumer;
import java.util.function.BinaryOperator;
import java.util.ArrayList;
import java.util.function.Predicate;
import java.lang.IllegalArgumentException;

abstract class InfiniteListImpl<T> implements InfiniteList<T> {

    public static <T> InfiniteListImpl<T> generate(Supplier<? extends T> s) {
        return new InfiniteListImpl<T>() {
            public Optional<T> get() {
                return Optional.of(s.get());
            }
        };
    }

    public static <T> InfiniteListImpl<T> iterate(T seed, Function<T,T> f) {
        return new InfiniteListImpl<T>() {
            boolean first = true;
            T element = seed;

            public Optional<T> get() {
                if (first) {
                    first = false;
                } else {
                    element = f.apply(element);
                }
                return Optional.of(element);
            }
        };
    }

    public InfiniteListImpl<T> limit(long maxSize) throws IllegalArgumentException {
        if (maxSize < 0) {
            throw new IllegalArgumentException("" + maxSize);
        } else {
            return new InfiniteListImpl<T>() {
                long size = maxSize;

                public Optional<T> get() {
                    if (size <= 0) {
                        return Optional.empty();
                    } else {
                        size = size - 1;
                        return InfiniteListImpl.this.get();
                    }
                }
            };
        }
    }

    public void forEach(Consumer<? super T> action) {
        Optional<T> curr = get();
        while (curr.isPresent()) {
            action.accept(curr.get());
            curr = get();
        }
    }

    public Object[] toArray() {
        ArrayList<T> temp = new ArrayList<>();
        this.forEach(x -> {
            temp.add(x);
        });
        return temp.toArray();
    }

    public <S> InfiniteListImpl<S> map(Function<? super T, ? extends S> mapper) {
        return new InfiniteListImpl<S>() {

            public Optional<S> get() {
                return InfiniteListImpl.this.get().map(mapper);
            }
        };
    }

    public InfiniteListImpl<T> filter(Predicate<? super T> predicate) {
        return new InfiniteListImpl<T>() {
            public Optional<T> get() {
                Optional<T> element = InfiniteListImpl.this.get();
                while (element.isPresent()) {
                    if (element.filter(predicate).isEmpty()) {
                        element = InfiniteListImpl.this.get();
                    } else {
                        break;
                    }
                }
                return element;
            }
        };
    }

    public InfiniteList<T> takeWhile(Predicate<? super T> predicate) {
        return new InfiniteListImpl<T>() {
            public Optional<T> get() {
                return InfiniteListImpl.this.get().filter(predicate);
            }
        };
    }

    public long count() {
        long count = 0;
        while (get().isPresent()) {
            count++;
        }
        return count;
    }


    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        Optional<T> result = get();
        Optional<T> element = get();
        while (element.isPresent()) {
            result = Optional.of(accumulator.apply(result.get(),element.get()));
            element = get();
        }
        return result;
    }

    public T reduce(T identity, BinaryOperator<T> accumulator) {
        T result = identity;
        Optional<T> element = get();
        while (element.isPresent()) {
            result = accumulator.apply(result,element.get());
            element = get();
        }
        return result;
    }

    public abstract Optional<T> get();
}
