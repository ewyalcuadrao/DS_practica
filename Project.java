import java.util.*;
import java.time.*;

public class Project extends Item{
  protected ArrayList <Item> item;

  public Project(String name){
    super(name);
    item = new ArrayList<Item>();
  }

  public void createNewTask(String name){
    Task t = new Task(name);
    t.setFather(this);
    item.add(t);
  }

  public void createNewProject(String name){
    Project p = new Project(name);
    p.setFather(this);
    item.add(p);
  }

  public Duration getTotalTime(){
    return null;
  }
}