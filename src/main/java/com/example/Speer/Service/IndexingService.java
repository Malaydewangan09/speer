package com.example.Speer.Service;

import com.example.Speer.entity.NoteDetails;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class IndexingService {
    @PersistenceContext
    private EntityManager entityManager;

    public FullTextQuery getJpaQueryNoteDetails(org.apache.lucene.search.Query luceneQuery) {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        return fullTextEntityManager.createFullTextQuery(luceneQuery, NoteDetails.class);
    }
    public QueryBuilder getQueryBuilderNoteDetails() {
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        return fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(NoteDetails.class).get();
    }
}
