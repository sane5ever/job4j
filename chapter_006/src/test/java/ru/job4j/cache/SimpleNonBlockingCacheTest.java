package ru.job4j.cache;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Неблокирующий кэш. Тестирование.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-23
 */
public class SimpleNonBlockingCacheTest {
    private final SimpleNonBlockingCache cache = new SimpleNonBlockingCache();

    private final List<Boolean> buffer = new CopyOnWriteArrayList<>();

    @Test
    public void whenUpdate() throws InterruptedException {
        IntStream.range(0, 5)
                .filter(i -> i % 2 == 0)
                .mapToObj(Base::new).forEach(cache::add);
        List<AtomicReference<Exception>> references = new CopyOnWriteArrayList<>();
        act(base -> {
            boolean result = false;
            try {
                result = cache.update(base);
            } catch (Exception e) {
                references.add(new AtomicReference<>(e));
            }
            return result;
        });

        assertThat(buffer.size(), is(10));
        assertThat(
                (int) buffer.stream().filter(v -> v).count(),
                is(3)   // 0, 2, 4 — first time
        );
        assertThat(references.size(), is(3));   // 0, 2, 4 — second time
        assertTrue(
                references.stream()
                        .map(ref -> ref.get().getClass().getSimpleName())
                        .allMatch("OptimisticException"::equals)
        );
    }

    @Test
    public void whenTwoThreadParallelAddSameModels() throws InterruptedException {
        act(cache::add);
        assertThat(buffer.size(), is(10));
        assertThat(
                (int) buffer.stream().filter(v -> v).count(),
                is(5)
        );
    }

    @Test
    public void whenTwoThreadParallelRemoveSameModels() throws InterruptedException {
        IntStream.range(0, 5).mapToObj(Base::new).forEach(cache::add);
        act(cache::delete);
        assertThat(buffer.size(), is(10));
        assertThat(
                (int) buffer.stream().filter(v -> v).count(),
                is(5)
        );
    }

    private void act(Predicate<Base> operation) throws InterruptedException {
        Runnable runnable = () ->
                IntStream.range(0, 5)
                        .mapToObj(Base::new)
                        .forEach(base -> buffer.add(operation.test(base)));
        Thread first = new Thread(runnable);
        Thread second = new Thread(runnable);
        first.start();
        second.start();
        first.join();
        second.join();
    }

}
