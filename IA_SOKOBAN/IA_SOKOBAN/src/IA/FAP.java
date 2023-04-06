package IA;

public class FAP<E> {
    FAPChain head;

    public FAP()
    {
        this.head = null;
    }

    public void insert(E element, int priority)
    {
        FAPChain new_element = new FAPChain<E>(element,priority);
        FAPChain current = head;
        if(head == null)
        {
            head = new FAPChain<E>(element,priority);
            return;
        }
        if(current.priority>priority)
        {
            new_element.next = head;
            head = new_element;
            return;
        }
        while(current.next != null)
        {
            if(current.next.priority>priority)
            {
                new_element.next = current;
                current.next = new_element;
                return;
            }
            current = current.next;
        }
        current.next = new_element;
    }
    public E pluck(){
        if(head != null)
        {
            FAPChain<E> elem = head;
            head = head.next;
            elem.next=null;
            return elem.value;
        }
        return null;
    }

    public boolean isEmpty()
    {
        if(head == null)
        {
            return true;
        }
        return false;
    }


//    public Path deleteMin() {
//        if (size == 0) {
//            throw new NoSuchElementException();
//        }
//        Path min = heap[1];
//        heap[1] = heap[size];
//        heap[size] = null;
//        size--;
//        int i = 1;
//        while (true) {
//            int left = i * 2;
//            int right = i * 2 + 1;
//            if (left > size) {
//                break;
//            } else if (right > size) {
//                if (heap[left].priority < heap[i].priority) {
//                    swap(i, left);
//                    i = left;
//                } else {
//                    break;
//                }
//            } else {
//                int minChild = heap[left].priority < heap[right].priority ? left : right;
//                if (heap[minChild].priority < heap[i].priority) {
//                    swap(i, minChild);
//                    i = minChild;
//                } else {
//                    break;
//                }
//            }
//        }
//        return min;
//    }
}
