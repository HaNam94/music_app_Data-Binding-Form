package org.example.music_app.controller;

import org.example.music_app.model.Song;
import org.example.music_app.model.SongForm;
import org.example.music_app.service.ISongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/songs")
public class MusicController {

    private final ISongService songService;
    @Autowired
    public MusicController(ISongService songService) {
        this.songService = songService;
    }
    @Value("${file-upload}")
    private static String UPLOAD_DIRECTORY;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("songs", songService.findAll());
        return "index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("songForm", new SongForm());
        return "create";
    }

    @PostMapping("/save")
    public String save(SongForm songForm, Model model) {
        MultipartFile file = songForm.getFile();
        String fileName = file.getOriginalFilename();
        if (fileName != null && (fileName.endsWith(".mp3") || fileName.endsWith(".wav") || fileName.endsWith(".ogg") || fileName.endsWith(".m4p"))) {
            try {
                File saveFile = new File(UPLOAD_DIRECTORY + fileName);
                file.transferTo(saveFile);
                Song song = new Song();
                song.setTitle(songForm.getTitle());
                song.setArtist(songForm.getArtist());
                song.setGenre(songForm.getGenre());
                song.setFilePath(saveFile.getAbsolutePath());
                songService.save(song);
                return "redirect:/songs";
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("message", "File upload failed!");
                return "create";
            }
        } else {
            model.addAttribute("message", "Invalid file type!");
            return "create";
        }
    }
}
