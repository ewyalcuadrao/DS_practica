import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


public class PrintTree implements Visitor {
  private Project root;

  public PrintTree(Project root) {
    this.root = root;
    System.out.printf("%-30s %-30s %-30s %-30s %s\n",
        "Item", "Parent", "Initial time", "End time", "Seconds");
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
    System.out.printf("%-30s %-30s %-30s %-30s %s\n",
        "Task " + t.getName(),
        "child of " + t.getFather().getName(), initTime, endTime, t.getTotalTime().toSeconds());
  }

  @Override
  public void visitInterval(Interval i) {
    System.out.printf("%-30s %-30s %-30s %-30s %s\n",
        "Interval ", "child of " + i.getFather().getName(),
        i.getInitTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
        i.getEndTime().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
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

    System.out.printf("%-30s %-30s %-30s %-30s %s\n",
        "Projecte " + p.getName(),
        "child of " + fatherName, initTime, endTime, p.getTotalTime().toSeconds());
    //Recorremos de forma recursiva el arbol de hijos del proyecto
    for (int i = 0; i < p.getItem().size(); i++) {
      p.getItem().get(i).acceptVisitor(this);
    }
  }
}
