public class Node<T>{
    private T key;
    private Node<T> p;
    private Node<T> left;
    private Node<T> middle;
    private Node<T> right;
    private boolean plusInf;
    private boolean minusInf;
    public Node(){
        this(null, null, null, null, null, false, false);
    }

    public Node(boolean plusInf, boolean minusInf){
        this(null, null, null, null, null, plusInf, minusInf);
    }

    public Node(Node<T> p, boolean plusInf, boolean minusInf){
        this(null, p, null, null, null, plusInf, minusInf);
    }

    public Node(T key, Node<T> p, Node<T> left, Node<T> middle, Node<T> right, boolean plusInf, boolean minusInf) {
        this.key = key;
        this.p = p;
        this.left = left;
        this.right = right;
        this.middle = middle;
        this.plusInf = plusInf;
        this.minusInf = minusInf;
    }

    public Node(T key, Node<T> p, Node<T> left, Node<T> middle, Node<T> right) {
        this(key, p, left, middle, right, false, false);
    }

    public Node(T key, Node<T> p){
        this(key, p, null, null, null);
    }

    public Node(Node<T> copy){
        this(copy.getKey(), copy.getP(), copy.getLeft(), copy.getMiddle(), copy.getRight());
    }

    public Boolean getPlusInf() {
        return plusInf;
    }

    public void setPlusInf(Boolean plusInf) {
        this.plusInf = plusInf;
        this.minusInf = !plusInf;
    }

    public Boolean getMinusInf() {
        return minusInf;
    }

    public void setMinusInf(Boolean minusInf) {
        this.minusInf = minusInf;
        this.plusInf = !minusInf;
    }
    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node<T> left) {
        this.left = left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node<T> right) {
        this.right = right;
    }

    public Node<T> getMiddle() {
        return middle;
    }

    public void setMiddle(Node<T> middle) {
        this.middle = middle;
    }


    public T getKey() {
        return key;
    }

    public void setKey(T key) {
        this.key = key;
    }

    public Node<T> getP() {
        return p;
    }

    public void setP(Node<T> p) {
        this.p = p;
    }
}
