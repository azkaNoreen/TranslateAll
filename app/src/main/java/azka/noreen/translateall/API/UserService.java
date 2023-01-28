package azka.noreen.translateall.API;

import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {
    @POST("translate_a/single?client=gtx&dt=t&ie=UTF-8&oe=UTF-8")
    Call<JsonArray> getTrans(
        @Query("sl") String sl,
        @Query("tl") String tl,
        @Query("q") String q

        );

//    @GET("public/v2/users")
//    Call<List<Users>> getAllUsers();
//    @GET("public/v2/users/{id}")
//    Call<Users> getUserDetail(@Path("id") int id);
}
