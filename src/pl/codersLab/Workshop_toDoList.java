package pl.codersLab;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Workshop_toDoList {

    static final String nameOfFile = "tasks.csv";
    static final String [] options = {"add", "remove", "list", "exit"};
    public static void main(String[] args) {

        print();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            switch (input){
                case "add":
                    addTask();
                    print();
                    break;
                case "remove":
                    break;
                case "list":
                    listingTasksFromFile(load(nameOfFile));
                    print();
                case "exit":
                    break;
                default:
                    System.out.println("Please select a correct option.");
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
        while(condition == true){
            importanceOfTask = scanner.nextLine();
            if (("true".equals(importanceOfTask)) || ("false".equals(importanceOfTask))){
                condition = false;
            }else {
                System.out.println("Please type 'true' or 'false'");
            }
        }
        String [][] newTable = load(nameOfFile);
        newTable = Arrays.copyOf(newTable, newTable.length +1);
        newTable[newTable.length - 1] = new String[3];
        newTable[newTable.length - 1][0] = descriptionOfTask;
        newTable[newTable.length - 1][1] = dueDate;
        newTable[newTable.length - 1][2] = importanceOfTask;
        String number = String.valueOf(newTable.length - 1);
        System.out.println(number);
//        try{
//            Files.write();
//        } catch (IOException e) {
//            System.out.println("Coudnt save to file " + e.getMessage());
//        }
    }
    public static void listingTasksFromFile (String [][] tab){
        for (int i = 0; i < tab.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < 3; j++) {
                String lineToPrint = new String(tab[i][j]);
                System.out.print(lineToPrint);
            }
            System.out.println();
        }
    }

    public static void print (){
        System.out.println(pl.coderslab.ConsoleColors.BLUE + "Please select an option:");
        System.out.println(String.format(pl.coderslab.ConsoleColors.RESET + "%s\n%s\n%s\n%s",options[0],options[1],options[2],options[3]));
    }
    public static String [][] load (String nameOfFile){
        Path filePath = Paths.get(nameOfFile);
        String [][] dane = null;
        try{
            List<String> strings = Files.readAllLines(filePath);
//            System.out.println(strings); //spr czy czyta plik i go wyswietla
            dane = new String [strings.size()][strings.get(0).split(",").length];
//            System.out.println(Arrays.deepToString(dane)); // spr czy dane ma dobry rozmiar
            for (int i = 0; i < strings.size(); i++) {
                for (int j = 0; j < 3; j++) {
                   String [] tabSplit = strings.get(i).split(",");
                    dane[i][j] = tabSplit[j];
                }
            }
//            System.out.println(Arrays.deepToString(dane)); //spr czy dobrze przeniesiono dane do tabeli[][]
        }catch (IOException e){
            System.out.println("Error ocurred: " + e.getMessage());
        }
        return dane;
    }
}
