
package ringsOfRings;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

class Processor {
    int ui;
    int myID;
    int startRound;
    Processor clockwiseNeighbor;
    List<Processor> subRing = new ArrayList<>();
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

    public void setSubRing(List<Processor> subRingProcessors) {
        this.subRing = subRingProcessors;
    }

    public void sendMessage(Message message) {
        System.out.println(this.ui + " sending to " + this.clockwiseNeighbor.ui + " message: ID " + message.id + ", terminate " + message.isTerminate);
        clockwiseNeighbor.inbox.add(message);
        ringsOfRings.totalMessages++;
    }

    public void processMessage() {
        while (!inbox.isEmpty()) {
            Message message = inbox.poll();
            if (message.isTerminate) {
                this.leaderID = message.id;
                this.terminated = true;
                // Ensure to forward termination message to correctly terminate the ring
                sendMessage(new Message(message.id, true));
            } else if (message.id > myID) {
                sendMessage(new Message(message.id, false)); // Forward higher ID message
            } else if (message.id == myID && !isLeader) {
                // Elect self as leader only if not already elected.
                isLeader = true;
                terminated = true;
                leaderID = myID;
                // Broadcast termination message with self ID as leader
                sendMessage(new Message(myID, true));
            }
            // No action if ID is lower
        }
    }

    public int findMaxSubRingID() {
        int maxID = myID;
        for (Processor p : subRing) {
            maxID = Math.max(maxID, p.myID);
        }
        return maxID;
    }
}

class Message {
    int id;
    boolean isTerminate;

    public Message(int id, boolean isTerminate) {
        this.id = id;
        this.isTerminate = isTerminate;
    }
}

public class ringsOfRings {
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

        // Connect processors in a ring
        for (int i = 0; i < numberOfProcessors - 1; i++) {
            processors[i].setClockwiseNeighbor(processors[i + 1]);
        }
        processors[numberOfProcessors - 1].setClockwiseNeighbor(processors[0]);

        // Setup sub-rings for u1 and u3 (if applicable)

        setUpSubRing(scanner, processors, 1);
        setUpSubRing(scanner, processors, 3);
        setUpSubRing(scanner, processors, 8);

        startMainRingElection(processors);

        for (Processor processor : processors) {
            System.out.println("Processor " + processor.ui + " knows the leader ID: " + processor.leaderID);
        }

        scanner.close();
    }

    static int totalMessages = 0 ;


    private static void setUpSubRing(Scanner scanner, Processor[] processors, int uIndex) {
        if (uIndex <= processors.length) {
            System.out.print("Enter the number of processors in the sub-ring of Processor " + uIndex + ": ");
            int subRingSize = scanner.nextInt();
            List<Processor> subRing = new ArrayList<>();
            for (int i = 0; i < subRingSize; i++) {
                System.out.print("Enter the unique ID for sub-ring processor " + (i + 1) + " of Processor " + uIndex + ": ");
                int id = scanner.nextInt();
                subRing.add(new Processor(uIndex * 100 + i, id, 1));
            }
            processors[uIndex - 1].setSubRing(subRing);
            int subRingLeaderID = processors[uIndex - 1].findMaxSubRingID();
            processors[uIndex - 1].myID = subRingLeaderID;
            ringsOfRings.totalMessages += subRingSize;
        }
        // Setup for sub-rings remains the same
        for (Processor processor : processors) {
            if (!processor.subRing.isEmpty()) {
                int subRingLeaderID = processor.findMaxSubRingID();
                processor.myID = subRingLeaderID;
            }
        }
    }


    private static void startMainRingElection(Processor[] processors) {
        int round = 1;
        boolean allTerminated = false;
        while (!allTerminated) {
            allTerminated = true;
            for (Processor processor : processors) {
                if (round == processor.startRound && processor.inbox.isEmpty()) {
                    processor.sendMessage(new Message(processor.myID, false)); // Send ID at start round
                }
                processor.processMessage();
                if (!processor.terminated) {
                    allTerminated = false; // Continue until all processors have terminated
                }
            }
            round++;
            System.out.println("Total rounds: " + round);

            System.out.println("Total messages sent: " + ringsOfRings.totalMessages);

        }
    }
}
