import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class File {
  private String path;
  static Logger logger = LoggerFactory.getLogger("Milestone1.File");

  public File(String path) {
    this.path = path;
  }

  public Project readJson() {
    logger.trace("Method readJson");
    try {
      JSONParser parser = new JSONParser();
      Object json = parser.parse(new FileReader(path));
      JSONObject obj = new JSONObject(json.toString());
      //Root será siempre el primer proyecto, por eso lo podemos crear como raiz
      Project root = new Project(obj.getString("name"), null);
      root.setActive(obj.getBoolean("active"));
      root.setInitTime(LocalDateTime.parse(obj.getString("init")));
      root.setTotalTime(Duration.parse(obj.getString("totalTime")));
      root.setEndTime(LocalDateTime.parse(obj.getString("end")));
      JSONArray arr = obj.getJSONArray("item");
      recorrerJsonArrayItem(arr, root);
      logger.warn("Data from " + path + " restored");
      return root;
    } catch (Exception e) {
      logger.warn("File " + path + " not found");
      return null;
    }
  }

  private void recorrerJsonArrayItem(JSONArray arr, Project father) {
    logger.trace("Method recorrerJsonArrayItem");
    int i = 0;
    try {
      while (i < arr.length()) {
        JSONObject jsonObject = arr.getJSONObject(i);
        if (arr.getJSONObject(i).getString("class").equals("Project")) {
          createProject(jsonObject, father);
          i++;
        } else if (arr.getJSONObject(i).getString("class").equals("Task")) {
          createTask(jsonObject, father);
          i++;
        } else {
          i++;
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void recorrerJsonArrayInterval(JSONArray arr, Task father) {
    logger.trace("Method recorrerJsonArrayInterval");
    int i = 0;
    try {
      while (i < arr.length()) {
        JSONObject jsonObject = arr.getJSONObject(i);
        createInterval(jsonObject, father);
        i++;
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void createProject(JSONObject json, Project father) {
    logger.trace("Method createProject");
    try {
      Project p = new Project(json.getString("name"), father);
      p.setActive((Boolean) json.getBoolean("active"));
      if (!json.getString("init").equals("null")) {
        p.setInitTime(LocalDateTime.parse(json.getString("init")));
      } else {
        p.setInitTime(null);
      }
      p.setTotalTime(Duration.parse(json.getString("totalTime")));
      if (!json.getString("end").equals("null")) {
        p.setEndTime(LocalDateTime.parse(json.getString("end")));
      } else {
        p.setEndTime(null);
      }
      JSONArray arrItem = json.getJSONArray("item");
      recorrerJsonArrayItem(arrItem, p);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void createTask(JSONObject json, Project father) {
    logger.trace("Method createTask");
    try {
      Task t = new Task(json.getString("name"), father);
      t.setActive((Boolean) json.getBoolean("active"));
      if (!json.getString("init").equals("null")) {
        t.setInitTime(LocalDateTime.parse(json.getString("init")));
      } else {
        t.setInitTime(null);
      }
      t.setTotalTime(Duration.parse(json.getString("totalTime")));
      if (!json.getString("end").equals("null")) {
        t.setEndTime(LocalDateTime.parse(json.getString("end")));
      } else {
        t.setEndTime(null);
      }
      recorrerJsonArrayInterval(json.getJSONArray("interval"), t);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void createInterval(JSONObject json, Task father) {
    logger.trace("Method createInterval");
    try {
      Interval i = new Interval(father);
      i.setInitTime(LocalDateTime.parse(json.getString("initTime")));
      i.setDuration(Duration.parse(json.getString("duration")));
      i.setEndTime(LocalDateTime.parse(json.getString("endTime")));
      father.addInterval(i);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void writeJsonFile(JSONObject json) {
    logger.trace("Method writeJsonFile");
    try {
      FileWriter file = new FileWriter(path);
      file.write(json.toString());
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}