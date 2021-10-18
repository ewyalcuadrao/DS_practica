import java.time.*;
import java.util.*;

public class Interval implements Observer{
  private LocalDateTime initTime;
  private LocalDateTime endTime;

  public Interval(LocalDateTime ini)
  {
    this.initTime = ini;
  }

  public void stopInterval(LocalDateTime end)
  {
    this.endTime = end;
  }

  public Duration getInterval()
  {
    Duration d = Duration.between(this.initTime, this.endTime);
    return d;
  }
  @Override
  public void update(Observable o, Object arg) {

  }

  public LocalDateTime getInitTime() {
    return initTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }
}