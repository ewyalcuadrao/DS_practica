import java.util.*;
import java.time.*;

public abstract class Item{
  protected int tam;
  protected Project father;
  protected String name;
  protected LocalDateTime init;
  protected LocalDateTime end;
  protected Duration totalTime;
  protected boolean active;

  public Item(String name){
    this.tam = 0;
    this.father = null;
    this.name = name;
    this.init = null;
    this.end = null;
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

  public LocalDateTime getInit() {
    return init;
  }

  public LocalDateTime getEnd() {
    return end;
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

  public void setInit(LocalDateTime init) {
    this.init = init;
  }

  public void setEnd(LocalDateTime end) {
    this.end = end;
  }

  public void setTotalTime(Duration totalTime) {
    this.totalTime = totalTime;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public abstract void acceptVisitor(Visitor v);

  public void updateEnd(LocalDateTime end) {
    this.end = end;
    if (father != null)
      father.updateEnd(end);
  }

  public void updateIni(LocalDateTime ini){
    if(father != null && father.getInit() != null)
      father.setInit(ini);
  }
}