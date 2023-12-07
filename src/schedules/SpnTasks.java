package schedules;

import Threads.Task;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class SpnThreads implements Iterable<Task> {
    private final BlockingQueue<Task> taskQueue = new PriorityBlockingQueue<>(
            10,
            Comparator.comparingInt(Task::getRemainingTime)
    );

    public void addTask(Task task) throws InterruptedException {
        this.taskQueue.put(task);
    }

    public boolean tasksAvailable() {
        return !taskQueue.isEmpty();
    }

    @Override
    public Iterator<Task> iterator() {
        return new SpnIterator(this);
    }

    class SpnIterator implements Iterator<Task> {

        private final SpnThreads threads;

        public SpnIterator(SpnThreads threads) {
            this.threads = threads;
        }

        @Override
        public boolean hasNext() {
            return !this.threads.taskQueue.isEmpty();
        }

        @Override
        public Task next() {
            return taskQueue.poll();
        }
    }

}
