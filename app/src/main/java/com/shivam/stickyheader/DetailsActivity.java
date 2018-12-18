package com.shivam.stickyheader;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private DatabaseHandler db;
    private User userList;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        progress = new ProgressDialog(this);
        progress.setMessage("User details...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setProgress(0);
        progress.show();
        Runnable progressRunnable = new Runnable() {

            @Override
            public void run() {
                progress.cancel();
            }
        };

        Handler pdCanceller = new Handler();
        pdCanceller.postDelayed(progressRunnable, 1500);

        db = new DatabaseHandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, db.getAllUsers());
        recyclerView.setAdapter(adapter);

        RecyclerSectionItemDecoration sectionItemDecoration =
                new RecyclerSectionItemDecoration(getResources().getDimensionPixelSize(R.dimen.header),
                        true,
                        getSectionCallback(db.getAllUsers()));
       recyclerView.addItemDecoration(sectionItemDecoration);
    }

    private RecyclerSectionItemDecoration.SectionCallback getSectionCallback(final List<String> people) {
        return new RecyclerSectionItemDecoration.SectionCallback() {
            @Override
            public boolean isSection(int position) {
                return position == 0
                        || people.get(position)
                        .charAt(0) != people.get(position - 1)
                        .charAt(0);
            }

            @Override
            public CharSequence getSectionHeader(int position) {
                return people.get(position)
                        .subSequence(0,
                                1);
            }
        };
    }

}
