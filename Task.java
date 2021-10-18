import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
public class Task extends Item{

  private ArrayList <Interval> interval;

  public Task(String name, Project father)
  {
    super(name);
    this.father = father;
    interval = new ArrayList();
  }

  protected void startWorking()
  {
    if(interval == null)
      this.init = LocalDateTime.now();
    LocalDateTime time = LocalDateTime.now();
    Interval i = new Interval(time);
    this.active = true;
    interval.add(i);
  }
  public void stopWorking()
  {
    LocalDateTime time = LocalDateTime.now();
    this.end = LocalDateTime.now();
    this.active = false;
    interval.get(interval.size()-1).stopInterval(time);
    this.totalTime = this.totalTime.plus(interval.get(interval.size()-1).getInterval());
    father.setTotalTime(father.totalTime.plus(interval.get(interval.size()-1).getInterval()));

  }

  @Override
  protected Duration getDurationBetween(LocalDateTime ini, LocalDateTime end)
  {
    int i=0;
    boolean trob = false;
    Duration d= Duration.ZERO;
    while( i<interval.size() && !trob)
    {
      if((interval.get(i).getInitTime().isAfter(ini)  && !interval.get(i).getInitTime().isAfter(end))|| interval.get(i).getInitTime().isEqual(ini) ){
        trob = true;
      }
      else {
        i++;
      }
    }
    if(trob)
    {
      int j=i;
      boolean enc = false;
      while(j<interval.size() && !enc)
      {
        if(interval.get(i).getEndTime().isAfter(end))
        {
          j=j-1;
          enc=true;
        }
        else
        {
         if(interval.get(i).getEndTime().isEqual(end))
         {
           enc=true;
         }
         else {
           j++;
         }
        }
      }
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
    else{
      return d;
    }
  }

  @Override
  public Duration getTotalTime()
  {
    if(active == false)
      return this.totalTime;
    else
    {
      Duration d = Duration.between( interval.get(interval.size()-1).getInitTime(), LocalDateTime.now());
      this.totalTime = this.totalTime.plus(d);
      this.father.setTotalTime(this.father.totalTime.plus(d));
      return this.totalTime;
    }
  }

}