import org.json.*;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.time.*;


public class File {
  private String path;

  public File(String path) {
    this.path = path;
  }

  public Project readJson() {
    try {
      JSONParser parser = new JSONParser();
      Object json = parser.parse(new FileReader(path));
      JSONObject obj = new JSONObject(json.toString());
      Project root = new Project(obj.getString("name"), null);
      root.setActive(obj.getBoolean("active"));
      root.setInitTime(LocalDateTime.parse(obj.getString("init")));
      root.setTotalTime(Duration.parse(obj.getString("totalTime")));
      root.setEndTime(LocalDateTime.parse(obj.getString("end")));
      JSONArray arr = obj.getJSONArray("item");
      int i = 0;
      recorrerJsonArrayItem(arr, root);
      System.out.println("Data from " + path + " restored");
      return root;
    }
    catch (Exception e){
      System.out.println("File " + path + " not found");
      return null;
    }
  }

  private void recorrerJsonArrayItem(JSONArray arr, Project father) {
    int i =0;
    try {
      while( i < arr.length())
      {
        JSONObject jsonObject = arr.getJSONObject(i);

        if(arr.getJSONObject(i).getString("class").equals("Project")){
          createProject(jsonObject, father);
          JSONArray arr3 = arr.getJSONObject(i).getJSONArray("item");
          i++;
        }else if(arr.getJSONObject(i).getString("class").equals("Task")){
          createTask(jsonObject, father);
          JSONArray arr3 = arr.getJSONObject(i).getJSONArray("interval");
          i++;
        }else{
          i++;
        }
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  private void recorrerJsonArrayInterval(JSONArray arr, Task father) {
    int i = 0;
    try {
      while( i < arr.length())
      {
        JSONObject jsonObject = arr.getJSONObject(i);
        createInterval(jsonObject, father);
        i++;
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void createProject(JSONObject json, Project father){
    try {
      Project p = new Project(json.getString("name"), father);
      p.setActive((Boolean) json.getBoolean("active"));
      if (!json.getString("init").equals("null")) {
        p.setInitTime(LocalDateTime.parse(json.getString("init")));
      }else{
        p.setInitTime(null);
      }
      p.setTotalTime(Duration.parse(json.getString("totalTime")));
      if (!json.getString("end").equals("null")) {
        p.setEndTime(LocalDateTime.parse(json.getString("end")));
      }else{
        p.setEndTime(null);
      }
      JSONArray arrItem = json.getJSONArray("item");
      recorrerJsonArrayItem(arrItem, p);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  public void createTask(JSONObject json, Project father){
    try {
      Task t = new Task(json.getString("name"), father);
      t.setActive((Boolean) json.getBoolean("active"));
      if (!json.getString("init").equals("null")) {
        t.setInitTime(LocalDateTime.parse(json.getString("init")));
      }else{
        t.setInitTime(null);
      }
      t.setTotalTime(Duration.parse(json.getString("totalTime")));
      if (!json.getString("end").equals("null")) {
        t.setEndTime(LocalDateTime.parse(json.getString("end")));
      }else{
        t.setEndTime(null);
      }
      recorrerJsonArrayInterval(json.getJSONArray("interval"), t);
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }
  public void createInterval(JSONObject json, Task father){
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
  public void writeJSONFile(JSONObject json) {
    try {
      FileWriter file = new FileWriter(path);
      file.write(json.toString());
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}