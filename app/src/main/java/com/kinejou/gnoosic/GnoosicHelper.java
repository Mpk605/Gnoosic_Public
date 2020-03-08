package com.kinejou.gnoosic;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GnoosicHelper extends AsyncTask<String, Void, Void> {
    private final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    private static GnoosicHelper gnoosicInstance;
    private String cookie;
    private OkHttpClient client = new OkHttpClient();
    private String base_host = "http://www.gnoosic.com/faves.php";
    private String artist_host = "http://www.gnoosic.com/artist/";

    private GnoosicHelper() {

    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            getNewBandFrom3Bands(strings);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GnoosicHelper getInstance() {
        if (gnoosicInstance != null)
            return gnoosicInstance;

        return new GnoosicHelper();
    }

    public void getNewBandFrom3Bands(String... bands) throws IOException {
        RequestBody body = new FormBody.Builder()
                .add("skip", "1")
                .add("Fave01", bands[0])
                .add("Fave02", bands[1])
                .add("Fave03", bands[2])
                .build();
        Request request = new Request.Builder().url(base_host)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .header("Accept-Language", "en-US,en;q=0.9,fr-FR;q=0.8,fr;q=0.7")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Cookie", "_ga=GA1.2.157438752.1583397522; _gid=GA1.2.169487856.1583492640")
                .post(body)
                .build();

        System.out.println(request.headers().toString());

        try (Response response = client.newCall(request).execute()) {
            System.out.println(Objects.requireNonNull(response.headers()).toString());
        }
    }
}
