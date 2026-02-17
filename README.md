# Vibe Playlist Matcher

A Spring Boot application that recommends songs based on how you feel, using semantic search over song lyrics with embeddings and vector similarity.

## Features

- **Semantic search** – Match songs by mood or feeling, not just keywords (e.g. "sad" → Fix You)
- **Genre filtering** – Filter results by genre (Inspiring, Romantic, Sad, etc.)
- **Vector embeddings** – Song lyrics embedded and stored in PGVector for similarity search
- **Local AI** – Uses Ollama for chat and embeddings (no external API keys required)

## Tech Stack

- **Java 21** | **Spring Boot 4** | **Spring AI**
- **PostgreSQL** with **pgvector** for vector storage
- **Ollama** for embeddings and chat (llama3.2, nomic-embed-text)

## Prerequisites

- Java 21+
- Maven
- Docker (for PostgreSQL with pgvector)
- [Ollama](https://ollama.com/download) (running locally)

## Setup

### 1. Start PostgreSQL with pgvector

```bash
docker run -d --name pgvector-db -e POSTGRES_PASSWORD=postgres -p 5433:5432 pgvector/pgvector:pg17
```

Enable the extension:

```bash
docker exec -it pgvector-db psql -U postgres -d postgres -c "CREATE EXTENSION vector;"
```

### 2. Start Ollama and pull models

```bash
ollama pull llama3.2
ollama pull nomic-embed-text
```

### 3. Run the application

```bash
mvn spring-boot:run
```

The app runs on **port 1212** by default. On startup, it loads 10 pre-defined songs into the vector store.

## API Endpoints

### Match vibe (search songs)

```
GET /match-vibe?feeling={word}&genre={genre}
```

| Parameter | Required | Description |
|-----------|----------|-------------|
| `feeling` | Yes | How you feel – single word only (e.g. sad, happy, romantic) |
| `genre`   | No  | Filter by genre: Inspiring, Romantic, Sad |

**Example:**
```bash
curl "http://localhost:1212/match-vibe?feeling=sad&genre=Inspiring"
```

### Add song

```
POST /add
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Song Title",
  "lyrics": "Lyrics or emotional summary...",
  "genre": "Romantic",
  "artist": "Artist Name"
}
```

**Example:**
```bash
curl -X POST http://localhost:1212/add \
  -H "Content-Type: application/json" \
  -d '{"name":"Perfect","lyrics":"I found a love for me. Romantic love.","genre":"Romantic","artist":"Ed Sheeran"}'
```

## Project Structure

```
src/main/java/com/khushi/vibe_playlist_matcher/
├── config/          # AI config, song data loader
├── controller/      # REST endpoints
├── data/            # Hardcoded song data
├── dto/             # Song record
└── service/         # Playlist service (vector search, ingestion)
```

