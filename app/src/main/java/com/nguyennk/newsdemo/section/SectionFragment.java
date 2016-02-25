package com.nguyennk.newsdemo.section;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.nguyennk.newsdemo.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nguye on 2/24/2016.
 */
public class SectionFragment extends Fragment {
    @Bind(R.id.rv_section)
    UltimateRecyclerView rvSection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_section, container, false);

        ButterKnife.bind(this, view);
        rvSection.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvSection.setAdapter(new SectionAdapter(getContext(), new SectionAdapter.SectionAdapterListener() {
            @Override
            public void onItemClick(View v, String section) {
                Intent intent = new Intent(getActivity(), SectionActivity.class);
                intent.putExtra(SectionActivity.KEY_SECTION, section);

                startActivity(intent);
            }
        }));

        return view;
    }
}
