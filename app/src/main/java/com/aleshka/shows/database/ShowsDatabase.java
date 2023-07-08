package com.aleshka.shows.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.aleshka.shows.dao.ShowDao;
import com.aleshka.shows.models.Show;

@Database(entities = Show.class, version = 4, exportSchema = false)
public abstract class ShowsDatabase extends RoomDatabase {

    private static ShowsDatabase showsDatabase;

    public static synchronized ShowsDatabase getDatabase(Context context) {
        if (showsDatabase == null) {
            showsDatabase = Room.databaseBuilder(
                            context,
                            ShowsDatabase.class,
                            "shows_db"
                    ).build();
        }
        return showsDatabase;
    }

    public abstract ShowDao showDao();

}
