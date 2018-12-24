package ru.job4j.cache;

import org.junit.Test;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
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
    private final List<Base> buffer = new CopyOnWriteArrayList<>();

    @Test
    public void whenTwoThreadParallelUpdateSameModels() throws InterruptedException {
        IntStream.range(0, 5)
                .filter(i -> i % 2 == 0)
                .mapToObj(Base::new)
                .forEach(cache::add);
        List<AtomicReference<Exception>> references = new CopyOnWriteArrayList<>();
        act(base -> {
            Base result = null;
            try {
                result = cache.update(base);
            } catch (Exception e) {
                references.add(new AtomicReference<>(e));
            }
            return result;
        });

        assertThat(buffer.size(), is(10));
        assertThat(
                (int) buffer.stream().filter(Objects::nonNull).count(),
                is(3)   // 0, 2, 4 — first time
        );
        assertThat(references.size(), is(3));   // 0, 2, 4 — second time
        assertTrue(
                references.stream()
                        .map(ref -> ref.get().getClass())
                        .allMatch(clazz -> clazz == OptimisticException.class)
        );
    }

    @Test
    public void whenTwoThreadParallelAddSameModels() throws InterruptedException {
        act(cache::add);
        assertThat(buffer.size(), is(10));
        assertThat(
                (int) buffer.stream().filter(Objects::isNull).count(),
                is(5)
        );
    }

    @Test
    public void whenTwoThreadParallelRemoveSameModels() throws InterruptedException {
        IntStream.range(0, 5).mapToObj(Base::new).forEach(cache::add);
        act(cache::delete);
        assertThat(buffer.size(), is(10));
        assertThat(
                (int) buffer.stream().filter(Objects::nonNull).count(),
                is(5)
        );
    }

    private void act(Function<Base, Base> operation) throws InterruptedException {
        Runnable task = () ->
                IntStream.range(0, 5)
                        .mapToObj(Base::new)
                        .forEach(base -> buffer.add(operation.apply(base)));
        Thread first = new Thread(task);
        Thread second = new Thread(task);
        first.start();
        second.start();
        first.join();
        second.join();
    }

}
