public abstract class TwoThreeTree<T> {
    private Node<T> root;

    public TwoThreeTree() {
        this.root = new Node<T>(true, false);
        Leaf<T> l = new Leaf<T>(root, false, true);
        Leaf<T> m = new Leaf<T>(Integer.MIN_VALUE, root, null, l);
        l.setSuccessor(m);
        root.setLeft(l);
        root.setMiddle(m);
    }

    //return positive number if node1 > node2
    //return 0 if node1 = node2
    //return negative if node1 < node2
    public int compareNodes(Node<T> node1, Node<T> node2){
        return 1;
    }

    protected abstract int compareKeys(T key1, T key2);

    public void updateKey(Node<T> x){
        if(x==null) return;
        x.setKey(x.getLeft().getKey());
        if(x.getMiddle()!=null){
            x.setKey(x.getMiddle().getKey());
        }
        if(x.getRight()!=null){
            x.setKey(x.getRight().getKey());
        }
    }

    public void setChildren(Node<T> x, Node<T> l, Node<T> m, Node<T> r){
        if(l==null) return;
        l.setP(x);
        x.setLeft(l);
        x.setRight(r);
        x.setMiddle(m);
        if(m!=null){
            m.setP(x);
        }
        if(r!=null){
            r.setP(x);
        }
        updateKey(x);
    }

    public Node<T> insert_And_Split(Node<T> x, Node<T> z){
        Node<T> l=x.getLeft();
        Node<T> m=x.getMiddle();
        Node<T> r=x.getRight();
        if (r==null){
            if(compareNodes(z,l)>0){
                setChildren(x,z,l,m);
            }else if(compareNodes(z,m)<0){
                setChildren(x,l,z,m);
            }else {
                setChildren(x,l,m,z);
            }
            return null;
        }
        Node<T> y = new Node<T>(r);
        if(compareNodes(z,l)<0){
            setChildren(x,z,l,null);
            setChildren(y,m,r,null);
        }else if(compareNodes(z,m)<0){
            setChildren(x,l,z,null);
            setChildren(y,m,r,null);
        }else if(compareNodes(z,m)<0){
            setChildren(x,l,m,null);
            setChildren(y,z,r, null);
        } else {
            setChildren(x,l,m,null);
            setChildren(y,r,z, null);
        }
        return y;
    }

    public void insert(Leaf<T> z){
        Node<T> nextNode = root;
        while(nextNode.getLeft() != null){
            if(compareNodes(z,nextNode.getLeft())<0) nextNode = nextNode.getLeft();
            else if (compareNodes(z,nextNode.getMiddle())<0) nextNode = nextNode.getMiddle();
            else nextNode = nextNode.getRight();
        }
        Leaf<T> nextLeaf = (Leaf<T>)nextNode;
        z.setSuccessor(nextLeaf);
        z.setPredecessor(nextLeaf.getPredecessor());
        nextLeaf.getPredecessor().setSuccessor(z);
        ((Leaf<T>)nextNode).setPredecessor(z);
        Node<T> papa = nextLeaf.getP();
        Node<T> split = insert_And_Split(papa, z);
        while(papa!=root){
            papa = papa.getP();
            if(split != null){
                split = insert_And_Split(papa, split);
            }
            else updateKey(papa);
        }
        if(split != null){
            Node<T> newRoot = new Node<T>();
            setChildren(newRoot, papa, split, null);
            root = newRoot;
        }
    }

    public Leaf<T> search(){

    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }
}
