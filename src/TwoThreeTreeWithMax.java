public abstract class TwoThreeTreeWithMax<T> extends TwoThreeTree<T>{
    private Leaf<T> max;

    public Leaf<T> getMax() {
        return max;
    }

    public void setMax(Leaf<T> max) {
        this.max = max;
    }
}
