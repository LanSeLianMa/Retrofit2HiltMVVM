package com.example.retrofit2hiltmvvm.common.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit2hiltmvvm.R;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.databinding.LvListItemBinding;

public class MListAdapter extends RecyclerView.Adapter<MListAdapter.ViewHolder> {

    private ListBean listBean;

    public void setListBean(ListBean bean) {
        this.listBean = bean;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lv_list_item, parent, false);
        LvListItemBinding bind = LvListItemBinding.bind(view);
        return new ViewHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(listBean.getDatas().get(position));
    }

    @Override
    public int getItemCount() {
        if (listBean != null && listBean.getDatas() != null) {
            return listBean.getDatas().size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LvListItemBinding bind;

        public ViewHolder(@NonNull LvListItemBinding bind) {
            super(bind.getRoot());
            this.bind = bind;
        }

        public void bindData(ListBean.DatasBean datasBean) {
            bind.setBean(datasBean);
        }
    }

}
