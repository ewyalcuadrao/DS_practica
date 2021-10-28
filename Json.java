import org.json.JSONException;;
import org.json.JSONObject;

public class Json implements Visitor{
    private Project Groot;
    Fichero fichero;

    public Json(Project root, String path){
        Groot = root;
        fichero = new Fichero(path);
    }

    public void save(Item i){
        Project p = null;
        if(Groot == null) {
            if (i.father != null)
                save(i.father);
            else {
                Groot = (Project) i;
                Groot.acceptVisitor(this);
            }
        }else{
            Groot.acceptVisitor(this);
        }
    }
    @Override
    public void visitInterval(Interval i) {
        JSONObject json = new JSONObject();
        try {
            json.put("class", "Interval");
            json.put("initTime", i.getInitTime());
            json.put("endTime", i.getEndTime());
            json.put("father", i.getFather().getName());
            json.put("duration", i.getDuration());
            fichero.writeJSONFile(json + "\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void visitTask(Task t) {
        JSONObject json = new JSONObject();
        try {
            json.put("class", "Task");
            json.put("name", t.getName());
            json.put("father", t.getFather().getName());
            json.put("init", t.getInitTime());
            json.put("end", t.getEndTime());
            json.put("totalTime", t.getTotalTime());
            json.put("active", t.active);
            fichero.writeJSONFile(json+"\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < t.getIntervals().size(); i++){
            t.getIntervals().get(i).acceptVisitor(this);
        }
    }

    @Override
    public void visitProject(Project p) {
        JSONObject json = new JSONObject();

        try {
            json.put("class", "Project");
            json.put("name", p.getName());
            if(p.getFather()==null){
                json.put("father", "null");
            } else {
                json.put("father", p.getFather().getName());
            }
            json.put("init", p.getInitTime());
            json.put("end", p.getEndTime());
            json.put("totalTime", p.getTotalTime());
            json.put("active", p.active);
            fichero.writeJSONFile(json+"\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < p.getItem().size(); i++) {
            p.getItem().get(i).acceptVisitor(this);
        }
    }
    public void close(){
        fichero.close();
    }
}
