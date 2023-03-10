public class FacultyTreeByPoints extends TwoThreeTreeWithMax<FacultyWithPlayers>{
    @Override
    protected int compareKeys(FacultyWithPlayers key1, FacultyWithPlayers key2) {
        int comparePoints = Integer.compare(key1.getNumPoints(), key2.getNumPoints());
        if(comparePoints == 0){
            return Integer.compare(key2.getFaculty().getId(), key1.getFaculty().getId());
        }
        return comparePoints;
    }
    public Leaf<FacultyWithPlayers> search(int id, int numpoints){
        FacultyWithPlayers temp = new FacultyWithPlayers(new Faculty(id, ""));
        temp.setNumPoints(numpoints);
        return super.search(temp);
    }
}
