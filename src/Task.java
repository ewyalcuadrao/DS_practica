import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
public class Task extends Item{

  private ArrayList <Interval> interval;
  private int i;
  private int j;

  public Task(String name, Project father)
  {
    super(name);
    this.father = father;
    interval = new ArrayList();
    i=0;
    j=0;
  }

  protected void startWorking()
  {
    Interval i = new Interval();
    if(interval.size() == 0)
      this.init = i.getInitTime();
    this.active = true;
    interval.add(i);
  }
  public void stopWorking()
  {
    this.active = false;
    interval.get(interval.size()-1).stopInterval();
    this.end = interval.get(interval.size()-1).getEndTime();
    this.totalTime = this.totalTime.plus(interval.get(interval.size()-1).getInterval());
    father.setTotalTime(father.totalTime.plus(interval.get(interval.size()-1).getInterval()));
  }
  private boolean dateBetween(LocalDateTime ini, LocalDateTime end)
  {
    while(i<interval.size()) {
      if ((interval.get(i).getInitTime().isAfter(ini) && !interval.get(i).getInitTime().isBefore(end)) || interval.get(i).getInitTime().isEqual(ini))
        return true; //comprobar si el intervalo empieza cuando nos marcan o entre el inicio y el final indiciado
      else
        i++;
    }
    return false;
  }
  private boolean dateEnd(LocalDateTime end) { //comprobar si hay un intervalo que acabe mas tarde o igual que la fecha indicada
    int j = i;
    while (j < interval.size()) {
      if (interval.get(i).getEndTime().isAfter(end)) {
        j = j - 1;
        return true;
      } else {
        if (interval.get(i).getEndTime().isEqual(end)) {
          return true;
        } else {
          j++;
        }
      }
    }
    return false;
  }

  private Duration sumaIntervalos(boolean enc, Duration d)
  {
    if(enc)
    {
      while(i<=j)
      {
        d = d.plus(interval.get(i).getInterval());
      }
      return d;
    }
    else
    {
      while(i<interval.size())
      {
        d = d.plus(interval.get(i).getInterval());
      }
      return d;
    }
  }

  @Override
  protected Duration getDurationBetween(LocalDateTime ini, LocalDateTime end)
  {
    int i=0;
    boolean trob = dateBetween(ini, end);
    Duration d = Duration.ZERO;
    if(trob){
      boolean enc = dateEnd(end);
      return sumaIntervalos(enc, d);
    }
    else{
      return d;
    }
  }

  @Override
  public Duration getTotalTime()
  {
    if(!active)
      return this.totalTime;
    else
    {
      Duration d = Duration.between( interval.get(interval.size()-1).getInitTime(), interval.get(interval.size()-1).getNowTime());
      this.totalTime = this.totalTime.plus(d);
      this.father.setTotalTime(this.father.totalTime.plus(d));
      return this.totalTime;
     }
  }

}