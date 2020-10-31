package com.ualr.recyclerviewassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.model.Inbox;

import java.util.List;
import java.util.Optional;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxViewHolder> {
    private final Context context;
    private final List<Inbox> inboxList;
    private int lastChecked;

    public InboxAdapter(Context context, List<Inbox> inboxList) {
        this.inboxList = inboxList;
        this.context = context;
        this.lastChecked = -1;
    }

    @NonNull
    @Override
    public InboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_layout, parent, false);

        itemView.setOnClickListener((View view) -> {
            int position = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
            inboxList.get(position).toggleSelection();

            notifyItemChanged(position);
        });

        return new InboxViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxViewHolder holder, int position) {
        Inbox inbox = inboxList.get(position);

        holder.from.setText(inbox.getFrom());
        holder.date.setText(inbox.getDate());
        holder.email.setText(inbox.getEmail());
        holder.message.setText(inbox.getMessage());
        holder.thumbnail.setText(
                inbox.isSelected() ? "×" : String.valueOf(inbox.getFrom().charAt(0)));
        holder.thumbnail.setEnabled(inbox.isSelected());
        holder.layout.setSelected(inbox.isSelected());
    }

    public void addIndex(Inbox inbox) {
        inboxList.add(0, inbox);
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return inboxList.size();
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView thumbnail;
        private final TextView from;
        private final TextView email;
        private final TextView message;
        private final TextView date;
        private final View layout;

        public InboxViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            from = itemView.findViewById(R.id.from);
            email = itemView.findViewById(R.id.email);
            message = itemView.findViewById(R.id.message);
            date = itemView.findViewById(R.id.date);
            layout = itemView.findViewById(R.id.layout);

            thumbnail.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getBindingAdapterPosition();

            inboxList.remove(position);
            notifyItemRemoved(position);
        }
    }
}
