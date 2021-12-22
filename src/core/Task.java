package core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;




public class Task extends Item {

  private ArrayList<Interval> interval;
  private static final Logger logger = LoggerFactory.getLogger("Milestone1.Item.Task");

  public Task(String name, Project father) {
    super(name, father);
    this.interval = new ArrayList<Interval>();
    this.father.addItem(this);
  }

  public Task(String name, Project father, ArrayList<String> tag) {
    super(name, father, tag);
    this.interval = new ArrayList<Interval>();
    this.father.addItem(this);
  }

  public void startWorking() {
    logger.trace("Method startWorking " + this.name);
    if (!active) {
      Interval i = new Interval(this);
      active = true;
      father.setActive(true);
      interval.add(i);
      logger.warn(this.name + " starts");
    }
  }

  public void stopWorking() {
    logger.trace("Method stopWorking" + this.name);
    active = false;
    father.setActive(false);
    interval.get(interval.size() - 1).stopInterval();
    this.updateTotalTime(interval.get(interval.size() - 1).getDuration());
    logger.warn(this.name + " stops");
  }

  @Override
  protected Duration getDurationBetween(LocalDateTime ini, LocalDateTime end) {
    logger.trace("Method getDurationBetween");
    assert (ini.isBefore(end));
    assert (invariant());
    Duration duration = Duration.ZERO;
    boolean cond = false;
    for (int i = 0; i < interval.size(); i++) {
      cond = (interval.get(i).getInitTime().isAfter(ini)
          || interval.get(i).getInitTime().isEqual(ini))
          && (interval.get(i).getEndTime().isBefore(end)
          || interval.get(i).getEndTime().isEqual(end));

      if (cond) {
        duration = duration.plus(interval.get(i).getDuration());
      }
    }
    logger.debug("Total duration between " + ini + " and " + end + " is " + duration);
    assert (invariant());
    assert (totalTime.isZero() || totalTime.compareTo(Duration.ZERO) > 0);
    return duration;
  }

  @Override
  public Duration getTotalTime() {
    logger.trace("Method getTotalTime");
    Duration total;
    if (!active) {
      total = this.totalTime;
    } else {
      Duration d = interval.get(interval.size() - 1).getDuration();
      total = this.totalTime.plus(d);
    }
    return total;
  }

  @Override
  public void acceptVisitor(Visitor v) {
    logger.trace("Method acceptVisitor");
    assert (v != null);
    assert (invariant());
    v.visitTask(this);
    assert (invariant());
  }


  public Interval getLastInterval() {
    logger.trace("Method getLastInterval");
    return interval.get(interval.size() - 1);
  }

  public ArrayList<Interval> getIntervals() {
    return interval;
  }

  public void addInterval(Interval i) {
    assert (i != null);
    assert (invariant());
    interval.add(i);
    assert (invariant());
  }

  @Override
  public Item findActivityById(int id) {
    if (this.id == id)
      return this;
    return null;
  }

  @Override
  public JSONObject toJson(int depth) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    JSONObject json = new JSONObject();
    JSONArray jsonArray = new JSONArray();
    json.put("id", this.id);
    json.put("class", "task");
    json.put("name", this.name);
    json.put("father", this.father.getName());
    if (this.initTime != null) {
      json.put("initialDate", formatter.format(this.initTime));
    }
    if (this.endTime != null) {
      json.put("finalDate", formatter.format(this.endTime));
    }
    json.put("duration", this.totalTime.toSeconds());
    json.put("active", this.active);
    for (int i = 0; i < this.interval.size(); i++) {
      jsonArray.put(this.interval.get(i).toJson());
    }
    json.put("intervals", jsonArray);
    return json;
  }
}
