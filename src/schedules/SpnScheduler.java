package schedules;

import Threads.Task;
import runner.Runner;

import java.util.concurrent.BlockingQueue;

public class SpnScheduler implements Runnable {
    private final BlockingQueue<Task> inputTaskQueue;
    private final SpnTasks spnTasks;
    private final Runner runner;
    private boolean allTasksScheduled = false;

    public SpnScheduler(BlockingQueue<Task> inputTaskQueue) {
        this.inputTaskQueue = inputTaskQueue;
        runner = new Runner();
        spnTasks = new SpnTasks();
        new Thread(this::scheduleTasks).start();
    }

    @Override
    public void run() {
        runSchedule();
    }

    private void scheduleTasks() {
        try {
            while (true) {
                Task task = inputTaskQueue.take();
                if (task.getId() == -1) {
                    System.out.println("-----Stop scheduling-----");
                    allTasksScheduled = true;
                    return;
                }
                System.out.println("new task: " + task.getId());
                spnTasks.addTask(task);
                System.out.println("Scheduling task: " + task.getId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void runSchedule() {
        while (true) {
            if (allTasksScheduled && !spnTasks.tasksAvailable()) {
                System.out.println("Scheduler exhausted");
                return;
            }
            for (Task task : spnTasks) {
                System.out.println("received task");
                runner.runTask(task);
            }
        }
    }
}
