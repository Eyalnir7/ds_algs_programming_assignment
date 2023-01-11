public class FacultyTreeByPoints extends TwoThreeTreeWithMax<FacultyWithPlayers>{
    @Override
    protected int compareKeys(FacultyWithPlayers key1, FacultyWithPlayers key2) {
        int comparePoints = Integer.compare(key1.getNumPoints(), key2.getNumPoints());
        if(comparePoints == 0){
            return Integer.compare(key1.getFaculty().getId(), key2.getFaculty().getId());
        }
        return comparePoints;
    }
}
