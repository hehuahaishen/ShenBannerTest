package com.shen.shenbannertest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shen.shenbanner.ShenBannerView;
import com.shen.shenbanner.holder.HolderCreator;
import com.shen.shenbanner.holder.ShenViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.shen.shenbannertest.ShenModeBannerFragment.RES;

/**
 * Created by zhouwei on 17/5/31.
 */

public class NormalViewPagerFragment extends Fragment {
    private ShenBannerView mShenBannerView;
    private ShenBannerView mNormalViewPager;

    public static NormalViewPagerFragment newInstance(){
        return new NormalViewPagerFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.normal_view_pager_layout,null);
        initView(view);
        return view;
    }

    private void initView(View view){
        mShenBannerView = (ShenBannerView) view.findViewById(R.id.mz_view_pager);
        mNormalViewPager = (ShenBannerView) view.findViewById(R.id.normal_viewPager);



        mShenBannerView.setPages(mockData(), new HolderCreator<ViewPagerHolder>() {
            @Override
            public ViewPagerHolder createViewHolder() {
                return new ViewPagerHolder();
            }
        });

        mNormalViewPager.setIndicatorRes(R.drawable.dot_unselect_image,R.drawable.dot_select_image);
        mNormalViewPager.setPages(mockData(), new HolderCreator<ViewPagerHolder>() {
            @Override
            public ViewPagerHolder createViewHolder() {
                return new ViewPagerHolder();
            }
        });

    }


    public static final class ViewPagerHolder implements ShenViewHolder<DataEntry> {
        private ImageView mImageView;
        private TextView mTitle;
        private TextView mDesc;
        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.normal_banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.normal_banner_image);
            mDesc = (TextView) view.findViewById(R.id.page_desc);
            return view;
        }

        @Override
        public void onBind(Context context, int position, DataEntry data) {
           mImageView.setImageResource(data.resId);
           mDesc.setText(data.desc);
        }
    }

    private List<DataEntry> mockData(){
        List<DataEntry> list = new ArrayList<>();
        DataEntry dataEntry = null;
        for(int i=0;i<RES.length;i++){
            dataEntry = new DataEntry();
            dataEntry.resId = RES[i];
            dataEntry.desc = "The time you enjoy wasting , is not wasted.";
            list.add(dataEntry);
        }

        return list;
    }
}
