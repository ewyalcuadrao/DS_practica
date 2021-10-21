import java.time.*;
import java.util.*;

public class Interval implements Observer{
  private ClockTimer clock;
  private LocalDateTime initTime;
  int seconds=2;
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
      seconds++;
      System.out.println("Hora inicial    "+ initTime + "    Hora actual: " + endTime+ "   Segundos:"+ seconds);
    }
  }

  public LocalDateTime getInitTime() {
    return initTime;
  }

  public LocalDateTime getEndTime() {
    return endTime;
  }
}
