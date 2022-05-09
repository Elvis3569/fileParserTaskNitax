package directoryFolderTracker;

import fileProcessing.FileProcess;
import fileStatistics.GetAllWords;
import fileStatistics.CountDots;
import fileStatistics.HighestOcurringWord;
import interfaces.FileProcessInterface;
import interfaces.MonitorDirectoryFolderInterface;
import interfaces.StatisticsProcessInterface;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class DirectoryFolderTracker implements MonitorDirectoryFolderInterface {

    private final Path directoryFolderPassed;

    public DirectoryFolderTracker(Path directoryFolderPassed) {
        this.directoryFolderPassed = directoryFolderPassed;
    }

    private final FileProcessInterface FileProcessTxt = new FileProcess();
    private String fileContent;
    private Path processedFolder;
    WatchKey watchKey;
    WatchService watchService;

    @Override
    public void monitorDirectoryFolder() {

        //      first creates the processed folder if it is absent
        createProcessedFolder(directoryFolderPassed);

        try {
//            prints stats for existing files
            printStatistics(directoryFolderPassed);

//            the watchService is assigned to a method that monitors the directory
            watchService = FileSystems.getDefault().newWatchService();
            directoryFolderPassed.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            while ((watchKey = watchService.take()) != null) {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    String file = event.context().toString();
                    System.out.println("New directory Folder detected: "+file);
                    System.out.println("Statistics=======================================================================");

                    createProcessedFolder(directoryFolderPassed);
//                    loops through create-events and prints the stats for new files
                    printStatistics(directoryFolderPassed);

                }
                watchKey.reset();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printStatistics(Path directoryPassed) throws IOException {
//
        try{
//            stream of filePaths in the passedDirectory
            DirectoryStream<Path> files = Files.newDirectoryStream(directoryPassed, "*.txt");
            List<StatisticsProcessInterface> statisticsProcess = new ArrayList<>();
            statisticsProcess.add(new GetAllWords());
            statisticsProcess.add(new HighestOcurringWord());
            statisticsProcess.add(new CountDots());

//            looping through the filePaths and calls the processStats method on each file

            files.forEach(filePath -> {
                fileContent = FileProcessTxt.readFile(filePath);
                Path fileName = filePath.getFileName();
                statisticsProcess.forEach(s -> System.out.println("File name: " + fileName + ". File statistics: " + s.processStatistics(fileContent)));
                moveProcessedFiles(filePath);
            });
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    @Override
    public void createProcessedFolder(Path pathToPassedDirectory) {
//        checks if processedFolder has been created and creates the folder if it has not been created
        processedFolder = pathToPassedDirectory.resolve("processedFolder");
        try {
            if (!Files.exists(processedFolder)) {
                Files.createDirectories(processedFolder);
            }
        } catch (Exception e) {
            System.out.println("failed");
        }
    }

    @Override
    public void moveProcessedFiles(Path filePath) {
//        method to move file
        try {
            Files.move(filePath, processedFolder.resolve(filePath.getFileName().toString()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
