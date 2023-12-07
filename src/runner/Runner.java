package runner;

import Threads.SchedulingThread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadRunner {
    private final Lock lock = new ReentrantLock();
    private final Condition preemption = lock.newCondition();
    private int timeSlice;
    private bool isTimeSliced;

    ThreadRunner() {}

    ThreadRunner(int timeSlice) {
        this.timeSlice = timeSlice;
    }

    public void run(SchedulingThread thread) {
        lock.lock();
        try {
            while (true) {
                Thread.sleep(timeSlice);
            }
        } catch (InterruptedException ignored) {}
    }
}
