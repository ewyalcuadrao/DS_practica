import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Project extends Item {
  protected ArrayList<Item> item;

  public Project(String name, Project father, String[] tag) {
    super(name, father, tag);
    if (this.father != null) {
      this.father.addItem(this);
    }
    this.item = new ArrayList<Item>();
  }

  public Project(String name, Project father) {
    super(name, father);
    if (this.father != null) {
      this.father.addItem(this);
    }
    this.item = new ArrayList<Item>();
  }

  @Override
  protected Duration getDurationBetween(LocalDateTime ini, LocalDateTime end) {
    assert(ini.isBefore(end));
    assert(invariant());
    Duration duration = Duration.ZERO;
    for (int i = 0; i < item.size(); i++) {
      duration = duration.plus(item.get(i).getDurationBetween(ini, end));
    }
    assert(invariant());
    assert(duration.compareTo(duration.ZERO) == 1 || duration.compareTo(duration.ZERO) == 0);
    return duration;
  }

  public void addItem(Item it) {
    item.add(it);
  }

  @Override
  public Duration getTotalTime() {
    this.totalTime = Duration.ZERO;
    for (int i = 0; i < item.size(); i++) {
      this.totalTime = this.totalTime.plus(item.get(i).getTotalTime());
    }
    return this.totalTime;
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
    assert(invariant());
    this.initTime = initTime;
    if (father != null) {
      if (father.getInitTime() == null) {
        father.setInitTime(LocalDateTime.now());
      }
    }
    assert(invariant());
  }
}
