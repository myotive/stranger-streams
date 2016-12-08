package com.example.myotive.strangerstreamsdemo.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myotive.codemash_common.BaseApplication;
import com.example.myotive.codemash_common.network.CodeMashAPI;
import com.example.myotive.codemash_common.network.models.Speaker;
import com.example.myotive.codemash_common.ui.SpeakerAdapter;
import com.example.myotive.strangerstreamsdemo.R;

import java.util.Collections;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class CodeMashFragment extends Fragment {

    public static CodeMashFragment newInstance() {
        return new CodeMashFragment();
    }

    private CodeMashAPI codeMashAPI;
    private RecyclerView speakerRecyclerView;
    private SpeakerAdapter speakerAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        codeMashAPI = BaseApplication.getApplication(context).getApplicationComponent().CodeMashAPI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_codemash, container, false);

        speakerAdapter = new SpeakerAdapter(getContext(), Collections.<Speaker>emptyList());

        speakerRecyclerView = (RecyclerView)view.findViewById(R.id.rv_speakers);
        speakerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        speakerRecyclerView.setAdapter(speakerAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        codeMashAPI
                .GetSpeakers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Speaker>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onNext(List<Speaker> speakers) {
                        speakerAdapter.swap(speakers);
                    }
                });
    }
}
