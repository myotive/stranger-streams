package com.example.myotive.strangerstreamsdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

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
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Realm realm;
    private MonsterAdapter adapter;

    private CompositeSubscription subscriptions = new CompositeSubscription();

    private EditText searchView;
    private RecyclerView searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();

        VerifyMonstersDatabase();
        BindUI();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SetupSubscriptions();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(subscriptions != null){
            subscriptions.unsubscribe();
        }

        if(realm != null && !realm.isClosed()){
            realm.close();
        }
    }

    private void BindUI(){

        adapter = new MonsterAdapter(null);

        searchResult = (RecyclerView)findViewById(R.id.rv_results);
        searchResult.setLayoutManager(new LinearLayoutManager(this));
        searchResult.setAdapter(adapter);

        searchView = (EditText) findViewById(R.id.txt_search);
    }


    private void SetupSubscriptions() {
        Subscription searchViewSubscription
                = RxTextView
                .textChanges(searchView)
                .debounce(1 , TimeUnit.SECONDS)
                .filter(new Func1<CharSequence, Boolean>() {
                    @Override
                    public Boolean call(CharSequence charSequence) {
                        return charSequence.length() != 0;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<CharSequence, RealmResults<Monster>>() {
                    @Override
                    public RealmResults<Monster> call(CharSequence charSequence) {

                        Realm localInstance = Realm.getDefaultInstance();

                        RealmResults<Monster> query = localInstance.where(Monster.class)
                                .contains("name", charSequence.toString())
                                .findAll();

                        localInstance.close();

                        return query;
                    }
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

            final ProgressDialog progress = new ProgressDialog(this);
            progress.setTitle("Loading");
            progress.setCancelable(false);
            progress.show();

            createMonsterListObservable()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new Func1<Monster, Monster>() {
                        @Override
                        public Monster call(Monster monster) {

                            realm.beginTransaction();
                            realm.copyToRealm(monster);
                            realm.commitTransaction();

                            return monster;
                        }
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

        return Observable.defer(new Func0<Observable<Monster>>() {
            @Override
            public Observable<Monster> call() {
                Gson gson = new Gson();
                StringBuilder buf=new StringBuilder();
                InputStream json;
                List<Monster> monsterList = null;
                try {
                    // Read monsters json file from assets into string buffer
                    json = getAssets().open("5e-SRD-Monsters.json");

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
            }
        });
    }

}
