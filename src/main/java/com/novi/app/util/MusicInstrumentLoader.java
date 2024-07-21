package com.novi.app.util;

import com.novi.app.model.MusicInstrument;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class MusicInstrumentLoader {

    private static final Logger logger = LoggerFactory.getLogger(
            MusicInstrumentLoader.class
    );

    public static void loadInstruments() throws Exception {
        Set<MusicInstrument> musicInstruments = new HashSet<>();

        //First page
        String firstPge = "https://ru.wikipedia.org/w/index.php?title=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D1%8F:%D0%9C%D1%83%D0%B7%D1%8B%D0%BA%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B5_%D0%B8%D0%BD%D1%81%D1%82%D1%80%D1%83%D0%BC%D0%B5%D0%BD%D1%82%D1%8B_%D0%BF%D0%BE_%D0%B0%D0%BB%D1%84%D0%B0%D0%B2%D0%B8%D1%82%D1%83";
        URL obj = new URL(firstPge);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        int responseCode = con.getResponseCode();
        logger.debug("Response code: {}", responseCode);
        logger.debug("-------------------------------------------------");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        String html = response.toString();
        Document doc = Jsoup.parse(html);
        Elements links = doc.select("a[title]");
        logger.debug(String.valueOf(links));
        logger.debug("-------------------------------------------------");
        int index = 0;
        int newIndex = links.size() - 1;

        while (index < links.size() && newIndex >= 0) {
            if (links.get(index).attr("title").equals("Категория:Музыкальные инструменты по алфавиту")) {
                links.remove(index);
            } else {
                links.remove(newIndex);
                index++;
            }
            newIndex--;
        }
        logger.debug(String.valueOf(links));
        logger.debug("-------------------------------------------------");
        for (Element link : links) {
            String instrumentsParse = link.attr("title");
            MusicInstrument instrument = new MusicInstrument();
            instrument.setInstrumentName(instrumentsParse);
            musicInstruments.add(instrument);
        }
        logger.info("output: {}", musicInstruments.stream().map(MusicInstrument::getInstrumentName).collect(Collectors.toList()));
    }
}
