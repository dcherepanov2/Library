package com.example.MyBookShopApp.service.tagServices;

import com.example.MyBookShopApp.data.book.Book;
import com.example.MyBookShopApp.data.tags.Tag;
import com.example.MyBookShopApp.repo.bookrepos.BookRepo;
import com.example.MyBookShopApp.repo.tagrepos.TagRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class TagService {

    private final TagRepo tagRepo;
    private final BookRepo bookRepo;

    @Autowired
    public TagService(TagRepo tagRepo, BookRepo bookRepo) {
        this.tagRepo = tagRepo;
        this.bookRepo = bookRepo;
    }

    public List<Book> findBooksBySlug(String slug, Integer offset, Integer limit){
        Pageable pageable = PageRequest.of(offset,limit);
        return bookRepo.findBooksByTag(slug, pageable);
    }

    public List<Tag> findAll() {//TODO: не возможно проверить на моудльном тестировании, т.к. pageable генерируется всегда разный
        Double allTagsClicks = 0.0;
        Random random = new Random();
        Pageable next = PageRequest.of(random.nextInt(30),30);
        List<Tag> tags = tagRepo.findAll(next).getContent();
        for(Tag tag:tags){
            allTagsClicks += bookRepo.findBooksByTagCount(tag.getSlug());
        }
        allTagsClicks = allTagsClicks/tags.size()/4;
        for(Tag tag:tags){
            Double localCount = Double.valueOf(bookRepo.findBooksByTagCount(tag.getSlug()));
            if(localCount<=allTagsClicks){
                tag.setSize(0);
            }
            else if(localCount<=allTagsClicks*2){
                tag.setSize(1);
            }
            else if(localCount<=allTagsClicks*3){
                tag.setSize(3);
            }
            else if(localCount<=allTagsClicks*4){
                tag.setSize(4);
            }
            else if(localCount<=allTagsClicks*5){
                tag.setSize(5);
            }
            tagRepo.save(tag);
        }
        return tagRepo.findAll(next).getContent();
    }

    public Tag findTagBySlug(String slug) {
        Tag tag = tagRepo.findBySlug(slug);
        Integer clickNew = tag.getTagClicks()+1;
        tag.setTagClicks(clickNew);
        tagRepo.save(tag);
        return tag;
    }
}
