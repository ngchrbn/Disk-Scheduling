import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int numberOfRequest, initialHead;

        System.out.print("How many cylinders I/O request? ");
        numberOfRequest = input.nextInt();

        int []requests = new int[numberOfRequest];
        System.out.println("Enter the requests: ");
        for (int i=0; i<numberOfRequest; ++i) {
            System.out.printf("Request %d: ", i+1);
            requests[i] = input.nextInt();
        }
        System.out.print("Enter the direction for SCAN: ");
        String ScanDirection = input.next();

        System.out.print("Enter the direction for Look: ");
        String LookDirection = input.next();

        System.out.print("Enter the head initial position: ");
        initialHead = input.nextInt();

        System.out.println("\nFCFS Algorithm: ");
        System.out.println("\nSequence of Head Movements: ");
        int FCFSHeadMovement = FCFS(requests, initialHead);
        System.out.println("\n==>The total head movements for FCFS are: " + FCFSHeadMovement);

        System.out.println("\nSSTF Algorithm: ");
        System.out.println("\nSequence of Head Movements: ");
        int SSTFHeadMovement = SSTF(requests, initialHead);
        System.out.println("\n==>The total head movements for SSTF are: " + SSTFHeadMovement);

        System.out.println("\nSCAN Algorithm: ");
        System.out.println("\nSequence of Head Movements: ");
        int ScanHeadMovement = SCAN(requests, initialHead, ScanDirection);
        System.out.println("\n==>The total head movements for SCAN are: " + ScanHeadMovement);

        System.out.println("\nC-SCAN Algorithm: ");
        System.out.println("\nSequence of Head Movements: ");
        int CSCANHeadMovement = CSCAN(requests, initialHead);
        System.out.println("\n==>The total head movements for C-SCAN are: " + CSCANHeadMovement);

        System.out.println("\nLOOK Algorithm: ");
        System.out.println("\nSequence of Head Movements: ");
        int LookHeadMovement = LOOK(requests, initialHead, LookDirection);
        System.out.println("\n==>The total head movements for LOOK are: " + LookHeadMovement);

        System.out.println("\nC-LOOK Algorithm: ");
        System.out.println("\nSequence of Head Movements: ");
        int CLookHeadMovement = CLOOK(requests, initialHead);
        System.out.println("\n==>The total head movements for C-LOOK are: " + CLookHeadMovement);

        System.out.println("\nNewly Optimized Algorithm: ");
        System.out.println("\nSequence of Head Movements: ");
        int optimized = Optimized(requests, initialHead);
        System.out.println("\n==>The total head movements for Newly Optimized  are: " + optimized);

        compare(FCFSHeadMovement, SSTFHeadMovement, ScanHeadMovement, CSCANHeadMovement,
                LookHeadMovement, CLookHeadMovement, optimized);

    }

    private static int Optimized(int[] requests, int head) {
        int seek_count = 0;
        head = 0;
        ArrayList<Integer> tempRequests = new ArrayList<>();
        for (int request: requests) {
            tempRequests.add(request);
        }
        Collections.sort(tempRequests);
        for (Integer tempRequest : tempRequests) {
            seek_count += tempRequest - head;
            System.out.printf("Current Track is %d%n", tempRequest);
            head = tempRequest;
        }
        return seek_count;
    }

    private static int FCFS(int[] requests, int head) {
        int seek_count = 0;
        for (int request : requests) {
            seek_count += Math.abs(head - request);
            System.out.printf("Current Track is %d%n", request);
            head = request;
        }
        return seek_count;
    }

    private static int SSTF(int[] requests, int head) {
        int seek_count = 0;
        ArrayList<Integer> tempRequests = new ArrayList<>();
        for (int request: requests) {
            tempRequests.add(request);
        }
        tempRequests.add(head);
        int i, end = 0;
        Collections.sort(tempRequests);
        int start = tempRequests.indexOf(head);
        for (i=start-1; i>=end; --i) {
            seek_count += head - tempRequests.get(i);
            System.out.printf("Current Track is %d%n", tempRequests.get(i));
            head = tempRequests.get(i);
            if (i == 0) {
                end = tempRequests.size()-1;
            }
        }
        for (i=start+1; i<=end; ++i) {
            seek_count += tempRequests.get(i) - head;
            System.out.printf("Current Track is %d%n", tempRequests.get(i));
            head = tempRequests.get(i);
        }
        tempRequests.clear();
        return seek_count;
    }

    private static int SCAN(int[] requests, int head, String direction) {
        int index, seek_count = 0;
        ArrayList<Integer> Requests = new ArrayList<>();
        for (int request: requests) {
            Requests.add(request);
        }
        Requests.add(head);
        if (direction.equals("left")) {
            Requests.add(0);

        } else if (direction.equals("right")) {
            Requests.add(199);
        }

        Collections.sort(Requests);
        int start = Requests.indexOf(head);
        if (direction.equals("left")) {
            for (index=start-1; index>=0; --index) {
                seek_count += head - Requests.get(index);
                System.out.printf("Current Track is %d%n", Requests.get(index));
                head = Requests.get(index);
            }
            for (index=start+1; index<=Requests.size()-1; ++index) {
                seek_count += Requests.get(index) - head;
                System.out.printf("Current Track is %d%n", Requests.get(index));
                head = Requests.get(index);
            }
        }
        else if (direction.equals("right")) {
            for (index=start+1; index<=Requests.size()-1; ++index) {
                seek_count += Requests.get(index) - head;
                System.out.printf("Current Track is %d%n", Requests.get(index));
                head = Requests.get(index);
            }
            for (index=start-1; index>=0; --index) {
                seek_count += head - Requests.get(index);
                System.out.printf("Current Track is %d%n", Requests.get(index));
                head = Requests.get(index);
            }
        }
        Requests.clear();
        return seek_count;
    }

    private static int CSCAN(int[] requests, int head) {
        int seek_count = 0;
        ArrayList<Integer> Requests = new ArrayList<>();
        for (int request: requests) {
            Requests.add(request);
        }
        Requests.add(0);
        Requests.add(199);
        Requests.add(head);

        Collections.sort(Requests);
        int start = Requests.indexOf(head);
        for (int i=start+1; i<=Requests.size()-1; ++i) {
            seek_count += Requests.get(i) - head;
            System.out.printf("Current Track is %d%n", Requests.get(i));
            head = Requests.get(i);
        }
        for (int i=0; i<= start-1; ++i) {
            seek_count += Math.abs(head - Requests.get(i));
            System.out.printf("Current Track is %d%n", Requests.get(i));
            head = Requests.get(i);
        }
        Requests.clear();
        return seek_count;
    }

    private static int LOOK(int[] requests, int head, String direction) {
        int seek_count = 0;
        ArrayList<Integer> Requests = new ArrayList<>();
        for (int request: requests) {
            Requests.add(request);
        }
        Requests.add(head);
        Collections.sort(Requests);
        int start = Requests.indexOf(head);
        if (direction.equals("left")) {
            for (int i=start-1; i>=0; --i) {
                seek_count += head - Requests.get(i);
                System.out.printf("Current Track is %d%n", Requests.get(i));
                head = Requests.get(i);
            }
            for (int i=start+1; i<=Requests.size()-1; ++i) {
                seek_count += Requests.get(i) - head;
                System.out.printf("Current Track is %d%n", Requests.get(i));
                head = Requests.get(i);
            }
        }
        else if (direction.equals("right")) {
            for (int i=start+1; i<=Requests.size()-1; ++i) {
                seek_count += Requests.get(i) - head;
                System.out.printf("Current Track is %d%n", Requests.get(i));
                head = Requests.get(i);
            }
            for (int i=start-1; i>=0; --i) {
                seek_count += head - Requests.get(i);
                System.out.printf("Current Track is %d%n", Requests.get(i));
                head = Requests.get(i);
            }
        }
        return seek_count;
    }

    private static int CLOOK(int[] requests, int head) {
        int seekCount = 0;
        ArrayList<Integer> Requests = new ArrayList<>();
        for (int request: requests) {
            Requests.add(request);
        }
        Requests.add(head);

        Collections.sort(Requests);
        int start = Requests.indexOf(head);
        for (int index=start+1; index<=Requests.size()-1; ++index) {
            seekCount += Requests.get(index) - head;
            System.out.printf("Current Track is %d%n", Requests.get(index));
            head = Requests.get(index);
        }
        for (int i=0; i<= start-1; ++i) {
            seekCount += Math.abs(head - Requests.get(i));
            System.out.printf("Current Track is %d%n", Requests.get(i));
            head = Requests.get(i);
        }
        return seekCount;
    }

    private static void compare(int fcfsHeadMovement, int sstfHeadMovement, int scanHeadMovement, int cscanHeadMovement, int lookHeadMovement, int cLookHeadMovement, int optimized) {

        ArrayList<Integer> comparator = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        comparator.add(fcfsHeadMovement);
        names.add("FCFS Algorithm");
        comparator.add(sstfHeadMovement);
        names.add("SSTF Algorithm");
        comparator.add(scanHeadMovement);
        names.add("SCAN Algorithm");
        comparator.add(cscanHeadMovement);
        names.add("C-SCAN Algorithm");
        comparator.add(lookHeadMovement);
        names.add("LOOK Algorithm");
        comparator.add(cLookHeadMovement);
        names.add("C-LOOK Algorithm");
        comparator.add(optimized);
        names.add("Newly Optimized Algorithm");

        int min, max;
        min = max = comparator.get(0);
        for (int i=1; i<comparator.size(); ++i) {
            if (comparator.get(i) < min) {
                min = comparator.get(i);
            }
        }

        for (int i=1; i<comparator.size(); ++i) {
            if (comparator.get(i) > max) {
                max = comparator.get(i);
            }
        }
        int maxIndex = comparator.indexOf(max);
        int minIndex = comparator.indexOf(min);
        System.out.printf("\n\n==>For this input queue the fastest is: %s with %d head movements",
                names.get(minIndex), min);
        System.out.printf("\n\n==>For this input queue the slowest is: %s with %d head movements",
                names.get(maxIndex), max);
    }
}
