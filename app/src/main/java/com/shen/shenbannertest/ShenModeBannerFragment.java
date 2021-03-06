package com.shen.shenbannertest;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.shen.shenbanner.ShenBannerView;
import com.shen.shenbanner.holder.HolderCreator;
import com.shen.shenbanner.holder.ShenViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * banner模式
 */
public class ShenModeBannerFragment extends Fragment {
    public static final String TAG = "ShenModeBannerFragment";

    /** 普通Banner的资源图片 */
    public static final int []RES = new int[]{R.mipmap.image5,R.mipmap.image2,R.mipmap.image3,R.mipmap.image4,R.mipmap.image6,R.mipmap.image7,R.mipmap.image8};
    /** 仿魅族Banner的资源图片 */
    public static final int []BANNER = new int[]{R.mipmap.banner1,R.mipmap.banner2,R.mipmap.banner3,R.mipmap.banner4,R.mipmap.banner5};

    private ShenBannerView mShenBanner;
    private ShenBannerView mNormalBanner;


    public static ShenModeBannerFragment newInstance(){
        return new ShenModeBannerFragment();
    }

    private void initView(View view) {

        mShenBanner = (ShenBannerView) view.findViewById(R.id.banner);
        mNormalBanner = (ShenBannerView) view.findViewById(R.id.banner_normal);

        mShenBanner.setBannerPageClickListener(new ShenBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Toast.makeText(getContext(),"click page:"+position,Toast.LENGTH_LONG).show();
            }
        });

        mShenBanner.addPageChangeLisnter(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e(TAG,"----->addPageChangeLisnter:"+position + "positionOffset:"+positionOffset+ "positionOffsetPixels:"+positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e(TAG,"addPageChangeLisnter:"+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        List<Integer> bannerList = new ArrayList<>();
        for(int i=0;i<BANNER.length;i++){
            bannerList.add(BANNER[i]);
        }

        List<Integer> list = new ArrayList<>();
        for(int i=0;i<RES.length;i++){
            list.add(RES[i]);
        }


        mShenBanner.setIndicatorVisible(true);
        mShenBanner.setPages(bannerList, new HolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });


        mNormalBanner.setPages(list, new HolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });


    }

    public static class BannerViewHolder implements ShenViewHolder<Integer> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            // 返回页面布局文件
            View view = LayoutInflater.from(context).inflate(R.layout.banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.banner_image);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main,null);
        initView(view);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        mShenBanner.pause();
        mNormalBanner.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mShenBanner.start();
        mNormalBanner.start();
    }
}
