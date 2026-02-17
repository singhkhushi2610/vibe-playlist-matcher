package com.khushi.vibe_playlist_matcher.service;

import com.khushi.vibe_playlist_matcher.dto.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public void ingestSongToVectorStore(Song song) {
        Document doc = new Document(
                song.lyrics(),   // content to embed (lyrics capture the vibe)
                Map.of(
                        "title", song.name(),
                        "genre", song.genre(),
                        "artist", song.artist()
                )
        );
        vectorStore.add(List.of(doc));
    }

    public List<Song> searchSongs(String feeling, String genre) {

        var requestBuilder = SearchRequest.builder()
                .query(feeling)
                .topK(5);

        if (genre != null && !genre.isBlank()) {
            requestBuilder.filterExpression(
                    new FilterExpressionBuilder().eq("genre", genre).build()
            );
        }

        List<Document> documents = vectorStore.similaritySearch(requestBuilder.build());

        if (documents.isEmpty()) {
            return List.of();
        }

        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        PromptTemplate promptTemplate = getPromptTemplate();
        String systemPrompt = promptTemplate.render(Map.of(
                "feeling", feeling,
                "genre", genre != null && !genre.isBlank() ? genre : "any",
                "context", context
        ));

        return chatClient.prompt()
                .system(systemPrompt)
                .advisors(new SimpleLoggerAdvisor())
                .call()
                .entity(new ParameterizedTypeReference<List<Song>>() {});
    }

    private static PromptTemplate getPromptTemplate() {
        String template = """
                
                You are a music recommendation assistant.
                Your job is to return songs that best match the user’s described feeling and preferred genre using only the context provided to you.
                
                User's feeling: {feeling}
                Preferred genre: {genre}
                
                Guidelines:
                1. Focus primarily on emotional alignment (lyrics meaning, tone, vibe).
                2. Respect the requested genre (when genre is any, return any genre).
                
                Recommendations must feel contextually relevant to the user’s situation.
                Prefer songs where the lyrics strongly reflect the feeling described.
                
                
                Output format: Return a JSON array of objects. Each object must have these fields: name, lyrics, genre, artist.
                (For lyrics - only 1 line from the lyrics that best matches the mood)
                
                Only return the list. No extra commentary.
                
                Context (retrieved songs): {context}
                """;

        return new PromptTemplate(template);
    }


}
