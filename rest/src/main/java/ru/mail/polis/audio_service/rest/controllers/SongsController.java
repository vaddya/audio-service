package ru.mail.polis.audio_service.rest.controllers;

import com.google.gson.Gson;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mail.polis.audio_service.rest.model.Song;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController("/songs")
public class SongsController {

    private final MongoDatabase db;
    private final Gson gson;

    @Autowired
    public SongsController(MongoDatabase db, Gson gson) {
        this.db = db;
        this.gson = gson;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Collection<Song> songs() {
        return db.getCollection("songs")
                .find()
                .into(new ArrayList<>())
                .stream().map(x -> gson.fromJson(x.toJson(), Song.class))
                .collect(toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void createSong(@RequestBody String song) {
        db.getCollection("songs").insertOne(Document.parse(song));
    }

}
