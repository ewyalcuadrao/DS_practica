//import com.google.gson.Gson;


public class Main {

    public static void main(String[] args) {
        //Cargamos el arbol desde el fichero json
        File fi= new File("./tree.json");
        Project root = fi.readJson();
        //Cuando no exista información del árbol pasaremos el test de apéndice B
        if (root == null) {
            simpleTest();
        } else {//En caso contarario cargamos i mostramos toda la información del árbol
            testRead(root);
        }
        System.exit(0);
    }

    private static void simpleTest() {
        try {
            Project root = new Project("root", null);

            Project SD = new Project("Software Design", root);
            Project ST = new Project("Software Testing", root);
            Project DB = new Project("Databases", root);
            Task TT = new Task("Transportation", root);
            Project PP = new Project("Problems", SD);
            Project PTR = new Project("Project Time Tracker", SD);
            Task TFL = new Task("First list", PP);
            Task TSL = new Task("Second list", PP);
            Task RH = new Task("Read handout", PTR);
            Task FM = new Task("First milestone", PTR);

            Printer printer = new Printer(root);

            System.out.println("Start test");
            //1. start task transportation, wait 4 seconds and then stop it
            TT.startWorking();
            Thread.sleep(4000);
            TT.stopWorking();
            //2. wait 2 seconds
            Thread.sleep(2000);
            //3. start task first list, wait 6 seconds
            TFL.startWorking();
            Thread.sleep(6000);
            //4. start task second list and wait 4 seconds
            TSL.startWorking();
            Thread.sleep(4000);
            //5. stop first list
            TFL.stopWorking();
            //6. wait 2 seconds and then stop second list
            Thread.sleep(2000);
            TSL.stopWorking();
            //7. wait 2 seconds
            Thread.sleep(2000);
            //8. start transportation, wait 4 seconds and then stop it
            TT.startWorking();
            Thread.sleep(4000);
            TT.stopWorking();

            //Save tree in a json file
            Json json = new Json(root, "./tree.json");
            json.saveRoot(root);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }

    private static void testRead(Project root){
        PrintTree printer = new PrintTree(root);
    }
}
