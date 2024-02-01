package com.bestinslot.osrswiki;

public class EquipmentItem
{
    private String imageUrl;
    private String name;

    public EquipmentItem() {}

    public EquipmentItem(String imageUrl, String name)
    {
        this.imageUrl = imageUrl;
        this.name = name;
    }

    public EquipmentItem(String name)
    {
        this.name = name;
        this.imageUrl = null;
    }


}
