package com.example.newsapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;


class NewsAppAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<HashMap<String, String>> data;

    public NewsAppAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
    }
    public int getCount() {
        return data.size();
    }
    public Object getItem(int position) {
        return position;
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsAppViewHolder holder = null;
        if (convertView == null) {
            holder = new NewsAppViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.my_list, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.type = (TextView) convertView.findViewById(R.id.type);
            holder.sectionId = (TextView) convertView.findViewById(R.id.title);
            holder.webTitle = (TextView) convertView.findViewById(R.id.webTitle);
            holder.sectionName = (TextView) convertView.findViewById(R.id.sectionName);
            holder.apiUrl = (TextView) convertView.findViewById(R.id.apiUrl);
            holder.id = (TextView) convertView.findViewById(R.id.id);
            holder.webUrl = (TextView) convertView.findViewById(R.id.webUrl);

            convertView.setTag(holder);
        } else {
            holder = (NewsAppViewHolder) convertView.getTag();
        }
        holder.image.setId(position);
        holder.type.setId(position);
        holder.sectionId.setId(position);
        holder.webTitle.setId(position);
        holder.sectionName.setId(position);
        holder.apiUrl.setId(position);
        holder.id.setId(position);
        holder.webUrl.setId(position);


        HashMap<String, String> news = new HashMap<String, String>();
        news = data.get(position);

        try {
            holder.type.setText(news.get(MainActivity.KEY_TYPE));
            holder.sectionId.setText(news.get(MainActivity.KEY_SECTIONID));
            holder.webTitle.setText(news.get(MainActivity.KEY_WEBTITLE));
            holder.sectionName.setText(news.get(MainActivity.KEY_SECTIONNAME));
            holder.apiUrl.setText(news.get(MainActivity.KEY_APIURL));
            holder.id.setText(news.get(MainActivity.KEY_ID));

            if(news.get(MainActivity.KEY_WEBURL).toString().length() < 8)
            {
                holder.image.setVisibility(View.GONE);
            }else{
                Picasso.with(activity)
                        .load(news.get(MainActivity.KEY_WEBURL).toString())
                        .resize(300, 200)
                        .into(holder.image);
            }


        }catch(Exception e) {}
        return convertView;
    }

    }

