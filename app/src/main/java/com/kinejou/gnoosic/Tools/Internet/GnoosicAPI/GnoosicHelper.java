package com.kinejou.gnoosic.Tools.Internet.GnoosicAPI;

import com.kinejou.gnoosic.Tools.Internet.CookieFetcher;
import com.kinejou.gnoosic.Tools.Internet.HTTPRequestHandler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.concurrent.ExecutionException;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

public class GnoosicHelper {
    private static GnoosicHelper gnoosicInstance;
    private String cookie;

    private GnoosicHelper() {
        gnoosicInstance = this;
    }

    public static GnoosicHelper getInstance() {
        if (gnoosicInstance != null)
            return gnoosicInstance;

        return new GnoosicHelper();
    }

    public String getCookie() {
        return this.cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String[] getTypeAheadSuggestion(String query) throws ExecutionException, InterruptedException {
        return new HTTPRequestHandler().execute(new Request.Builder().url("http://cdn.gnoosic.com/typeahead?query=" + query)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36")
                .header("Accept", "*/*")
                .build()).get().split("\n");
    }
}
