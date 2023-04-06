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
            head = new_element;
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
                FAPChain<E> temp = current.next;
                current.next = new_element;
                new_element.next = temp;
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
}
