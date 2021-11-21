import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Project extends Item {
  protected ArrayList<Item> item;

  public Project(String name, Project father) {
    super(name, father);
    if (this.father != null) {
      this.father.addItem(this);
    }
    this.item = new ArrayList<Item>();
  }

  @Override
  protected Duration getDurationBetween(LocalDateTime ini, LocalDateTime end) {
    Duration duration = Duration.ZERO;
    for (int i = 0; i < item.size(); i++) {
      duration.plus(item.get(i).getDurationBetween(ini, end));
    }
    return duration;
  }

  public void addItem(Item it) {
    item.add(it);
  }

  public void createNewTask(String name) {
    Task t = new Task(name, this);
    item.add(t);
  }

  @Override
  public Duration getTotalTime() {
    this.totalTime = Duration.ZERO;
    for (int i = 0; i < item.size(); i++) {
      this.totalTime = this.totalTime.plus(item.get(i).getTotalTime());
    }
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

  @Override
  public void acceptVisitor(Visitor v) {
    v.visitProject(this);
  }

  @Override
  public void setInitTime(LocalDateTime initTime) {
    this.initTime = initTime;
    if (father != null) {
      if (father.getInitTime() == null) {
        father.setInitTime(LocalDateTime.now());
      }
    }
  }
}
