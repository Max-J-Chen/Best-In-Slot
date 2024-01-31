package com.bestinslot.osrswiki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
        CompletableFuture<EquipmentTableSection[]> equipmentTables = new CompletableFuture<>();
        String url = GetWikiUrlForEquipment(bossName);

        ScrapeWikiPageAsync(url).whenCompleteAsync((res, ex) -> {
            List<EquipmentTableSection> tableSections = new ArrayList<>();

            if (ex != null) {
                EquipmentTableSection[] result = new EquipmentTableSection[0];
                equipmentTables.complete(result);
            }

            doc = Jsoup.parse(res);

            Elements tables = doc.select("div.tabbertab");

            for (Element tab: tables) {
                String tabHeader = tab.attr("data-title");
                Elements rows = tab.select("tr");

                for (Element row : rows) {
                    Elements entries = row.select("td a[title]");
                    System.out.println("Row has " + entries.text().length() + " elements");

                    EquipmentSection[] itemRow = new EquipmentSection[entries.size()];

                    for (Element item: entries) {



                    }

                    System.out.println(entries.text());

                }


                System.out.println("________________________");

            }


            EquipmentSection currentSection = new EquipmentSection();
            EquipmentTableSection currentTable = new EquipmentTableSection();

            System.out.println("\n_____________________________________________________\n");

            System.out.println(tables.get(0));

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

}
