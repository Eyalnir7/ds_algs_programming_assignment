public abstract class TwoThreeTree<T> {
    protected Node<T> root;

    public TwoThreeTree() {
        this.root = new Node<T>(true, false);
        Leaf<T> l = new Leaf<T>(root, null, null, false, true);
        Leaf<T> m = new Leaf<T>(root, null, l, true, false);
        l.setSuccessor(m);
        root.setLeft(l);
        root.setMiddle(m);
    }

    //return positive number if node1 > node2
    //return 0 if node1 = node2
    //return negative if node1 < node2
    public int compareNodes(Node<T> node1, Node<T> node2){
        if(node1.getPlusInf()){
            if(node2.getPlusInf()) return 0;
            return 1;
        }
        if(node1.getMinusInf()){
            if(node2.getMinusInf()) return 0;
            return -1;
        }
        if(node2.getPlusInf()) return -1;
        if(node2.getMinusInf()) return 1;
        return compareKeys(node1.getKey(), node2.getKey());
    }

    protected abstract int compareKeys(T key1, T key2);

    public void updateKey(Node<T> x){
        if(x==null) return;
        x.setKey(x.getLeft().getKey());
        if(x.getMiddle()!=null){
            if (x.getMiddle().getPlusInf()){
                x.setPlusInf(true);
            }else{
                x.setKey(x.getMiddle().getKey());
                x.setPlusInf(false);
            }
            //problem
        }
        if(x.getRight()!=null){
            if (x.getRight().getPlusInf()){
                x.setPlusInf(true);
            }else{
                x.setKey(x.getRight().getKey());
                x.setPlusInf(false);
            }
            //problem
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
        Node<T> l = x.getLeft();
        Node<T> m = x.getMiddle();
        Node<T> r = x.getRight();
        if (r==null){
            if(compareNodes(z,l)<0){
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
        }else if(compareNodes(z,r)<0){
            setChildren(x,l,m,null);
            setChildren(y,z,r, null);
        } else {
            setChildren(x,l,m,null);
            setChildren(y,r,z, null);
        }
        return y;
    }

    public Node<T> borrowOrMerge(Node<T> y){
        Node<T> z = y.getP();
        if(y == z.getLeft()){
            Node<T> x = z.getMiddle();
            if(x.getRight()!=null){
                setChildren(y, y.getLeft(), x.getLeft(), null);
                setChildren(x, x.getMiddle(), x.getRight(), null);
            }
            else{
                setChildren(x, y.getLeft(), x.getLeft(), x.getMiddle());
                setChildren(z, x, z.getRight(), null);
            }
            return z;
        }
        if(y == z.getMiddle()){
            Node<T> x = z.getLeft();
            if(x.getRight() != null){
                setChildren(y, x.getRight(), y.getLeft(), null);
                setChildren(x, x.getLeft(), x.getMiddle(), null);
            }
            else {
                setChildren(x, x.getLeft(), x.getMiddle(), y.getLeft());
                setChildren(z, x, z.getRight(), null);
            }
            return z;
        }
        Node<T> x = z.getMiddle();
        if(x.getRight() != null){
            setChildren(y, x.getRight(), y.getLeft(), null);
            setChildren(x, x.getLeft(), x.getMiddle(), null);
        }
        else{
            setChildren(x, x.getLeft(), x.getMiddle(), y.getLeft());
            setChildren(z, z.getLeft(), x, null);
        }
        return z;
    }

    public void delete(T key){
        deleteAux(search(key));
    }

    public void deleteAux(Leaf<T> x){
        Node<T> y = x.getP();
        x.getPredecessor().setSuccessor(x.getSuccessor());
        x.getSuccessor().setPredecessor(x.getPredecessor());
        if(x == y.getLeft()){
            setChildren(y, y.getMiddle(), y.getRight(), null);
        }
        else if(x == y.getMiddle()){
            setChildren(y, y.getLeft(), y.getRight(), null);
        }
        else {
            setChildren(y, y.getLeft(), y.getMiddle(), null);
        }
        while(y!=null){
            if(y.getMiddle()==null){
                if(y != root){
                    y = borrowOrMerge(y);
                }
                else{
                    setRoot(y.getLeft());
                    y.getLeft().setP(null);
                    return;
                }
            }
            else{
                updateKey(y);
                y = y.getP();
            }
        }
    }

    public void insert(T z){
        insertAux(new Leaf<T>(z));
    }

    public void insertAux(Leaf<T> z){
        Node<T> nextNode = root;
        while(nextNode.getLeft() != null){
            if (compareNodes(z,nextNode.getLeft())<0){
                nextNode = nextNode.getLeft();
            }
            else if (compareNodes(z,nextNode.getMiddle())<0){
                nextNode = nextNode.getMiddle();
            }
            else {
                nextNode = nextNode.getRight();
            }
        }
        Leaf<T> nextLeaf = ((Leaf<T>)nextNode);
        Node<T> papa = nextLeaf.getP();
        Node<T> split = insert_And_Split(papa, z);
        z.setSuccessor(nextLeaf);
        if (nextLeaf.getPredecessor()!=null) {
            z.setPredecessor(nextLeaf.getPredecessor());
            nextLeaf.getPredecessor().setSuccessor(z);
        }
        ((Leaf<T>)nextNode).setPredecessor(z);

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

    public Leaf<T> search(T key){
        Node<T> keyNode = new Node<T>(key, null);
        return searchAux(root, keyNode);
    }

    private Leaf<T> searchAux(Node<T> x,Node<T> key){
        if(x.getLeft() == null){
            if(compareNodes(x, key) == 0){
                return (Leaf<T>)x;
            }
            else return null;
        }
        if(compareNodes(key, x.getLeft())<=0){
            return searchAux(x.getLeft(), key);
        }
        else if(compareNodes(key, x.getMiddle())<=0){
            return searchAux(x.getMiddle(), key);
        }
        else return searchAux(x.getRight(), key);
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }
}
