package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("/Users/vovamv/Desktop/Учоба/Java/Logs/src/com/company/logs.txt");
        Path path2 = Paths.get("/Users/vovamv/Desktop/Учоба/Java/Logs/src/com/company/logs2.txt");
        String pathString = "/Users/vovamv/Desktop/Учоба/Java/Logs/src/com/company/logs.txt";
        LocalDateTime start = LocalDateTime.now();

        //STREAM

        System.out.println("------------streams------------");
        List<String> logsViaStreams =
                Files.lines(path)
                .filter(log -> log.contains("2020-01"))
                .filter(log -> log.contains("ERROR"))
                .filter(log -> {
                    List<String> timeString = Arrays.asList(log.split(" ++"));
                    LocalTime time = LocalTime.parse(timeString.get(1));;
                    if(time.isAfter(LocalTime.of(18,59,59))) {
                        return true;
                    }
                    if(time.isBefore(LocalTime.of(7,0,0))){
                        return true;
                    }
                        return false;
                })
                .collect(Collectors.toList());


        String errorLogsViaStreams = "";
        for (String log: logsViaStreams) {
            errorLogsViaStreams += log + "\n";
        }
        Path errorPathViaStream = Paths.get("/Users/vovamv/Desktop/Учоба/Java/Logs/src/com/company/errorLogsViaStreams.txt");
        Files.write(errorPathViaStream,errorLogsViaStreams.getBytes());
        System.out.println("Time: " + ChronoUnit.MILLIS.between(start, LocalDateTime.now()));
        System.out.println("Size: " + logsViaStreams.size());
        errorLogsViaStreams = null;
        for (String item : logsViaStreams){
            item = null;
        }

        //STRING

        System.out.println("------------strings------------");
        start = LocalDateTime.now();

        String logsString = new String(Files.readAllBytes(path2));
        List<String> logsViaStrings = Arrays.asList(logsString.split(System.lineSeparator()));
        String errorLogsViaStrings = "";
        List<String> errorLogs = new ArrayList<>();

        for (String log : logsViaStrings){
            if(log.contains("2020-01") && log.contains("ERROR")) {
                List<String> timeString = Arrays.asList(log.split(" ++"));
                LocalTime time = LocalTime.parse(timeString.get(1));
                if(time.isAfter(LocalTime.of(18,59,59))) {
                    errorLogsViaStrings += log;
                    errorLogs.add(log);
                    continue;
                }
                if(time.isBefore(LocalTime.of(7,0,0))){
                    errorLogsViaStrings += log;
                    errorLogs.add(log);
                }
            }
        }
        Path errorPathViaString = Paths.get("/Users/vovamv/Desktop/Учоба/Java/Logs/src/com/company/errorLogsViaString.txt");
        Files.write(errorPathViaString,errorLogsViaStrings.getBytes());
        System.out.println("Time: " + ChronoUnit.MILLIS.between(start, LocalDateTime.now()));
        System.out.println("Size: " + errorLogs.size());

        //SCANNER

        System.out.println("------------scanner------------");
        start = LocalDateTime.now();
        Scanner scanner = new Scanner(new File(pathString));

        List<String> errorLogsViaScannerList = new ArrayList<>();
        String errorsViaScannerString = "";

        while (scanner.hasNext()){
            String log = scanner.nextLine();
            if (log.contains("2020-01") && log.contains("ERROR")){
                List<String> timeString = Arrays.asList(log.split(" ++"));
                LocalTime time = LocalTime.parse(timeString.get(1));
                if(time.isAfter(LocalTime.of(18,59,59))){
                    errorLogsViaScannerList.add(log);
                    errorsViaScannerString += log;
                    continue;
                }
                if(time.isBefore(LocalTime.of(7,0,0))){
                    errorLogsViaScannerList.add(log);
                    errorsViaScannerString += log;
                }
            }
        }

        Path errorPathViaScanner = Paths.get("/Users/vovamv/Desktop/Учоба/Java/Logs/src/com/company/errorLogsViaScanner.txt");
        Files.write(errorPathViaScanner,errorsViaScannerString.getBytes());
        System.out.println("Time: " + ChronoUnit.MILLIS.between(start, LocalDateTime.now()));
        System.out.println("Size: " + errorLogsViaScannerList.size());
    }
}
