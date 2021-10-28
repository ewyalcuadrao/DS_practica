import org.json.simple.parser.JSONParser;
import org.json.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.*;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;


public class Fichero {
  private String ruta;
  private JSONParser parser;
  private FileWriter file;
  private JSONObject jsonObject;

  public Fichero(String path) {
    jsonObject = null;
    ruta = path;
    parser = new JSONParser();
    try {
      file = new FileWriter(path, false);
    }catch (Exception e){
      e.getStackTrace();
    }
  }

  protected void funcionUno(JSONArray array, Project father)
  {
    for(int j = 0; j < array.length(); j++)
    {
      jsonObject = (JSONObject) array.get(j);
      if(jsonObject.get("father")==father.getName()) {
        Project p = new Project((String) jsonObject.get("name"), father);
        p.setActive((Boolean) jsonObject.get("active"));
        p.setInitTime((LocalDateTime) jsonObject.get("init"));
        p.setTotalTime((Duration) jsonObject.get("totalTime"));
        p.setEndTime((LocalDateTime) jsonObject.get("end"));
        funcionUno(array, p);
      }
    }
  }
  public void readJSONFile(String path) {
    try {
      Object obj = parser.parse(new FileReader(path));
      JSONArray array = (JSONArray) obj;

      boolean enc = false;
      int j=0;
      while(j<array.length() && !enc )
      {
        jsonObject = (JSONObject) array.get(j);
        if(jsonObject.get("father")==null){
          enc = true;
        }
        else
          j++;
      }
      if(enc) {
        Project p = new Project((String)jsonObject.get("name"), null);
        p.setActive((Boolean) jsonObject.get("active"));
        p.setInitTime((LocalDateTime) jsonObject.get("init"));
        p.setTotalTime((Duration) jsonObject.get("totalTime"));
        p.setEndTime((LocalDateTime) jsonObject.get("end"));
        funcionUno(array, p);
      }

    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void writeJSONFile(String json) {
    try {
      file.write(json);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void close(){
    try {
      file.close();
    }catch (Exception e){
      e.getStackTrace();
    }
  }
}
