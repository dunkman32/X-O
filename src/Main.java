public class Main {
    /**
     * სწორედ აქ ხდება ყველა იმ ფუნქციის გამოძახება რომელიც ჩვენ აღვწერეთ...
     * ანუ ხდება თამაშის წარმართვა
     */
    public static void main(String... args) {
        XO xo = new XO();
        int turn = 0;
        while (!xo.ifSomeoneWin(Player.HUMAN) && !xo.ifSomeoneWin(Player.COMPUTER) && !xo.isGameOver()) {
            if (turn % 2 == 0) {
                System.out.println("Computer move: ");
                Pair AImove = xo.minMax(xo.matrix);

                System.out.println("here");
                xo.matrix[AImove.row][AImove.column] = xo.COMPUTER;
                if (xo.ifSomeoneWin(Player.COMPUTER))
                    System.out.println("Computer Win");
                turn++;
                xo.printMatrix();
            } else {
                System.out.print("Your move: ");
                int x = xo.in.nextInt();
                int y = xo.in.nextInt();
                xo.matrix[x][y] = xo.HUMAN;

                if (xo.ifSomeoneWin(Player.HUMAN))
                    System.out.println("You Win");

                turn++;
                xo.printMatrix();
            }
        }
    }
}
