import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PrintTree implements Visitor {
  private Project root;
  static Logger logger = LoggerFactory.getLogger("Visitor.PrintTree");

  public PrintTree(Project root) {
    this.root = root;
    logger.info("{} {} {} {} {}",
        String.format("%-30s","Item"),
        String.format("%-30s","Parent"),
        String.format("%-30s", "Initial time"),
        String.format("%-30s", "End time"),
        "Seconds");
    root.acceptVisitor(this);
  }

  @Override
  public void visitTask(Task t) {
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
        String.format("%-30s", "Task " + t.getName()),
        String.format("%-30s","child of " + t.getFather().getName()),
        String.format("%-30s", initTime),
        String.format("%-30s", endTime),
        t.getTotalTime().toSeconds());
  }

  @Override
  public void visitInterval(Interval i) {
    logger.info("{} {} {} {} {}",
        String.format("%-30s", "Interval "),
        String.format("%-30s", "child of " + i.getFather().getName()),
        String.format("%-30s", i.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))),
        String.format("%-30s", i.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))),
        i.getDuration().toSeconds());
  }

  @Override
  public void visitProject(Project p) {
    String fatherName = "null";
    String initTime = "null";
    String endTime = "null";

    if (p.getFather() != null) {
      fatherName = p.getFather().getName();
    }
    if (p.getInitTime() != null) {
      initTime = p.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    if (p.getEndTime() != null) {
      endTime = p.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
    logger.info("{} {} {} {} {}",
        String.format("%-30s", "Projecte " + p.getName()),
        String.format("%-30s","child of " + fatherName),
        String.format("%-30s", initTime),
        String.format("%-30s", endTime),
        p.getTotalTime().toSeconds());
    //Recorremos de forma recursiva el arbol de hijos del proyecto
    for (int i = 0; i < p.getItem().size(); i++) {
      p.getItem().get(i).acceptVisitor(this);
    }
  }
}