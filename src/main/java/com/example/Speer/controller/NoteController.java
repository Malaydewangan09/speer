package com.example.Speer.controller;

import com.example.Speer.Service.NoteService.NotesService;
import com.example.Speer.entity.NoteDetails;
import com.example.Speer.entity.UserInfo;
import com.example.Speer.repository.Note.NoteRepository;
import com.example.Speer.repository.User.UserInfoRepository;
import com.example.Speer.request.note.NoteAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/notes")
public class NoteController {
    @Autowired
    UserInfoRepository userInfoRepository;
    @Autowired
    NoteRepository noteRepository;
    @Autowired
    NotesService notesService;

    @GetMapping("/")
    public ResponseEntity<?> GetAllNotes(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserName(principal.getName());
        if (optionalUserInfo.isEmpty()) return ResponseEntity.badRequest().body("User Not found, Please Try again!");
        return ResponseEntity.ok(noteRepository.findAll());
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<?> GetNoteDetailsByNoteId(@PathVariable(required = true) Long noteId,
                                                    HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserName(principal.getName());
        if (optionalUserInfo.isEmpty()) return ResponseEntity.badRequest().body("User Not found, Please Try again!");
        return ResponseEntity.ok(noteRepository.findById(noteId));
    }

    @PostMapping("/notes")
    public ResponseEntity<?> AddNoteDetails(@Validated @RequestBody NoteAddRequest noteAddRequest,
                                            HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserName(principal.getName());
        if (optionalUserInfo.isEmpty()) return ResponseEntity.badRequest().body("User Not found, Please Try again!");
        notesService.addNoteDetails(noteAddRequest);
        return ResponseEntity.ok("Note Details saved!");
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<?> UpdateNoteDetails(@PathVariable(required = true) Long noteId,
                                               @Validated @RequestBody NoteAddRequest noteAddRequest,
                                               HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserName(principal.getName());
        if (optionalUserInfo.isEmpty()) return ResponseEntity.badRequest().body("User Not found, Please Try again!");
        Optional<NoteDetails> optionalNoteDetails = noteRepository.findById(noteId);
        if (optionalNoteDetails.isEmpty())
            return ResponseEntity.badRequest().body("Note Details Not found, Please provide a valid NoteId!");
        notesService.updateNoteDetails(noteId, noteAddRequest, optionalNoteDetails);
        return ResponseEntity.ok("Note Details updated!");
    }

    @DeleteMapping("/{noteId}")
    @Transactional
    public ResponseEntity<?> DeleteNoteDetails(@PathVariable(required = true) Long noteId, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserName(principal.getName());
        if (optionalUserInfo.isEmpty()) return ResponseEntity.badRequest().body("User Not found, Please Try again!");
        Optional<NoteDetails> optionalNoteDetails = noteRepository.findById(noteId);
        if (optionalNoteDetails.isEmpty())
            return ResponseEntity.badRequest().body("Note Details Not found, Please provide a valid NoteId!");
        noteRepository.deleteById(noteId);
        return ResponseEntity.ok("Note Details deleted!");
    }

    @GetMapping(value = {"/search/{searchText}", "/search"})
    public ResponseEntity<?> SearchNotes(@PathVariable(required = true) String searchText, HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        Optional<UserInfo> optionalUserInfo = userInfoRepository.findByUserName(principal.getName());
        if (optionalUserInfo.isEmpty()) return ResponseEntity.badRequest().body("User Not found, Please Try again!");
        List<Object[]> notesList = notesService.searchNotes(searchText);
        return ResponseEntity.ok(notesList);
    }


}
