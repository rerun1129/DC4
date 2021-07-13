package Polynom2;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MySingleLinkedList<T> { //단방향 노드의 연결리스트

    private Node<T> head;  //주소값만 가짐(next) 데이터 필드 정보는 Node 클래스의 data가 item을 입력받아서 가지고 있다.
    private int size;

    public MySingleLinkedList() {
        head = null; //생성자에서 값들을 초기화 시켜준다. 위에서 안했으니까.
        size = 0;
    }

    private static class Node<T> {  //이너 클래스

        public T data;
        public Node<T> next;

        public Node(T item) {
            data = item;
            next = null;


        }
    }

    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private Node<T> nextNode;

        public MyIterator() {
            nextNode = head;
        }

        public boolean hasNext() {
            return nextNode != null;

        }

        public T next() {
            if (nextNode != null)
                throw new NoSuchElementException();
            T val = nextNode.data;
            nextNode = nextNode.next;

            return val;
        }

        public void remove() {
            //이 상태에선 간단하지 않다. 4-2절에서 다시 보자.

        }
    }

    public int size() {
        return size;
    }

    public T get(int index) {  //getNode는 인덱스 번지수의 노드를 반환하는거고 이것은 노드의 데이터를 반환하는 것이다.
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        /*Node<T> p = head;
        for (int i = 0; i < index; i++)
            p = p.next;
        return p.data;*/ //1. getNode가 없을경우

        return getNode(index).data;         //2. getNode가 있을경우(하지만 이걸 그대로 따서 쓰면 Node기준에서는)
        //노드 자체를 null로 리턴하면 문제가 없지만 데이터 기준에서는 null을 리턴하면 오류가 생긴다.
        //따라서 첫줄의 if는 써줘야한다.
    }

    private void addFirst(T item) { //목록의 맨 앞에 끼워넣기(head 만들기랑 똑같다)

        Node<T> temp = new Node<T>(item); //끼워넣을 노드 만들기 T = type parameter

        temp.next = head;
        head = temp;
        size++;

    }

    private void addAfter(Node<T> before, T item) {   //끼워넣을 주소의 앞주소와 데이터 필드 정보

        Node<T> temp = new Node<T>(item); //끼워넣을 노드 만들기
        temp.next = before.next;  //이 둘이 같아진게 아니고 우변의 정보를 좌변으로 덮어준 것이다.
        before.next = temp;
        size++;
    }

    private void add(int index, T item) {             //add 메서드들을 만든건 지금까지의 빌드업이었다.
        if (index < 0 || index > size) {           //인덱스 값의 예외사항은 이걸로 처리해주고
            throw new IndexOutOfBoundsException();
        }                        //여기서 등호가 사라진 이유는 다른 기능들은 index번지를 찾거나 삭제할수는 없다.
        //배열은 index-1번지까지 있으니까, 그런데 add는 index번지를 추가할수도 있기 때문에
        //index = size를 예외처리해버리면 addAfter에서 마지막 번지에 노드를 추가할수가 없다.
        if (index == 0) {                              //인덱스가 0 즉 처음에 추가할거라면 addFirst를 쓰면된다.
            addFirst(item);
            return;
        }
        Node<T> q = getNode(index - 1);         //여기서 getNode로 번지수-1의 노드를 받고(이놈 앞에 새 노드를 넣을거임)
        addAfter(q, item);                           //받은 노드와 데이터 필드를 입력해서 새 노드를 만든다.
    }

    private T removeFirst() {            //만약에 헤드가 null이면 지울게 없기 때문에 굳이 무슨 작업을 할 필요가 없다.
        //그런데 void이면 리턴 값이 필요가 없어서 리턴을 쓰려면 부득이하게 T타입 매서드로
        //쓰는 수밖에 없다. 그래서 리턴 값을 T 클래스로 돌려주는 것이다.
        if (head == null)
            return null;
        T temp = head.data;
        head = head.next;               //단순하게 맨 앞을 지우는 방법은 헤드에서 next값만 없애주고 2번째를 써 넣으면 된다.
        size--;
        return temp;


    }

    private T removeAfter(Node<T> before) {
        if (before.next == null)
            return null;
        T temp = before.next.data;
        before.next = before.next.next;
        size--;
        return temp;

    }

    private T remove(int index) {             //이것도 빌드업 한거다 이제 각 메서드를 만들어둔거를 적절하게 가져와서 써먹자.
        if (index < 0 || index >= size)     //데이터 타입이 T인 이유는 삭제한 데이터는 필요할수도 있으니까 일단 살리려고.
            throw new IndexOutOfBoundsException();
        if (index == 0)
            return removeFirst();
        Node<T> prev = getNode(index - 1); //입력받은 인덱스 번지를 지우기 위해서는 일단 인덱스-1번지의 next를 알아야해서 받는다.
        return removeAfter(prev);             //이 메서드를 적용하기 위해서 여기도 T타입인거고 prev이 before라는 매개변수로 들어가서
        // prev의 넥스트 값을 index의 넥스트값으로 복사하면 index 번지는 사라진다.
        //첨언하자면 prev-index-3 이였는데 2번째인 index가 사라지면 prev-3이 되는것이다.
    }

    public boolean remove(T item) {                //이건 데이터 필드의 값을 찾아서 노드를 삭제하는 것이다. 따라서 데이터를 입력받아야한다.
        Node<T> p = head, q = null;
        while (p != null && !p.data.equals(item)) {
            q = p;
            p = p.next;
        }
        if (p == null)                             //이 경우는 while문의 두가지 반복조건 중에 전자만 false인 경우이다.
            return false;                        //후자가 계속 false인 경우는 필드를 못찾은거고 후자까지 조건이 안가는 경우는 head가 null인 경우이다.
        if (q == null) {                             //이 경우는 while문의 첫 반복시에 전자는 true 후자가 false인 경우인데 p.next가 null인 경우이다.
            T tmp = removeFirst();               //p.next가 null이면 q는 움직일수가 없기 때문에 q도 계속 null상태인것이다.
            return (tmp != null);
        } else {                                   //이 경우는 후자가 false인 상태에서 q가 null이 아닌 상태이다. 이러면 q.next가 p를 지정해서
            T tmp = removeAfter(q);              //q.next.next를 q.next에 써버리면 p 주소는 사라지고 p.data는 리턴된다.
        return (tmp != null);
        }
    }



    /*public void add(int index, T item) { //삽입(목록의 임의의 장소에 넣기)

    }*/// 이건 일단 틀만 잡아 놓은거고 위에 퍼스트랑 애프터 만들어놨으니까 주석처리함. 애프터가 라스트 대체함.


    public int indexOf(T item) { //검색

        Node<T> p = head;
        int index = 0;
        while (p != null) {
            if (p.data.equals(item))
                return index;
            p = p.next;
            index++;
        }
        return -1;
    }

    private Node<T> getNode(int index) {          // 노드 자체를 가져와야 하니까 Node<T>를 타입으로 써야한다.
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        Node<T> p = head;
        for (int i = 0; i < index; i++)
            p = p.next;
        return p;
    }

    public static void main(String[] args) {
        MySingleLinkedList<String> list = new MySingleLinkedList<String>();

        list.add(0, "토요일"); //토
        list.add(1, "금요일"); //토금
        list.add(0, "월요일"); //월토금
        list.add(2, "화요일"); //월토화금 순서가 이렇게 되는 이유는 입력된 인덱스 번호에
        //해당 데이터 필드가 들어가야 하기때문에 그 자리를 밀어내고 자기가 들어감
        String str = list.get(2);        //str = "화요일"
        list.remove("금요일");             //월토금(화가 삭제)
        int pos = list.indexOf("토요일");  //2를 리턴

    }
}
