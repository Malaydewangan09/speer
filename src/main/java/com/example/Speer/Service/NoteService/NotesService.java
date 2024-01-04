package com.example.Speer.Service.NoteService;

import com.example.Speer.Service.IndexingService;
import com.example.Speer.entity.NoteDetails;
import com.example.Speer.repository.Note.NoteRepository;
import com.example.Speer.request.note.NoteAddRequest;
import org.apache.lucene.search.Query;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class NotesService {
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    IndexingService indexingService;
    public void addNoteDetails(NoteAddRequest noteAddRequest) {
        NoteDetails noteDetails = new NoteDetails();
        noteDetails.setTitle(noteAddRequest.getTitle());
        noteDetails.setDescription(noteAddRequest.getDescription());
        try {
            noteRepository.save(noteDetails);
        } catch (Exception e) {
            throw new RuntimeException("Error occured while saving Note Details. Please try again");
        }
    }

    public void updateNoteDetails(Long noteId, NoteAddRequest noteAddRequest, Optional<NoteDetails> optionalNoteDetails) {
        NoteDetails noteDetails = optionalNoteDetails.get();
        noteDetails.setTitle(noteAddRequest.getTitle());
        noteDetails.setDescription(noteAddRequest.getDescription());
        try {
            noteRepository.save(noteDetails);
        } catch (Exception e) {
            throw new RuntimeException("Error occured while updating Note Details. Please try again");
        }
    }

    public List<Object[]> searchNotes(String searchText) {
        QueryBuilder qb = indexingService.getQueryBuilderNoteDetails();
        BooleanJunction<BooleanJunction> bool = qb.bool();
        if (searchText != null) {
            searchText = searchText.toLowerCase(Locale.ROOT);
            String[] words = searchText.toLowerCase().split("\\s+");
            for (String word : words) {
                bool.must(qb.bool().should(qb.keyword().wildcard().onField("description").andField("title").matching(searchText).createQuery()).createQuery());
            }
        } else {
            bool.must(qb.all().createQuery());
        }
        Query query = bool.createQuery();
        FullTextQuery noteQuery = indexingService.getJpaQueryNoteDetails(query);
        noteQuery.setProjection("noteId","title","description");
        List<Object[]> list = noteQuery.getResultList();
        return list;
    }
}
