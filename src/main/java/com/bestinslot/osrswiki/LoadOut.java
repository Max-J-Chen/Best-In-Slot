package com.bestinslot.osrswiki;

import lombok.Getter;

import java.util.List;
import java.util.Map;

public class LoadOut
{
    @Getter
    private String Title;

    private Map<String, List<EquipmentItem>> Table;

    public LoadOut() {}

    public LoadOut(String title, Map<String, List<EquipmentItem>> table)
    {
        this.Title = title;
        this.Table = table;
    }
}
