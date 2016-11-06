package com.example.myotive.strangerstreamsdemo.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myotive.strangerstreamsdemo.R;
import com.example.myotive.strangerstreamsdemo.models.Monster;

import io.realm.RealmResults;

/**
 * Created by myotive on 10/17/2016.
 */

public class MonsterAdapter extends RecyclerView.Adapter<MonsterAdapter.MonsterViewHolder> {

    private RealmResults<Monster>  mMonsterList;

    public MonsterAdapter(RealmResults<Monster> query){
        mMonsterList = query;
    }

    @Override
    public MonsterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_monster, parent, false);

        return new MonsterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MonsterViewHolder holder, int position) {
        Monster monster = mMonsterList.get(position);

        holder.name.setText(monster.getName());
    }

    @Override
    public int getItemCount() {
        return mMonsterList != null ? mMonsterList.size() : 0;
    }

    public void setResults(RealmResults<Monster> mMonsterList) {
        this.mMonsterList = mMonsterList;
    }

    public class MonsterViewHolder extends RecyclerView.ViewHolder
    {

        TextView name;

        public MonsterViewHolder(View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.item_monster_name);
        }
    }
}
