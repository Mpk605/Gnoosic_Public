package com.kinejou.gnoosic.Database;

import androidx.room.Dao;
import androidx.room.Insert;

import com.kinejou.gnoosic.Database.Entities.Artist;

@Dao
public abstract class ArtistDAO {
    @Insert
    public abstract void insert(Artist artist);


}
