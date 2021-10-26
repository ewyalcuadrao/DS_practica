import java.time.*;

public abstract class Item{
  protected int tam;
  protected Project father;
  protected String name;
  protected LocalDateTime initTime;
  protected LocalDateTime endTime;
  protected Duration totalTime;
  protected boolean active;

  public Item(String name){
    this.tam = 0;
    this.father = null;
    this.name = name;
    this.initTime = null;
    this.endTime = null;
    this.totalTime = Duration.ZERO;
    this.active = false;
  }

  protected abstract Duration getDurationBetween(LocalDateTime ini, LocalDateTime end);

  public int getTam() {
    return tam;
  }

  public Project getFather() {
    return father;
  }

  public String getName() {
    return name;
  }

  public LocalDateTime getInitTime() {return initTime;}

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public abstract Duration getTotalTime();

  public boolean isActive() {
    return active;
  }

  public void setTam(int tam) {
    this.tam = tam;
  }

  public void setFather(Project father) {
    this.father = father;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setInitTime(LocalDateTime initTime) {this.initTime = initTime;}

  public void setEndTime(LocalDateTime endTime) {
    this.endTime = endTime;
  }

  public void setTotalTime(Duration totalTime) {
    this.totalTime = totalTime;
  }

  public void setActive(boolean active) {this.active = active;}

  public abstract void acceptVisitor(Visitor v);

  public void updateEnd(LocalDateTime end) {
    this.endTime = end;
    if (father != null)
      father.updateEnd(end);
  }

  public void updateIni(LocalDateTime ini){
    this.initTime = ini;
    if(father != null && father.getInitTime() == null)
      father.updateIni(ini);
  }

  public void updateTotalTime(Duration d){
    totalTime = this.totalTime.plus(d);
    if (father != null)
      father.updateTotalTime(d);
  }
}