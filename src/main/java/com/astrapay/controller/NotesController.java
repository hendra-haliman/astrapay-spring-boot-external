package com.astrapay.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.astrapay.dto.NoteDto;
import com.astrapay.entity.Note;
import com.astrapay.exception.ExampleException;
import com.astrapay.service.NotesService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@Api(value = "NotesController")
@Slf4j
@RequestMapping("/api/v1")
public class NotesController {
    private final NotesService notesService;

    @Autowired
    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/notes")
    @ApiOperation(value = "List Notes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = NoteDto.class)
    })
    public ResponseEntity<?> listNotes() {
        log.info("Listing all notes");

        try {
            List<Note> notes = notesService.listNotes();

            if (notes == null || notes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No notes found");
            }

            return ResponseEntity.ok(notes);

        } catch (ExampleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/notes/{id}")
    @ApiOperation(value = "Get Note by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = NoteDto.class),
            @ApiResponse(code = 404, message = "Note not Found")
    })
    public ResponseEntity<?> getNoteById(@PathVariable Long id) {
        log.info("Getting note with ID: " + id);

        try {
            Note note = notesService.getNoteById(id);
            if (note == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
            }

            return ResponseEntity.ok(note);

        } catch (ExampleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/notes")
    @ApiOperation(value = "Create Note")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = NoteDto.class),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    public ResponseEntity<?> createNote(@RequestBody NoteDto noteDto) {
        log.info("Creating a new note: " + noteDto);

        try {
            Note note = new Note();
            note.setTitle(noteDto.getTitle());
            note.setContent(noteDto.getContent());
            note.setCreatedAt(new Date());

            Note createdNote = notesService.createNote(note);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);

        } catch (ExampleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/notes/{id}")
    @ApiOperation(value = "Delete/Remove Note")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Note not Found")
    })
    public ResponseEntity<?> deleteNote(Long id) {
        log.info("Deleting note with ID: " + id);

        try {
            notesService.deleteNote(id);
            return ResponseEntity.noContent().build();

        } catch (ExampleException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/notes/{id}")
    @ApiOperation(value = "Update Note")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = NoteDto.class),
            @ApiResponse(code = 404, message = "Note not Found"),
            @ApiResponse(code = 400, message = "Bad Request")
    })
    public ResponseEntity<?> updateNote(@PathVariable Long id, @RequestBody NoteDto noteDto) {
        log.info("Updating note with ID: " + id + " to " + noteDto);

        try {
            Note existingNote = notesService.getNoteById(id);
            if (existingNote == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Note not found");
            }

            existingNote.setTitle(noteDto.getTitle());
            existingNote.setContent(noteDto.getContent());
            // existingNote.setUpdatedAt(new Date());

            Note updatedNote = notesService.updateNote(id, existingNote);

            return ResponseEntity.ok(updatedNote);

        } catch (ExampleException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}