package KWLinkedList;


import Polynom2.MySingleLinkedList;

import java.util.Iterator;
import java.util.ListIterator;

public class KWLinkedList<E> {

    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;


    private static class Node<T> {  //이너 클래스
                                    //해당 클래스에는 다른 멤버들이 직접 접근하지 않는다.
        public T data;              //따라서 static 이어도 문제가 생기지 않는다.
        public Node<T> next;        //여기만 T타입이고 아래는 E타입인데 이거는 일단 그러려니 하고 나중에 돌아와서 다시 보자
        public Node<T> prev;


        public Node(T item) {
            data = item;
            next = null;
            prev = null;


        }
    }

    public Iterator<E> iterator(){
        return new KWListIterator(0);
    }
    public ListIterator<E> listIterator(){
        return new KWListIterator(0);
    }
    public ListIterator<E> listIterator(int index){
        return new KWListIterator(index);
    }


    private class KWListIterator implements ListIterator<E>{    //여기가 static 이면 당장 문제가 생긴다.
                                    //당장 위의 head 나 tail 이 여기에 접근할 수가 없고 size 도 마찬가지이다.

        //생성자와 데이터 멤버
        private Node<E> nextItem;   //nextNode랑 같은 개념. 이름만 다름.
        private Node<E> lastItemReturned;
        private int index;

        public KWListIterator(int index) {
            this.index = index;
        }

        //인터페이스에서 지정한 메서드들
        @Override
        public boolean hasNext() {

            return false;
        }

        @Override
        public E next() {
            return null;
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

        @Override
        public E previous() {
            return null;
        }

        @Override
        public int nextIndex() {
            return 0;
        }

        @Override
        public int previousIndex() {
            return 0;
        }

        @Override
        public void remove() {

        }

        @Override
        public void set(E e) {

        }

        @Override
        public void add(E e) {

        }
    }


    public int indexOf(E item) { //검색

        return 0;
    }

    public void add (int index, E item){

    }

    public E get(int index){
        return null;
    }

    public boolean remove(int index){
        return true;
    }

    public int size(){
        return size;
    }

}
