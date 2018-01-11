package com.example.ivank.ronik;

import android.os.AsyncTask;
import android.text.Html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class ParseProgram extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... voids) {
        String programImprez = "";
        try {
            Document doc = Jsoup.connect("http://www.ronik.org.pl/program-imprez/").get();
            Element element = doc.getElementById("workarea-inner");

            programImprez= element.html();
           /* Elements elements;

                elements = element.getElementsByTag("p");

                for(Element el:elements){
                    if(!el.text().trim().equals("")) {
                        programImprez+= el.html() + "<br />";
                    }

                }*/


        } catch (IOException e) {
            e.printStackTrace();
        }
        return programImprez;
    }

}
