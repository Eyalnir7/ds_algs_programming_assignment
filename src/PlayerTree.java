public class PlayerTree extends TwoThreeTreeWithMax<PlayerWithGoals>{

    @Override
    protected int compareKeys(PlayerWithGoals key1, PlayerWithGoals key2) {
        int compareGoals = Integer.compare(key1.getNumGoals(), key2.getNumGoals());
        if (compareGoals==0){
            return Integer.compare(key1.getId(), key2.getId());
        }else{
            return compareGoals;
        }
    }
}
