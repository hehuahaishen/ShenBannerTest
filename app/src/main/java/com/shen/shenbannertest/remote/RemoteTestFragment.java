package com.shen.shenbannertest.remote;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shen.shenbanner.ShenBannerView;
import com.shen.shenbanner.holder.HolderCreator;
import com.shen.shenbanner.holder.ShenViewHolder;
import com.shen.shenbannertest.R;
import com.shen.shenbannertest.http.Fault;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

/**
 * Created by zhouwei on 17/6/8.
 *
 */
public class RemoteTestFragment extends Fragment implements View.OnClickListener{
    private MovieLoader mMovieLoader;
    private ShenBannerView mShenBannerView;
    private TextView mBtnChange;
    private List<Movie> mMovies;
    private Handler mHandler = new Handler();

    public static RemoteTestFragment newInstance(){
        RemoteTestFragment fragment = new RemoteTestFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.remote_test_layout,null);
        mShenBannerView = (ShenBannerView) view.findViewById(R.id.my_banner);
        mBtnChange = (TextView) view.findViewById(R.id.btn_change);
        mBtnChange.setOnClickListener(this);
        mMovieLoader = new MovieLoader();
        getMovieList();
        return view;
    }


    /**
     * 获取电影列表
     */
    private void getMovieList(){
        mMovieLoader.getMovie(0,10).subscribe(new Action1<List<Movie>>() {
            @Override
            public void call(List<Movie> movies) {
                mMovies = movies;
                Log.i("shen","get data suceess");
                setBanner(movies);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i("shen","error message:"+throwable.getMessage());
                if(throwable instanceof Fault){
                    Fault fault = (Fault) throwable;
                    if(fault.getErrorCode() == 404){
                        //错误处理
                    }else if(fault.getErrorCode() == 500){
                        //错误处理
                    }else if(fault.getErrorCode() == 501){
                        //错误处理
                    }
                }
            }
        });

    }

    private void setBanner(List<Movie> movies){
        mShenBannerView.setPages(movies, new HolderCreator<BannerViewHolder>() {
            @Override
            public BannerViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });

        mShenBannerView.start();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mShenBannerView.pause();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_change){
            //List movies = new ArrayList(mMovies);  // 这里如果 mMovies 是 null 会报异常的
            List<Movie> movies = new ArrayList();
            if(mMovies != null && mMovies.size() > 0){
                movies.add(mMovies.get(0));
                if(mMovies.size() > 1){
                    movies.add(mMovies.get(1));
                }
            }
            setBanner(movies);
        }
    }

    public static class BannerViewHolder implements ShenViewHolder<Movie> {
        private ImageView mImageView;
        @Override
        public View createView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.remote_banner_item,null);
            mImageView = (ImageView) view.findViewById(R.id.remote_item_image);
            return view;
        }

        @Override
        public void onBind(Context context, int i, Movie movie) {
            Log.e("zhouwei","current position:"+i);
            Picasso.with(context).load(movie.images.large).into(mImageView);
        }
    }
}
