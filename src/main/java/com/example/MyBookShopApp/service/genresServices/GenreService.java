package com.example.MyBookShopApp.service.genresServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.genre.GenreEntity;
import com.example.MyBookShopApp.dto.GenreEntitySort;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.genrerepos.GenreRepo;
import com.example.MyBookShopApp.utils.GenreEntitySortComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GenreService {

    private final GenreRepo genreRepo;
    private final BookRepo bookRepo;

    @Autowired
    public GenreService(GenreRepo genreRepo, BookRepo bookRepo) {
        this.genreRepo = genreRepo;
        this.bookRepo = bookRepo;
    }

    public Map<GenreEntity,Map<GenreEntity,List<GenreEntity>>> getTreeGenre(){
        List<GenreEntity> allGenresFromGenreService = genreRepo.findByParentId(0);

        Map<GenreEntity,List<GenreEntity>> level2 = new LinkedHashMap<>();
        Map<GenreEntity,Map<GenreEntity,List<GenreEntity>>> allGenres = new LinkedHashMap<>();
        for(GenreEntity genre: allGenresFromGenreService){
            for(GenreEntity genre1: genreRepo.findByParentId(genre.getId())){
                level2.put(genre1,genreRepo.findByParentId(genre1.getId()));
            }
            allGenres.put(genre, level2);
            level2 = new HashMap<>();
        }
        return allGenres;
    }


    public GenreEntity findBySlug(String slug){
        return genreRepo.findBySlug(slug);
    }
    public List<Book> allBooksByGenre(String slug, Integer offset,Integer limit){
        List<Book> allBooksGenres = new ArrayList<>();
        List<GenreEntity> allGenres = new ArrayList<>();
        GenreEntity genreSlug = genreRepo.findBySlug(slug);
        allGenres.add(genreSlug);
        for (GenreEntity genre1: genreRepo.findByParentId(genreSlug.getId())){
            allGenres.addAll(genreRepo.findByParentId(genre1.getId()));
        }
        Pageable pageable = PageRequest.of(offset,limit);
        for(GenreEntity genreEntity: allGenres){
            allBooksGenres.addAll(bookRepo.findBooksByGenreSlug(genreEntity.getSlug(), pageable));
        }
        return allBooksGenres;
    }
    public GenreEntitySort allGenreTree(){
        GenreEntitySort genreEntitySort = genreRepo.findAll().stream().map(GenreEntitySort::new)
                .sorted(GenreEntitySort::compareTo)
                .reduce(this::makeTree).orElse(null);
        return sortBookOfBookSize(Objects.requireNonNull(genreEntitySort));
    }

    private GenreEntitySort makeTree(GenreEntitySort parent, GenreEntitySort child){
        if (child.getParentId() == parent.getId()) {
            parent.getChild().add(child);
            parent.incrementBooksCount(child.getBooksCount());
        } else {
            parent.getChild().forEach(subParent -> makeTree(subParent, child));
        }
        return parent;
    }

    private GenreEntitySort sortBookOfBookSize(GenreEntitySort genre){
        GenreEntitySortComparator genreEntitySortComparator = new GenreEntitySortComparator();
        if(genre.getChild() != null && genre.getBooksCount()!= 0){
            genre.getChild().sort(genreEntitySortComparator);
            genre.getChild().forEach(this::sortBookOfBookSize);
        }
        return genre;
    }
}
























//    Map<Integer, List<GenresPageDto>> allParentSorted = pageDtoList.stream()
//            .collect(Collectors.groupingBy(GenresPageDto::getParent_id));
//Map<Integer,Map<Integer, List<GenresPageDto>>>
//       return allParentSorted;