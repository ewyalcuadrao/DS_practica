import java.util.*;
import java.time.*;
public class Item{
  private int tam;
  private Project father;
  private String name;
  private LocalDateTime init;
  private LocalDateTime end;
  private Duration totalTime;
  private boolean active;

  public Item(String name){
    this.tam = 0;
    this.father = null;
    this.name = name;
    this.init = null;
    this.end = null;
    this.totalTime = null;
    this.active = true;
  }

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

  public Duration getTotalTime() {
    return totalTime;
  }

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
}