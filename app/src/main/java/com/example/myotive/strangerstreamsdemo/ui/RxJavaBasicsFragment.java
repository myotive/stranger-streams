package com.example.myotive.strangerstreamsdemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.myotive.codemash_common.BaseApplication;
import com.example.myotive.codemash_common.network.CodeMashAPI;
import com.example.myotive.codemash_common.network.models.Speaker;
import com.example.myotive.codemash_common.ui.SpeakerAdapter;
import com.example.myotive.strangerstreamsdemo.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class RxJavaBasicsFragment extends Fragment {

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
            codeMashAPI
                    .GetSpeakers()
                    .subscribe(new Subscriber<List<Speaker>>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Toast.makeText(getContext(), "ERROR: Could not make network call on main thread", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(List<Speaker> speakers) {
                            Toast.makeText(getContext(), "Speaker List Count: " + speakers.size(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
