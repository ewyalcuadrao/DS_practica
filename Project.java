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
  public Project(String name, Project p) {
    super(name);
    this.father=p;
    p.addTask(this);
    item = new ArrayList<Item>();
  }
  public void addTask(Item task){
    item.add(task);
  }
  public void createNewTask(String name) {
    Task t = new Task(name, this);
    item.add(t);
  }
  public Duration getTotalTime(){
    return this.totalTime;
  }

  public void createNewProject(String name) {
    Project p = new Project(name, this);
    item.add(p);
  }

  public ArrayList<Item> getItem() {
    return item;
  }

  public void setItem(ArrayList<Item> item) {
    this.item = item;
  }

  public Item getSonItem(String name){
    return null;
  }
}
