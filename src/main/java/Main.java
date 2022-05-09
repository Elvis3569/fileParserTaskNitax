import directoryFolderTracker.DirectoryFolderTracker;
import interfaces.MonitorDirectoryFolderInterface;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {

        //confirms if any directory path was passed in

        if(args.length == 0){
            System.out.println("A directory path is needed in the program argument");
            return;
        }

        Path directoryPath = Path.of(args[0]);

        //Check if directoryPath doesn't Exist...
        //Prints exception to say that directoryPath doesn't exist
        //else it executes the next line of code.

        if(!Files.exists(directoryPath)){
            System.out.println("Path does not exist." );
            return;
        }


        boolean pathExist = Files.exists(directoryPath);
        System.out.println("Folder Exists");

        MonitorDirectoryFolderInterface inspectDirectory = new DirectoryFolderTracker(directoryPath);
        inspectDirectory.monitorDirectoryFolder();

    }
}
