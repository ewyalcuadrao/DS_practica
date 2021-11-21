import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Observable;
import java.util.Observer;

public class Printer implements Visitor, Observer {

  private ClockTimer clock;
  private Project groot;

  public Printer(Project root) {
    this.groot = root;
    this.clock = ClockTimer.getInstance();
    this.clock.addObserver(this);
    System.out.printf("%-31s %-30s %-30s %s\n", "", "Initial time", "End time", "Seconds");
  }

  @Override
  public void visitTask(Task t) {
    String initTime = "null";
    String endTime = "null";

    if (t.getInitTime() != null) {
      initTime = t.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    if (t.getEndTime() != null) {
      endTime = t.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    System.out.printf("%-10s %-20s %-30s %-30s %s\n",
          "Task: ", t.getName(), initTime, endTime, t.getTotalTime().toSeconds());

    if (t.isActive()) {
      t.getLastInterval().acceptVisitor(this);
    }
  }

  @Override
  public void visitInterval(Interval i) {
    System.out.printf("%-31s %-30s %-30s %s\n",
        "Interval: ",
        i.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
        i.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
        i.getDuration().toSeconds());
  }

  @Override
  public void visitProject(Project p) {
    String initTime = "null";
    String endTime = "null";
    if (p.getInitTime() != null) {
      initTime = p.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    if (p.getEndTime() != null) {
      endTime = p.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    System.out.printf("%-10s %-20s %-30s %-30s %s\n",
          "Project: ", p.getName(), initTime, endTime, p.getTotalTime().toSeconds());

    for (int i = 0; i < p.getItem().size(); i++) {
      if (p.getItem().get(i).isActive()) {
        p.getItem().get(i).acceptVisitor(this);
      }
    }
  }

  @Override
  public void update(Observable o, Object arg) {
    if (groot.isActive()) {
      groot.acceptVisitor(this);
    }
  }
}
