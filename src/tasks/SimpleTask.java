package tasks;

public class SimpleTask implements Task {

    public int id;
    public int duration;

    public SimpleTask(int id, int duration) {
        this.id = id;
        this.duration = duration;
    }

    @Override
    public void run() {
        System.out.println("Running task: " + id + " - time: " + duration);
    }

    @Override
    public int getRemainingTime() {
        return duration;
    }

    @Override
    public void setRemainingTime(int duration) {
        this.duration = duration;
    }

    @Override
    public int getId() {
        return id;
    }
}
