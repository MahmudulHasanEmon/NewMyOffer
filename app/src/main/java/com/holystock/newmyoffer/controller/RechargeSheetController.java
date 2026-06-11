package com.holystock.newmyoffer.controller;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.model.TextSegment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RechargeSheetController {

    private final Activity activity;
    private final ArrayList<Map<String, Object>> items;

    private BottomSheetDialog dialog;
    private OnItemSelectedListener listener;
    public RechargeSheetController(Activity activity) {

        this.activity = activity;
        this.items = new ArrayList<>();
    }

    public RechargeSheetController addItem(
            String title,
            int icon
    ) {

        Map<String, Object> map =
                new HashMap<>();

        map.put("title", title);
        map.put("icon", icon);

        items.add(map);

        return this;
    }

    public RechargeSheetController onItemSelected(
            OnItemSelectedListener listener
    ) {

        this.listener = listener;
        return this;
    }

    public void show() {

        dialog =
                new BottomSheetDialog(activity);

        View view =
                LayoutInflater.from(activity)
                        .inflate(
                                R.layout.bottom_sheet_recharge,
                                null
                        );

        TextView t1 =
                view.findViewById(R.id.t1);

        ArrayList<TextSegment> segments =
                new ArrayList<>();

        segments.add(
                new TextSegment(
                        "অপারেটর বেছে নিন"
                ).setTextColor(Color.BLACK)
        );

        segments.add(
                new TextSegment(
                        "\nএই নাম্বারের বর্তমান অপারেটর বেছে নিন"
                )
                        .setTextColor(Color.GRAY)
                        .setTextSize(13)
        );

        RichTextBuilder.apply(
                t1,
                segments
        );

        view.findViewById(R.id.cancelButton)
                .setOnClickListener(v ->
                        dialog.dismiss()
                );

        GridView gridView =
                view.findViewById(R.id.gridView);

        GridAdapter adapter =
                new GridAdapter();

        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(
                (parent, itemView, position, id) -> {

                    Map<String, Object> item =
                            items.get(position);

                    if (listener != null) {

                        listener.onItemSelected(
                                item.get("title")
                                        .toString(),

                                (Integer) item.get("icon"),

                                position
                        );
                    }

                    dialog.dismiss();
                }
        );

        dialog.setContentView(view);
        dialog.show();
    }

    public interface OnItemSelectedListener {

        void onItemSelected(
                String title,
                int icon,
                int position
        );
    }

    private class GridAdapter
            extends BaseAdapter {

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(
                int position
        ) {
            return items.get(position);
        }

        @Override
        public long getItemId(
                int position
        ) {
            return position;
        }

        @Override
        public View getView(
                int position,
                View convertView,
                ViewGroup parent
        ) {

            ViewHolder holder;

            if (convertView == null) {

                convertView =
                        LayoutInflater.from(
                                activity
                        ).inflate(
                                R.layout.recharge_operator_item,
                                parent,
                                false
                        );

                holder = new ViewHolder();

                holder.title =
                        convertView.findViewById(
                                R.id.title
                        );

                holder.rootLayout =
                        convertView.findViewById(
                                R.id.rootLayout
                        );

                holder.icon =
                        convertView.findViewById(
                                R.id.icon
                        );

                convertView.setTag(holder);

            } else {

                holder =
                        (ViewHolder)
                                convertView.getTag();
            }

            Map<String, Object> item =
                    items.get(position);

            holder.title.setText(
                    item.get("title")
                            .toString()
            );

            holder.icon.setImageResource(
                    (Integer) item.get("icon")
            );


            holder.rootLayout.setOnClickListener(v -> {
                if (listener !=null){
                    listener.onItemSelected((String) item.get("title"), (Integer) item.get("icon"), position);
                    dialog.dismiss();
                }

            });


            return convertView;
        }

        class ViewHolder {
            TextView title;
            ImageView icon;
            LinearLayout rootLayout;
        }
    }
}