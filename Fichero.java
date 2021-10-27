import org.json.simple.parser.JSONParser;
import org.json.*;
import java.time.*;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class Fichero {
  private String ruta;
  private JSONParser parser;

  public Fichero() {
    ruta = "/ProyectoDS/file.json";
    parser = new JSONParser();
  }

  public void readJSONFile(Item i) {
    try {
      Object obj = parser.parse(new FileReader(ruta));
      JSONArray array = (JSONArray) obj;
      JSONObject jsonObject = (JSONObject) array.get(0);
      i.setFather((Project) jsonObject.get("father"));
      i.setInitTime((LocalDateTime) jsonObject.get("init"));
      i.setEndTime((LocalDateTime) jsonObject.get("end"));
      i.setTotalTime((Duration) jsonObject.get("totalTime"));
      i.setActive((Boolean) jsonObject.get("active"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
