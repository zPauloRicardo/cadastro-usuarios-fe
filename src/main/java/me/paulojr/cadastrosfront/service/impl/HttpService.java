package me.paulojr.cadastrosfront.service.impl;

import okhttp3.*;
import java.io.IOException;
import java.util.Optional;

public class HttpService {

    private final OkHttpClient okHttpClient;

    public HttpService(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public Response executeRequest(Request request) throws IOException {
        Call call = this.okHttpClient.newCall(request);
        return call.execute();
    }

    public String getAsString(Response response) {
        return Optional.of(response.body())
                .map(responseBody -> {
                    try {
                        return responseBody.string();
                    } catch (IOException e) {
                        return "{}";
                    }
                }).orElse("{}");
    }
}

