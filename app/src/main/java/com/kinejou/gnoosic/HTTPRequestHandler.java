package com.kinejou.gnoosic;

import android.os.AsyncTask;

import java.io.IOException;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HTTPRequestHandler extends AsyncTask<Request, Void, String[]> {
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected String[] doInBackground(Request... requests) {
        try (Response response = client.newCall(requests[0]).execute()) {
            return new String[]{Objects.requireNonNull(response.body()).string(), String.valueOf(response.headers())};
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
