# README

## 1. Asynchronous Ring Algorithm README

### 1.1 Description

This program implements an asynchronous leader-election algorithm for a network organized in a ring topology. Each processor in the ring asynchronously starts its participation in the election process based on a predefined start round. The algorithm ensures that the processor with the highest unique ID is elected as the leader.

### 1.2 Compilation and Execution Instructions

#### 1.2.1 Compilation

Navigate to the directory containing the `asynchronousAlgorithm.java` file and compile it using the Java compiler.

#### 1.2.2 Running the Program

After compilation, run the program using the Java interpreter.

#### 1.2.3 Inputs

Upon running the program, you will be prompted to enter the following information:
The number of processors in the ring (Enter the number of processors:).
For each processor, enter its unique ID and the start round. The program will prompt you sequentially for each processor (Enter the unique ID for processor X: and Enter the start round for processor X:) where X is the processor number starting from 1.

#### 1.2.4 Output

The program will execute the leader-election algorithm and output the unique ID of the elected leader along with the processor number that acknowledges the leader's ID. The output will be in the form:
Processor Y with unique ID Z is the leader.
Where Y is the processor number and Z is the unique ID of the elected leader.

**Notes:**
Ensure that each processor is assigned a unique ID.
The start round determines when each processor begins its participation in the election process. Processors will only start sending and processing messages from their respective start rounds.

### 1.3 Example Execution

Enter the number of processors: 5
Enter the unique ID for processor 1: 57
Enter the start round for processor 1: 3
Enter the unique ID for processor 2: 42
Enter the start round for processor 2: 2
Enter the unique ID for processor 3: 76
Enter the start round for processor 3: 1
Enter the unique ID for processor 4: 13
Enter the start round for processor 4: 4
Enter the unique ID for processor 5: 22
Enter the start round for processor 5: 5
Processor 3 with unique ID 76 is the leader.
Processor 1 knows the leader ID: 76
Processor 2 knows the leader ID: 76
Processor 3 knows the leader ID: 76
Processor 4 knows the leader ID: 76
Processor 5 knows the leader ID: 76

## 2. Rings of Rings Leader Election Algorithm README

### 2.1 Description

The algorithm presented operates within a network architecture characterized as a ring of rings facilitating a hierarchical leader election process. Initially, it focuses on conducting elections within any existing sub-rings to identify local leaders effectively leveraging the distributed nature of the network. The core of the algorithm updates the IDs of interface nodes to reflect the IDs of these local leaders, ensuring that the highest ID within each sub-network is represented in the main ring's election process. Subsequently, an asynchronous leader election is executed on the main ring targeting the election of the processor with the ultimate highest unique ID as the network's leader. This methodological approach underscores the algorithm's capacity to adeptly navigate and streamline leader election across complex and layered network structures, achieving optimal outcomes through a systematic and phased election process.

### 2.2 Compilation and Execution Instructions

#### 2.2.1 Compilation

Navigate to the directory containing the `ringsOfRings.java` file and compile it using the Java compiler.

#### 2.2.2 Running the Program

After compilation, run the program using the Java interpreter.

#### 2.2.3 Inputs

Upon running the program, you will be prompted to enter the following information:
The number of processors in the ring (Enter the number of processors:).
For each processor, enter its unique ID and the start round. The program will prompt you sequentially for each processor (Enter the unique ID for processor X: and Enter the start round for processor X:) where X is the processor number starting from 1.
If applicable, the program will prompt you to set up sub-rings for specific interface processors (e.g., u1, u3, and u8). Enter the number of processors in each sub-ring followed by their unique IDs.

#### 2.2.4 Output

The program will display messages indicating the transmission of election messages between processors.
Upon election completion, it will print the unique ID of the elected leader and the total number of messages sent during the election process.
The program also displays the total number of rounds until the algorithm terminates.

**Notes:**
Ensure that each processor is assigned a unique ID to guarantee correct leader election.
The start round dictates when each processor begins participating in the election process. Processors will only start their activities at or after their specified start round.
Currently, only u1, u3, and u8 are set as the interface nodes.

### 2.3 Example Execution

Enter the number of processors: 10
Enter the unique ID for processor 1: 50
Enter the start round for processor 1: 3
Enter the unique ID for processor 2: 58
Enter the start round for processor 2: 5
Enter the unique ID for processor 3: 38
Enter the start round for processor 3: 7
Enter the unique ID for processor 4: 283
Enter the start round for processor 4: 9
Enter the unique ID for processor 5: 27
Enter the start round for processor 5: 1
Enter the unique ID for processor 6: 473
Enter the start round for processor 6: 2
Enter the unique ID for processor 7: 34
Enter the start round for processor 7: 4
Enter the unique ID for processor 8: 58
Enter the start round for processor 8: 6
Enter the unique ID for processor 9: 28
Enter the start round for processor 9: 8
Enter the unique ID for processor 10: 71
Enter the start round for processor 10: 10
Enter the number of processors in the sub-ring of Processor 1: 3
Enter the unique ID for sub-ring processor 1 of Processor 1: 387
Enter the unique ID for sub-ring processor 2 of Processor 1: 608
Enter the unique ID for sub-ring processor 3 of Processor 1: 879
Enter the number of processors in the sub-ring of Processor 3: 3
Enter the unique ID for sub-ring processor 1 of Processor 3: 264
Enter the unique ID for sub-ring processor 2 of Processor 3: 142
Enter the unique ID for sub-ring processor 3 of Processor 3: 352
Enter the number of processors in the sub-ring of Processor 8: 3
Enter the unique ID for sub-ring processor 1 of Processor 8: 142
Enter the unique ID for sub-ring processor 2 of Processor 8: 352
Enter the unique ID for sub-ring processor 3 of Processor 8: 621
5 sending to 6 message: ID 27 terminate false
Total rounds: 2
Total messages sent: 10
6 sending to 7 message: ID 473 terminate false
7 sending to 8 message: ID 473 terminate false
Total rounds: 3
Total messages sent: 12
1 sending to 2 message: ID 879 terminate false
2 sending to 3 message: ID 879 terminate false
3 sending to 4 message: ID 879 terminate false
4 sending to 5 message: ID 879 terminate false
5 sending to 6 message: ID 879 terminate false
6 sending to 7 message: ID 879 terminate false
7 sending to 8 message: ID 879 terminate false
8 sending to 9 message: ID 879 terminate false
9 sending to 10 message: ID 879 terminate false
10 sending to 1 message: ID 879 terminate false
Total rounds: 4
Total messages sent: 22
1 sending to 2 message: ID 879 terminate true
2 sending to 3 message: ID 879 terminate true
3 sending to 4 message: ID 879 terminate true
4 sending to 5 message: ID 879 terminate true
5 sending to 6 message: ID 879 terminate true
6 sending to 7 message: ID 879 terminate true
7 sending to 8 message: ID 879 terminate true
8 sending to 9 message: ID 879 terminate true
9 sending to 10 message: ID 879 terminate true
10 sending to 1 message: ID 879 terminate true
Total rounds: 5
Total messages sent: 32
Processor 1 knows the leader ID: 879
Processor 2 knows the leader ID: 879
Processor 3 knows the leader ID: 879
Processor 4 knows the leader ID: 879
Processor 5 knows the leader ID: 879
Processor 6 knows the leader ID: 879
Processor 7 knows the leader ID: 879
Processor 8 knows the leader ID: 879
Processor 9 knows the leader ID: 879
Processor 10 knows the leader ID: 879
