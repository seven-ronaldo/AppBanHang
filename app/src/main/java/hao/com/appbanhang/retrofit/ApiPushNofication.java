package hao.com.appbanhang.retrofit;

import hao.com.appbanhang.model.NotiResponse;
import hao.com.appbanhang.model.NotiSendData;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNofication {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAtz10aXw:APA91bEioHEl0TR_gULE2Pk5on9AyD9QL8RrHPRlI08HourlMgXIs6hDXtIMJ3cJMnKZdGdIqIpiAdAIvBSwAto8FDkO7O0lTNZQdQpgDKDD5ZY-GDB7JuU3M2qfSZt84y6RpxTXXlGy"
            }
    )
    @POST("fcm/send")
    Observable<NotiResponse> sendNofication(@Body NotiSendData data);
}
