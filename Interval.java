import java.time.*;
import java.util.*;


public class Interval implements Observer{
  private ClockTimer clock;
  private LocalDateTime initTime;
  private Duration duration;
  private LocalDateTime endTime;
  private Task father;

  public Interval(Task father) {
    this.father = father;
    this.duration = Duration.ZERO.plusSeconds(ClockTimer.getPeriode());
    this.clock = ClockTimer.getInstance();
    this.clock.addObserver(this);
    this.initTime = LocalDateTime.now().minusSeconds(ClockTimer.getPeriode());
    this.updateIni(initTime);
    this.endTime = LocalDateTime.now();
    this.updateEnd(endTime);
  }

  public Interval(Interval i){
    this.father = i.getFather();
    this.initTime = i.getInitTime();
    this.endTime = i.getEndTime();
    this.duration = i.getDuration();
    this.clock = ClockTimer.getInstance();
    this.clock.addObserver(this);
  }

  public void stopInterval() {
    clock.deleteObserver(this);
  }

  public Duration getDuration() {
    return duration;
  }

  @Override
  public void update(Observable o, Object arg) {
    if(o == clock) {
      //Utilizamos endtime como tiempo actual porque al salir del programa se quedara con el último tiempo
      LocalDateTime dateTime = (LocalDateTime) arg;
      this.updateEnd(dateTime);
      this.duration = this.duration.plusSeconds(ClockTimer.getPeriode());
    }
  }

  public LocalDateTime getInitTime() {
    return initTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void acceptVisitor(Visitor v){
    v.visitInterval(this);
  }

  public Task getFather() {return this.father;}

  public void updateEnd(LocalDateTime end) {
    this.endTime = end;
    this.father.updateEnd(end);
  }

  public void updateIni(LocalDateTime ini){
    this.initTime = ini;
    if(father.getInitTime() == null) {
      father.updateIni(ini);
    }
  }

}
