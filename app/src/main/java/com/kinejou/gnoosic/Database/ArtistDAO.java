package com.kinejou.gnoosic.Database;

import android.content.ClipData;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RawQuery;

import com.kinejou.gnoosic.Database.Entities.Artist;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class ArtistDAO {
    @Insert
    public abstract void insert(Artist artist);

    @Query("SELECT * FROM ARTISTS")
    public abstract List<String> getSavedArtists();

    @Query("DELETE FROM ARTISTS")
    public abstract void clearAll();

    @Delete
    public abstract void deleteArtist(Artist artist);

    @Query("SELECT * FROM ARTISTS WHERE name = :name")
    public abstract Artist getArtist(String name);
}
