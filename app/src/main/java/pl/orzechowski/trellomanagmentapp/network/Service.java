package pl.orzechowski.trellomanagmentapp.network;


import java.util.List;

import pl.orzechowski.trellomanagmentapp.models.TrelloBoard;
import pl.orzechowski.trellomanagmentapp.models.TrelloCard;
import pl.orzechowski.trellomanagmentapp.network.responses.BaseListResponse;
import pl.orzechowski.trellomanagmentapp.network.responses.BaseResponse;
import pl.orzechowski.trellomanagmentapp.network.responses.MBase;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Service {


    @GET("boards/{board_id}?fields=name")
    Call<TrelloBoard> getBoard(
            @Path("board_id") String board_id,
            @Query("lists") String lists,
            @Query("list_fields") String list_fields,
            @Query("key") String key,
            @Query("token") String token
    );

    @GET("lists/{list_id}/cards?fields=id,name,desc")
    Call<List<TrelloCard>> getCards(
            @Path("list_id") String list_id,
            @Query("key") String key,
            @Query("token") String token
    );

    @DELETE("cards/{card_id}?")
    Call<BaseResponse<MBase>> deleteCard(
            @Path("card_id") String card_id,
            @Query("key") String key,
            @Query("token") String token
    );

    @PUT("cards/{card_id}?")
    Call<BaseResponse<MBase>> updateCard(
            @Path("card_id") String card_id,
            @Query("name") String name,
            @Query("desc") String desc,
            @Query("idList") String idList,
            @Query("key") String key,
            @Query("token") String token
    );

    @POST("cards?")
    Call<BaseResponse<MBase>> addCard(
            @Query("name") String name,
            @Query("desc") String desc,
            @Query("idList") String idList,
            @Query("keepFromSource") String keepFromSource,
            @Query("key") String key,
            @Query("token") String token
    );
}