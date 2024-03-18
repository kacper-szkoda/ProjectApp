import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Plant {
    private String pref_light_plant;
    private String pref_moist_plant;
    private String plant_name;
    private File plant_img;
    private String file_path;
    enum pref_moist {
        very_dry,
        slightly_dry,
        slightly_moist,
        moist,
    }

    enum pref_light
    {
        direct_sun,
        high,
        medium,
        low,
    }

    public Plant(String plant_name, String pref_moist, String pref_light) {
        this.plant_name = plant_name;
        this.pref_moist_plant = pref_moist;
        this.pref_light_plant = pref_light;
        this.plant_img = new File(initializePlant_img());

    }

    public String getPlant_name() {
        return plant_name;
    }

    public String getPref_light_plant()
    {
        return pref_light_plant;
    }

    public String getPref_moist_plant()
    {
        return pref_moist_plant;
    }
//    public void set_pref(String pref_moist, String pref_light)
//    {
//        for (Plant.pref_moist prefMoist : Plant.pref_moist.values()  )
//        {
//            if (pref_moist.equals(prefMoist.name()))
//            {
//                pref_moist_plant = Plant.pref_moist.prefMoist;
//            }
//        }
//    }
    public void plantSetImg(File img)
    {
        plant_img = img;
    }

    public File getPlant_img() {
        return plant_img;
    }

    public void setPlant_img(String newpath) {
        List<String> fileContent = null;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of("C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\dane.txt"), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < fileContent.size(); i++) {
            String a = plant_name + file_path;
            String b = fileContent.get(i);
            if (fileContent.get(i).equals(plant_name + " " + file_path)) {
                fileContent.set(i, plant_name + " " + newpath);
                String c = fileContent.get(i);
                break;
            }
        }
        try {
            Files.write(Path.of("C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\dane.txt"), fileContent, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        file_path = newpath;
        plant_img = new File(newpath);
    }

    public String initializePlant_img()  {
        List<String> fileContent = null;
        try {
            fileContent = new ArrayList<>(Files.readAllLines(Path.of("C:\\Users\\Kacper Szkoda\\IdeaProjects\\ProjectApp\\src\\dane.txt"), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < fileContent.size(); i++) {
            String[] tokens = fileContent.get(i).split(" ", 2);
            String tok = tokens[0];
            if (tok.equals(plant_name)) {
                System.out.println(fileContent.get(i));
                return tokens[1];
            }
        }
        return "";
    }
}
