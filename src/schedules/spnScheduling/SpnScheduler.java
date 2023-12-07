package schedules.spnScheduling;

import tasks.Task;
import runner.Runner;

import java.util.concurrent.BlockingQueue;

public class SpnScheduler implements Runnable {
    private final BlockingQueue<Task> taskArrivalQueue;
    private final SpnTasksContainer spnTasksContainer;
    private final Runner runner;
    private boolean allTasksScheduled = false;

    public SpnScheduler(BlockingQueue<Task> taskArrivalQueue) {
        this.taskArrivalQueue = taskArrivalQueue;
        runner = new Runner();
        spnTasksContainer = new SpnTasksContainer();
        new Thread(this::scheduleTasks).start();
    }

    @Override
    public void run() {
        runSchedule();
    }

    private void scheduleTasks() {
        try {
            while (true) {
                Task task = taskArrivalQueue.take();
                if (task.getId() == -1) {
                    System.out.println("-----Stop scheduling-----");
                    allTasksScheduled = true;
                    return;
                }
                System.out.println("new task: " + task.getId());
                spnTasksContainer.addTask(task);
                System.out.println("Scheduling task: " + task.getId());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void runSchedule() {
        while (true) {
            if (allTasksScheduled && !spnTasksContainer.tasksAvailable()) {
                System.out.println("Scheduler exhausted");
                return;
            }
            for (Task task : spnTasksContainer) {
                System.out.println("received task");
                runner.runTask(task);
            }
        }
    }
}
