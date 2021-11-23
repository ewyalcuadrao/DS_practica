import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Task extends Item {

  private ArrayList<Interval> interval;

  public Task(String name, Project father) {
    super(name, father);
    this.interval = new ArrayList<Interval>();
    this.father.addItem(this);
  }

  public Task(String name, Project father, String[] tag) {
    super(name, father, tag);
    this.interval = new ArrayList<Interval>();
    this.father.addItem(this);
  }

  protected void startWorking() {
    assert(invariant());
    if (!active) {
      Interval i = new Interval(this);
      active = true;
      father.setActive(true);
      interval.add(i);
      System.out.println(this.name + " starts");
    }
    assert(invariant());
    assert(!interval.isEmpty());
    assert(active);
  }

  public void stopWorking() {
    assert(invariant());
    active = false;
    father.setActive(false);
    interval.get(interval.size() - 1).stopInterval();
    this.updateTotalTime(interval.get(interval.size() - 1).getDuration());
    System.out.println(this.name + " stops");
    assert(invariant());
    assert (!active);
    assert(totalTime.isZero() || totalTime.compareTo(Duration.ZERO) > 0);
  }

  @Override
  protected Duration getDurationBetween(LocalDateTime ini, LocalDateTime end) {
    assert(ini.isBefore(end));
    assert(invariant());
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
    assert (invariant());
    assert(totalTime.isZero() || totalTime.compareTo(Duration.ZERO) > 0);
    return duration;
  }

  @Override
  public Duration getTotalTime() {
    assert(invariant());
    Duration total;
    if (!active) {
      total = this.totalTime;
    } else {
      Duration d = interval.get(interval.size() - 1).getDuration();
      total =  this.totalTime.plus(d);
    }
    assert(invariant());
    assert(total.isZero() || total.compareTo(Duration.ZERO) > 0);
    return total;
  }

  @Override
  public void acceptVisitor(Visitor v) {
    v.visitTask(this);
  }


  public Interval getLastInterval() {
    assert(!interval.isEmpty());
    assert(invariant());
    return interval.get(interval.size() - 1);
  }

  public ArrayList<Interval> getIntervals() {
    return interval;
  }

  public void addInterval(Interval i) {
    interval.add(i);
  }
}
