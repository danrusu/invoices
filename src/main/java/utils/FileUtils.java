package utils;

import exceptions.FolderFilePathsException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.util.logging.Level.SEVERE;
import static java.util.logging.Logger.getAnonymousLogger;
import static java.util.stream.Collectors.toList;

public class FileUtils {

    private FileUtils() { }

    public static List<Path> getFilePaths(Path folder) {
        try(Stream<Path> paths = Files.walk(folder)){
            return paths
                    .filter(Files::isRegularFile)
                    .collect(toList());
        }
        catch (IOException e){
            getAnonymousLogger().log(SEVERE, "Failed to get paths from folder " + folder);
            throw new FolderFilePathsException(e);
        }
    }

    public static Path getLocalPath(String path) {
        return Paths.get(System.getProperty("user.dir"), path);
    }
}
