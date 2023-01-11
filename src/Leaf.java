public class Leaf<T> extends Node<T>{
    private Leaf<T> successor;

    public Leaf<T> getSuccessor() {
        return successor;
    }

    public void setSuccessor(Leaf<T> successor) {
        this.successor = successor;
    }

    public Leaf<T> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Leaf<T> predecessor) {
        this.predecessor = predecessor;
    }

    private Leaf<T> predecessor;

    public Leaf(T key, Node<T> p, Node<T> left, Node<T> middle, Node<T> right, Leaf<T> successor, Leaf<T> predecessor, Boolean plusInf, Boolean minusInf) {
        super(key, p, left, middle, right, plusInf, minusInf);
        this.successor = successor;
        this.predecessor = predecessor;
    }

    public Leaf(T key, Node<T> p, Leaf<T> successor, Leaf<T> predecessor, Boolean plusInf, Boolean minusInf) {
        super(key, p, null, null,null, plusInf, minusInf);
        this.successor = successor;
        this.predecessor = predecessor;
    }
}
