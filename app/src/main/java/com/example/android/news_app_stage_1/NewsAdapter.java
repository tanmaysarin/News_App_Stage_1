    package com.example.android.news_app_stage_1;

    import android.content.Context;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ArrayAdapter;
    import android.widget.TextView;

    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.List;
    import java.util.Locale;

    public class NewsAdapter extends ArrayAdapter<News> {

        public NewsAdapter(Context context, List<News> NewsItem) {
            super(context, 0, NewsItem);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Check if there is an existing list item view (called convertView) that we can reuse,
            // otherwise, if convertView is null, then inflate a new list item layout.
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item, parent, false);
            }

            // Find the news article at the given position in the list of earthquakes
            News currentNewsItem = getItem(position);

            TextView nameView = (TextView) listItemView.findViewById(R.id.artivleType);
            nameView.setText(currentNewsItem.getName());

            // Find the TextView with view ID location
            TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
            // Display the location of the current earthquake in that TextView
            primaryLocationView.setText(currentNewsItem.getWebTitle());

            // Find the TextView with view ID location offset
            TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.date);
            // Display the location offset of the current earthquake in that TextView
            locationOffsetView.setText(formatDate(currentNewsItem.getDate()));

            // Get the original location string from the Earthquake object,
            // which can be in the format of "5km N of Cairo, Egypt" or "Pacific-Antarctic Ridge".
            String originalLocation = currentNewsItem.getWebTitle();


            // Find the TextView with view ID date
            TextView dateView = (TextView) listItemView.findViewById(R.id.date);

            // Return the list item view that is now showing the appropriate data
            return listItemView;
        }


        private String formatDate(String date) {
            Date dateObject = new Date ();
            try {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat ( "yyyy-MM-dd", Locale.US );
                dateObject = simpleDateFormat.parse ( date );
            } catch (ParseException e) {
                e.printStackTrace ();
            }
            SimpleDateFormat newDateFormat = new SimpleDateFormat ( "MMM dd, yyyy ", Locale.US );
            String dateFormatted = newDateFormat.format ( dateObject );
            return dateFormatted;
        }
    }