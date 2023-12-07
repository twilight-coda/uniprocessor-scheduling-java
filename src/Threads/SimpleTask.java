package Threads;

import runner.ThreadRunner;

public class SimpleThread implements SchedulingThread {

    private final ThreadRunner runner;

    SimpleThread(ThreadRunner runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        runner.run(this);
    }

    @Override
    public int getRemainingTime() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }
}
