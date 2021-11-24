import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Interval implements Observer {
  private ClockTimer clock;
  private LocalDateTime initTime;
  private Duration duration;
  private LocalDateTime endTime;
  private Task father;
  private static Logger logger = LoggerFactory.getLogger("Milestone1.Interval");

  public Interval(Task father) {
    this.father = father;
    this.clock = ClockTimer.getInstance();
    this.duration = Duration.ZERO.plusSeconds(ClockTimer.getPeriode());
    this.clock.addObserver(this);
    this.initTime = LocalDateTime.now().minusSeconds(ClockTimer.getPeriode());
    this.updateIni(initTime);
    this.endTime = LocalDateTime.now();
    this.updateEnd(endTime);
  }

  public void stopInterval() {
    logger.trace("Method stopInterval");
    clock.deleteObserver(this);
  }

  public Duration getDuration() {
    return duration;
  }

  @Override
  public void update(Observable o, Object arg) {
    logger.trace("Method update");
    //endtime sirve como tiempo actual para que al salir del programa se quede con el último tiempo
    LocalDateTime dateTime = (LocalDateTime) arg;
    this.updateEnd(dateTime);
    this.duration = this.duration.plusSeconds(ClockTimer.getPeriode());
  }

  public LocalDateTime getInitTime() {
    return initTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }

  public void acceptVisitor(Visitor v) {
    logger.trace("Method acceptVisitor");
    v.visitInterval(this);
  }

  public Task getFather() {
    return this.father;
  }

  public void updateEnd(LocalDateTime end) {
    logger.trace("Method updateEnd");
    this.endTime = end;
    this.father.updateEnd(end);
  }

  public void updateIni(LocalDateTime ini) {
    logger.trace("Method updateIni");
    this.initTime = ini;
    if (father.getInitTime() == null) {
      father.updateIni(ini);
    }
  }

  public void setInitTime(LocalDateTime ini) {
    this.initTime = ini;
  }

  public void setEndTime(LocalDateTime end) {
    this.endTime = end;
  }

  public void setDuration(Duration d) {
    this.duration = d;
  }

}
