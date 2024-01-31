package com.bestinslot.osrswiki;

import java.util.Map;

public class EquipmentTableSection
{
    private String Title;

    private Map<String, EquipmentSection[]> Table;

    public EquipmentTableSection() {}

    public EquipmentTableSection(String title, Map<String, EquipmentSection[]> table)
    {
        this.Title = title;
        this.Table = table;
    }
}
