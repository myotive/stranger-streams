package com.example.myotive.strangerstreamsdemo.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.myotive.strangerstreamsdemo.R;
import com.example.myotive.strangerstreamsdemo.adapters.MonsterAdapter;
import com.example.myotive.strangerstreamsdemo.models.Monster;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class DnDFragment extends Fragment {

    private static final String TAG = DnDFragment.class.getSimpleName();

    private Realm realm;
    private MonsterAdapter adapter;

    private EditText searchView;
    private RecyclerView searchResult;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    public static DnDFragment newInstance() {
        return new DnDFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        realm = Realm.getDefaultInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dn_d, container, false);

        VerifyMonstersDatabase();
        BindUI(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        SetupSubscriptions();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        if(subscriptions != null){
            subscriptions.unsubscribe();
        }

        if(realm != null && !realm.isClosed()){
            realm.close();
        }
    }


    private void BindUI(View view){

        adapter = new MonsterAdapter(null);

        searchResult = (RecyclerView)view.findViewById(R.id.rv_results);
        searchResult.setLayoutManager(new LinearLayoutManager(getContext()));
        searchResult.setAdapter(adapter);

        searchView = (EditText) view.findViewById(R.id.txt_search);
    }


    private void SetupSubscriptions() {
        Subscription searchViewSubscription
                = RxTextView
                .textChanges(searchView)
                .debounce(1 , TimeUnit.SECONDS)
                .filter(charSequence -> charSequence.length() != 0)
                .observeOn(AndroidSchedulers.mainThread())
                .map(charSequence -> {

                    Realm localInstance = Realm.getDefaultInstance();

                    RealmResults<Monster> query = localInstance.where(Monster.class)
                            .contains("name", charSequence.toString())
                            .findAll();

                    localInstance.close();

                    return query;
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RealmResults<Monster>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e);
                    }

                    @Override
                    public void onNext(RealmResults<Monster> monsters) {
                        adapter.setResults(monsters);
                        adapter.notifyDataSetChanged();
                    }
                });

        subscriptions.add(searchViewSubscription);
    }

    private void VerifyMonstersDatabase(){
        RealmQuery<Monster> query = realm.where(Monster.class);
        long monsterCount = query.count();

        if(monsterCount == 0) {

            final ProgressDialog progress = new ProgressDialog(getContext());
            progress.setTitle("Loading");
            progress.setCancelable(false);
            progress.show();

            createMonsterListObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(monster -> {

                        realm.beginTransaction();
                        realm.copyToRealm(monster);
                        realm.commitTransaction();

                        return monster;
                    })
                    .subscribe(new Observer<Monster>() {
                        @Override
                        public void onCompleted() {
                            progress.dismiss();
                        }

                        @Override
                        public void onError(Throwable e) {
                            Timber.e(e);
                            progress.dismiss();
                        }

                        @Override
                        public void onNext(Monster monster) {

                        }
                    });
        }
    }


    private Observable<Monster> createMonsterListObservable(){

        return Observable.defer(() -> {
            Gson gson = new Gson();
            StringBuilder buf=new StringBuilder();
            InputStream json;
            List<Monster> monsterList = null;
            try {
                // Read monsters json file from assets into string buffer
                json = getContext().getAssets().open("5e-SRD-Monsters.json");

                BufferedReader in=
                        new BufferedReader(new InputStreamReader(json, "UTF-8"));
                String str;

                while ((str=in.readLine()) != null) {
                    buf.append(str);
                }
                in.close();

                // parse json string buffer into json array
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(buf.toString()).getAsJsonArray();

                // remove the last element (license node)
                jsonArray.remove(jsonArray.size() - 1);

                Type listType = new TypeToken<List<Monster>>(){}.getType();
                monsterList = gson.fromJson(jsonArray, listType);
            }
            catch (IOException | JsonSyntaxException e) {
                Timber.e(e);
            }

            return monsterList != null  ? Observable.from(monsterList) : Observable.<Monster>empty();
        });
    }
}
