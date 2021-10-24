import org.json.JSONException;
import org.json.JSONObject;


public class Json {
    public void isRoot(Item i){
        Project p;
        String s= null;
        if(i.father != null)
            isRoot(i.father);
        else
            generarDatos(p= (Project) i, s);
    }

    public JSONObject generarDatos(Project p, String s){
        s= "{ 'name':'"+p.getName()+"', 'tam':'"+p.getTam()+"', 'father':'"+p.getFather()+"'," +
                "'init':'"+p.getInit()+"', 'end':'"+p.getEnd()+"', 'totalTime':'"+p.getTotalTime()+"'}";
        JSONObject json = new JSONObject();
        JSONObject user = new JSONObject();

        try {
            json.put("tam", p.getTam());
            json.put("father", p.getFather());
            json.put("init", p.getInit());
            json.put("end", p.getEnd());
            json.put("totalTime", p.getTotalTime());
            json.put("item", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    public JSONObject generarDatos(Task p, JSONObject s){
        try {
            s.put("tam", p.getTam());
            s.put("father", p.getFather());
            s.put("init", p.getInit());
            s.put("end", p.getEnd());
            s.put("totalTime", p.getTotalTime());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    public JSONObject generarDatos(Interval p, JSONObject s){
        try {
            s.put("tam", p.getInitTime());
            s.put("father", p.getEndTime());
            return s;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }
}
