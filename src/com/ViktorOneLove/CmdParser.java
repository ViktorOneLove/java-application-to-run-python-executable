package com.ViktorOneLove;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class CmdParser {

    public void run() {
        Timer timer = new Timer();
        try {
            String command = parseInput(readInput());
            runCommand(command, timer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            timer.cancel();
        }
    }

    private String readInput() {
        Scanner scanner= new Scanner(System.in);
        System.out.println("Path to python executable: ");
        return scanner.nextLine();
    }

    private String parseInput(String stringToParse) {
        String commandToAppend = "-m timeit -r 10";
        return stringToParse + " " + commandToAppend;
    }

    private void runCommand(String command, Timer timer) throws SecurityException, IOException,
            NullPointerException, IllegalArgumentException {
        Process process = Runtime.getRuntime().exec(command);
        timer.schedule(new PrintTime(), 0, 1000);
        printResults(process, timer);
    }

    public void printResults(Process process, Timer timer) throws IOException {
        BufferedReader output = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errors = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        String line;

        StringBuffer errorString = new StringBuffer();
        while ((line = errors.readLine()) != null) {
            errorString.append(line).append("\n");
        }
        StringBuffer outputString = new StringBuffer();
        while ((line = output.readLine()) != null) {
            outputString.append(line).append("\n");
        }

        timer.cancel();

        System.out.println(errorString.toString());
        System.out.println(outputString.toString());
    }

}


class PrintTime extends TimerTask {

    PrintTime() {
        seconds = 0;
    }

    public void run() {
        System.out.println(seconds);
        seconds++;
    }

    private int seconds;
}