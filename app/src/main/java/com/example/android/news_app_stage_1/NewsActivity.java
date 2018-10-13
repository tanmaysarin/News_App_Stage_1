    package com.example.android.news_app_stage_1;

    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;

    import android.app.LoaderManager;
    import android.app.LoaderManager.LoaderCallbacks;
    import android.content.Context;
    import android.content.Intent;
    import android.content.Loader;
    import android.net.ConnectivityManager;
    import android.net.NetworkInfo;
    import android.net.Uri;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ListView;
    import android.widget.TextView;

    import java.util.ArrayList;
    import java.util.List;

    public class NewsActivity extends AppCompatActivity
            implements LoaderCallbacks<List<News>> {

        private static final String LOG_TAG = NewsActivity.class.getName();

        /** URL for earthquake data from the guardian dataset */
        private static final String GUARDIAN_REQUEST_URL =
                "https://content.guardianapis.com/search?api-key=1205bd93-b6c3-4f90-9eac-eba3ff2c2b02";

        private static final int NEWS_LOADER_ID = 1;

        private NewsAdapter mAdapter;

        /** TextView that is displayed when the list is empty */
        private TextView mEmptyStateTextView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.news_activity);

            // Find a reference to the {@link ListView} in the layout
            ListView newsListView = (ListView) findViewById(R.id.list);

            mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
            newsListView.setEmptyView(mEmptyStateTextView);

            mAdapter = new NewsAdapter(this, new ArrayList<News>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            newsListView.setAdapter(mAdapter);

            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current earthquake that was clicked on
                    News currentNewsArticle = mAdapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri newsUri = Uri.parse(currentNewsArticle.getUrl());

                    // Create a new intent to view the earthquake URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });

            // Get a reference to the ConnectivityManager to check state of network connectivity
            ConnectivityManager connMgr = (ConnectivityManager)
                    getSystemService(Context.CONNECTIVITY_SERVICE);

            // Get details on the currently active default data network
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            // If there is a network connection, fetch data
            if (networkInfo != null && networkInfo.isConnected()) {
                // Get a reference to the LoaderManager, in order to interact with loaders.
                LoaderManager loaderManager = getLoaderManager();

                // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                // because this activity implements the LoaderCallbacks interface).
                loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            } else {
                // Otherwise, display error
                // First, hide loading indicator so error message will be visible
                View loadingIndicator = findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);

                // Update empty state with no connection error message
                mEmptyStateTextView.setText(R.string.no_internet_connection);
            }
        }

        @Override
        public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
            // Create a new loader for the given URL
            return new NewsLoader(this, GUARDIAN_REQUEST_URL);
        }

        @Override
        public void onLoadFinished(Loader<List<News>> loader, List<News> NewsItems) {
            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Set empty state text to display "No NewsItems found."
            mEmptyStateTextView.setText(R.string.no_news);

            mAdapter.clear();

            if (NewsItems != null && !NewsItems.isEmpty()) {
                mAdapter.addAll(NewsItems);
            }
        }

        @Override
        public void onLoaderReset(Loader<List<News>> loader) {
            // Loader reset, so we can clear out our existing data.
            mAdapter.clear();
        }
    }

