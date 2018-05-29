import java.util.Arrays;
import java.util.Scanner;

class XO {

     static final int SIZE = 3;     // თამაშში გვჭირდება 3X3 მატრიცა ამიტო size = 3
     static final char EMPTY = '*'; // ის ადგილები სადაც მოძრაობა შესაძლებელია განსაზღვრულია * ნიშანით
     static final char HUMAN = 'O'; // ადამიანი
     static final char COMPUTER = 'X'; // მოწინააღმდეგე


    char[][] matrix = new char[SIZE][SIZE]; // მდგომარეობების წარმოდგენა ([3X3] მატრიცა)

    Scanner in;  // მონაცემების შეტანა

    //კონსტრუქტორი

    XO() {
        in = new Scanner(System.in);
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new char[SIZE];
            Arrays.fill(matrix[i], EMPTY);
        }
    }

    /**
     * მატრიცის (სათამაშო სივცის) ბეჭდვა
     */

    void printMatrix() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * მეთოდს გადაეცემა მოთამაშე და ამოწმებს კონკრეტულმა მოთამაშემ მოიგო თუ არა თამაში
     * ეს ხდება ყველა იმ უჯრათა შემოწმებით რომელთა დაკავებისას ჩაითვლებოდა თამაში მოგებული...
     */
     boolean ifSomeoneWin(Player player) {
        char playerChar = player == Player.HUMAN ? HUMAN : COMPUTER;

        for (int i = 0; i < SIZE; i++) {
            if (matrix[i][0] == playerChar && matrix[i][1] == playerChar
                    && matrix[i][2] == playerChar)
                return true;

            if (matrix[0][i] == playerChar && matrix[1][i] == playerChar
                    && matrix[2][i] == playerChar)
                return true;
        }

        if (matrix[0][0] == playerChar && matrix[1][1] == playerChar
                && matrix[2][2] == playerChar) {
            return true;
        } else return matrix[0][2] == playerChar && matrix[1][1] == playerChar
                && matrix[2][0] == playerChar;

    }

    /**
     * ვამოწმებ ხო არ დამთავრებულა თამაში (თუ ვინმემ მოიგო ან ადგილები თუ აღარაა ვაბრუნებ true)
     * სხვა შემთხვევაში false
     */

     boolean isGameOver() {
        if (ifSomeoneWin(Player.HUMAN)) {
            return true;
        } else if (ifSomeoneWin(Player.COMPUTER)) {
            return true;
        }

        boolean emptySpace = false;
        for (int i = 0; i < SIZE; i++) {
            if (matrix[i][0] == '*' || matrix[i][1] == '*' || matrix[i][2] == '*') {
                emptySpace = true;
            }
        }
        return !emptySpace;
    }

    /**
     * შესაძლებელია ამ ფუნქციის ამოღება რადგან ამ შემთხვევისთვის
     * თამაშის მსვლელობას არანაირად უშლის ხელს მისი არყოფნა
     *
     * @return (Integer)
     */

     int gameScore() {
        if (ifSomeoneWin(Player.HUMAN)) {
            return 10;
        } else if (ifSomeoneWin(Player.COMPUTER)) {
            return -10;
        }
        return 0;
    }

     int minSearch(char AIboard[][]) {
        if (isGameOver()) return gameScore();
        Pair bestMove = new Pair();

        int bestMoveScore = 1000;
        bestMoveScore = getBestMoveScore(AIboard, bestMove, bestMoveScore);

        return bestMoveScore;
    }

    /**
     * აბრუნებს საუკეთესო გზის ფასს, იდეაში განიხილიავს იმ ვარინტებს,
     * რომლებიც წაადგება ოპტიმალური გზის გაგნებაში
     *
     * @param AIboard
     * @param bestMove
     * @param bestMoveScore
     * @return საუკეთესო გზა რომელსაც კომპიუტერი აკეთებს
     */
     int getBestMoveScore(char[][] AIboard, Pair bestMove, int bestMoveScore) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (AIboard[i][j] == '*') {
                    AIboard[i][j] = COMPUTER;
                    int tempMove = maxSearch(AIboard);
                    if (tempMove <= bestMoveScore) {
                        bestMoveScore = tempMove;
                        bestMove.row = i;
                        bestMove.column = j;
                    }
                    AIboard[i][j] = '*';
                }
            }
        }
        return bestMoveScore;
    }

    /**
     * ელოდება მისთვის საზიანო სვლას ადამიანისგან და აბრუნებს იმ სვლას,
     * რომელიც მაქსიმალურად  მომგებიანი იქნება მისთვის
     *
     * @param AIboard
     * @return საუკეთესო გზა
     */
     int maxSearch(char AIboard[][]) {
        if (isGameOver())
            return gameScore();
        Pair bestMove = new Pair();

        int bestMoveScore = -1000;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (AIboard[i][j] == '*') {
                    AIboard[i][j] = HUMAN;
                    int tempMoveScore = minSearch(AIboard);
                    if (tempMoveScore >= bestMoveScore) {
                        bestMoveScore = tempMoveScore;
                        bestMove.row = i;
                        bestMove.column = j;
                    }
                    AIboard[i][j] = '*';
                }
            }
        }

        return bestMoveScore;
    }

    /**
     * სწორედ ამ ალგორითმის მიხედვით ხდება მოძრაობა და ამოცანის გადაწყვეტა
     *
     * @param AIboard
     * @return
     */
     Pair minMax(char AIboard[][]) {
        int bestMoveScore = 1000;
        Pair bestMove = new Pair();

        getBestMoveScore(AIboard, bestMove, bestMoveScore);

        return bestMove;
    }
}
