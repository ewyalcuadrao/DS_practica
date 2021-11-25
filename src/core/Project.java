package core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;


public class Project extends Item {
  protected ArrayList<Item> item;
  private static final Logger logger = LoggerFactory.getLogger("Milestone1.Item.Project");

  public Project(String name, Project father, ArrayList<String> tag) {
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
    logger.trace("Method getDurationBetween");
    assert (ini.isBefore(end));
    assert (invariant());
    Duration duration = Duration.ZERO;
    for (int i = 0; i < item.size(); i++) {
      duration = duration.plus(item.get(i).getDurationBetween(ini, end));
    }
    logger.debug("Total duration between " + ini + " and " + end + " is " + duration);
    assert (invariant());
    assert (!duration.isNegative());
    return duration;
  }

  public void addItem(Item it) {
    assert (it != null);
    assert (invariant());
    logger.trace("Method addItem");
    item.add(it);
    assert (invariant());
  }

  @Override
  public Duration getTotalTime() {
    logger.trace("Method getTotalTime");
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
    assert (item != null);
    assert (invariant());
    this.item = item;
    assert (invariant());
  }

  @Override
  public void acceptVisitor(Visitor v) {
    assert (v != null);
    assert (invariant());
    logger.trace("Method acceptVisitor");
    v.visitProject(this);
    assert (invariant());
  }

  @Override
  public void setInitTime(LocalDateTime initTime) {
    logger.trace("Method setInitTime");
    assert (initTime != null);
    assert (invariant());
    this.initTime = initTime;
    if (father != null) {
      if (father.getInitTime() == null) {
        father.setInitTime(LocalDateTime.now());
      }
    }
    assert (invariant());
  }
}
