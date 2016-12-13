package com.example.myotive.codemash_common.network;

import com.example.myotive.codemash_common.network.models.Session;
import com.example.myotive.codemash_common.network.models.Speaker;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by michaelyotive_hr on 12/3/16.
 */

public interface CodeMashAPI {
    @GET("api/SessionsData")
    Observable<List<Session>> GetSessions();

    @GET("api/SessionsData/{id}")
    Observable<Session> GetSessionById(@Path("id") UUID id);

    @GET("api/SpeakersData")
    Observable<List<Speaker>> GetSpeakers();

    @GET("api/SpeakersData/{id}")
    Observable<Speaker> GetSpeakerById(@Path("id") UUID id);
}
