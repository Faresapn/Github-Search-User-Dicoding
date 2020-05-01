package com.faresa.githubsearchuser.koneksi;



import com.faresa.githubsearchuser.pojo.UserResponse;
import com.faresa.githubsearchuser.pojo.follower.FollowerResponse;
import com.faresa.githubsearchuser.pojo.following.FollowingResponse;
import com.faresa.githubsearchuser.pojo.search.SearchResponse;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface Service {


   @GET("users/{username}")
   Call<UserResponse> detailUser(@Header("Authorization") String authorization,
                                     @Path("username") String username
   );
   @GET("/search/users")
   Call<SearchResponse> searchUser(@Header("Authorization") String authorization,
                                   @Query("q") String username);

   @GET("users/{username}/followers")
   Call<ArrayList<FollowerResponse>> Follower(@Header("Authorization") String authorization,
                                       @Path("username") String username);

   @DELETE("divisi/{id}")
   Call<FollowingResponse> Following(@Header("Authorization") String authorization,
                                         @Path("id") int id);

}
