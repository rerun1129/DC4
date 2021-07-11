package Polynom;

import java.util.Scanner;

public class Main {

    private Polynomial[] polys = new Polynomial[100]; // 항은 연결리스트에 담고 다항식들은 배열에 넣도록 한다.
    int n = 0;
    Scanner kb = new Scanner(System.in);

    public void processCommand() {

        while (true) {
            System.out.print("$ ");
            String command = kb.next();
            if (command.equals("생성"))
                handleCreate();
            else if (command.equals("추가"))
                handleAdd();
            else if (command.equals("계산"))
                handleCalc();
            else if (command.equals("출력"))
                handlePrint();
            else if (command.equals("나가기"))
                break;
        }
        kb.close();
    }

    private void handlePrint() {
        char name = kb.next().charAt(0);
        int index = find(name);
        if (index < 0) {
            System.out.println("No search.");
            return;
        }
        System.out.println(polys[index].toString());
    }

    private void handleCalc() {
        char name = kb.next().charAt(0);
        int x = kb.nextInt();
        int index = find(name);
        if (index < 0) {
            System.out.println("No search.");
            return;
        }
        System.out.println(polys[index].calc(x));

    }

    private void handleAdd() {
        char name = kb.next().charAt(0);
        int coef = kb.nextInt();
        int expo = kb.nextInt();
        int index = find(name);
        if (index < 0) {
            System.out.println("No search.");
            return;
        }
        polys[index].addTerm(coef, expo);

    }

    private int find(char name) {
        for (int i = 0; i < n; i++) {
            if (polys[i].name == name)
                return i;
        }
        return -1;
    }


    private void handleCreate() {               //함수 이름이 중복되는 경우가 있는데 그러한 예외는 다른 메서드로 해결하면된다.
        char name = kb.next().charAt(0);
        Polynomial p = new Polynomial(name);

        polys[n++] = p;
    }

    public Main() {

    }

    public static void main(String[] args) {
        Main app = new Main();
        app.processCommand();

    }

}
