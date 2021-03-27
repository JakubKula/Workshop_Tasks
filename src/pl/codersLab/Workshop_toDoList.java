package pl.codersLab;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Workshop_toDoList {

    static final String nameOfFile = "tasks.csv";
    static final String [] options = {"add", "remove", "list", "exit"};
    static final Path pathToFile = Paths.get(nameOfFile);
    static String [][] tableOfTasks;
    public static void main(String[] args) {

        tableOfTasks = load();
        print();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input){
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask();
                    break;
                case "list":
                    listingTasksFromFile(tableOfTasks);
                    break;
                case "exit":
                    quit();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            print();
        }
    }
    public static void quit (){
           String [] lines = new String[tableOfTasks.length];
            for (int i = 0; i < tableOfTasks.length; i++) {
                lines[i] = String.join(",", tableOfTasks[i]);
            }
            try {
                Files.write(pathToFile, Arrays.asList(lines));
            } catch (IOException e) {
                System.out.println("Coudnt save to file " + e.getMessage());
            }
    }
    public static void removeTask(){
        System.out.println("Please select number to remove");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if(NumberUtils.isParsable(line)){
                int number = Integer.parseInt(line);
                        try{
                            tableOfTasks = ArrayUtils.remove(tableOfTasks, number);
                        }catch(ArrayIndexOutOfBoundsException e){
                            System.out.println("Item of that number does not exist");
                        }
            }else{
                System.out.println("Please give number");
            }
        }
    }
    public static void addTask (){
        System.out.println("Please add task description");
        Scanner scanner = new Scanner(System.in);
        String descriptionOfTask = scanner.nextLine();
        System.out.println("Please add task due date");
        String dueDate = scanner.nextLine();
        System.out.println("Is your task imortant: true/false");
        String importanceOfTask = null;
        boolean condition = true;
        while(condition){
            importanceOfTask = scanner.nextLine();
            if (("true".equals(importanceOfTask)) || ("false".equals(importanceOfTask))){
                condition = false;
            }else {
                System.out.println("Please type 'true' or 'false'");
            }
        }
        tableOfTasks = Arrays.copyOf(tableOfTasks, tableOfTasks.length +1);
        tableOfTasks[tableOfTasks.length - 1] = new String[3];
        tableOfTasks[tableOfTasks.length - 1][0] = descriptionOfTask;
        tableOfTasks[tableOfTasks.length - 1][1] = dueDate;
        tableOfTasks[tableOfTasks.length - 1][2] = importanceOfTask;
    }
    public static void listingTasksFromFile (String [][] tab){
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < 3; j++) {
                String lineToPrint = (tab[i][j] + " ");
                System.out.print(lineToPrint);
            }
            System.out.println();
        }
    }

    public static void print (){
        System.out.println(pl.coderslab.ConsoleColors.BLUE + "Please select an option:");
        System.out.println(String.format(pl.coderslab.ConsoleColors.RESET + "%s\n%s\n%s\n%s",options[0],options[1],options[2],options[3]));
    }
    public static String [][] load (){
        try{
            List<String> strings = Files.readAllLines(pathToFile);
            tableOfTasks = new String [strings.size()][strings.get(0).split(",").length];
            for (int i = 0; i < strings.size(); i++) {
                for (int j = 0; j < 3; j++) {
                   String [] tabSplit = strings.get(i).split(",");
                    tableOfTasks[i][j] = tabSplit[j];
                }
            }
        }catch (IOException e){
            System.out.println("Error ocurred: " + e.getMessage());
        }
        return tableOfTasks;
    }
}
