package me.paulojr.cadastrosfront.service.factory;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.paulojr.cadastrosfront.service.DataService;
import me.paulojr.cadastrosfront.service.impl.ApiDataService;
import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

public class DataServiceFactory {

    private static final String BASE_URL = "http://127.0.0.1:8091";


    public static DataService getDataService() {
        return ApiDataService.getInstance(BASE_URL, getHttpClient(), getGson());
    }

    private static OkHttpClient getHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true)
                .hostnameVerifier((hostname, session) -> true)
                .callTimeout(1, TimeUnit.MINUTES)
                .build();
    }


    private static Gson getGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).disableHtmlEscaping().create();
    }

}
