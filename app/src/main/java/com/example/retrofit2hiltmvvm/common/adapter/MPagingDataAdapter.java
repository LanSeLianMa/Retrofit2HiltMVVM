package com.example.retrofit2hiltmvvm.common.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagingDataAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit2hiltmvvm.R;
import com.example.retrofit2hiltmvvm.common.bean.ListBean;
import com.example.retrofit2hiltmvvm.databinding.LvListItemBinding;

public class MPagingDataAdapter extends PagingDataAdapter<ListBean.DatasBean, MPagingDataAdapter.ViewHolder> {

    public MPagingDataAdapter() {
        super(new DiffUtil.ItemCallback<ListBean.DatasBean>() {
            @Override
            public boolean areItemsTheSame(@NonNull ListBean.DatasBean oldItem, @NonNull ListBean.DatasBean newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull ListBean.DatasBean oldItem, @NonNull ListBean.DatasBean newItem) {
                return oldItem == newItem;
            }
        });
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
        ListBean.DatasBean bean = getItem(position);
        holder.bindData(bean);
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
