package com.ualr.recyclerviewassignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxViewModel;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.InboxViewHolder> {
    private final InboxViewModel inboxViewModel;

    public InboxAdapter(InboxViewModel inboxViewModel) {
        this.inboxViewModel = inboxViewModel;
    }

    @NonNull
    @Override
    public InboxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_layout, parent, false);

        itemView.setOnClickListener((View view) -> {
            int position = ((RecyclerView) view.getParent()).getChildAdapterPosition(view);
            Inbox clickedInbox = inboxViewModel.getInboxes().getValue().get(position);
            clickedInbox.toggleSelection();


            Integer selectedInbox = inboxViewModel.getSelectedInboxIndex();

            if (selectedInbox != -1 && selectedInbox != position) {
                inboxViewModel.getInboxes().getValue().get(selectedInbox).toggleSelection();
                notifyItemChanged(selectedInbox);
                inboxViewModel.setSelectedInbox(position);
            } else if (selectedInbox == position) {
                inboxViewModel.deselectInbox();
            } else {
                inboxViewModel.setSelectedInbox(position);
            }

            notifyItemChanged(position);
        });

        return new InboxViewHolder(itemView);
    }

    public void removeSelectedItem() {
        Integer selectedInbox = inboxViewModel.removeSelectedInbox();
        notifyItemRemoved(selectedInbox);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxViewHolder holder, int position) {
        Inbox inbox = inboxViewModel.getInboxes().getValue().get(position);

        holder.from.setText(inbox.getFrom());
        holder.date.setText(inbox.getDate());
        holder.email.setText(inbox.getEmail());
        holder.message.setText(inbox.getMessage());
        holder.thumbnail.setText(
                inbox.isSelected() ? "✓" : String.valueOf(inbox.getFrom().charAt(0)));
        holder.thumbnail.setEnabled(inbox.isSelected());
        holder.layout.setSelected(inbox.isSelected());
    }

    @Override
    public int getItemCount() {
        return inboxViewModel.getInboxes().getValue().size();
    }

    public class InboxViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
