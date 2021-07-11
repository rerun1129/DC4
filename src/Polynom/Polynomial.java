package Polynom;

import LinkedList.MySingleLinkedList;
import LinkedList.Node;

public class Polynomial {

    public char name;
    public MySingleLinkedList<Term> terms;

    public Polynomial(char name) {
        this.name = name;
        terms = new MySingleLinkedList<Term>();         //이렇게 해도되고 클래스 단에서 생성해도 된다.
    }

    public void addTerm(int c, int e) {
        if (c == 0)
            return;

        Node<Term> p = terms.head, q = null;

        while (p != null && p.data.expo > e) {          //p가 null이면 전진을 멈춰야 하고 입력받은 차수보다 p노드의 차수가 낮으면
            //더이상 이동할 필요가 없다.
            q = p;
            p = p.next;
        }
        if (p != null && p.data.expo == e) {          //추가하려는 항과 동일차수의 항이 있는 경우 + 차수가 0이 아닌경우
            p.data.coef += c;
            if (p.data.coef == 0) {                   // 연산의 결과 항차의 계수가 0이 되는 경우(노드를 삭제해야됨)
                if (q == null) {                   //p의 차수가 0이면(마지막 항이면) q가 null이 된다.(p의 뒤에 아무것도 없으므로)
                    terms.removeFirst();      //그러면 차수가 0이고 계수도 0이니까 다항식에 항이 없다. 즉, removeFirst를 해야한다.
                } else {
                    terms.removeAfter(q);       //차수가 0이 아니고 계수가 0이면 바로 다음 차수를 날려버리면 된다.
                }
            }
        } else {                              //이제 while의 정지조건을 만족했으면 항을 만들어줘야 한다(받은 계수와 차수 항에 써주기)
            Term t = new Term(c, e);
            if (q == null) {                //t는 데이터필드 값이고 q는 next 값이다. 그런데 q가 null이라는 것은 항을 더하는데
                terms.addFirst(t);          //p.data.expo > e가 false가 되는 지점이 첫 항부터 나왔다는 것이고 p는 바로 전진을 멈추기
            } else {                        //때문에(while문이 돌지 않는다.) p = head고 q는 null이다. 그래서 p를 첫 항에 써줘야 하기
                terms.addAfter(q, t);       //때문에 q가 null인 경우 addFirst를 하지 않으면 오류가 난다.
            }                               //그게 아니라면 그대로 addAfter를 해야한다.
        }
    }

    public int calc(int x) {
        int result = 0;
        Node<Term> p = terms.head;              //이거는 리스트를 끝까지 순회해야 하기 때문에 q는 필요가 없고 중간에 정지도 필요없다.
        while (p != null) {
            result += p.data.calc(x);
            p = p.next;
        }
        return result;
    }

    public String toString() {
        String result = "";
        Node<Term> p = terms.head;              //이거는 리스트를 끝까지 순회해야 하기 때문에 q는 필요가 없고 중간에 정지도 필요없다.
        while (p != null) {
            result += ("+" + p.data.toString());
            p = p.next;
        }
        return result;
    }
}
