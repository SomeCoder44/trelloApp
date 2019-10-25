package pl.orzechowski.trellomanagmentapp.modules.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import pl.orzechowski.trellomanagmentapp.R;
import pl.orzechowski.trellomanagmentapp.databinding.ItemListBinding;
import pl.orzechowski.trellomanagmentapp.interfaces.ActivityProvider;
import pl.orzechowski.trellomanagmentapp.interfaces.OnItemClickListener;
import pl.orzechowski.trellomanagmentapp.models.TrelloCard;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private OnItemClickListener<TrelloCard> onItemClickListener;
    private List<TrelloCard> objectList = new ArrayList<>();
    private ActivityProvider activityProvider;
    private int colorRes;

    public ListAdapter(OnItemClickListener<TrelloCard> onItemClickListener, ActivityProvider activityProvider, int colorRes) {
        this.onItemClickListener = onItemClickListener;
        this.activityProvider = activityProvider;
        this.colorRes = colorRes;
    }

    public void setData(List<TrelloCard> objectList) {
        this.objectList.clear();
        this.objectList.addAll(objectList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);

        ListItemViewModel viewModel = new ListItemViewModel();
        viewModel.init(activityProvider);

        ItemListBinding binding = ItemListBinding.bind(itemView);
        binding.setViewModel(viewModel);

        return new ListAdapter.ViewHolder(itemView, binding, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrelloCard object = objectList.get(position);
        holder.setElement(object);
        holder.itemView.setTag(object);
        holder.itemView.setOnClickListener(view -> {
            TrelloCard tag = (TrelloCard) view.getTag();
            onItemClickListener.onItemClick(tag);
        });
    }

    @Override
    public int getItemCount() {
        return objectList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected final ListItemViewModel viewModel;
        private final ViewDataBinding binding;

        ViewHolder(@NonNull View itemView, ItemListBinding binding, ListItemViewModel viewModel) {
            super(itemView);
            this.binding = binding;
            this.viewModel = viewModel;
        }

        void setElement(TrelloCard object) {
            viewModel.setItem(object, colorRes);
            binding.executePendingBindings();
        }
    }
}
