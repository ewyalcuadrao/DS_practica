import java.time.LocalDateTime;
import java.time.format.*;
import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor, Observer{

  private ClockTimer clock;
  private Project Groot;

  public Printer(Project root){
    this.Groot = root;
    this.clock = ClockTimer.getInstance();
    this.clock.addObserver(this);
  }

  @Override
  public void visitTask(Task t) {
    String initTime = "null";
    String endTime = "null";

    if (t.getInitTime() != null)
      initTime = t.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    if (t.getEndTime() != null)
      endTime = t.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));

    System.out.println("Task " + t.getName() + "    child of " + t.getFather().getName() + "   " +
        initTime + "  " + endTime + "  " + t.getTotalTime().toSeconds());
  }

  @Override
  public void visitInterval(Interval i) {
    System.out.println("Interval   child of " + i.getFather().getName() + "   " +
        i.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) + "  " +
        i.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)) + "  " +
        i.getInterval().toSeconds());
  }

  @Override
  public void visitProject(Project p) {
    String fatherName = "null";
    String initTime = "null";
    String endTime = "null";

    if (p.getFather() != null)
      fatherName = p.getFather().getName();
    if (p.getInitTime() != null)
      initTime = p.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    if (p.getEndTime() != null)
      endTime = p.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));


    System.out.println("Projecte " + p.getName() + "          child of " + fatherName + "   " + initTime + "  " +
        endTime + "  " + p.getTotalTime().toSeconds());
  }

  @Override
  public void update(Observable o, Object arg) {
    LocalDateTime dateTime = (LocalDateTime) arg;
    System.out.println(dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    Groot.acceptVisitor(this);
  }
}