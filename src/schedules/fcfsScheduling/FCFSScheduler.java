package schedules.fcfsScheduling;


import java.util.concurrent.BlockingQueue;

import runner.Runner;
import tasks.Task;

public class FCFSScheduler extends schedules.AbstractScheduler<Task> {
    private final FCFSTasksContainer fcfsTasksContainer;
    protected final Runner runner;

    public FCFSScheduler(BlockingQueue<Task> taskArrivalQueue){
        super(taskArrivalQueue);
        fcfsTasksContainer = new FCFSTasksContainer();
        new Thread(()-> transferTasksFromArrivalToExecutionQueue(fcfsTasksContainer)).start();
        runner=new Runner();
    }
}
