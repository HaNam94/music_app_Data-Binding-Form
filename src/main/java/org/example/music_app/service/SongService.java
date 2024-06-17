package org.example.music_app.service;

import org.example.music_app.model.Song;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongService implements ISongService {
    private List<Song> songs = new ArrayList<>();

    @Override
    public void save(Song song) {
        songs.add(song);
    }

    @Override
    public List<Song> findAll() {
        return songs;
    }
}
