public abstract class TwoThreeTreeWithMax<T> extends TwoThreeTree<T>{
    private Leaf<T> max;

    public Leaf<T> getMax() {
        return max;
    }

    public void setMax(Leaf<T> max) {
        this.max = max;
    }

    @Override
    public void insert(T z){
        if(compareKeys(z, max.getKey()) > 0){
            max = new Leaf<T>(z);
        }
        super.insert(z);
    }
}
