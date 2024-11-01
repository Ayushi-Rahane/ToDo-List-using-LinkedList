import java.util.ArrayList;
import java.util.Scanner;

class Node {
    int taskid;
    String task;
    Node next;

    Node(int tid, String t) {
        taskid = tid;
        task = t;
        next = null;
    }
}

class TaskList {
    Node head, ptr;
    String listName;

    TaskList(String name) {
        this.listName = name;
        this.head = null;
    }

    // Create task in the list
    void createTask(int tid, String task) {
        Node temp = new Node(tid, task);
        if (head == null) {
            head = temp;
        } else {
            ptr = head;
            while (ptr.next != null) {
                ptr = ptr.next;
            }
            ptr.next = temp;
        }
        System.out.println("Task inserted successfully in " + listName);
    }

    // Update task in the list
    void updateTask() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Task id: ");
        int tid = sc.nextInt();
        sc.nextLine();
        ptr = head;
        boolean found = false;
        while (ptr != null) {
            if (ptr.taskid == tid) {
                System.out.println("TaskID | Task");
                System.out.println(ptr.taskid + " | " + ptr.task);
                System.out.println("Enter new Task no: ");
                ptr.taskid = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter new Task: ");
                ptr.task = sc.nextLine();
                System.out.println("Task Updated Successfully");
                found = true;
                break;
            }
            ptr = ptr.next;
        }
        if (!found) {
            System.out.println("Task not Found");
        }
    }

    // Delete task and move to completed list
    void deleteTask(TaskList completeTaskList) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the task id: ");
        int tid = sc.nextInt();
        sc.nextLine();

        if (head == null) {
            System.out.println("No Task to Delete");
            return;
        }

        if (head.taskid == tid) {
            completeTaskList.createTask(head.taskid, head.task);
            head = head.next;
            System.out.println("Task Deleted Successfully");
            return;
        }

        ptr = head;
        boolean found = false;
        while (ptr.next != null) {
            if (ptr.next.taskid == tid) {
                completeTaskList.createTask(ptr.next.taskid, ptr.next.task);
                ptr.next = ptr.next.next;
                System.out.println("Task deleted Successfully");
                found = true;
                break;
            }
            ptr = ptr.next;
        }

        if (!found) {
            System.out.println("Task not Found");
        }
    }

    // Display tasks in the list
    void displayTasks() {
        if (head == null) {
            System.out.println("Currently no Task");
        } else {
            ptr = head;
            System.out.println("TaskID | Task");
            while (ptr != null) {
                System.out.println(ptr.taskid + " | " + ptr.task);
                ptr = ptr.next;
            }
        }
    }
}

public class ToDoList {
    static ArrayList<TaskList> taskLists = new ArrayList<>();

    // Function to create a new task list
    static void createNewList() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the name for the new task list: ");
        String listName = sc.nextLine();

        for (TaskList list : taskLists) {
            if (list.listName.equals(listName)) {
                System.out.println("List with this name already exists.");
                return;
            }
        }

        taskLists.add(new TaskList(listName));
        System.out.println("New list '" + listName + "' created successfully.");
    }

    // Function to select an existing task list
    static TaskList selectList() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Available Lists:");
        for (TaskList list : taskLists) {
            System.out.println("- " + list.listName);
        }
        System.out.println("Enter the name of the list you want to select: ");
        String listName = sc.nextLine();

        for (TaskList list : taskLists) {
            if (list.listName.equals(listName)) {
                return list;
            }
        }
        System.out.println("List not found.");
        return null;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TaskList completeTaskList = new TaskList("Completed Tasks");

        while (true) {
            System.out.println("Enter 1 to Create New List \n2 to Select a List \n3 to Display Completed Tasks \n4 to Exit");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    createNewList();
                    break;

                case 2:
                    TaskList selectedList = selectList();
                    if (selectedList == null) {
                        break;
                    }

                    int taskOption;
                    do {
                        System.out.println("You are managing the list: " + selectedList.listName);
                        System.out.println("Enter 1 to Add Task \n2 to Update Task \n3 to Delete Task \n4 to Display Tasks \n5 to Return to Main Menu");
                        taskOption = sc.nextInt();
                        sc.nextLine();

                        switch (taskOption) {
                            case 1:
                                System.out.println("Enter Task no: ");
                                int tid = sc.nextInt();
                                sc.nextLine();
                                System.out.println("Enter Task: ");
                                String task = sc.nextLine();
                                selectedList.createTask(tid, task);
                                break;

                            case 2:
                                selectedList.updateTask();
                                break;

                            case 3:
                                selectedList.deleteTask(completeTaskList);
                                break;

                            case 4:
                                System.out.println("-------------Tasks in " + selectedList.listName + "-------------");
                                selectedList.displayTasks();
                                break;

                            case 5:
                                System.out.println("Returning to main menu...");
                                break;

                            default:
                                System.out.println("Invalid choice, try again.");
                        }
                    } while (taskOption != 5);
                    break;

                case 3:
                    System.out.println("-------------Completed Tasks-------------");
                    completeTaskList.displayTasks();
                    break;

                case 4:
                    System.out.println("Exiting application...");
                    return;

                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }
}
