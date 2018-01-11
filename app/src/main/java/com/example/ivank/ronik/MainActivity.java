package com.example.ivank.ronik;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.ivank.easytrack.R;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;




public class MainActivity extends AppCompatActivity  {
    private TextView information;
    private TextView kontakt;
    private TextView programImprez;
    private ImageView imageNews;
    private ListView listView;
    private NewsAdapter newsAdapter;


    public static void main(String[] args) throws IOException {


    }



    class MyTask extends AsyncTask<Void, Void, Void> {

        String title;
        @Override
        protected Void doInBackground(Void... params) {

            //Тут пишем основной код
            Document doc = null;//Здесь хранится будет разобранный html документ
            try {
                //Считываем заглавную страницу http://harrix.org
                doc = Jsoup.connect("http://www.ronik.org.pl/news/").get();
            } catch (IOException e) {
                //Если не получилось считать
                e.printStackTrace();
            }

            //Если всё считалось, что вытаскиваем из считанного html документа заголовок
            if (doc!=null)
                title = doc.html();
            else
                title = "Ошибка";

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            System.out.println(title);
            information.setText(title);
            //Тут выводим итоговые данные
        }
    }





    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    information.setVisibility(View.GONE);
                    imageNews.setVisibility(View.GONE);
                    programImprez.setVisibility(View.GONE);
                    kontakt.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    information.setVisibility(View.GONE);
                    imageNews.setVisibility(View.GONE);

                    listView.setVisibility(View.GONE);
                    kontakt.setVisibility(View.GONE);
                    programImprez.setVisibility(View.VISIBLE);

                    return true;
                case R.id.navigation_notifications:
                    information.setVisibility(View.GONE);
                    imageNews.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    programImprez.setVisibility(View.GONE);
                    kontakt.setVisibility(View.VISIBLE);

                    return true;
            }
            return false;
        }
    };


     class DownLoadImageTask extends AsyncTask<String,Void,Bitmap>{
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }


        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();

                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }

        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        information = (TextView) findViewById(R.id.information);
        kontakt = (TextView) findViewById(R.id.kontakt);
        information.setMovementMethod(new ScrollingMovementMethod());
        programImprez = (TextView) findViewById(R.id.programImprez);
        programImprez.setMovementMethod(new ScrollingMovementMethod());
        imageNews = (ImageView) findViewById(R.id.image_news);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        kontakt.setText("Adres: 00-761 Warszawa,\n" +
                "ul. Belwederska 25\n" +
                "tel./fax (022) 849-27-30\n" +
                "e-mail: ronik@ronik.org.pl\n" +
                "Kursy języka rosyjskiego\n" +
                "tel. (022) 848-04-11\n" +
                "e-mail: kursy@ronik.org.pl\n" +
                "Facebook: fb.com/ronik.warszawa\n" +
                "Vkontakte: vk.com/ronik.warszawa\n" +
                "Instagram: instagram.com/ronik.warszawa");



        ParseNews parseNews = new ParseNews();
        parseNews.execute();
        ParseProgram parseProgram = new ParseProgram();
        parseProgram.execute();

        try {

            String pi = "";
            pi = parseProgram.get();

                programImprez.setText(Html.fromHtml(pi));


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        try {
            ArrayList<News> news = parseNews.get();
            ArrayList<String> arrayList = new ArrayList<>();

            for(News item: news){
                arrayList.add(item.getTitle());
            }

            newsAdapter = new NewsAdapter(this, news);

            // настраиваем список
            listView = (ListView) findViewById(R.id.listView);
            listView.setAdapter(newsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    News n = (News) adapterView.getAdapter().getItem(pos);
                    listView.setVisibility(View.GONE);

                    ParseFullNews parseFullNews = new ParseFullNews(n.getUrl());
                    parseFullNews.execute();

                    try {
                        FullNews fullNews = new FullNews(parseFullNews.get());

                        information.setText(Html.fromHtml(fullNews.getText()));
                        new DownLoadImageTask(imageNews).execute(fullNews.getImg());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }



                  //  new DownLoadImageTask(imageNews).execute(n.getImg());

                    imageNews.setVisibility(View.VISIBLE);
                    information.setVisibility(View.VISIBLE);
                }
            });



          //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);
           // listView.setAdapter(arrayAdapter);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



    }







    }




