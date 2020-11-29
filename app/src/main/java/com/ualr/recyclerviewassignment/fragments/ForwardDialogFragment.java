package com.ualr.recyclerviewassignment.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.ualr.recyclerviewassignment.R;
import com.ualr.recyclerviewassignment.model.Inbox;

public class ForwardDialogFragment extends DialogFragment {
    private static final String BUNDLE_KEY = "data";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Inbox inbox = getArguments().getParcelable(BUNDLE_KEY);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout, null);
        ((EditText) view.findViewById(R.id.to_edit_text)).setText(inbox.getEmail());
        ((EditText) view.findViewById(R.id.name_edit_text)).setText(inbox.getFrom());
        ((EditText) view.findViewById(R.id.text_edit_text)).setText(inbox.getMessage());

        builder.setView(view)
                .setPositiveButton(R.string.send_button, (dialogInterface, i) ->
                        onSendButtonClicked());

        Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onSendButtonClicked() {

    }

    public static ForwardDialogFragment newInstance(Inbox inbox) {
        ForwardDialogFragment fragment = new ForwardDialogFragment();

        Bundle args = new Bundle();
        args.putParcelable(BUNDLE_KEY, inbox);
        fragment.setArguments(args);

        return fragment;
    }
}
