package com.bestinslot.osrswiki;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public static /*CompletableFuture<List<Activity>>*/ void GetEquipmentByBossName(String bossName)
    {
        CompletableFuture<EquipmentTableTab[]> equipmentTables = new CompletableFuture<>();
        String url = GetWikiUrlForEquipment(bossName);
        System.out.println("url: " + url);

        ScrapeWikiPageAsync(url).whenCompleteAsync((res, ex) -> {
            List<Activity> tableSections = new ArrayList<>();

            if (ex != null) {
                EquipmentTableTab[] result = new EquipmentTableTab[0];
                equipmentTables.complete(result);
            }

            doc = Jsoup.parse(res);

            //System.out.println("doc: " + doc);

            Elements tableHeaders = doc.select("a[href='#']");
            EquipmentItem currentSection = new EquipmentItem();
            EquipmentTableTab currentTable = new EquipmentTableTab();

            for(Element tableHeader : tableHeaders) {
                System.out.println("tableHeader: " + tableHeader.data());
            }
        });

        return tableSections;
    }



    public static String GetWikiUrlForEquipment(String bossName)
    {
       return baseWikiUrl + FormatName(bossName) + "/Strategies#Equipment";
    }

    public static String FormatName(String bossName)
    {
        return bossName
                .toLowerCase()
                .substring(0, 1)
                .toUpperCase() + bossName.substring(1)
                .trim()
                .replaceAll("\\s+", "_");
    }

    public static List<String> ScrapeWikiForBosses()
    {
        String url = baseWikiUrl + "Category:Strategies";
        List<String> bossNames = new ArrayList<>();

        CompletableFuture<String> completableFuture = ScrapeWikiPageAsync(url).whenCompleteAsync((res, ex) -> {
            doc = Jsoup.parse(res);
            //System.out.println("doc: " + doc);

            Elements liElements = doc.select("li");

            for (Element liElement : liElements)
            {
                Element aElement = liElement.selectFirst("a");
                if (aElement != null)
                {
                    String href = aElement.attr("href");

                    if (href.contains("/Strategies"))
                    {
                        String name = aElement.text();
                        name = name.replaceAll("/Strategies$", "");

                        System.out.println("name: " + name);
                        bossNames.add(name);
                    }
                }
            }
        });

        completableFuture.join();
        return bossNames;
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
