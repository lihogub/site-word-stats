package ru.lihogub.sitewordstats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.lihogub.sitewordstats.dto.SiteListDto;
import ru.lihogub.sitewordstats.model.SiteModel;
import ru.lihogub.sitewordstats.service.ParserService;
import ru.lihogub.sitewordstats.service.PersistenceService;
import ru.lihogub.sitewordstats.utils.Mapper;

import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MainController {
    private final ParserService parserService;
    private final PersistenceService persistenceService;

    /**
     * Shows saved sites list.
     */
    @GetMapping
    public String indexEndpoint(Model model) {
        model.addAttribute("sites", new SiteListDto(
                persistenceService
                        .getSavedSiteList()
                        .stream()
                        .map(Mapper::toSiteDto)
                        .collect(Collectors.toList()))
        );
        return "index";
    }

    /**
     * Shows words from saved site.
     */
    @GetMapping("/get/{siteId}")
    public String selectedSiteEndpoint(@PathVariable Long siteId, Model model) {
        model.addAttribute("selectedSite", persistenceService.findSiteById(siteId).orElse(null));
        return indexEndpoint(model);
    }

    /**
     * Deletes saved site from database.
     */
    @GetMapping("/delete/{siteId}")
    public String deleteEndpoint(@PathVariable Long siteId) {
        persistenceService.deleteSiteById(siteId);
        return "redirect:/";
    }

    /**
     * Parses words from specified site and saves them to database.
     */
    @PostMapping("/parse")
    public String parseEndpoint(@RequestParam("urlField") String urlField, Model model) {
        SiteModel siteModel = parserService.parse(urlField);
        persistenceService.saveSite(siteModel);
        selectedSiteEndpoint(siteModel.getId(), model);
        return "redirect:/get/" + siteModel.getId();
    }

    /**
     * Shows messange about exception.
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model) {
        log.error("handleException", e);
        model.addAttribute("exception", e);
        return indexEndpoint(model);
    }
}
