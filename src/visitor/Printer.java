package visitor;

import core.ClockTimer;
import core.Interval;
import core.Project;
import core.Task;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Observable;
import java.util.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Printer implements Visitor, Observer {

  private ClockTimer clock;
  private final Project root;
  private static final Logger logger = LoggerFactory.getLogger("Milestone1.Visitor.Printer");

  public Printer(Project root) {
    this.root = root;
    this.clock = ClockTimer.getInstance();
    this.clock.addObserver(this);
  }

  @Override
  public void update(Observable o, Object arg) {
    logger.trace("Method Update");
    if (root.isActive()) {
      root.acceptVisitor(this);
    }
  }

  @Override
  public void visitTask(Task t) {
    logger.trace("Method visitTask");
    String initTime = "null";
    String endTime = "null";

    //Format de la data i l'hora: 'DD Mon YYYY hh:mm:ss'
    if (t.getInitTime() != null) {
      initTime = t.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    if (t.getEndTime() != null) {
      endTime = t.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    logger.info("{} {} {} {} {}",
        String.format("%-10s", "Task: "),
        String.format("%-20s", t.getName()),
        String.format("%-30s", initTime),
        String.format("%-30s", endTime),
        t.getTotalTime().toSeconds());

    if (t.isActive()) {
      t.getLastInterval().acceptVisitor(this);
    }
  }

  @Override
  public void visitInterval(Interval i) {
    logger.trace("Method visitInterval");
    logger.info("{} {} {} {}",
        String.format("%-31s", "Interval: "),
        String.format("%-30s",
            i.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))),
        String.format("%-30s",
            i.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))),
        i.getDuration().toSeconds());
  }

  @Override
  public void visitProject(Project p) {
    logger.trace("Method visitProject");
    String initTime = "null";
    String endTime = "null";
    if (p.getFather() == null) {
      logger.info("{} {} {} {}",
          String.format("%-31s", ""),
          String.format("%-30s", "Initial time"),
          String.format("%-30s", "End time"),
          "Seconds");
    }
    if (p.getInitTime() != null) {
      initTime = p.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    if (p.getEndTime() != null) {
      endTime = p.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    logger.info("{} {} {} {} {}",
        String.format("%-10s", "Project: "),
        String.format("%-20s", p.getName()),
        String.format("%-30s", initTime),
        String.format("%-30s", endTime),
        p.getTotalTime().toSeconds());

    for (int i = 0; i < p.getItem().size(); i++) {
      if (p.getItem().get(i).isActive()) {
        p.getItem().get(i).acceptVisitor(this);
      }
    }
  }
}
