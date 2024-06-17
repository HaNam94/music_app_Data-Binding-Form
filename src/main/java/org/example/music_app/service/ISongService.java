package org.example.music_app.service;

import org.example.music_app.model.Song;

import java.util.List;

public interface ISongService {
    void save(Song song);
    List<Song> findAll();
}
