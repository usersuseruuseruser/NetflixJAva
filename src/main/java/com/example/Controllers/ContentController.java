package com.example.Controllers;

import com.example.dto.*;
import com.example.models.ContentType;
import com.example.models.Genre;
import com.example.models.Movie;
import com.example.services.Abstractions.IContentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/content")
public class ContentController {
    private final IContentService contentService;

    public ContentController(IContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/promos")
    public List<PromoDto> getPromos() {
        return contentService.getPromos();
    }

    @GetMapping("/sections")
    public List<SectionDto> getSections() {
        return contentService.getSections();
    }

    @GetMapping("/{id:\\d+}")
    public MovieViewDto getMovieById(@PathVariable long id) {
        return contentService.getMovieById(id);
    }

    @GetMapping("/types")
    public List<ContentType> getContentTypes() {
        return contentService.getContentTypes();
    }

    @GetMapping("/genres")
    public List<GenreDto> getGenres(){
        return contentService.getGenres();
    }

    @GetMapping("/filter")
    public List<MovieViewDto> getContentByFilter(@ModelAttribute FilterDto filter){
        return contentService.getContentByFilter(filter);
    }
}
