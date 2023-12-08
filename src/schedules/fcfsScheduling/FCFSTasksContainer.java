package schedules.fcfsScheduling;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.*;
import schedules.SchedulingTasksContainer;
import tasks.Task;

public class FCFSTasksContainer implements SchedulingTasksContainer<Task> {
    private final BlockingQueue<Task> TaskQueue = new LinkedBlockingQueue<>();

    public void addTask(Task task) throws InterruptedException 
    {
        this.TaskQueue.put(task);
    }

    public boolean hasTasksAvailable()
    {
        return !TaskQueue.isEmpty();
    }
    
}
