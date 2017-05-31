package ru.mail.polis.audio_service.rest.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mail.polis.audio_service.rest.model.Playlist;
import ru.mail.polis.audio_service.rest.model.Song;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/playlists")
public class PlaylistsController {

    private MongoDatabase db;
    private Gson gson;

    @Autowired
    public PlaylistsController(MongoDatabase db, Gson gson) {
        this.db = db;
        this.gson = gson;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Collection<Playlist> findPlaylists() {
        return db.getCollection("playlists")
                .find()
                .into(new ArrayList<>())
                .stream().map(x -> parsePlaylist(x.toJson()))
                .collect(toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void createPlaylist(@RequestBody String playlist) {
        db.getCollection("findPlaylist").insertOne(Document.parse(playlist));
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public Playlist findPlaylist(@PathVariable String id) {
        return db.getCollection("playlists")
                .find(eq("id", id))
                .into(new ArrayList<>())
                .stream().map(x -> parsePlaylist(x.toJson()))
                .findFirst()
                .orElse(null);
    }

    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    public void removePlaylist(@PathVariable String id) {
        db.getCollection("playlists").deleteOne(eq("id", id));
    }

    private Playlist parsePlaylist(String json) {
        JsonElement element = gson.fromJson(json, JsonElement.class);
        JsonObject object = element.getAsJsonObject();
        String id = object.get("id").getAsString();
        String name = object.get("name").getAsString();
        boolean isAvailable = object.get("isAvailable").getAsBoolean();
        JsonArray array = object.get("songs").getAsJsonArray();
        List<Song> songs = new ArrayList<>();
        for (JsonElement songEl : array) {
            String songId = songEl.getAsString();
            Document songDoc = db.getCollection("songs").find(eq("id", songId)).first();
            Song song = gson.fromJson(songDoc.toJson(), Song.class);
            songs.add(song);
        }
        return new Playlist(id, name, songs, isAvailable);
    }
}