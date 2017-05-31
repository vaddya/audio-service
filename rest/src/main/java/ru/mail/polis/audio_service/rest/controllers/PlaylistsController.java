package ru.mail.polis.audio_service.rest.controllers;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mail.polis.audio_service.rest.model.Playlist;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
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
    public Collection<Playlist> playlists() {
        return db.getCollection("playlists")
                .find()
                .into(new ArrayList<>())
                .stream().map(x -> gson.fromJson(x.toJson(), Playlist.class))
                .collect(toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void createPlaylist(@RequestBody String playlist) {
        db.getCollection("playlists").insertOne(Document.parse(playlist));
    }

}
