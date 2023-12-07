package schedules.spnScheduling;

import tasks.Task;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

public class SpnTasksContainer implements Iterable<Task> {
    private final BlockingQueue<Task> taskExecutionQueue = new PriorityBlockingQueue<>(
            10,
            Comparator.comparingInt(Task::getRemainingTime)
    );

    public void addTask(Task task) throws InterruptedException {
        this.taskExecutionQueue.put(task);
    }

    public boolean tasksAvailable() {
        return !taskExecutionQueue.isEmpty();
    }

    @Override
    public Iterator<Task> iterator() {
        return new SpnIterator(this);
    }

    class SpnIterator implements Iterator<Task> {

        private final SpnTasksContainer threads;

        public SpnIterator(SpnTasksContainer threads) {
            this.threads = threads;
        }

        @Override
        public boolean hasNext() {
            return !this.threads.taskExecutionQueue.isEmpty();
        }

        @Override
        public Task next() {
            return taskExecutionQueue.poll();
        }
    }

}
