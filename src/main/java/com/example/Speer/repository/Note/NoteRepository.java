package com.example.Speer.repository.Note;

import com.example.Speer.entity.NoteDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<NoteDetails,Long> {
}
