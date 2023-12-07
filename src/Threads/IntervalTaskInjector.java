package Threads;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public class IntervalTaskInjector implements Runnable {
    private final BlockingQueue<Task> inputQueue;
    private final List<Task> tasks;

    public IntervalTaskInjector(BlockingQueue<Task> inputQueue, List<Task> tasks) {
        this.inputQueue = inputQueue;
        this.tasks = tasks;
    }

    public void run() {
        try {
            for (Task task : tasks) {
                System.out.format("\nAdding task: %d - Duration: %d ms", task.getId(), task.getRemainingTime());
                inputQueue.put(task);
                 Thread.sleep(500); // add task to input queue at set intervals
            }
            inputQueue.put(SimpleTaskFactory.createSentinelTask());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}
