package com.android.facefilter;

/**
 * Created by nisar on 16-06-2017.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Fragment {

    RecyclerView horizontal_recycler_view;
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    HorizontalAdapter horizontalAdapter;
    private List<Data> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // setContentView(R.layout.activity_main);
        View v = getView();
        horizontal_recycler_view= (RecyclerView)v.findViewById(R.id.horizontal_recycler_view);
        data = fill_with_data();
        horizontalAdapter=new HorizontalAdapter(data,getActivity());

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManager);
        horizontal_recycler_view.setAdapter(horizontalAdapter);
    }


    public List<Data> fill_with_data() {

        List<Data> data = new ArrayList<>();

        data.add(new Data( R.drawable.hair, "Hair"));
        data.add(new Data( R.drawable.glasses, "Glasses"));
        data.add(new Data( R.drawable.cap, "Cap"));
        data.add(new Data( R.drawable.beard, "Beard"));
        data.add(new Data( R.drawable.beard_one, "Beard 1"));
        data.add(new Data( R.drawable.goku_hair, "GokuHair"));
        data.add(new Data( R.drawable.blue_goggles, "Goggles"));
        data.add(new Data( R.drawable.goku, "Gokuhair2"));
        data.add(new Data( R.drawable.hair_blonde, "Hair 2"));
        data.add(new Data( R.drawable.long_hair, "long Hair"));
        data.add(new Data( R.drawable.moustache_glasses, "glasses2"));
        data.add(new Data( R.drawable.beard_three, "beard 2"));
        return data;
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {


        List<Data> horizontalList = Collections.emptyList();
        Context context;


        public HorizontalAdapter(List<Data> horizontalList, Context context) {
            this.horizontalList = horizontalList;
            this.context = context;
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView txtview;
            public MyViewHolder(View view) {
                super(view);
                imageView=(ImageView) view.findViewById(R.id.imageview);
                txtview=(TextView) view.findViewById(R.id.txtview);
            }
        }



        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_menu, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.imageView.setImageResource(horizontalList.get(position).imageId);
            holder.txtview.setText(horizontalList.get(position).txt);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    String list = horizontalList.get(position).txt.toString();
                    Toast.makeText(getActivity(), list, Toast.LENGTH_SHORT).show();
                    FaceFilterActivity.imageid = horizontalList.get(position).imageId;
                }

            });

        }


        @Override
        public int getItemCount()
        {
            return horizontalList.size();
        }
    }
}
