import org.json.JSONException;
import org.json.JSONObject;

public class Json implements Visitor{
    private Project Groot=null;
    public void isRoot(Item i){
        Project p = null;
        if(Groot == null) {
            if (i.father != null)
                isRoot(i.father);
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
            json.put("initTime", i.getInitTime());
            json.put("endTime", i.getEndTime());
            json.put("father", i.getFather());
            json.put("duration", i.getInterval());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void visitTask(Task t) {
        JSONObject json = new JSONObject();
        try {
            json.put("father", t.getFather());
            json.put("init", t.getInitTime());
            json.put("end", t.getEndTime());
            json.put("totalTime", t.getTotalTime());
            json.put("active", t.active);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitProject(Project p) {
        JSONObject json = new JSONObject();

        try {
            json.put("father", p.getFather());
            json.put("init", p.getInitTime());
            json.put("end", p.getEndTime());
            json.put("totalTime", p.getTotalTime());
            json.put("active", p.active);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
