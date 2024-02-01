package com.bestinslot.osrswiki;

import java.util.List;
import java.util.Map;

public class EquipmentTableTab
{
    private String Title;

    private Map<String, List<EquipmentItem>> Table;

    public EquipmentTableTab() {}

    public EquipmentTableTab(String title, Map<String, List<EquipmentItem>> table)
    {
        this.Title = title;
        this.Table = table;
    }
}
