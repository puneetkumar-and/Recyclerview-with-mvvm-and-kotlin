package com.example.demo.views.adapter;

import androidx.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.demo.BR;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;



public class CommonAdapter<T extends ViewModel> extends RecyclerView.Adapter<CommonAdapter.ViewHolder<T>> {

    private final List<T> items;
    private final ItemClickListener<T> itemClickListener;
    private LayoutInflater mLayoutInflater;

    public CommonAdapter(List<T> items, ItemClickListener<T> itemClickListener) {
        this.items = items;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public CommonAdapter.ViewHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        return new ViewHolder<>(mLayoutInflater.inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(CommonAdapter.ViewHolder<T> holder, int position) {
        holder.bind(items.get(position), itemClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder<T> extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        void bind(final T value, final ItemClickListener<T> clickListener) {
            binding.setVariable(BR.data, value);
            binding.getRoot().setOnClickListener(v -> clickListener.onItemClick(value));
        }
    }
}
