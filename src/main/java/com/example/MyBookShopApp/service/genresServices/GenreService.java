package com.example.MyBookShopApp.service.genresServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.dto.GenreEntitySort;
import com.example.MyBookShopApp.repo.genrerepos.GenreRepo;
import com.example.MyBookShopApp.utils.GenreEntitySortComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GenreService {

    private final GenreRepo genreRepo;

    @Autowired
    public GenreService(GenreRepo genreRepo) {
        this.genreRepo = genreRepo;
    }

    public GenreEntity findBySlug(String slug) {
        return genreRepo.findBySlug(slug);
    }

    public List<Book> allBooksByGenreSlug(String slug, Integer offset, Integer limit) {
        GenreEntity genreSlug = genreRepo.findBySlug(slug);
        GenreEntitySort booksByGenre = Stream.of(Collections.singletonList(genreSlug),
                        genreRepo.findByParentId(genreSlug.getId()))
                .flatMap(Collection::stream)
                .map(GenreEntitySort::new)
                .sorted(GenreEntitySort::compareTo)
                .reduce(this::makeTree).orElse(null);
        List<Book> response = Objects.requireNonNull(booksByGenre).getBooks();
        if (response.size() > limit && response.size() > offset * limit)
            return response.subList(offset * limit, limit);
        else if (response.size() < offset * limit)
            return Collections.emptyList();
        return response.subList(offset * limit, response.size());
        //Пришлось реализовать сортировку книг с помощью list, а не с помощью sql-запроса, т.к. неизвестна глубина обхода
    }

    public GenreEntitySort allGenreTree() {
        GenreEntitySort genreEntitySort = genreRepo.findAll().stream().map(GenreEntitySort::new)
                .sorted(GenreEntitySort::compareTo)
                .reduce(this::makeTree).orElse(null);
        return sortBookOfBookSize(Objects.requireNonNull(genreEntitySort));
    }

    private GenreEntitySort makeTree(GenreEntitySort parent, GenreEntitySort child) {
        if (child.getParentId() == parent.getId()) {
            parent.getChild().add(child);
            List<Book> allBooks = Stream.of(parent.getBooks(), child.getBooks())
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            parent.setBooks(allBooks);
            parent.incrementBooksCount(child.getBooksCount());
        } else {
            parent.getChild().forEach(subParent -> makeTree(subParent, child));
        }
        return parent;
    }

    private GenreEntitySort sortBookOfBookSize(GenreEntitySort genre) {
        GenreEntitySortComparator genreEntitySortComparator = new GenreEntitySortComparator();
        if (genre.getChild() != null && genre.getBooksCount() != 0) {
            genre.getChild().sort(genreEntitySortComparator);
            genre.getChild().forEach(this::sortBookOfBookSize);
        }
        return genre;
    }
}