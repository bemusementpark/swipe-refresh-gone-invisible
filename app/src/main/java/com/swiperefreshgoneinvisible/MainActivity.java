package com.swiperefreshgoneinvisible;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TITLE_NO_ADAPTER = "no adapter";
    private static final String TITLE_EMPTY = "empty";
    private static final String TITLE_ITEMS = "items";
    private static final String TITLE_ZERO_HEIGHT_HEADER = "zero height header";
    private static final String TITLE_INVISIBLE = "invisible";
    private static final String TITLE_GONE = "gone";

    String[] titles = {TITLE_NO_ADAPTER, TITLE_EMPTY, TITLE_ITEMS, TITLE_ZERO_HEIGHT_HEADER, TITLE_INVISIBLE, TITLE_GONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((ViewGroup) findViewById(R.id.top)).addView(createListViewLayout());
        ((ViewGroup) findViewById(R.id.bottom)).addView(createRecyclerViewLayout());
    }

    private View createListViewLayout() {
        LinearLayout linearLayout = new LinearLayout(this);

        Map<String, ListView> map = new HashMap<>();

        for (String title : titles) {
            FrameLayout frame = new FrameLayout(this);

            frame.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

            SwipeRefreshLayout swipe = new SwipeRefreshLayout(this);
            ListView listView = new ListView(this);
            map.put(title, listView);
            swipe.addView(listView);

            TextView txt = new TextView(this);
            txt.setGravity(Gravity.CENTER);
            txt.setText(title);
            frame.addView(swipe);
            frame.addView(txt);

            linearLayout.addView(frame);
        }

        map.get(TITLE_EMPTY).setAdapter(new EmptyAdapter(this));
        map.get(TITLE_ITEMS).setAdapter(new ItemsAdapter());
        map.get(TITLE_ZERO_HEIGHT_HEADER).setAdapter(new ZeroHeightHeaderAdapter());

        map.get(TITLE_INVISIBLE).setAdapter(new ItemsAdapter());
        map.get(TITLE_INVISIBLE).setVisibility(View.INVISIBLE);

        map.get(TITLE_GONE).setAdapter(new ItemsAdapter());
        map.get(TITLE_GONE).setVisibility(View.GONE);

        return linearLayout;
    }

    private View createRecyclerViewLayout() {
        LinearLayout linearLayout = new LinearLayout(this);

        Map<String, RecyclerView> map = new HashMap<>();

        for (String title : titles) {
            FrameLayout frame = new FrameLayout(this);

            frame.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f));

            SwipeRefreshLayout swipe = new SwipeRefreshLayout(this);
            RecyclerView recyclerView = new RecyclerView(this);
            map.put(title, recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            swipe.addView(recyclerView);

            TextView txt = new TextView(this);
            txt.setGravity(Gravity.CENTER);
            txt.setText(title);
            frame.addView(swipe);
            frame.addView(txt);

            linearLayout.addView(frame);

        }

        map.get(TITLE_EMPTY).setAdapter(new RecyclerEmptyAdapter());
        map.get(TITLE_ITEMS).setAdapter(new RecyclerItemsAdapter());
        map.get(TITLE_ZERO_HEIGHT_HEADER).setAdapter(new RecyclerZeroHeightHeaderAdapter());

        map.get(TITLE_INVISIBLE).setAdapter(new RecyclerItemsAdapter());
        map.get(TITLE_INVISIBLE).setVisibility(View.INVISIBLE);

        map.get(TITLE_GONE).setAdapter(new RecyclerItemsAdapter());
        map.get(TITLE_GONE).setVisibility(View.GONE);

        return linearLayout;
    }

    class EmptyAdapter extends ArrayAdapter {
        public EmptyAdapter(Context c) {
            super(c, 0);
        }
    }

    class ItemsAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView t = new TextView(viewGroup.getContext());
            t.setText("item: " + i);
            return t;
        }
    }

    private class ZeroHeightHeaderAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (i == 0) {
                return getLayoutInflater().inflate(R.layout.zero_height, viewGroup, false);
            } else {
                TextView t = new TextView(viewGroup.getContext());
                t.setText("item: " + i);
                return t;
            }
        }
    }

    private class RecyclerEmptyAdapter extends RecyclerView.Adapter {

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private class RecyclerItemsAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(new TextView(parent.getContext())) {
            };
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TextView tv = (TextView) holder.itemView;
            tv.setText("item: " + holder.getAdapterPosition());
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

    private class RecyclerZeroHeightHeaderAdapter extends RecyclerView.Adapter {
        @Override
        public int getItemViewType(int position) {
            return Math.min(position, 1);
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 0) {
                return new RecyclerView.ViewHolder(getLayoutInflater().inflate(R.layout.zero_height, parent, false)) {
                };
            } else {
                return new RecyclerView.ViewHolder(new TextView(parent.getContext())) {
                };
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (position > 0) {
                TextView tv = (TextView) holder.itemView;
                tv.setText("item: " + holder.getAdapterPosition());
            }
        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }
}
