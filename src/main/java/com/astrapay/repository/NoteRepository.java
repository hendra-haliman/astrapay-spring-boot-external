package com.astrapay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astrapay.entity.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {

}
