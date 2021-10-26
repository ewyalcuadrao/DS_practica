import java.util.*;
import java.time.*;

public class Project extends Item {
  protected ArrayList<Item> item;

  public Project(String name) {
    super(name);
    item = new ArrayList<Item>();
  }

  public Project(String name, Project father) {
    super(name);
    this.father=father;
    this.father.addTask(this);
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

  public void addTask(Item task){
    item.add(task);
  }

  public void createNewTask(String name) {
    Task t = new Task(name, this);
    item.add(t);
  }
  public Duration getTotalTime(){
    this.totalTime = Duration.ZERO;
    for (int i = 0; i < item.size(); i++) {
      this.totalTime=this.totalTime.plus(item.get(i).getTotalTime());
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

  public Item getSonItem(String name){
    return null;
  }

  @Override
  public void acceptVisitor(Visitor v){
    v.visitProject(this);
    for (int i = 0; i < item.size(); i++){
      item.get(i).acceptVisitor(v);
    }
  }

  @Override
  public void setInit(LocalDateTime init) {
    this.init = init;
    if(father!=null)
      if(father.getInit()==null)
        father.setInit(LocalDateTime.now());
  }
}
