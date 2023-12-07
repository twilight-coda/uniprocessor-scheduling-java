package Threads;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class TasksProducer {
        private final BlockingQueue<Integer> outputQueue;

        private boolean running = true;

        public TasksProducer(BlockingQueue<Integer> outputQueue) {
            this.outputQueue = outputQueue;
        }

        public void run() {
            try {
                while (running) {
                    int item = produce();
                    outputQueue.put(item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        public void stopProducer() {
            this.running = false;
        }

        private int produce() {
            // Produce an item...
            return new Random().nextInt();
        }
}
