package com.bestinslot.osrswiki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.bestinslot.utils.Constants;
import net.runelite.http.api.RuneLiteAPI;
import okhttp3.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WikiScraper
{
    private final static String baseUrl = "https://oldschool.runescape.wiki";
    private final static String baseWikiUrl = baseUrl + "/w/";

    public static OkHttpClient client = RuneLiteAPI.CLIENT;

    private static Document doc;

    public static /*CompletableFuture<EquipmentTableSection[]>*/ void GetEquipmentByBossName(String bossName)
    {
        CompletableFuture<EquipmentTableTab[]> equipmentTables = new CompletableFuture<>();
        String url = GetWikiUrlForEquipment(bossName);

        ScrapeWikiPageAsync(url).whenCompleteAsync((res, ex) -> {
            List<EquipmentTableTab> activityStrategies = new ArrayList<>();

            if (ex != null) {
                EquipmentTableTab[] result = new EquipmentTableTab[0];
                equipmentTables.complete(result);
            }

            doc = Jsoup.parse(res);

            Elements tabTables = doc.select("div.tabbertab");

            for (Element tab: tabTables) {
                Elements innerTables = tab.select("table");

                String tabHeader = tab.attr("data-title");
                Map<String, List<EquipmentItem>> tabEquipment = new HashMap<>();

                System.out.println("TabHeader: " + tabHeader);
                parseEquipmentTable(tabEquipment, innerTables.get(0));
                parseInventoryTable(tabEquipment, innerTables.get(1));

                EquipmentTableTab equipmentTab = new EquipmentTableTab(tabHeader, tabEquipment);

                activityStrategies.add(equipmentTab);
            }
        });
    }

    public static String GetWikiUrlForEquipment(String bossName)
    {
       return baseWikiUrl + FormatName(bossName) + "/Strategies#Equipment";
    }

    public static String FormatName(String bossName)
    {
        return bossName
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", "_")
                .substring(0, 1)
                .toUpperCase() + bossName.substring(1);
    }

    private static CompletableFuture<String> ScrapeWikiPageAsync(String url)
    {
        CompletableFuture<String> future = new CompletableFuture<>();

        Request request = new Request.Builder().url(url).header("User-Agent", Constants.USER_AGENT).build();

        client
                .newCall(request)
                .enqueue(
                        new Callback()
                        {
                            @Override
                            public void onFailure(Call call, IOException ex)
                            {
                                future.completeExceptionally(ex);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException
                            {
                                try (response; ResponseBody responseBody = response.body()) {
                                    if (!response.isSuccessful()) {
                                        future.complete("");
                                    }

                                    future.complete(responseBody.string());
                                }
                            }
                        });

        return future;
    }

    private static void parseEquipmentTable (Map<String, List<EquipmentItem>> equipmentMap, Element equipmentInnerTable)
    {
        Elements rows = equipmentInnerTable.select("tr");

        for (int i = 1; i < rows.size(); i++) {     // We skip the first row since that contains misc header
            Element row = rows.get(i);

            Elements cells = row.select("td");

            String itemSlot = cells.first().select("img").attr("alt");

            System.out.println("\t " + itemSlot);

            List<EquipmentItem> items = new ArrayList<>();
            for (int j = 1; j < cells.size(); j++) {
                Element cell = cells.get(j);

                Elements images = cell.select("img");   // Extract title if img element is present since those are the actual items
                for (Element image : images) {
                    String item = image.parent().select("a").attr("title");
                    System.out.println("\t\t" + item);
                    EquipmentItem equipmentItem = new EquipmentItem(item);
                    items.add(equipmentItem);
                }
            }
            equipmentMap.put(itemSlot, items);
        }
    }

    private static void parseInventoryTable (Map<String, List<EquipmentItem>> equipmentMap, Element inventoryInnerTable)
    {
        Elements itemNames = inventoryInnerTable.select("td a[title]");

        System.out.println("\tInventory");

        List<EquipmentItem> items = new ArrayList<>();

        for (Element item : itemNames) {
            String itemTitle = item.attr("title");
            System.out.println("\t\t" + itemTitle);
            EquipmentItem inventoryItem = new EquipmentItem(itemTitle);
            items.add(inventoryItem);
        }
        equipmentMap.put("Inventory", items);
    }
}
