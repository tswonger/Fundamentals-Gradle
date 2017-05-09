package com.thomasSwonger;

import java.util.*;
import com.google.gson.Gson;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


class Tasks implements Comparable<Tasks>{

    //variables
    public String title;
    public String description;
    public int priority;

    public Tasks(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    @Override
    public int compareTo(Tasks o) {

        if(!this.title.equals(o.title)) {
            return this.title.compareTo(o.title);
        }
        else{
            return this.priority - o.priority;
        }
    }
}

class TaskCollection implements Iterable<Tasks>{

    private List<Tasks> internalList = new ArrayList<>();
    Scanner input = new Scanner(System.in);

    public void addTask(){

        String title;
        String description;
        int priority = 0;
        boolean isInt = false;

        System.out.println("Enter the new task's name.");
        title = input.nextLine();
        System.out.println("Enter the new tasks description.");
        description = input.nextLine();
        System.out.println("Enter the new tasks priority");
        while (!isInt){
            try {

                String stringPriority = input.nextLine();
                priority = Integer.parseInt(stringPriority);
                isInt = true;
            }
            catch (NumberFormatException e){
                System.out.println("Enter the new tasks priority");
            }

        }

        if(priority < 0){
            priority = 0;
        }else if(priority > 5){
            priority = 5;
        }

        internalList.add(new Tasks(title,description,priority));
    }

    public void removeTask(){

        int selection;
        System.out.println("Enter the index of the task to remove.");
        while(!input.hasNextInt()){
            input.next();
            System.out.println("Please choose an option");
        }
        selection = input.nextInt();

    }

    public void updateTask(){

        String title;
        String description;
        int index = 0;
        int priority = 0;
        boolean isInt = false, done = false;
        System.out.println("Enter the index of the task to update.");
        while(!done){
            try{
                String stringIndex = input.nextLine();
                index = Integer.parseInt(stringIndex);
                done = true;
            }
            catch(NumberFormatException ie){
                System.out.println("Enter the index of the task to update.");
            }

        }

        System.out.println("Enter the new task's name.");
        title = input.nextLine();
        System.out.println("Enter the new tasks description.");
        description = input.nextLine();
        System.out.println("Enter the new tasks priority");
        while (!isInt){
            try {
                String stringPriority = input.nextLine();
                priority = Integer.parseInt(stringPriority);
                isInt = true;
            }
            catch (NumberFormatException e){
                System.out.println("Enter the new tasks priority");
            }

        }

        internalList.set(index, new Tasks(title,description,priority));
    }

    public void listTasks(){
        for(Tasks task: internalList){
            System.out.println("Task index: " + internalList.indexOf(task) + ", Name: " + task.title + ", Description: " + task.description + ", Priority: " + task.priority);
        }
    }

    public void listTaskPriority (){
        //variables
        int priority = 0;
        boolean isInt = false;

        System.out.println("Enter a Priority");

        while (!isInt){
            try {
                String stringPriority = input.nextLine();
                priority = Integer.parseInt(stringPriority);
                isInt = true;
            }
            catch (NumberFormatException e){
                System.out.println("Enter a Priority");
            }

        }

        // loops through the ArrayList finds the number that matches the task priority
        for(Tasks task: internalList){
            if (priority == task.priority){
                System.out.println("Task index: " + internalList.indexOf(task) + ", Name: " + task.title + ", Description: " + task.description + ", Priority: " + task.priority);
            }
        }
    }

    public void sort(){
        Collections.sort(internalList);
    }

    @Override
    public Iterator<Tasks> iterator() {
        return internalList.iterator();
    }
}

public class Main {

    static final String FILENAME = "data.json";

    public static TaskCollection load(String filename) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(filename);
        try {
            return gson.fromJson(reader, TaskCollection.class);
        }
        finally {
            reader.close();
        }
    }

    public static void save(String filename, TaskCollection data) throws IOException {
        Gson gson = new Gson();
        FileWriter writer = new FileWriter(filename);
        try {
            gson.toJson(data, writer);
        }
        finally {
            writer.close();
        }
    }

    public static void main(String[] args) {
        // variables
        int selection;

        //constructors
        Scanner input = new Scanner(System.in);
        TaskCollection taskCollection = new TaskCollection();
        try {
            TaskCollection savedTasks = load(FILENAME);
            do {

                menu();
                while (!input.hasNextInt()) {
                    input.next();
                    System.out.println("Please choose an option");
                }
                selection = input.nextInt();

                switch (selection) {
                    case 1:
                        taskCollection.addTask();
                        break;
                    case 2:
                        taskCollection.removeTask();
                        break;
                    case 3:
                        taskCollection.updateTask();
                        break;
                    case 4:
                        taskCollection.sort();
                        taskCollection.listTasks();
                        break;
                    case 5:
                        taskCollection.sort();
                        taskCollection.listTaskPriority();
                        break;
                }

            } while (selection != 0);
            save(FILENAME, savedTasks);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void menu(){
        System.out.println("Menu");
        System.out.println("(1) Add a task.");
        System.out.println("(2) Remove a task.");
        System.out.println("(3) Update a task.");
        System.out.println("(4) List all tasks.");
        System.out.println("(5) List all tasks of a certain priority.");
        System.out.println("(0) Exit.");
        System.out.println("Please choose an option:");
    }

}