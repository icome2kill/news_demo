package com.nguyennk.newsdemo.section;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nguyennk.newsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nguye on 2/24/2016.
 */
public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder> {
    private String[] sections;
    private int[] sectionImgs;

    private SectionAdapterListener listener;

    public SectionAdapter(Context context, SectionAdapterListener listener) {
        this.sections = context.getResources().getStringArray(R.array.sections);

        TypedArray temp = context.getResources().obtainTypedArray(R.array.section_imgs);
        this.sectionImgs = new int[temp.length()];
        for (int i = 0; i < temp.length(); i++) {
            this.sectionImgs[i] = temp.getResourceId(i, -1);
        }
        temp.recycle();

        this.listener = listener;
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_item, parent, false);
        SectionViewHolder viewHolder = new SectionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, final int position) {
        holder.tvSection.setText(sections[position]);
        holder.imvSection.setImageResource(sectionImgs[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, sections[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.sections.length;
    }

    interface SectionAdapterListener {
        void onItemClick(View v, String section);
    }

    static class SectionViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_section)
        TextView tvSection;
        @Bind(R.id.imv_section)
        ImageView imvSection;

        public SectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
