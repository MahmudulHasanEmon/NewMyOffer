package com.holystock.newmyoffer.activity.home.page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.utils.HorizontalSpaceItemDecoration;
import com.holystock.newmyoffer.views.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePage extends Fragment {
    private ExpandableHeightGridView gridView;
    private ExpandableHeightGridView trxGridView;
    private RecyclerView specialRecyclerView;
    private List<Map<String, Object>> list;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {

        View root = inflater.inflate(
                R.layout.home_page,
                container,
                false
        );

        gridView = root.findViewById(R.id.gridView);
        trxGridView = root.findViewById(R.id.trxGridView);

        specialRecyclerView =
                root.findViewById(R.id.specialRecyclerView);

        gridView.setExpanded(true);
        trxGridView.setExpanded(true);

        list = new ArrayList<>();

        addItem("এড মানি", R.drawable.wallet);
        addItem("মোবাইল রিচার্জ", R.drawable.mobile);
        addItem("সেন্ড মানি", R.drawable.send);
        addItem("বিল পেমেন্ট", R.drawable.bill);
        addItem("ব্যাংক ট্রান্সফার", R.drawable.bank_transfer);
        addItem("সিম অফার", R.drawable.offer);

        gridView.setAdapter(
                new GridAdapter()
        );

        trxGridView.setAdapter(
                new TransactionAdapter()
        );

        specialRecyclerView.setLayoutManager(
                new LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false
                )
        );

        specialRecyclerView.addItemDecoration(
                new HorizontalSpaceItemDecoration(
                        getResources().getDimensionPixelSize(R.dimen._10dp)
                )
        );

        specialRecyclerView.setAdapter(
                new SpecialOfferAdapter()
        );

        return root;
    }

    private void addItem(
            String title,
            int icon
    ) {

        Map<String, Object> map =
                new HashMap<>();

        map.put("title", title);
        map.put("icon", icon);

        list.add(map);
    }

    //========================
    // Grid Adapter
    //========================

    private class GridAdapter
            extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(
                int position
        ) {
            return list.get(position);
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
                                getContext()
                        ).inflate(
                                R.layout.home_grid_item,
                                parent,
                                false
                        );

                holder = new ViewHolder();

                holder.title =
                        convertView.findViewById(
                                R.id.title
                        );

                holder.icon =
                        convertView.findViewById(
                                R.id.icon
                        );

                convertView.setTag(
                        holder
                );

            } else {

                holder =
                        (ViewHolder)
                                convertView.getTag();
            }

            Map<String, Object> item =
                    list.get(position);

            holder.title.setText(
                    item.get("title")
                            .toString()
            );

            holder.icon.setImageResource(
                    (Integer) item.get("icon")
            );

            return convertView;
        }

        class ViewHolder {

            TextView title;
            ImageView icon;
        }
    }

    //========================
    // Transaction Grid Adapter
    //========================

    private class TransactionAdapter
            extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(
                int position
        ) {
            return position;
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

            if (convertView == null) {

                convertView =
                        LayoutInflater.from(
                                getContext()
                        ).inflate(
                                R.layout.transaction_item,
                                parent,
                                false
                        );
            }

            return convertView;
        }
    }

    //========================
    // Special Offer Recycler
    //========================

    private static class SpecialOfferAdapter
            extends RecyclerView.Adapter<
            SpecialOfferAdapter.SpecialOfferHolder> {

        @NonNull
        @Override
        public SpecialOfferHolder onCreateViewHolder(
                @NonNull ViewGroup parent,
                int viewType
        ) {

            View view =
                    LayoutInflater.from(
                            parent.getContext()
                    ).inflate(
                            R.layout.special_offer_card,
                            parent,
                            false
                    );

            return new SpecialOfferHolder(
                    view
            );
        }

        @Override
        public void onBindViewHolder(
                @NonNull SpecialOfferHolder holder,
                int position
        ) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        class SpecialOfferHolder
                extends RecyclerView.ViewHolder {

            public SpecialOfferHolder(
                    @NonNull View itemView
            ) {
                super(itemView);
            }
        }
    }
}