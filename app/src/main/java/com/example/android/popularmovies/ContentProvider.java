package com.example.android.popularmovies;

import de.triplet.simpleprovider.AbstractProvider;
import de.triplet.simpleprovider.Column;
import de.triplet.simpleprovider.Table;

/**
 * Created by RajatSharma on 5/12/16.
 */
public class ContentProvider extends AbstractProvider {

    protected String getAuthority() {
        return "com.example.android.popularmovies;";
    }

    @Table
    public class Movie {

        @Column(value = Column.FieldType.INTEGER, primaryKey = true)
        public static final String KEY_ID = "_id";

        @Column(Column.FieldType.TEXT)
        public static final String KEY_IMGURL = "imgurl";

        @Column(Column.FieldType.TEXT)
        public static final String KEY_TITLE= "title";

        @Column(Column.FieldType.TEXT)
        public static final String KEY_PLOT = "plot";

        @Column(Column.FieldType.TEXT)
        public static final String KEY_RATING = "rating";

        @Column(Column.FieldType.TEXT)
        public static final String KEY_RELEASEDATE = "releasedate";

        @Column(Column.FieldType.TEXT)
        public static final String KEY_MOVIEID = "movieid";

    }

    @Table
    public class Post {

        // ... (previously defined columns)

        @Column(value = Column.FieldType.INTEGER, since = 2)
        public static final String KEY_CREATION_DATE = "creation_date";

    }

    @Override
    protected int getSchemaVersion() {
        return 2;
    }

}
