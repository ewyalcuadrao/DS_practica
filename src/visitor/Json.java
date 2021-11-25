package visitor;

import core.File;
import core.Interval;
import core.Item;
import core.Project;
import core.Task;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Json implements Visitor {
  private Project root;
  private File file;
  static Logger logger = LoggerFactory.getLogger("Milestone1.Visitor.Json");

  public Json(Project root, String path) {
    this.root = root;
    this.file = new File(path);
  }

  public Json(String path) {
    this.root = null;
    this.file = new File(path);
  }

  public void saveRoot(Item i) {
    logger.trace("Method saveRoot");
    if (root == null) {
      if (i.getFather() != null) { //comprovamos que el proyecto es root, sino llamamos al padre
        saveRoot(i.getFather());
      } else {
        JSONArray jsonArray = new JSONArray();
        root = (Project) i;
        createJson(root, jsonArray);
        logger.warn("Data saved successfully");
      }
    } else {
      JSONArray jsonArray = new JSONArray();
      createJson(root, jsonArray);
      logger.warn("Data saved successfully");
    }
  }

  public void createJson(Interval i, JSONArray jsonArray) {
    logger.trace("Method createIntervalJson");
    JSONObject json = new JSONObject();
    try {
      json.put("class", "core.Interval");
      json.put("initTime", i.getInitTime());
      json.put("endTime", i.getEndTime());
      json.put("father", i.getFather().getName());
      json.put("duration", i.getDuration());
      jsonArray.put(json); //Añadimos el JSONObject a
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void createJson(Task t, JSONArray jsonArray) {
    logger.trace("Method createTaskJson");
    JSONObject json = new JSONObject();
    JSONArray jsonArrayInterval = new JSONArray();
    try {
      json.put("class", "core.Task");
      json.put("name", t.getName());
      json.put("father", t.getFather().getName());
      if (t.getInitTime() == null) {
        json.put("init", "null");
      } else {
        json.put("init", t.getInitTime());
      }
      if (t.getEndTime() == null) {
        json.put("end", "null");
      } else {
        json.put("end", t.getEndTime());
      }
      json.put("totalTime", t.getTotalTime());
      json.put("active", t.isActive());
      for (int i = 0; i < t.getIntervals().size(); i++) {
        createJson(t.getIntervals().get(i), jsonArrayInterval);
      }
      json.put("interval", jsonArrayInterval);
      jsonArray.put(json);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void createJson(Project p, JSONArray jsonArray) {
    logger.trace("Method createProjectJson");
    JSONObject json = new JSONObject();
    JSONArray jsonArrayItem = new JSONArray();
    try {
      json.put("class", "core.Project");
      json.put("name", p.getName());
      if (p.getFather() == null) {
        json.put("father", "null");
      } else {
        json.put("father", p.getFather().getName());
      }
      if (p.getInitTime() == null) {
        json.put("init", "null");
      } else {
        json.put("init", p.getInitTime());
      }
      if (p.getEndTime() == null) {
        json.put("end", "null");
      } else {
        json.put("end", p.getEndTime());
      }
      json.put("totalTime", p.getTotalTime());
      json.put("active", p.isActive());
      for (int i = 0; i < p.getItem().size(); i++) {
        if (p.getItem().get(i) instanceof Project) {
          createJson((Project) p.getItem().get(i), jsonArrayItem);
        } else {
          createJson((Task) p.getItem().get(i), jsonArrayItem);
        }
      }
      json.put("item", jsonArrayItem);
      jsonArray.put(json);
      if (p.getName().equals("root")) {
        file.writeJsonFile(json);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  //Redireccionador a función que buscaria root para crear JSON completo
  @Override
  public void visitTask(Task t) {
    logger.trace("Method visitTask");
    saveRoot(t);
  }

  @Override
  public void visitInterval(Interval i) {}

  //Redireccionador a función que buscaria root para crear JSON completo
  @Override
  public void visitProject(Project p) {
    logger.trace("Method visitProject");
    saveRoot(p);
  }
}
