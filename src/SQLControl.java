import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class SQLControl {

    public static void makePOSTRequest(String urlName)
    {
        BufferedReader rd = null;
        StringBuilder sb = null;
        String line = null;
        try{
            URL url = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            sb = new StringBuilder();
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch (ProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }

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
}
