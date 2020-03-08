package com.kinejou.gnoosic;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

class GnoosicHelper {
    private static GnoosicHelper gnoosicInstance;
    private String cookie;
    private final String gnoosic_home = "http://www.gnoosic.com";
    private final String base_host = "http://www.gnoosic.com/faves.php";
    private final String artist_host = "http://www.gnoosic.com/artist/";

    private GnoosicHelper() {
        gnoosicInstance = this;
    }

    static GnoosicHelper getInstance() {
        if (gnoosicInstance != null)
            return gnoosicInstance;

        return new GnoosicHelper();
    }

    String[] getTypeAheadSuggestion(String query) throws ExecutionException, InterruptedException {
        Request request = new Request.Builder().url("http://cdn.gnoosic.com/typeahead?query=" + query)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                .header("Accept", "*/*")
                .build();

        return new HTTPRequestHandler().execute(request).get()[0].split("\n");
    }

    void getNewBandFrom3Bands(@NotNull String... bands) throws ExecutionException, InterruptedException {
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
                .post(body)
                .build();

        String[] response = new HTTPRequestHandler().execute(request).get();

        Log.d("Response", response[0]);
        Log.d("Response", response[1]);
    }
}
