package webdriver;

import core.Item;
import core.ClockTimer;
import core.Project;
import core.Task;

import java.util.ArrayList;
import java.util.List;

public class MainWebServer {
  public static void main(String[] args) {
    webServer();
  }

  public static void webServer() {
    final Item root = makeTreeCourses();
    // implement this method that returns the tree of
    // appendix A in the practicum handout

    // start our clock
    ClockTimer.getInstance().start();

    new WebServer((Project) root);
  }

  public static Item makeTreeCourses() {
    Project root = new Project("root", null);
    Project sd = new Project("Software Design", root, new ArrayList<String>(List.of("java", "flutter")));
    Project st = new Project("Software Testing", root, new ArrayList<String>(List.of("c++", "Java", "python")));
    Project db = new Project("Databases", root, new ArrayList<String>(List.of("SQL", "python", "C++")));
    Task tt = new Task("Transportation", root);
    Project pp = new Project("Problems", sd);
    Project ptr = new Project("core.Project Time Tracker", sd);
    Task tfl = new Task("First list", pp, new ArrayList<String>(List.of("java")));
    Task tsl = new Task("Second list", pp, new ArrayList<String>(List.of("Dart")));
    Task rh = new Task("Read handout", ptr);
    Task fm = new Task("First milestone", ptr, new ArrayList<String>(List.of("Java", "IntelliJ")));
    return root;
  }
}