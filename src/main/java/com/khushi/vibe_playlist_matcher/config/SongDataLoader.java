package com.khushi.vibe_playlist_matcher.config;

import com.khushi.vibe_playlist_matcher.data.SongData;
import com.khushi.vibe_playlist_matcher.dto.Song;
import com.khushi.vibe_playlist_matcher.service.PlaylistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Loads the 10 hardcoded songs into PGVector on application startup.
 * Required by homework: "Ingest: Embed them and store them in PGVector."
 * Set app.seed-on-startup=false to skip (e.g. to avoid duplicates on restart).
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SongDataLoader implements ApplicationRunner {

    private final PlaylistService playlistService;

    @Value("${app.seed-on-startup:true}")
    private boolean seedOnStartup;

    @Override
    public void run(ApplicationArguments args) {
        if (!seedOnStartup) {
            log.info("Skipping song data load (app.seed-on-startup=false).");
            return;
        }
        log.info("Ingesting {} songs into vector store...", SongData.SONGS.size());
        for (Song song : SongData.SONGS) {
            playlistService.ingestSongToVectorStore(song);
        }
        log.info("Successfully ingested {} songs into PGVector.", SongData.SONGS.size());
    }
}
