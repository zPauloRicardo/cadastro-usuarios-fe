package me.paulojr.cadastrosfront.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.paulojr.cadastrosfront.exceptions.ApiErrorException;
import me.paulojr.cadastrosfront.models.ErrorResponse;
import me.paulojr.cadastrosfront.models.Pagination;
import me.paulojr.cadastrosfront.models.UserApiModel;
import me.paulojr.cadastrosfront.service.DataService;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ApiDataService implements DataService {

    private static final Type PAGINATION_USER = new TypeToken<Pagination<UserApiModel>>() {}.getType();

    private static DataService dataService = null;

    private final String baseUrl;
    private final HttpService httpService;
    private final Gson gson;

    private ApiDataService(String baseUrl, OkHttpClient okHttpClient, Gson gson) {
        this.baseUrl = baseUrl;
        this.httpService = new HttpService(okHttpClient);
        this.gson = gson;
    }

    public static DataService getInstance(String baseUrl, OkHttpClient cliente, Gson gson) {
        if (dataService == null) {
            dataService = new ApiDataService(baseUrl, cliente, gson);
        }
        return dataService;
    }

    private String getFinalUrl(String endpoint) {
        return this.baseUrl.concat(endpoint);
    }

    @Override
    public List<UserApiModel> getAll(Integer page) {
        HttpUrl url = HttpUrl.parse(this.getFinalUrl("/v1/user"))
                .newBuilder()
                .addQueryParameter("page", page.toString())
                .build();

        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try {
            Response response = this.httpService.executeRequest(request);
            String body = this.httpService.getAsString(response);
            Pagination<UserApiModel> pagination = this.gson.fromJson(body, PAGINATION_USER);
            return pagination.items();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao obter dados. Verifique a rede.", e);
        }
    }

    @Override
    public UserApiModel save(UserApiModel user) {
        return executeSaveOrUpdate(user, "/v1/user", "POST");
    }

    @Override
    public UserApiModel update(UserApiModel user) {
        return executeSaveOrUpdate(user, "/v1/user/" + user.id(), "PUT");
    }

    private UserApiModel executeSaveOrUpdate(UserApiModel user, String endpoint, String method) {
        String url = this.getFinalUrl(endpoint);
        String jsonBody = this.gson.toJson(user);

        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request.Builder requestBuilder = new Request.Builder().url(url);

        if (method.equals("POST")) {
            requestBuilder.post(body);
        } else if (method.equals("PUT")) {
            requestBuilder.put(body);
        }

        try {
            Response response = this.httpService.executeRequest(requestBuilder.build());
            if (response.isSuccessful()) {
                String responseBody = this.httpService.getAsString(response);
                return this.gson.fromJson(responseBody, UserApiModel.class);
            } else {
                String errorBody = this.httpService.getAsString(response);
                ErrorResponse errorResponse = this.gson.fromJson(errorBody, ErrorResponse.class);
                throw new ApiErrorException(errorResponse);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar ou atualizar o usuário. Verifique a rede.", e);
        }
    }
}
