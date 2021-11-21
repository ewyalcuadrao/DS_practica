import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Json implements Visitor {
  private Project groot;
  private File file;
  protected String jsonString = "";

  public Json(Project root, String path) {
    this.groot = root;
    this.file = new File(path);
  }

  public Json(String path) {
    this.groot = null;
    this.file = new File(path);
  }

  public void saveRoot(Item i) {
    if (groot == null) {
      if (i.father != null) { //comprovamos que el proyecto es root, sino llamamos al padre
        saveRoot(i.father);
      } else {
        JSONArray jsonArray2 = new JSONArray();
        groot = (Project) i;
        jsonator(groot, jsonArray2);
        System.out.println("Data saved successfully");
      }
    } else {
      JSONArray jsonArray2 = new JSONArray();
      jsonator(groot, jsonArray2);
      System.out.println("Data saved successfully");
    }
  }

  public void jsonator(Interval i, JSONArray jsonArray) {
    JSONObject json = new JSONObject();
    try {
      //Guardamos toda la informacion del intervalo en un JSONObject
      json.put("class", "Interval");
      json.put("initTime", i.getInitTime());
      json.put("endTime", i.getEndTime());
      json.put("father", i.getFather().getName());
      json.put("duration", i.getDuration());
      jsonArray.put(json); //Añadimos el JSONObject al JSONarray
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void jsonator(Task t, JSONArray jsonArray) {
    JSONObject json = new JSONObject();
    JSONArray jsonArrayInterval = new JSONArray();
    try {
      //Guardamos toda la informacion de la tarea en un JSONObject
      json.put("class", "Task");
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
      json.put("active", t.active);
      for (int i = 0; i < t.getIntervals().size(); i++) {
        jsonator(t.getIntervals().get(i), jsonArrayInterval);
      }
      json.put("interval", jsonArrayInterval);
      jsonArray.put(json);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void jsonator(Project p, JSONArray jsonArray) {
    JSONObject json = new JSONObject();
    JSONArray jsonArrayItem = new JSONArray();
    try {
      json.put("class", "Project");
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
      json.put("active", p.active);
      for (int i = 0; i < p.getItem().size(); i++) {
        if (p.getItem().get(i) instanceof Project) {
          jsonator((Project) p.getItem().get(i), jsonArrayItem);
        } else {
          jsonator((Task) p.getItem().get(i), jsonArrayItem);
        }
      }
      json.put("item", jsonArrayItem);
      jsonArray.put(json);
      if (p.getName() == "root") {
        file.writejsonFile(json);
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void visitTask(Task t) {
    saveRoot(t);
  }

  @Override
  public void visitInterval(Interval i) {

  }

  @Override
  public void visitProject(Project p) {
    saveRoot(p);
  }
}
