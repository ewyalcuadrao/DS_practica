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
    this.duration = Duration.ZERO;
    this.clock = ClockTimer.getInstance();
    this.clock.addObserver(this);
    this.initTime = clock.getDate();
    this.updateIni(initTime);
    this.endTime = clock.getDate();
    this.updateEnd(endTime);
  }

  public void stopInterval() {
    clock.deleteObserver(this);
  }

  public Duration getInterval() {
    Duration d = Duration.between(this.initTime, this.endTime);
    return d;
  }

  @Override
  public void update(Observable o, Object arg) {
    if(o == clock) {
      //Utilizamos endtime como tiempo actual porque al salir del programa se quedara con el último tiempo
      LocalDateTime dateTime = (LocalDateTime) arg;
      this.updateEnd(endTime);
      this.duration = this.duration.plusSeconds(1);
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
    if(father.getInit() != null)
      this.father.updateEnd(end);
  }

  public void updateIni(LocalDateTime ini){
    this.initTime = ini;
    if(father.getInit() != null)
      father.setInit(ini);
  }
}
