package in.kestone.eventbuddy.http;


import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.model.ScheduleStatusResponse;
import in.kestone.eventbuddy.model.activity_stream_model.PostImageResponse;
import in.kestone.eventbuddy.model.activity_stream_model.Stream;
import in.kestone.eventbuddy.model.activity_stream_model.StreamDatum;
import in.kestone.eventbuddy.model.agenda_model.ModelAgenda;
import in.kestone.eventbuddy.model.app_config_model.AppConf;
import in.kestone.eventbuddy.model.faq_model.MFAQ;
import in.kestone.eventbuddy.model.helpdesk_model.MHelpDesk;
import in.kestone.eventbuddy.model.knowledgebase_model.Knowledge;
import in.kestone.eventbuddy.model.networking_model.MNetworking;
import in.kestone.eventbuddy.model.networking_model.NetworkingList;
import in.kestone.eventbuddy.model.partners_model.PartnerDetail;
import in.kestone.eventbuddy.model.polling_model.Polling;
import in.kestone.eventbuddy.model.polling_model.PostRequest;
import in.kestone.eventbuddy.model.qanda_model.QandA;
import in.kestone.eventbuddy.model.social_model.MSocial;
import in.kestone.eventbuddy.model.speaker_model.Speaker;
import in.kestone.eventbuddy.model.user_model.Profile;
import in.kestone.eventbuddy.model.user_model.User;
import in.kestone.eventbuddy.model.venue_model.MVenue;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("appconfig/{id}")
    Call<AppConf> getAppConfiguration(@Path("id") int id);

    @POST("UserModule")
    Call<User> login(@Body Profile profile);

    @POST("Userotp")
    Call<User> getOtp(@Body Profile profile);

    @GET("agenda/{id}/{userID}")
    Call<ModelAgenda> getAgenda(@Path("id") int id, @Path("userID") long userID);

    @POST("MyAgenda")
    Call<JsonObject> addMyAgenda(@Body HashMap<String, Long> request);

    @POST("MyAgenda/deleteMyAgenda")
    Call<JsonObject> deleteMyAgenda(@Body HashMap<String, Long> request);

    @GET("UserModule/Getusers/speaker")
    Call<Speaker> getAllSpeaker();

    @GET("UserModule/Getusers/delegate")
    Call<Speaker> getAllDelegates();

    @GET("PartnerInventory/{id}")
    Call<PartnerDetail> getPartners(@Path("id") long id);

    @GET("SponsorsInventory/{id}")
    Call<PartnerDetail> getSponsors(@Path("id") long id);

    @GET("FAQInventory/{id}")
    Call<MFAQ> getFAQ(@Path("id") long id);

    @GET("QAInventory/{id}")
    Call<QandA> getQandA(@Path("id") long id);

    @Headers("Content-Type: application/json")
    @POST("QAInventory/{id}")
    Call<JSONObject> postQandA(@Path("id") long id, @Body PostRequest postQandA);

    @GET("KnowledgebaseInventory/{id}")
    Call<Knowledge> getKnowledgeBase(@Path("id") long id);

    @POST("UserModule/{userID}")
    Call<Profile> updateProfile(@Path("userID") int userId, Profile profile);

    @GET("HelpDesk/{id}")
    Call<MHelpDesk> helpDesk(@Path("id") long id);

    @GET("SocialMediaConfiguration/{id}")
    Call<MSocial> social(@Path("id") long id);

    @GET("Tracksession/{id}")
    Call<Polling> getPolling(@Path("id") long id);

    @POST("PollingResponses")
    Call<JSONObject> postPolling(@Body PostRequest postRequest);

    //networking API
    @POST("NetworingRequests/ScheduleRequest")
    Call<MNetworking> schedule(@Body NetworkingList mNetworking);

    @POST("NetworingRequests/ReScheduleRequest")
    Call<MNetworking> reSchedule(@Body NetworkingList mNetworking);

    @GET("NetworingRequests/GetStatus/{id}")
    Call<MNetworking> networkingStatus(@Path("id") int id);

    @POST("NetworingRequests/changestatus/{EBMR_ID}/Approved")
    Call<ScheduleStatusResponse> approve(@Path("EBMR_ID") long EBMR_ID);

    @POST("NetworingRequests/changestatus/{EBMR_ID}/Rejected")
    Call<ScheduleStatusResponse> reject(@Path("EBMR_ID") long EBMR_ID);

    @GET("FeedbackModuleConfiguration/{id}")
    Call<JsonObject> feedBack(@Path("id") long id);

    @GET("VenueConfig/{id}")
    Call<MVenue> venue(@Path("id") long id);

    @GET("ActivityStream/{id}")
    Call<Stream> activityStream(@Path("id") long id);

    @POST("ActivityStream/{id}")
    Call<Stream> postStream(@Path("id") long id, @Body StreamDatum streamData);

    @Multipart
    @POST("ActivityStream/post")
    Call<PostImageResponse> postImageStream(@Part MultipartBody.Part imageFile);

    @GET("TnCModuleConfiguration/{id}")
    Call<JsonObject> termsAndCondition(@Path("id") long eventId);


}
