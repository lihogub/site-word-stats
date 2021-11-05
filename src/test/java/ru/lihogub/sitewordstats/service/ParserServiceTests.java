package ru.lihogub.sitewordstats.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.lihogub.sitewordstats.service.parser.ParserServiceImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ParserServiceTests {
    private final List<Character> allDelimiterList =
            Arrays.asList(' ', ',', '.', '!', '?', '\"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t');

    @Mock
    private RestTemplate restTemplate;

    /**
     * Using Mockito to inject mocked RestTemplate to test http request.
     * e.g. https://www.baeldung.com/mockito-annotations#injectmocks-annotation
     */
    @InjectMocks
    private ParserServiceImpl parserService;

    @Test
    public void testParseURL0() {
        Mockito
                .when(restTemplate.getForEntity("", String.class))
                .thenReturn(ResponseEntity.ok("a,b"));
        try {
            Map<String, Integer> frequency = parserService.getStatsForURL("", Collections.singletonList(','));
            Assertions.assertEquals(1, frequency.get("A"));
            Assertions.assertEquals(1, frequency.get("B"));
        } catch (Exception ignored) {
            Assertions.fail();
        }
    }

    @Test
    public void testParseURL1() {
        Mockito
                .when(restTemplate.getForEntity("", String.class))
                .thenReturn(ResponseEntity.ok("a,b.A c!a?b\"C;d:e[D]e(h)\nA\rb\tc"));
        try {
            Map<String, Integer> frequency = parserService.getStatsForURL("",
                    Arrays.asList(' ', ',', '.', '!', '?', '\"', ';', ':', '[', ']', '(', ')', '\n', '\r', '\t'));
            Assertions.assertEquals(4, frequency.get("A"));
            Assertions.assertEquals(3, frequency.get("B"));
            Assertions.assertEquals(3, frequency.get("C"));
            Assertions.assertEquals(2, frequency.get("D"));
            Assertions.assertEquals(2, frequency.get("E"));
            Assertions.assertEquals(1, frequency.get("H"));
        } catch (Exception ignored) {
            Assertions.fail();
        }
    }

    @Test
    public void testParseURL2() {
        Mockito
                .when(restTemplate.getForEntity("", String.class))
                .thenReturn(ResponseEntity.ok("A...B,.,C"));
        try {
            Map<String, Integer> frequency = parserService.getStatsForURL("", Arrays.asList('.', ','));
            Assertions.assertEquals(1, frequency.get("A"));
            Assertions.assertEquals(1, frequency.get("B"));
            Assertions.assertEquals(1, frequency.get("C"));
        } catch (Exception ignored) {
            Assertions.fail();
        }
    }

    @Test
    public void testStringSplitter0() {
        // result of split
        List<String> doubleSplit = Arrays.asList("A", "B");
        // for each delimiter try to split source string: e.g. "a*b" -> ["a", "b"]
        for (Character delimiter : allDelimiterList) {
            Assertions.assertEquals(doubleSplit, parserService.splitString("a" + delimiter + "b", allDelimiterList));
        }
    }

    @Test
    public void testStringSplitter1() {
        List<String> tripleSplit = Arrays.asList("A", "B", "C");
        // e.g. "a*b*c" -> ["a", "b", "c"]
        for (Character delimiter : allDelimiterList) {
            Assertions.assertEquals(tripleSplit, parserService.splitString("a" + delimiter + "b" + delimiter + "c", allDelimiterList));
        }
    }

    @Test
    public void testStringSplitter2() {
        List<String> tripleSplit = Arrays.asList("A", "B", "C");
        // e.g. "a*b**c" -> ["a", "b", "c"]
        for (Character delimiter : allDelimiterList) {
            Assertions.assertEquals(tripleSplit, parserService.splitString("a" + delimiter + delimiter + "b" + delimiter + "c", allDelimiterList));
        }
    }

    @Test
    public void testCountWordFrequency0() {
        Map<String, Integer> wordFrequency = parserService.countWordFrequency(Arrays.asList("a", "b", "b", "c", "a", "a"));
        Assertions.assertEquals(3, wordFrequency.get("a"));
        Assertions.assertEquals(2, wordFrequency.get("b"));
        Assertions.assertEquals(1, wordFrequency.get("c"));
        Assertions.assertEquals(3, wordFrequency.size());
    }

    @Test
    public void testCountWordFrequency1() {
        Map<String, Integer> wordFrequency = parserService.countWordFrequency(Arrays.asList("a", "a", "a", "a", "a", "a"));
        Assertions.assertEquals(6, wordFrequency.get("a"));
        Assertions.assertEquals(1, wordFrequency.size());
    }

    @Test
    public void testCountWordFrequency2() {
        Map<String, Integer> wordFrequency = parserService.countWordFrequency(Arrays.asList("a", "a", "a", "b", "b", "b"));
        Assertions.assertEquals(3, wordFrequency.get("a"));
        Assertions.assertEquals(3, wordFrequency.get("b"));
        Assertions.assertEquals(2, wordFrequency.size());
    }
}
