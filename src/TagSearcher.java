import java.util.ArrayList;

public class TagSearcher implements Visitor {
  private Project groot;

  private String tag;
  protected ArrayList<Item> tagfound;

  public TagSearcher(Project root) {
    this.groot = root;
    this.tagfound = new ArrayList<Item>();
  }

  @Override
  public void visitTask(Task t) {
    String[] tasktag = t.getTag();
    for (int j = 0; j < tasktag.length; j++) {
      if (tasktag[j].equals(this.tag)) {
        tagfound.add(t);
        System.out.println(t.getName());
        j = tasktag.length;
      }
    }
  }

  @Override
  public void visitInterval(Interval i) {

  }

  @Override
  public void visitProject(Project p) {
    String[] projecttag = p.getTag();
    for (int j = 0; j < projecttag.length; j++) {
      if (projecttag[j].equals(this.tag)) {
        //TODO: Podemos hacerlo así o printando cada vez que encuentre uno, pongo las dos maneras
        tagfound.add(p);
        String name = p.getName();
        System.out.println(name);
      }
    }
    for (int i = 0; i < p.getItem().size(); i++) {
      p.getItem().get(i).acceptVisitor(this);
    }
  }

  public void searchTask(String tag) {
    this.tag = tag;
    groot.acceptVisitor(this);
  }
}
