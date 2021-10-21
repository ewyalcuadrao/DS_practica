import java.time.*;
import java.util.*;

public class Interval implements Observer{
  private ClockTimer clock;
  private LocalDateTime initTime;
  private LocalDateTime nowTime;
  private LocalDateTime endTime;

  public Interval() {
    clock = ClockTimer.getInstance();
    clock.addObserver(this);
    this.initTime = nowTime; //mirar si aqui now esta vacio
  }

  public void stopInterval() {
    this.endTime = nowTime;
  }

  public Duration getInterval() {
    Duration d = Duration.between(this.initTime, this.endTime);
    return d;
  }

  @Override
  public void update(Observable o, Object arg) {
    if(o == clock)
      nowTime = (LocalDateTime) arg;
  }

  public LocalDateTime getInitTime() {
    return initTime;
  }

  public LocalDateTime getNowTime() {
    return nowTime;
  }

  public void setNowTime(LocalDateTime nowTime) {
    this.nowTime = nowTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }
}