package visitor;

import core.Interval;
import core.Project;
import core.Task;

public interface Visitor {
  void visitTask(Task t);

  void visitInterval(Interval i);

  void visitProject(Project p);


}
