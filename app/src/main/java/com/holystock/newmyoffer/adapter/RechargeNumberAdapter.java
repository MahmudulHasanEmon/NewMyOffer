package com.holystock.newmyoffer.adapter;
/*

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.model.Contact;

import java.util.ArrayList;

public class RechargeNumberAdapter
        extends RecyclerView.Adapter<RechargeNumberAdapter.RechargeNumberHolder> {

    private final Context context;
    private final ArrayList<Contact> contacts;

    public RechargeNumberAdapter(
            Context context,
            ArrayList<Contact> contacts
    ) {
        this.context = context;
        this.contacts = new ArrayList<>(contacts);
    }

    @NonNull
    @Override
    public RechargeNumberHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_recharge_number,
                        parent,
                        false
                );

        return new RechargeNumberHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RechargeNumberHolder holder,
            int position
    ) {

        Contact contact = contacts.get(position);

        holder.tvName.setText(contact.getName());

        if (contact.getPhones() != null
                && !contact.getPhones().isEmpty()) {

            if (contact.getPhones().size() == 1) {

                holder.tvNumber.setText(
                        contact.getPhones().get(0)
                );

                holder.arrow.setVisibility(GONE);

            } else {

                holder.tvNumber.setText(
                        contact.getPhones().size() + " টি সেভ করা নাম্বার"
                );

                holder.arrow.setVisibility(VISIBLE);
                bindPhoneNumbers(
                        holder.numberContainer,
                        contact.getPhones()
                );

            }
        }

        String image = contact.getImage();

        if (image != null && !image.isEmpty()) {

            Glide.with(context)
                    .load(image)
                    .circleCrop()
                    .placeholder(R.drawable.bank_transfer)
                    .into(holder.ivPhoto);

        } else {

            holder.ivPhoto.setImageResource(
                    R.drawable.bank_transfer
            );
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setData(
            ArrayList<Contact> newContacts
    ) {

        contacts.clear();
        contacts.addAll(newContacts);

        notifyDataSetChanged();
    }

    public Contact getItem(int position) {

        if (position < 0 || position >= contacts.size()) {
            return null;
        }

        return contacts.get(position);
    }

    class RechargeNumberHolder
            extends RecyclerView.ViewHolder {

        ImageView ivPhoto, arrow;
        TextView tvName;
        TextView tvNumber;

        LinearLayout numberContainer;

        RelativeLayout mainLayout;

        public RechargeNumberHolder(
                @NonNull View itemView
        ) {
            super(itemView);

            ivPhoto =
                    itemView.findViewById(R.id.ivPhoto);

            arrow =
                    itemView.findViewById(R.id.arrow_down);

            tvName =
                    itemView.findViewById(R.id.tvName);

            tvNumber =
                    itemView.findViewById(R.id.tvNumber);

            numberContainer =
                    itemView.findViewById(R.id.numberContainer);
            mainLayout =
                    itemView.findViewById(R.id.mainLayout);

            mainLayout.setOnClickListener(v -> {

                int position =
                        getBindingAdapterPosition();

                if (position == RecyclerView.NO_POSITION) {
                    return;
                }

                Contact contact =
                        contacts.get(position);

                // click event
                if (contact.getPhones().size() != 1) {

                    if (numberContainer.getVisibility() == VISIBLE) {
                        collapse(numberContainer);
                        arrow.setImageResource(R.drawable.keyboard_arrow_up_24dp);
                        arrow.setImageTintList(context.getColorStateList(R.color.primary));

                    } else {
                        expand(numberContainer);
                        arrow.setImageResource(R.drawable.keyboard_arrow_down_24dp);
                    }
                }



            });
        }
    }


    private void bindPhoneNumbers(
            LinearLayout container,
            ArrayList<String> phones
    ) {

        container.removeAllViews();

        if (phones == null || phones.isEmpty()) return;

        Context context = container.getContext();

        int paddingLeft = (int) (30 * context.getResources().getDisplayMetrics().density);
        int paddingTopBottom = (int) (8 * context.getResources().getDisplayMetrics().density);

        Typeface typeface =
                ResourcesCompat.getFont(context, R.font.bangla_medium);


        for (String phone : phones) {

            TextView textView = new TextView(context);

            textView.setText(phone);

            // ✅ correct text size (sp)
            textView.setTextSize(14);

            // ✅ FONT FAMILY SET
            textView.setTypeface(typeface);

            textView.setClickable(true);
            textView.setFocusable(true);
            textView.setBackground(context.getDrawable(R.drawable.bg_ripple_primary));

            // ✅ correct color
            textView.setTextColor(Color.parseColor("#757575"));

            textView.setIncludeFontPadding(false);

            textView.setPadding(
                    50,
                    paddingTopBottom,
                    paddingLeft,
                    paddingTopBottom
            );

            textView.setOnClickListener(v -> {

                android.content.ClipboardManager clipboard =
                        (android.content.ClipboardManager)
                                context.getSystemService(Context.CLIPBOARD_SERVICE);

                android.content.ClipData clip =
                        android.content.ClipData.newPlainText("number", phone);

                clipboard.setPrimaryClip(clip);

                Toast.makeText(
                        context,
                        "Copied: " + phone,
                        Toast.LENGTH_SHORT
                ).show();
            });

            container.addView(textView);
        }
    }

    private void collapse(View view) {

        view.animate()
                .alpha(0f)
                .scaleY(0f)
                .setDuration(200)
                .withEndAction(() ->
                        view.setVisibility(GONE)
                )
                .start();
    }

    private void expand(View view) {

        view.setVisibility(VISIBLE);

        view.setAlpha(0f);
        view.setScaleY(0f);

        view.animate()
                .alpha(1f)
                .scaleY(1f)
                .setDuration(200)
                .start();
    }
}
*/

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.holystock.newmyoffer.R;
import com.holystock.newmyoffer.model.Contact;
import com.holystock.newmyoffer.views.BorderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class RechargeNumberAdapter
        extends RecyclerView.Adapter<RechargeNumberAdapter.RechargeNumberHolder> {

    private final Context context;
    private final ArrayList<Contact> contacts;

    private final int[] lightColors = {
            Color.parseColor("#FFCDD2"),
            Color.parseColor("#F8BBD0"),
            Color.parseColor("#E1BEE7"),
            Color.parseColor("#D1C4E9"),
            Color.parseColor("#C5CAE9"),
            Color.parseColor("#BBDEFB"),
            Color.parseColor("#B3E5FC"),
            Color.parseColor("#B2EBF2"),
            Color.parseColor("#C8E6C9"),
            Color.parseColor("#DCEDC8"),
            Color.parseColor("#FFF9C4"),
            Color.parseColor("#FFE0B2"),
            Color.parseColor("#FFCCBC")
    };

    // store expanded state (VERY IMPORTANT for RecyclerView)
    private final HashSet<Integer> expandedPositions = new HashSet<>();

    public RechargeNumberAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = new ArrayList<>(contacts);
    }

    @NonNull
    @Override
    public RechargeNumberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recharge_number, parent, false);
        return new RechargeNumberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RechargeNumberHolder holder, int position) {

        Contact contact = contacts.get(position);

        holder.tvName.setText(contact.getName());

        ArrayList<String> phones = contact.getPhones();

        if (phones == null || phones.isEmpty()) {
            holder.tvNumber.setText("No number");
            holder.arrow.setVisibility(View.GONE);
            holder.numberContainer.setVisibility(View.GONE);
            return;
        }

        if (phones.size() == 1) {
            holder.tvNumber.setText(phones.get(0));
            holder.arrow.setVisibility(View.GONE);
            holder.numberContainer.setVisibility(View.GONE);

        } else {
            holder.tvNumber.setText(phones.size() + " টি সেভ করা নাম্বার");
            holder.arrow.setVisibility(View.VISIBLE);

            bindPhoneNumbers(holder.numberContainer, phones);

            boolean expanded = expandedPositions.contains(position);

            holder.numberContainer.setVisibility(expanded ? View.VISIBLE : View.GONE);

            holder.arrow.setRotation(
                    expanded ? 180f : 0f
            );

        }

        // image
        if (contact.getImage() != null && !contact.getImage().isEmpty()) {
            holder.ivPhoto.setVisibility(View.VISIBLE);
            holder.photoLayout.setVisibility(View.INVISIBLE);
            Glide.with(context)
                    .load(contact.getImage())
                    .circleCrop()
                    .placeholder(R.drawable.bank_transfer)
                    .into(holder.ivPhoto);
        } else {

            String firstLetter = String.valueOf(
                    contact.getName().trim().charAt(0)
            );

            holder.tvLetter.setText(
                    firstLetter.toUpperCase()
            );

            holder.ivPhoto.setVisibility(View.INVISIBLE);
            holder.photoLayout.setVisibility(View.VISIBLE);

            String name = contact.getName();

            int index = Math.abs(
                    name != null ? name.hashCode() : position
            ) % lightColors.length;

            holder.photoLayout.setBackgroundColorCustom(
                    lightColors[index]
            );

        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void setData(ArrayList<Contact> newContacts) {

        if (newContacts == null) {
            contacts.clear();
            expandedPositions.clear();
            notifyDataSetChanged();
            return;
        }

        // পুরানো data clear
        contacts.clear();
        contacts.addAll(newContacts);

        // পুরানো expanded state clear (IMPORTANT)
        expandedPositions.clear();

        notifyDataSetChanged();
    }

    class RechargeNumberHolder extends RecyclerView.ViewHolder {

        ImageView ivPhoto, arrow;
        TextView tvName, tvNumber, tvLetter;
        LinearLayout numberContainer;
        RelativeLayout mainLayout;

        BorderView photoLayout;

        public RechargeNumberHolder(@NonNull View itemView) {
            super(itemView);

            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            arrow = itemView.findViewById(R.id.arrow_down);
            tvName = itemView.findViewById(R.id.tvName);
            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvLetter = itemView.findViewById(R.id.tvLetter);
            numberContainer = itemView.findViewById(R.id.numberContainer);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            photoLayout = itemView.findViewById(R.id.photoLayout);

            mainLayout.setOnClickListener(v -> {

                int position = getBindingAdapterPosition();
                if (position == RecyclerView.NO_POSITION) return;

                Contact contact = contacts.get(position);

                if (contact.getPhones() == null || contact.getPhones().size() <= 1) {
                    return;
                }

                boolean isExpanded = expandedPositions.contains(position);

                if (isExpanded) {
                    expandedPositions.remove(position);
                    collapse(numberContainer);
                    arrow.setImageResource(R.drawable.keyboard_arrow_down_24dp);
                } else {
                    expandedPositions.add(position);
                    expand(numberContainer);
                    arrow.setImageResource(R.drawable.keyboard_arrow_up_24dp);
                }
            });
        }
    }

    private void bindPhoneNumbers(LinearLayout container, ArrayList<String> phones) {

        container.removeAllViews();
        if (phones == null) return;

        // Sort phone numbers ascending (0 → 9)
        ArrayList<String> sortedPhones = new ArrayList<>(phones);

        sortedPhones.sort((p1, p2) -> {
            String n1 = p1.replaceAll("[^0-9]", "");
            String n2 = p2.replaceAll("[^0-9]", "");

            try {
                return Long.compare(
                        Long.parseLong(n1),
                        Long.parseLong(n2)
                );
            } catch (Exception e) {
                return p1.compareTo(p2);
            }
        });

        Typeface typeface = ResourcesCompat.getFont(context, R.font.bangla);

        int paddingStart = dp(16);
        int paddingTopBottom = dp(10);

        for (int i = 0; i < sortedPhones.size(); i++) {

            String phone = sortedPhones.get(i);

            TextView textView = new TextView(context);


            LinearLayout.LayoutParams paramsTv =
                    new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

            paramsTv.setMargins(
                    0,
                    0,
                    dpToPx(context, 16),
                    0
            );

            textView.setText(phone);
            textView.setTextSize(16);
            textView.setTypeface(typeface);
            textView.setTextColor(Color.parseColor("#4A4A4A"));
            textView.setIncludeFontPadding(false);
            textView.setBackground(context.getDrawable(R.drawable.bg_ripple_primary2));

            textView.setPadding(
                    paddingStart,
                    paddingTopBottom,
                    paddingStart,
                    paddingTopBottom
            );

            textView.setOnClickListener(v -> copyToClipboard(phone));

            textView.setLayoutParams(paramsTv);
            container.addView(textView);

            // Last item হলে divider add হবে না
            if (i < sortedPhones.size() - 1) {

                View divider = new View(context);

                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                dpToPx(context, 1)
                        );

                params.setMargins(
                        0,
                        0,
                        dpToPx(context, 16),
                        0
                );

                divider.setLayoutParams(params);
                divider.setBackgroundColor(
                        context.getColor(R.color.greyLight)
                );

                container.addView(divider);
            }
        }

    }

    private void copyToClipboard(String phone) {
        ClipboardManager clipboard =
                (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        ClipData clip = ClipData.newPlainText("number", phone);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, "Copied: " + phone, Toast.LENGTH_SHORT).show();
    }

    private void expand(View view) {
        view.setVisibility(View.VISIBLE);
        view.setAlpha(0f);
        view.setScaleY(0.8f);

        view.animate()
                .alpha(1f)
                .scaleY(1f)
                .setDuration(200)
                .start();
    }

    private void collapse(View view) {
        view.animate()
                .alpha(0f)
                .scaleY(0f)
                .setDuration(200)
                .withEndAction(() -> view.setVisibility(View.GONE))
                .start();
    }

    private int dp(int value) {
        return (int) (value * context.getResources().getDisplayMetrics().density);
    }

    private int dpToPx(Context context, int dp) {
        return (int) (
                dp * context.getResources()
                        .getDisplayMetrics().density
        );
    }

}
