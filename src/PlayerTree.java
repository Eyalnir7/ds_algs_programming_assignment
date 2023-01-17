public class PlayerTree extends TwoThreeTreeWithMax<PlayerWithGoals>{

    @Override
    protected int compareKeys(PlayerWithGoals key1, PlayerWithGoals key2) {
        int compareGoals = Integer.compare(key1.getNumGoals(), key2.getNumGoals());
        if (compareGoals==0){
            return Integer.compare(key2.getId(), key1.getId());
        }
        return compareGoals;
    }

    public Leaf<PlayerWithGoals> search(int id, int numgoals){
        PlayerWithGoals temp = new PlayerWithGoals(new Player(id, ""));
        temp.setNumGoals(numgoals);
        return super.search(temp);
    }
}
