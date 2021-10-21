import java.time.LocalDateTime;
import java.util.*;

public class ClockTimer extends Observable{
  private Timer timer;
  private LocalDateTime dateTime;
  private Observer o;
  private static ClockTimer instance= null;
  private static final int periode = 1; //seconds

  private ClockTimer() {timer = new Timer();}

  public static ClockTimer getInstance(){
    if(instance == null){instance = new ClockTimer();}
    return instance;
  }

  public void start() {
    timer = new Timer();
    TimerTask repeatedTask = new TimerTask() {
      public void run() {
        dateTime = LocalDateTime.now();
        setChanged();
        notifyObservers(dateTime);
      }
    };
    timer.scheduleAtFixedRate(repeatedTask, 0, 1000 * periode);
  }

  public void stop(){
    timer.cancel();
  }
  public LocalDateTime getDate(){return dateTime;}

}