import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class SQLControl
{
    public static void update(String urlName)
    {
        try{
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            Object rd = conn.getInputStream();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String makeGETRequest(String urlName) {
        //Get JSON object using the API
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        try {
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                sb.append(line + '\n');
            }
            conn.disconnect();
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static ArrayList<Plant> pJSONForCurrent(String jsonString) {
        //Parse JSON to extract the necessary values
        ArrayList<Plant> plants = new ArrayList<Plant>();
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                String p_light = curObject.getString("preffered_light");
                String p_moist = curObject.getString("preffered_moist");
                String name = curObject.getString("plant_name");
                plants.add(new Plant(name, p_moist, p_light));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return plants;
    }

    public static int parseJSONForMoist(String jsonString)
    {
        int moist = 0;
        System.out.println(jsonString);
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                moist = curObject.getInt("moisture_reading");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(moist);
        return moist;
    }

    public static int parseJSONForLight(String jsonString)
    {
        int ldr = 0;
        System.out.println(jsonString);
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                ldr = curObject.getInt("light_reading");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(ldr);
        return ldr;
    }

    public static float parseJSONForWeight(String jsonString)
    {
        float weight = 0.0f;
        System.out.println(jsonString);
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                weight = curObject.getFloat("weight");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(weight);
        return weight;
    }

    public static void addNewCurrPlant(String jsonString)
    {
        String plant_name = "";
        String pref_moist = "";
        String pref_light = "";
        try {
            JSONArray array = new JSONArray(jsonString);
            for (int i = 0; i < array.length(); i++) {
                JSONObject curObject = array.getJSONObject(i);
                plant_name = curObject.getString("plant_name");
                pref_moist = curObject.getString("preffered_moist");
                pref_light = curObject.getString("preffered_light");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        makeGETRequest("https://studev.groept.be/api/a23ib2a01/addCurPlant/" + plant_name.replace(" ", "+") + "/" + pref_moist.replace(" ", "+") + "/" + pref_light.replace(" ", "+"));
    }
//    public static float parseJSONForParameter(String jsonString, String key)
//    {
//        try {
//        JSONArray array = new JSONArray(jsonString);
//        for (int i = 0; i < array.length(); i++) {
//            JSONObject curObject = array.getJSONObject(i);
//            String p_light = curObject.getString("preffered_light");
//            String p_moist = curObject.getString("preffered_moist");
//            String name = curObject.getString("plant_name");
//            plants.add(new Plant(name, p_moist, p_light));
//        }
//    } catch (JSONException e) {
//        e.printStackTrace();
//    }
//        return
//    }
}



//    public String makeGETRequest(String urlName)
//    {
//        BufferedReader rd = null;
//        StringBuilder sb = null;
//        String line = null;
//        try{
//            URL url = new URL(urlName);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.connect();
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            sb = new StringBuilder();
//        }
//        catch (MalformedURLException e){
//            e.printStackTrace();
//        }
//        catch (ProtocolException e){
//            e.printStackTrace();
//        }
//        catch (IOException e){
//            e.printStackTrace();
//        }
//
//    }

