package com.example.ivank.ronik;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


class ParseNews extends AsyncTask<Void, Void, ArrayList<News>> {
    @Override
    protected ArrayList<News> doInBackground(Void... voids) {
        ArrayList<News> news = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("http://www.ronik.org.pl/news/").get();
            Elements elements = doc.select(".news-item");
            for(Element element:elements){
                Element element1 = element.select("a[href]").first();
                Element element2 = element.select("img[src]").first();
                Element element3 = element.select("b").first();
                Element element4 = element.select("span").first();
                news.add(new News( element1.attr("abs:href"),
                        element2.attr("abs:src" ),
                        element4.text(),
                        element3.text(),
                        element.text()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return news;
    }
}