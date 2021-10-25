public interface Visitor {
  void visitTask(Task t);
  void visitInterval(Interval i);
  void visitProject(Project p);
}
