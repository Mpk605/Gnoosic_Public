package com.kinejou.gnoosic.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.kinejou.gnoosic.Database.Entities.Artist;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class ArtistDAO {
    @Insert
    public abstract void insert(Artist artist);

    @Query("SELECT * FROM ARTISTS")
    public abstract List<String> getSavedArtists();
}
