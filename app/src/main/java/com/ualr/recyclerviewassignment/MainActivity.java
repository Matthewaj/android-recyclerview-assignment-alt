package com.ualr.recyclerviewassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.ualr.recyclerviewassignment.Utils.DataGenerator;
import com.ualr.recyclerviewassignment.fragments.ForwardDialogFragment;
import com.ualr.recyclerviewassignment.fragments.InboxListFragment;
import com.ualr.recyclerviewassignment.model.Inbox;
import com.ualr.recyclerviewassignment.model.InboxViewModel;

import java.util.List;

import static com.ualr.recyclerviewassignment.Utils.Consts.DELETE_INBOX;

// TODO 05. Create a new Adapter class and the corresponding ViewHolder class in a different file. The adapter will be used to populate
//  the recyclerView and manage the interaction with the items in the list
// TODO 06. Detect click events on the list items. Implement a new method to toggle items' selection in response to click events
// TODO 07. Detect click events on the thumbnail located on the left of every list row when the corresponding item is selected.
//  Implement a new method to delete the corresponding item in the list
// TODO 08. Create a new method to add a new item on the top of the list. Use the DataGenerator class to create the new item to be added.

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton mFAB;
    private RecyclerView recyclerView;
    private InboxViewModel inboxViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_multi_selection);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initComponent();
    }

    private void initComponent() {
        // TODO 01. Generate the item list to be displayed using the DataGenerator class
        inboxViewModel = new ViewModelProvider(this).get(InboxViewModel.class);
        inboxViewModel.addInbox(DataGenerator.getInboxData(this));

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_placeholder, new InboxListFragment());
        ft.commit();

        mFAB = findViewById(R.id.fab);
        mFAB.setOnClickListener((View view) ->
                inboxViewModel.addInbox(DataGenerator.getRandomInboxItem(this)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();

        menuInflater.inflate(R.menu.items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                inboxViewModel.removeSelectedInbox();
                CoordinatorLayout parentView = findViewById(R.id.lyt_parent);
                String msg = getResources().getString(R.string.snackbar_delete);
                int duration = Snackbar.LENGTH_LONG;

                Snackbar.make(parentView, msg, duration).show();
            case R.id.action_forward:
                Inbox selectedInbox = inboxViewModel.getSelectedInbox();
                if (selectedInbox == null) return true;

                ForwardDialogFragment fragment =
                        ForwardDialogFragment.newInstance(selectedInbox);

                fragment.show(getSupportFragmentManager(), "forward_diag");
        }

        return true;
    }
}