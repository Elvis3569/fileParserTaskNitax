package fileStatistics;

import interfaces.StatisticsProcessInterface;

public class CountDots implements StatisticsProcessInterface {

    @Override
    public String processStatistics(String fileContent) {

        String [] words = fileContent.split("");

        int dotCount = 0;

        for (String chars : words) {

            if (chars.equals(".")) {
                dotCount++;
            }

        }
        return "The total number of dots is = " + dotCount;

    }
}
