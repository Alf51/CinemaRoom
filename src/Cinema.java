import java.util.Scanner;

public class Cinema {
    static String[][] CINEMA_HALL;
    static Scanner SCANNER = new Scanner(System.in);
    static int ticketsPurchased = 0;
    static int allSeats;
    static int currentIncome = 0;
    static int totalIncome = 0;
    final static int FRONT_ROWS_SEAT_PRICE = 10;
    final static int BACK_ROWS_SEAT_PRICE = 8;


    public static void main(String[] args) {
        startMenu();
    }

    public static void startMenu() {
        boolean flag = true;
        CINEMA_HALL = createCinemaHall();
        while (flag) {
            System.out.println("""
                                        
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    0. Exit
                    """);

            switch (SCANNER.nextInt()) {
                case 1 -> showCinemaHall();
                case 2 -> buyTicket();
                case 3 -> showStatistic();
                default -> flag = false;
            }
        }
    }

    static String[][] createCinemaHall() {
        String[][] cinemaHall;
        System.out.println("Enter the number of rows:");
        final int rows = SCANNER.nextInt() + 1;
        System.out.println("Enter the number of seats in each row:");
        final int seats = SCANNER.nextInt() + 1;
        cinemaHall = new String[rows][seats];

        for (int i = 0; i < cinemaHall.length; i++) {
            for (int y = 0; y < cinemaHall[i].length; y++) {
                if (i == 0 && y == 0) cinemaHall[i][y] = " ";
                else if (i == 0) cinemaHall[i][y] = Integer.toString(y);
                else if (y == 0) cinemaHall[i][y] = Integer.toString(i);
                else cinemaHall[i][y] = "S";
            }
        }

        allSeats = (rows - 1) * (seats - 1);
        showTotalIncome(cinemaHall);
        return cinemaHall;
    }

    public static void showCinemaHall() {
        System.out.println("Cinema:");
        for (String[] strings : CINEMA_HALL) {
            for (String string : strings) {
                System.out.print(string + " ");
            }
            System.out.println();
        }
    }


    static void buyTicket() {
        boolean finishBuying = false;
        int row = 0;
        int seat;

        while (!finishBuying) {
            System.out.println("Enter a row number:");
            System.out.println("or enter 0 for exit:");

            row = SCANNER.nextInt();
            System.out.println("Enter a seat number in that row:");
            seat = SCANNER.nextInt();
            finishBuying = bookingSeat(row, seat);
        }
        countPrice(row);
    }

    static void countPrice(int row) {
        //if there are less than 60 available seats = we take the maximum price
        if (allSeats < 60) {
            currentIncome += FRONT_ROWS_SEAT_PRICE;
            System.out.printf("Ticket price: $%d\n", FRONT_ROWS_SEAT_PRICE);
        } else {
            int frontRowLine = Math.max((CINEMA_HALL.length - 1) / 2, 4);
            int currentPrice = row <= frontRowLine ? FRONT_ROWS_SEAT_PRICE : BACK_ROWS_SEAT_PRICE;
            currentIncome += currentPrice;
            System.out.printf("Ticket price: $%d\n", currentPrice);
        }
    }

    static boolean bookingSeat(int row, int seat) {
        if (row == 0 || seat == 0 || row >= CINEMA_HALL.length || seat >= CINEMA_HALL[0].length) {
            System.out.println("Wrong input!");
            return false;
        } else if (CINEMA_HALL[row][seat].equals("B")) {
            System.out.println("That ticket has already been purchased");
            return false;
        } else {
            CINEMA_HALL[row][seat] = "B";
            ticketsPurchased++;
            return true;
        }
    }

    static void showStatistic() {
        double PERCENTAGE_TICKETS_PURCHASED = ticketsPurchased != 0 ? 100 / ((double) allSeats / ticketsPurchased) : 0;

        System.out.printf("""
                                
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d
                                
                """, ticketsPurchased, PERCENTAGE_TICKETS_PURCHASED, currentIncome, totalIncome);
    }

    static void showTotalIncome(String[][] cinemaHall) {
        //if there are less than 60 available seats = we take the maximum price
        if (allSeats < 60) {
            totalIncome = allSeats * FRONT_ROWS_SEAT_PRICE;
        } else {
            int row = cinemaHall.length - 1;
            int seats = cinemaHall[0].length - 1;
            int frontRowLine = row / 2;
            int backRowLine = row - frontRowLine;
            totalIncome = frontRowLine * FRONT_ROWS_SEAT_PRICE * seats + backRowLine * BACK_ROWS_SEAT_PRICE * seats;
        }
    }
}
