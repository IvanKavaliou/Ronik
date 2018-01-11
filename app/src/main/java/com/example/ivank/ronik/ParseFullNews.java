package com.example.ivank.ronik;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


class ParseFullNews extends AsyncTask<Void, Void, FullNews> {

    private String url;

    public ParseFullNews(String url) {
        this.url = url;
    }

    @Override
    protected FullNews doInBackground(Void... voids) {
        FullNews fullNews = null;
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select(".news-detail");
            for(Element element:elements){
                Element element1 = element.select("img[src]").first();
                Element element2 = element.getElementsByTag("p").first();
                fullNews = new FullNews("date", "title", element.html(),
                                        element1.attr("abs:src" ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fullNews;
    }
}