import java.util.ArrayList;
import java.util.Hashtable;

public class StateStorage {
    ArrayList<Plant> currPlant;
    Hashtable<Plant, Integer> hashMoist;
    Hashtable<Plant, Integer> hashLight;
    public StateStorage(ArrayList<Plant> currPlant) {
        this.currPlant = currPlant;
        hashLight = new Hashtable<Plant, Integer>();
        hashMoist = new Hashtable<Plant, Integer>();
        for (Plant plant : currPlant)
        {
            hashMoist.put(plant, SQLControl.parseJSONForMoist(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_ldr_curr/" + plant.getPlant_name())));
            hashLight.put(plant, SQLControl.parseJSONForLight(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_ldr_curr/" + plant.getPlant_name())));
        }
    }

    public void update()
    {
        for (Plant plant : currPlant)
        {
            hashMoist.put(plant, SQLControl.parseJSONForMoist(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_ldr_curr/" + plant.getPlant_name())));
            hashLight.put(plant, SQLControl.parseJSONForLight(SQLControl.makeGETRequest("https://studev.groept.be/api/a23ib2a01/get_ldr_curr/" + plant.getPlant_name())));
        }
    }




}
