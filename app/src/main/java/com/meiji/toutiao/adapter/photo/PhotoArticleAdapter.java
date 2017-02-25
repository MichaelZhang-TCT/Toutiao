package com.meiji.toutiao.adapter.photo;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiji.toutiao.R;
import com.meiji.toutiao.bean.photo.PhotoArticleBean;
import com.meiji.toutiao.interfaces.IOnItemClickListener;
import com.meiji.toutiao.utils.SettingsUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Meiji on 2016/12/28.
 */

public class PhotoArticleAdapter extends RecyclerView.Adapter<PhotoArticleAdapter.PhotoViewViewHolder> {

    private List<PhotoArticleBean.DataBean> list = new ArrayList();
    private Context context;
    private IOnItemClickListener onItemClickListener;

    public PhotoArticleAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setOnItemClickListener(IOnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public PhotoViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_article_item, parent, false);
        return new PhotoViewViewHolder(view, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(PhotoViewViewHolder holder, int position) {
        PhotoArticleBean.DataBean bean = list.get(position);
        String tv_title = bean.getTitle();
        if (!SettingsUtil.getInstance().getNoPhotoMode()) {
            if (bean.getImage_list() != null) {
                int size = bean.getImage_list().size();
                String[] ivs = new String[size];
                for (int i = 0; i < bean.getImage_list().size(); i++) {
                    ivs[i] = bean.getImage_list().get(i).getUrl();
                }
                switch (ivs.length) {
                    case 1:
                        Glide.with(context).load(ivs[0]).crossFade().centerCrop().into(holder.iv_0);
                        break;
                    case 2:
                        Glide.with(context).load(ivs[0]).crossFade().centerCrop().into(holder.iv_0);
                        Glide.with(context).load(ivs[1]).crossFade().centerCrop().into(holder.iv_1);
                        break;
                    case 3:
                        Glide.with(context).load(ivs[0]).crossFade().centerCrop().into(holder.iv_0);
                        Glide.with(context).load(ivs[1]).crossFade().centerCrop().into(holder.iv_1);
                        Glide.with(context).load(ivs[2]).crossFade().centerCrop().into(holder.iv_2);
                        break;
                    default:
                        holder.iv_0.setBackgroundColor(Color.WHITE);
                        holder.iv_1.setBackgroundColor(Color.WHITE);
                        holder.iv_2.setBackgroundColor(Color.WHITE);
                }
            }
        }

        holder.tv_title.setText(tv_title);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class PhotoViewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_title;
        private ImageView iv_0;
        private ImageView iv_1;
        private ImageView iv_2;
        private IOnItemClickListener onItemClickListener;

        public PhotoViewViewHolder(View itemView, IOnItemClickListener onItemClickListener) {
            super(itemView);
            this.tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            this.iv_0 = (ImageView) itemView.findViewById(R.id.iv_0);
            this.iv_1 = (ImageView) itemView.findViewById(R.id.iv_1);
            this.iv_2 = (ImageView) itemView.findViewById(R.id.iv_2);
            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onItemClickListener != null) {
                onItemClickListener.onClick(view, getLayoutPosition());
            }
        }
    }
}