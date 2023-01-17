public abstract class TwoThreeTreeWithMax<T> extends TwoThreeTree<T>{
    private Leaf<T> max;

    public TwoThreeTreeWithMax() {
        max = (Leaf<T>)root.getMiddle();
    }

    public Leaf<T> getMax() {
        return max;
    }

    public void setMax(Leaf<T> max) {
        this.max = max;
    }

    @Override
    public void insert(T z){
        super.insert(z);
        if(max.getPlusInf() || compareKeys(z, max.getKey()) > 0){
            max = search(z);
        }
    }

    @Override
    public void delete(T z){
        if(max != null && compareKeys(max.getKey(), z) == 0){
            max = max.getPredecessor();
        }
        super.delete(z);
    }
}
