package com.khushi.vibe_playlist_matcher.data;

import com.khushi.vibe_playlist_matcher.dto.Song;

import java.util.List;

public final class SongData {

    private SongData() {
    }

    public static final List<Song> SONGS = List.of(
            // English - Inspiring
            new Song(
                    "Fix You",
                    "When you try your best but you don't succeed, when you feel so tired but you can't sleep, lights will guide you home. Comfort and hope for someone who feels broken and wants to give up.",
                    "Inspiring",
                    "Coldplay"
            ),
            new Song(
                    "Don't Stop Believin'",
                    "Hold on to that feeling. Don't stop believin'. Never give up on your dreams and keep the faith.",
                    "Inspiring",
                    "Journey"
            ),
            new Song(
                    "Roar",
                    "I got the eye of the tiger. You're gonna hear me roar. Finding your voice and standing up for yourself after being held back.",
                    "Inspiring",
                    "Katy Perry"
            ),
            // English - Sad
            new Song(
                    "Someone Like You",
                    "Sometimes it lasts in love, sometimes it hurts instead. Heartbreak and acceptance after losing someone you loved.",
                    "Sad",
                    "Adele"
            ),
            new Song(
                    "Rolling in the Deep",
                    "We could have had it all. Burning anger and betrayal, the scars of love that went wrong.",
                    "Sad",
                    "Adele"
            ),
            // English - Romantic
            new Song(
                    "Shape of You",
                    "I'm in love with the shape of you. Romantic attraction and love at first sight.",
                    "Romantic",
                    "Ed Sheeran"
            ),
            // Hindi - Romantic
            new Song(
                    "Tum Hi Ho",
                    "Tum hi ho, tum hi ho. You are my everything, my love. Deep devotion and romantic love that completes life.",
                    "Romantic",
                    "Arijit Singh"
            ),
            new Song(
                    "Tujh Mein Rab Dikhta Hai",
                    "I see God in you. When I look at you, I find my faith. Spiritual romantic love and devotion.",
                    "Romantic",
                    "Shahrukh Khan"
            ),
            new Song(
                    "Gerua",
                    "Rang de tu mohe gerua. Color me in your love. Passionate romantic love, longing and devotion.",
                    "Romantic",
                    "Arijit Singh"
            ),
            // Hindi - Sad
            new Song(
                    "Channa Mereya",
                    "Channa mereya, tera kya hoga. Heartbreak and pain of unrequited love. Sadness of loving someone who cannot be yours.",
                    "Sad",
                    "Arijit Singh"
            )
    );
}
