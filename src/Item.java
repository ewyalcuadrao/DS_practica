import java.time.Duration;
import java.time.LocalDateTime;

public abstract class Item {
  protected Project father;
  protected String name;
  protected LocalDateTime initTime;
  protected LocalDateTime endTime;
  protected Duration totalTime;
  protected boolean active;
  //TODO: Puede ser un arraylist o algo, o un array de strings esta bien???
  protected String[] tag;

  public Item(String name, Project father, String[] tag) {
    assert(name.length() > 0);
    this.father = father;
    this.name = name;
    this.initTime = null;
    this.endTime = null;
    this.totalTime = Duration.ZERO;
    this.active = false;
    this.tag = tag;
    assert(this.name.equals(name));
  }

  public Item(String name, Project father) {
    assert(name.length() > 0);
    this.father = father;
    this.name = name;
    this.initTime = null;
    this.endTime = null;
    this.totalTime = Duration.ZERO;
    this.active = false;
    this.tag = new String[]{};
    assert(this.name.equals(name));
  }

  public Item(String name, Project father, LocalDateTime initTime,
              LocalDateTime endTime, Duration totalTime, boolean active, String[] tag) {
    assert(name.length() > 0);
    this.name = name;
    this.father = father;
    this.initTime = initTime;
    this.endTime = endTime;
    this.totalTime = totalTime;
    this.active = active;
    this.tag = tag;
    assert(this.name.equals(name));
  }

  protected abstract Duration getDurationBetween(LocalDateTime ini, LocalDateTime end);

  public Project getFather() {
    return father;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getInitTime() {
    return initTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public abstract Duration getTotalTime();

  public boolean isActive() {
    return active;
  }

  public void setFather(Project father) {
    this.father = father;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getTag() {
    return tag;
  }

  public void setTag(String[] tag) {
    this.tag = tag;
  }

  public void setInitTime(LocalDateTime initTime) {
    this.initTime = initTime;
  }

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public void setTotalTime(Duration totalTime) {
    this.totalTime = totalTime;
  }

  public void setActive(boolean active) {
    this.active = active;
    if (father != null) {
      this.father.setActive(active);
    }
  }

  public abstract void acceptVisitor(Visitor v);

  public void updateEnd(LocalDateTime end) {
    this.endTime = end;
    if (father != null) {
      father.updateEnd(end);
    }
  }

  public void updateIni(LocalDateTime ini) {
    this.initTime = ini;
    if (father != null && father.getInitTime() == null) {
      father.updateIni(ini);
    }
  }

  public void updateTotalTime(Duration d) {
    totalTime = this.totalTime.plus(d);
    if (father != null) {
      father.updateTotalTime(d);
    }
  }

  protected boolean invariant(){
    return initTime.isBefore(endTime) || initTime.isEqual(endTime);
  }
}