import java.util.*;
import java.time.*;

public class Project extends Item {
  protected ArrayList<Item> item;

  @Override
  public Duration getDurationBetween(LocalDateTime ini, LocalDateTime end) {
    Duration d = Duration.ZERO;
    for (int i = 0; i < item.size(); i++) {
      d.plus(item.get(i).getDurationBetween(ini, end));
    }
    return d;
  }

  public Project(String name) {
    super(name);
    item = new ArrayList<Item>();
  }

  public void createNewTask(String name) {
    Task t = new Task(name, this);
    item.add(t);
  }
  public Duration getTotalTime(){
    return this.totalTime;
  }
  public void createNewProject(String name) {
    Project p = new Project(name);
    p.setFather(this);
    item.add(p);
  }
}