public class Plant {
    private String pref_light_plant;
    private String pref_moist_plant;
    private String plant_name;
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
}
