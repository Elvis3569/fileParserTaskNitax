package interfaces;

import java.io.IOException;
import java.nio.file.Path;

public interface MonitorDirectoryFolderInterface {

    void monitorDirectoryFolder();

    void printStatistics(Path passedDirectory) throws IOException;

    void createProcessedFolder(Path pathToPassedDirectory);

    void moveProcessedFiles(Path filePath);
}
