package com.kinejou.gnoosic.Tools.Internet;

import android.os.AsyncTask;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class CookieFetcher extends AsyncTask<Void, Void, String> {

    @Override
    protected String doInBackground(Void... params) {
        try {
            return Unirest.post("http://www.gnoosic.com/faves.php")
                    .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                    .field("skip", "1")
                    .field("Fave01", "")
                    .field("Fave02", "")
                    .field("Fave03", "")
                    .asString().getHeaders().get("set-cookie");
        } catch (UnirestException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}