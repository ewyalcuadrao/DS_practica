package core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;
import java.time.temporal.ChronoUnit;


public class Interval implements Observer {
  private final ClockTimer clock;
  private LocalDateTime initTime;
  private Duration duration;
  private LocalDateTime endTime;
  private final Task father;
  private final int id;
  private boolean active;

  private static final Logger logger = LoggerFactory.getLogger("Milestone1.Interval");

  public Interval(Task father) {
    this.id = Id.getInstance().getId();
    this.father = father;
    this.clock = ClockTimer.getInstance();
    this.duration = Duration.ZERO.plusSeconds(ClockTimer.getPeriode());
    this.clock.addObserver(this);
    this.initTime = LocalDateTime.now().minusSeconds(ClockTimer.getPeriode());
    this.initTime= this.initTime.truncatedTo(ChronoUnit.SECONDS);
    this.updateIni(initTime);
    this.endTime = LocalDateTime.now();
    this.endTime= this.endTime.truncatedTo(ChronoUnit.SECONDS);
    this.updateEnd(endTime);
    this.active=true;
  }

  public void stopInterval() {
    logger.trace("Method stopInterval");
    clock.deleteObserver(this);
    this.active = false;
  }

  public Duration getDuration() {
    return duration;
  }

  @Override
  public void update(Observable o, Object arg) {
    logger.trace("Method update");
    //endtime sirve como tiempo actual para que al salir del programa se quede con el último tiempo
    LocalDateTime dateTime = (LocalDateTime) arg;
    logger.debug("Update endtime interval to " + endTime);
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

  public boolean getActive() { return this.active; }

  public JSONObject toJson() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    JSONObject json = new JSONObject();
    json.put("id", this.id);
      json.put("class", "interval");
      json.put("initialDate", formatter.format(this.getInitTime()));
      json.put("finalDate", formatter.format(this.getEndTime()));
      json.put("father", this.getFather().getName());
      json.put("duration", this.getDuration().toSeconds());
      json.put("active", this.getActive());
    return json;

  }
}
