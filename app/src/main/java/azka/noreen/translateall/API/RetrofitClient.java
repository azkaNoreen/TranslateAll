package azka.noreen.translateall.API;

import java.util.concurrent.TimeUnit;

import azka.noreen.translateall.API.UserService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static String baseURL= "https://translate.googleapis.com/";
    public UserService getUserService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(UserService.class);
    }
    private OkHttpClient makeOKhttp(){
        return new OkHttpClient.Builder().connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();

    }
}
