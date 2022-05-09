package fileStatistics;

import interfaces.StatisticsProcessInterface;

import java.util.Arrays;

public class GetAllWords implements StatisticsProcessInterface {

    @Override
    public String processStatistics(String fileContent) {

        String [] words = fileContent.split("\\s");

        long count = Arrays.stream(words).count();

        return "The total number of words is = " + count;
    }
}
