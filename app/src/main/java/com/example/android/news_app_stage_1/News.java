    package com.example.android.news_app_stage_1;

    public class News {

        /**Name of the news field */
        private String mSectionName;

        /** web title of the selected news item */
        private String mWebTitle;

        /** date of news item */
        private String mDate;

        /** Website URL of the news article */
        private String mUrl;

        public News(String sectionName, String webTitle, String date, String url) {
            mSectionName = sectionName;
            mWebTitle = webTitle;
            mDate = date;
            mUrl = url;
        }

        /**
         * Returns the name of the section of the news item
         */
        public String getName(){return mSectionName;}

        /**
         * Returns the title of the news item.
         */
        public String getWebTitle() {
            return mWebTitle;
        }

        /**
         * Returns the date of the news article.
         */
        public String getDate() {
            return mDate;
        }

        /**
         * Returns the website URL to find more information about the news article.
         */
        public String getUrl() {
            return mUrl;
        }
    }