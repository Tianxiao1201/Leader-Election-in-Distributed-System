package asynchronousAlgorithm;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Processor {
    int ui;
    int myID;
    int startRound;
    Processor clockwiseNeighbor;
    Queue<Message> inbox = new LinkedList<>();
    boolean isLeader = false;
    boolean terminated = false;
    int leaderID = -1;

    public Processor(int ui, int myID, int startRound) {
        this.ui = ui;
        this.myID = myID;
        this.startRound = startRound;
    }

    public void setClockwiseNeighbor(Processor clockwiseNeighbor) {
        this.clockwiseNeighbor = clockwiseNeighbor;
    }

    public void sendMessage(Message message) {
        if (!terminated || message.isTerminate) {
            clockwiseNeighbor.inbox.add(message);
        }
    }

    public void processMessage() {
        while (!inbox.isEmpty()) {
            Message message = inbox.poll();
            if (message.isTerminate) {
                this.leaderID = message.id;
                this.terminated = true;
                sendMessage(message);
                break;
            } else if (message.id > myID) {
                sendMessage(message);
            } else if (message.id == myID) {
                isLeader = true;
                terminated = true;
                leaderID = myID;
                System.out.println("Processor " + ui + " with unique ID " + myID + " is the leader.");
                sendMessage(new Message(myID, true));
                break;
            }

        }
    }
}

class Message {
    int id;
    boolean isTerminate;

    public Message(int id) {
        this.id = id;
        this.isTerminate = false;
    }

    public Message(int id, boolean isTerminate) {
        this.id = id;
        this.isTerminate = isTerminate;
    }
}

public class asynchronousAlgorithm {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of processors: ");
        int numberOfProcessors = scanner.nextInt();
        Processor[] processors = new Processor[numberOfProcessors];

        for (int i = 0; i < numberOfProcessors; i++) {
            System.out.print("Enter the unique ID for processor " + (i + 1) + ": ");
            int id = scanner.nextInt();
            System.out.print("Enter the start round for processor " + (i + 1) + ": ");
            int startRound = scanner.nextInt();
            processors[i] = new Processor(i + 1, id, startRound);
        }

        for (int i = 0; i < numberOfProcessors; i++) {
            processors[i].setClockwiseNeighbor(processors[(i + 1) % numberOfProcessors]);
        }

        int round = 1;
        boolean allTerminated;
        do {
            allTerminated = true;
            for (Processor processor : processors) {
                if (round >= processor.startRound && !processor.terminated) {
                    allTerminated = false;
                    if (processor.inbox.isEmpty() && round == processor.startRound) {
                        processor.sendMessage(new Message(processor.myID));
                    }
                    processor.processMessage();
                } else if (!processor.terminated) {
                    allTerminated = false;
                }
            }
            round++;
        } while (!allTerminated);


        for (Processor processor : processors) {
            System.out.println("Processor " + processor.ui + " knows the leader ID: " + processor.leaderID);
        }

        scanner.close();
    }
}
