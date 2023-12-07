package schedules.feedbackScheduling;

import schedules.SchedulingTasksContainer;
import tasks.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FeedbackTasksContainer implements SchedulingTasksContainer<BlockingQueue<Task>> {

    private final BlockingQueue<Task> firstPriorityQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Task> secondPriorityQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Task> thirdPriorityQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Task> fourthPriorityQueue = new LinkedBlockingQueue<>();
    private final FeedbackIterator iterator = new FeedbackIterator(this);

    @Override
    public void addTask(Task task) throws InterruptedException {
        firstPriorityQueue.put(task);
    }

    @Override
    public boolean hasTasksAvailable() {
        return false;
    }

    public void promote() throws InterruptedException {
        BlockingQueue<Task> currentQueue = iterator.currentQueue();
        if (!currentQueue.equals(firstPriorityQueue)) {
            Task task = currentQueue.take();
            BlockingQueue<Task> higherPriorityQueue = iterator.peekPreviousQueue();
            higherPriorityQueue.put(task);
        }
    }

    public void demote() throws InterruptedException {
        BlockingQueue<Task> currentQueue = iterator.currentQueue();
        if (!currentQueue.equals(fourthPriorityQueue)) {
            Task task = currentQueue.take();
            BlockingQueue<Task> lowerPriorityQueue = iterator.peekPreviousQueue();
            lowerPriorityQueue.put(task);
        }
    }

    @Override
    public Iterator<BlockingQueue<Task>> iterator() {
        return iterator;
    }

    class FeedbackIterator implements Iterator<BlockingQueue<Task>> {
        private final ArrayList<BlockingQueue<Task>> listOfQueues = new ArrayList<>(4);
        private int currentQueueIndex = -1;

        public FeedbackIterator(FeedbackTasksContainer taskContainer) {
            listOfQueues.add(taskContainer.firstPriorityQueue);
            listOfQueues.add(taskContainer.secondPriorityQueue);
            listOfQueues.add(taskContainer.thirdPriorityQueue);
            listOfQueues.add(taskContainer.fourthPriorityQueue);
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public BlockingQueue<Task> next() {
            if (currentQueueIndex == listOfQueues.size() - 1) {
                currentQueueIndex = 0;
            } else {
                currentQueueIndex++;
            }
            return listOfQueues.get(currentQueueIndex);
        }

        public BlockingQueue<Task> currentQueue() {
            return listOfQueues.get(currentQueueIndex);
        }

        public BlockingQueue<Task> peekNextQueue() {
            int peekIndex = currentQueueIndex == listOfQueues.size() - 1 ? 0 : currentQueueIndex + 1;
            return listOfQueues.get(peekIndex);
        }

        public BlockingQueue<Task> peekPreviousQueue() {
            int peekIndex = currentQueueIndex > 0 ? currentQueueIndex - 1 : listOfQueues.size() - 1;
            return listOfQueues.get(peekIndex);
        }
    }
}
