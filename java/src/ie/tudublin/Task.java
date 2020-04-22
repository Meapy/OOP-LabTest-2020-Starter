package ie.tudublin;
import processing.data.TableRow;


public class Task {
    private String Task;
    private int Start;
    private int End;

    public String getTask() {
        return Task;
    }

    public void setTask(String task) {
        Task = task;
    }

    public int getStart() {
        return Start;
    }

    public void setStart(int start) {
        Start = start;
    }

    public int getEnd() {
        return End;
    }

    public void setEnd(int end) {
        End = end;
    }
    public Task(String task, int start, int end){
        this.Task = task;
        this.Start = start;
        this.End = end;
    }
    public Task(TableRow tr){
        this(tr.getString("Task"), tr.getInt("Start"), tr.getInt("End"));
    }
    @Override
    public String toString() {
        return "Name of the task = " + Task + ", Start = " + Start + ", End = " + End;
    }
}
