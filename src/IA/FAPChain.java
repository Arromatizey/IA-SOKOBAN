package IA;

public class FAPChain<E> {
    E value;
    int priority;
    FAPChain next;

    public FAPChain(E value,int priority)
    {
        this.value = value;
        this.priority = priority;
        this.next = null;
    }
}
