package ru.mail.polis.audio_service.rest.controllers;

import com.google.gson.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.mail.polis.audio_service.rest.model.Playlist;
import ru.mail.polis.audio_service.rest.model.Song;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/songs")
public class SongsController {

    private final MongoDatabase db;
    private final Gson gson;

    @Autowired
    public SongsController(MongoDatabase db, Gson gson) {
        this.db = db;
        this.gson = gson;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public Collection<Song> songs(@RequestParam(required = false) String id,
                                  @RequestParam(required = false) String name,
                                  @RequestParam(required = false) String artist,
                                  @RequestParam(required = false) String genre,
                                  @RequestParam(required = false) Integer year,
                                  @RequestParam(required = false) Integer limit) {
        Bson filter = createFilter(id, name, artist, genre, year);
        FindIterable<Document> it = db.getCollection("songs")
                .find(filter);
        it = limit != null ? it.limit(limit) : it;
        return it.into(new ArrayList<>())
                .stream().map(x -> gson.fromJson(x.toJson(), Song.class))
                .collect(toList());
    }

    private Bson createFilter(String id, String name, String artist, String genre, Integer year) {
        Bson filter = new BsonDocument();
        filter = id != null ? and(filter, eq("id", id)) : filter;
        filter = name != null ? and(filter, eq("name", name)) : filter;
        filter = artist != null ? and(filter, eq("artist", artist)) : filter;
        filter = genre != null ? and(filter, eq("genre", genre)) : filter;
        filter = year != null ? and(filter, eq("year", year)) : filter;
        return filter;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public void createSong(@RequestBody String song) {
        db.getCollection("songs").insertOne(Document.parse(song));
    }



}
