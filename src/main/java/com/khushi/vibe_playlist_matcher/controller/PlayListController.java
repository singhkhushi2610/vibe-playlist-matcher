package com.khushi.vibe_playlist_matcher.controller;

import com.khushi.vibe_playlist_matcher.dto.Song;
import com.khushi.vibe_playlist_matcher.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PlayListController {

    private final PlaylistService playlistService;

    @GetMapping("/match-vibe")
    public List<Song> searchSongs(@RequestParam String feeling,
                                  @RequestParam(required = false) String genre) {
        if (feeling == null || feeling.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "feeling must not be blank");
        }
        if (feeling.trim().contains(" ")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "feeling must be a single word");
        }
        return playlistService.searchSongs(feeling, genre);
    }

    @PostMapping("/add")
    public void ingestSongs(@RequestBody Song song) {
        playlistService.ingestSongToVectorStore(song);
    }
}
