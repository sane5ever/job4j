package ru.job4j.queue;

/**
 * Упрощённый код корректной работы производителя-потребителя.
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2018-12-22
 */
public class ParallelSearch {
    public static void main(String[] args) {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        final Thread consumer = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted() || !queue.isEmpty()) {
                try {
                    System.out.println(queue.poll());
                } catch (InterruptedException ie) {
                    System.out.println("Consumer is finishing.");
                    Thread.currentThread().interrupt();
                }
            }
        });
        consumer.start();
        
        final Thread producer = new Thread(() -> {
            for (int i = 0; i < 3; i++) {
                try {
                    queue.offer(i);
                    Thread.sleep(50);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            System.out.println("Producer is finishing.");
            consumer.interrupt();
        });
        producer.start();
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
