package com.astrapay.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.astrapay.entity.Note;
import com.astrapay.repository.NoteRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotesService {
    private NoteRepository noteRepository;

    @Autowired
    public NotesService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    /**
     * Get a list of notes.
     *
     * @param none
     * 
     * @return a list of notes
     */
    public List<Note> listNotes() {
        log.info("Returning an list of notes");

        List<Note> notes = noteRepository.findAll();

        return notes != null ? notes : new ArrayList<>();
    }

    /**
     * Create a new note.
     * 
     * @param note the note to create
     * 
     * @return the created note
     */
    public Note createNote(Note note) {
        log.info("Creating a new note: " + note);

        return noteRepository.save(note);
    }

    /**
     * Update an existing note.
     * 
     * @param id   the ID of the note to update
     * @param note the note with updated info
     * 
     * @return the updated note
     */
    public Note updateNote(Long id, Note note) {
        log.info("Updating note with ID: " + id + " to " + note);

        return noteRepository.findById(id)
                .map(existingNote -> {
                    existingNote.setTitle(note.getTitle());
                    existingNote.setContent(note.getContent());
                    return noteRepository.save(existingNote);
                })
                .orElseThrow(() -> new RuntimeException("Note not found with ID: " + id));
    }

    /**
     * Delete a note by its ID.
     * 
     * @param id the ID of the note to delete
     * 
     * @return void
     */
    public void deleteNote(Long id) {
        log.info("Deleting note with ID: " + id);
    }

    public Note getNoteById(Long id) {
        log.info("Fetching note with ID: " + id);

        return noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Note not found with ID: " + id));
    }
}