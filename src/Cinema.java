import java.util.Scanner;

// прочитай в конце код минимум 4 раза !! и подумай ! Мб что-то поменять ?
// в конце скопипую, создай свой проект, а потм на ГИТХАБ
// обработай исключения, если вводишь буквы вместо цифр, сейчас выбрасывает
/* может отдельный метод для сканера ? Чтобы возвращал цифру, там же и обработает и мб закроется ? Но если он будет постоянно
открываться и закрываться это будет топу ! Мб видос про рефакторинг поможет !
проверить название методов, они тебе понятны ?
В конце после того как создашь и перенесёшь код в свой проект, попробуй его скомпилировать и запустить )
Перечитать код в пятый раз и убрать коментарии, оставить один или два и то, если они нужны
*/
public class Cinema {
    //проверить, все ли глобальные переменные нужны
    static String[][] CINEMA_HALL; // можно ли избавится от глобальных переменных ?
    static Scanner SCANNER = new Scanner(System.in); // это вообще бред, подумай как убрать сканер и нормально ли его так оставлять ?
    static int TICKETS_PURCHASED = 0;
    static int ALL_SEATS;
    static int CURRENT_INCOME = 0;
    static int TOTAL_INCOME = 0;
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

    //вот тут проверка, чтобы люди вводили ТОЛЬКО цифры и не отрицательные
    static String[][] createCinemaHall() {
        String[][] cinemaHall;
        boolean finishEnter = false;
        while (!finishEnter) {
            System.out.println("Enter the number of rows:");
            final int rows = SCANNER.nextInt() + 1;
            System.out.println("Enter the number of seats in each row:");
            final int seats = SCANNER.nextInt() + 1;
            cinemaHall = new String[rows][seats];
        }
        for (int i = 0; i < cinemaHall.length; i++) {
            for (int y = 0; y < cinemaHall[i].length; y++) {
                if (i == 0 && y == 0) cinemaHall[i][y] = " ";
                else if (i == 0) cinemaHall[i][y] = Integer.toString(y);
                else if (y == 0) cinemaHall[i][y] = Integer.toString(i);
                else cinemaHall[i][y] = "S";
            }
        }

        ALL_SEATS = (rows - 1) * (seats - 1); // если All seats будет ниже, то мы не увидим общую прибыль за все сиденья, нужно отрефакторить, хотя это в методе и вроде норм
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

    //продумай, когда пользовотель передумает брать, может место ему не понравилось ? Добавить вариант выхода
    static void buyTicket() {
        boolean finishBuying = false;
        int row = 0;
        int seat = 0;

        while (!finishBuying) {
            System.out.println("Enter a row number:");
            System.out.println("or enter 0 for exit:");
            finishBuying = SCANNER.nextInt() == 0;

            row = SCANNER.nextInt();
            System.out.println("Enter a seat number in that row:");
            seat = SCANNER.nextInt();
            finishBuying = bookingSeat(row, seat);
        }
        countPrice(row);
    }

    static void countPrice(int row) {
        //if there are less than 60 available seats = we take the maximum price
        if (ALL_SEATS < 60) {
            CURRENT_INCOME += FRONT_ROWS_SEAT_PRICE;
            System.out.printf("Ticket price: $%d\n", FRONT_ROWS_SEAT_PRICE);
        } else {
            int frontRowLine = Math.max((CINEMA_HALL.length - 1) / 2, 4);
            int currentPrice = row <= frontRowLine ? FRONT_ROWS_SEAT_PRICE : BACK_ROWS_SEAT_PRICE;
            CURRENT_INCOME += currentPrice;
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
            TICKETS_PURCHASED++;
            return true;
        }
    }

    static void showStatistic() {

        double PERCENTAGE_TICKETS_PURCHASED = TICKETS_PURCHASED != 0 ? 100 / ((double)ALL_SEATS / TICKETS_PURCHASED) : 0;

        System.out.printf("""
                
                Number of purchased tickets: %d
                Percentage: %.2f%%
                Current income: $%d
                Total income: $%d
                
                """, TICKETS_PURCHASED, PERCENTAGE_TICKETS_PURCHASED, CURRENT_INCOME, TOTAL_INCOME);
    }

    //отрефактори, ты используешь похожий код 2 раза ( в countPrice). Можно метод общий сделать
    static void showTotalIncome(String[][] cinemaHall) {
        //if there are less than 60 available seats = we take the maximum price
        if (ALL_SEATS < 60) {
            TOTAL_INCOME = cinemaHall.length * cinemaHall[0].length * FRONT_ROWS_SEAT_PRICE;
        } else {
            int row = cinemaHall.length - 1;
            int seats = cinemaHall[0].length - 1;
            int frontRowLine = Math.max((row - 1) / 2, 4);
            int backRowLine = row - frontRowLine;
            TOTAL_INCOME = frontRowLine * FRONT_ROWS_SEAT_PRICE * seats + backRowLine * BACK_ROWS_SEAT_PRICE * seats;
        }
    }
}
