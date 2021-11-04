package ru.lihogub.sitewordstats.service.parser;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.lihogub.sitewordstats.model.SiteModel;
import ru.lihogub.sitewordstats.model.WordOccurrenceModel;
import ru.lihogub.sitewordstats.service.ParserService;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {
    private final RestTemplate restTemplate;
    private final List<Character> allDelimitersList = List
            .of(' ', ',', '.', '!', '?', '\"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t');

    /**
     * Parses specified URL.
     */
    @Override
    public SiteModel parse(String URL) {
        Map<String, Integer> wordFrequencyMap = getStatsForURL(URL, allDelimitersList);
        // Map stats to "Site" domain model.
        List<WordOccurrenceModel> wordOccurrenceModelList = new ArrayList<>();
        for (var entry : wordFrequencyMap.entrySet()) {
            wordOccurrenceModelList.add(new WordOccurrenceModel(null, entry.getKey(), entry.getValue().longValue()));
        }
        // Sort by frequency descending.
        wordOccurrenceModelList.sort((o1, o2) -> (int) (o2.getCount() - o1.getCount()));
        return new SiteModel(null, URL, wordOccurrenceModelList);
    }

    /**
     * Splits URL body with given delimiters list and counts its frequencies.
     */
    public Map<String, Integer> getStatsForURL(String urlString, List<Character> delimiterList) {
        // Do http request to get page source code using RestTemplate (esp. easy for mock).
        ResponseEntity<String> response = restTemplate.getForEntity(urlString, String.class);
        String responseString = response.getBody();
        // Check string != null (Jsoup.parse contract).
        assert responseString != null;
        // Solution for parsing from https://stackoverflow.com/a/9826027/16415543
        String parsedText = Jsoup.parse(responseString).body().text();
        List<String> splitWordList = splitString(parsedText, delimiterList);
        return countWordFrequency(splitWordList);
    }

    /**
     * Splits string with given delimiters list.
     */
    public List<String> splitString(String sourceText, List<Character> delimiterList) {
        // Concat delimiters list to string, e.g. ['.', ',', '-'] -> ".,-"
        String delimitersString = delimiterList
                .stream()
                .map(Object::toString)
                .collect(Collectors.joining());
        // Call Splitter(from Google Guava) with string of delimiters
        // Code from https://www.baeldung.com/java-string-split-multiple-delimiters#2-guava-solution
        Iterable<String> words = Splitter
                .on(CharMatcher.anyOf(delimitersString))
                .split(sourceText);
        // Collect Iterable to List
        return StreamSupport
                .stream(words.spliterator(), false)
                .filter(word -> !word.isBlank())
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }

    /**
     * Count elements in collection.
     * Solution found at https://stackoverflow.com/a/5211215/16415543
     */
    public Map<String, Integer> countWordFrequency(List<String> wordList) {
        Set<String> wordSet = new HashSet<>(wordList);
        Map<String, Integer> wordCount = new HashMap<>();
        for (var word : wordSet) {
            wordCount.put(word, Collections.frequency(wordList, word));
        }
        return wordCount;
    }
}
