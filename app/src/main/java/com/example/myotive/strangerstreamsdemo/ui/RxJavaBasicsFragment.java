package com.example.myotive.strangerstreamsdemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myotive.codemash_common.BaseApplication;
import com.example.myotive.codemash_common.network.CodeMashAPI;
import com.example.myotive.codemash_common.network.models.Speaker;
import com.example.myotive.strangerstreamsdemo.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

public class RxJavaBasicsFragment extends Fragment {

    private static final String TAG = RxJavaBasicsFragment.class.getSimpleName();

    public static RxJavaBasicsFragment newInstance() {
        return new RxJavaBasicsFragment();
    }

    private Button btnJust, btnFrom, btnMap, btnNetworkCall;

    private CodeMashAPI codeMashAPI;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        codeMashAPI = BaseApplication.getApplication(context).getApplicationComponent().CodeMashAPI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_rxbasic, container, false);

        SetupUI(view);

        return view;
    }

    private void SetupUI(View view) {

        btnJust = (Button)view.findViewById(R.id.button_just);
        btnJust.setOnClickListener(v -> {
            Observable.just(1)
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer i) {
                            Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        btnFrom = (Button)view.findViewById(R.id.button_from);
        btnFrom.setOnClickListener(v -> {
            Observable.from(Arrays.asList(1, 2, 3, 4))
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer i) {
                            Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        btnMap = (Button)view.findViewById(R.id.button_map);
        btnMap.setOnClickListener(v -> {

            Observable.just("Eleven", "Mike", "Dustin", "Will", "Lucas")
                    .map(s -> s.length())
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(Integer i) {
                            Toast.makeText(getContext(), String.valueOf(i), Toast.LENGTH_SHORT).show();
                        }
                    });


        });

        btnNetworkCall = (Button)view.findViewById(R.id.button_network_call);
        btnNetworkCall.setOnClickListener(v -> {

            createSpeakerObservable()
                    .subscribe(new Subscriber<List<Speaker>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "Error calling CodeMash api", e);

                            displayError();
                        }

                        @Override
                        public void onNext(List<Speaker> speakers) {
                            Toast.makeText(getContext(),
                                    "Number of speakers" + String.valueOf(speakers.size()),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }

    /**
     * Create an observable that uses OkHttp3 to call the codemash api
     * The codemash api will return a JSON response of speaker data.
     * We can use GSON to deserialize this response into an object and emit via
     * the observable contract.
     * @return
     */
    private Observable<List<Speaker>> createSpeakerObservable(){
        return Observable.create(subscriber -> {
            List<Speaker> speakers = new ArrayList<>();

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://speakers.codemash.org/api/SpeakersData")
                    .get()
                    .build();
            try {

                Response response = client.newCall(request).execute();
                Gson gson = new Gson();

                speakers = gson.fromJson(response.body().string(),
                        new TypeToken<List<Speaker>>(){}.getType());

            } catch (Exception e) {
                subscriber.onError(e);
            }

            subscriber.onNext(speakers);
            subscriber.onCompleted();
        });
    }

    private void displayError() {
        Handler mainThread = new Handler(getContext().getMainLooper());
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),
                        "Error calling codemash api!!",
                        Toast.LENGTH_LONG).show();
            }
        };

        mainThread.post(runnable);
    }
}
