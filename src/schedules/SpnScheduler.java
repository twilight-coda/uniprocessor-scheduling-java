package schedules;

import Threads.SchedulingTask;
import runner.Runner;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class FCFSScheduler implements Runnable {
    private final BlockingQueue<SchedulingTask> inputTaskQueue;
    private final FCFSThreads fcfsTasks;
    private final Runner runner;
    private final Lock lock = new ReentrantLock();
    private final Condition taskAdded = lock.newCondition();
    private boolean running = true;

    public FCFSScheduler(BlockingQueue<SchedulingTask> inputTaskQueue) {
        this.inputTaskQueue = inputTaskQueue;
        runner = new Runner();
        fcfsTasks = new FCFSThreads();
    }

    public void addTask(SchedulingTask task) {
        lock.lock();
    }

    public void runSchedule() {
        while (true) {
            lock.lock();
            try {
                while (!fcfsTasks.iterator().hasNext()) {
                    if (!running) {
                        return;
                    }
                    taskAdded.await();
                }
                if (fcfsTasks.iterator().hasNext()) {
                    SchedulingTask task = fcfsTasks.iterator().next();
                    runner.runTask(task);
                }
            } catch (InterruptedException ignored) {}
            finally {
                lock.unlock();
            }
        }
    }

    @Override
    public void run() {
        scheduleTasks();
    }

    private void scheduleTasks() {
        try {
            while (running) {
                SchedulingTask task = inputTaskQueue.take();
                lock.lock();
                try {
                    fcfsTasks.addTask(task);
                    taskAdded.signal();
                } finally {
                    lock.unlock();
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void stopSchedule() {
        running = false;
        lock.lock();
        try {
            taskAdded.signalAll();
        } finally {
            lock.unlock();
        }
    }
}
