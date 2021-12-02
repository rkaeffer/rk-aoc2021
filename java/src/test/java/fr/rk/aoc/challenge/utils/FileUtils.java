package fr.rk.aoc.challenge.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ListIterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public final class FileUtils {

    public static Optional<Stream<String>> readInputFileAsStream(String pathInTestResources, int day) {
        try {
            return Optional.of(Files.lines(Paths.get("src/test/resources/day" + day + "/" + pathInTestResources)));
        } catch (IOException ioException) {
            log.error("Unable to read inputFile : {}", ioException.getMessage());
        }
        return Optional.empty();
    }

    public static Optional<List<String>> readInputFileAsList(String pathInTestResources, int day) {
        return FileUtils.readInputFileAsStream(pathInTestResources, day)
                .map(stringStream -> stringStream.collect(Collectors.toList()));
    }

}
