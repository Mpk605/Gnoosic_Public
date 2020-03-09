package com.kinejou.gnoosic.Tools.Internet.GnoosicAPI;

import android.os.AsyncTask;

import com.kinejou.gnoosic.Tools.Internet.AsyncResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetNewBandFrom3Bands extends AsyncTask<String, Void, Integer> {
    private OkHttpClient client = new OkHttpClient();
    private String[] newBand;

    /**
     * Return new band based on 3 others
     *
     * @param bands bands
     * @return int result code
     */
    @Override
    protected Integer doInBackground(String... bands) {
        RequestBody body = new FormBody.Builder()
                .add("skip", "1")
                .add("Fave01", bands[0])
                .add("Fave02", bands[1])
                .add("Fave03", bands[2])
                .build();
        String artist_host = "http://www.gnoosic.com/artist/";
        Request request = new Request.Builder().url(artist_host)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .header("Accept-Language", "en-US,en;q=0.9,fr-FR;q=0.8,fr;q=0.7")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Cookie", GnoosicHelper.getInstance().getCookie())
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Document doc = Jsoup.parse(Objects.requireNonNull(response.body()).string());

            newBand = new String[]{doc.getElementById("result").html(), doc.select("input[name=SuppID]").attr("value")};

            return 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public AsyncResponse delegate = null;

    @Override
    protected void onPostExecute(Integer result) {
        if (delegate != null)
            delegate.processFinish(newBand);
    }
}
