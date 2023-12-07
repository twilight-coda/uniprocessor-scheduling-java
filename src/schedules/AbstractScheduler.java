package schedules;

import runner.Runner;
import tasks.Task;

import java.util.concurrent.BlockingQueue;

public abstract class AbstractScheduler implements Runnable {
    protected final BlockingQueue<Task> taskArrivalQueue;
    protected final Runner runner;
    protected boolean allTasksScheduled = false;

    public AbstractScheduler(BlockingQueue<Task> taskArrivalQueue) {
        this.taskArrivalQueue = taskArrivalQueue;
        runner = new Runner();
    }

    @Override
    public abstract void run();

    protected void transferTasksFromArrivalToExecutionQueue(SchedulingTasksContainer<Task> tasksContainer) {
        try {
            while (true) {
                Task task = taskArrivalQueue.take();
                if (task.getId() == -1) {
                    System.out.println("All tasks accepted");
                    allTasksScheduled = true;
                    return;
                }
                tasksContainer.addTask(task);
                System.out.println("Scheduling task: " + task.getId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public abstract void runSchedule();
}
