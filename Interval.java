import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.time.format.DateTimeFormatter.*;

public class Interval implements Observer{
  private ClockTimer clock;
  private LocalDateTime initTime;
  Duration duration = Duration.ZERO;
  private LocalDateTime endTime;

  public Interval() {
    clock = ClockTimer.getInstance();
    clock.addObserver(this);
    this.initTime = LocalDateTime.now(); //mirar si aqui now esta vacio
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
      endTime = (LocalDateTime) arg;
      this.duration = this.duration.plusSeconds(1);
      System.out.println("Hora inicial    "+ initTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) + "    Hora actual: " + endTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) + "   Segundos:"+ duration.toSeconds());
    }
  }

  public LocalDateTime getInitTime() {
    return initTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }
}
