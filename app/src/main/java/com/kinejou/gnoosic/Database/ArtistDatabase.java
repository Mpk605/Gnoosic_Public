package com.kinejou.gnoosic.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kinejou.gnoosic.Database.Entities.Artist;

@Database(entities = {Artist.class}, version = 1)
public abstract class ArtistDatabase extends RoomDatabase {
    private static ArtistDatabase instance;
    private static final String DB_NAME = "quotes_db";

//    public abstract QuoteDao getQuoteDao();

    public static synchronized ArtistDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ArtistDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }

        return instance;
    }
}
