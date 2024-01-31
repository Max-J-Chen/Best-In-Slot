package com.bestinslot.osrswiki;

public class EquipmentSection
{
    private String imageUrl;
    private String name;

    public EquipmentSection() {}

    public EquipmentSection(String imageUrl, String name)
    {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public EquipmentSection(String name)
    {
        this.name = name;
        this.imageUrl = null;
    }


}
