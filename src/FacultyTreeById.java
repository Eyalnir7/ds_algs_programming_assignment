public class FacultyTreeById extends TwoThreeTree<FacultyWithPlayers>{
    @Override
    protected int compareKeys(FacultyWithPlayers key1, FacultyWithPlayers key2) {
        return Integer.compare(key1.getFaculty().getId(), key2.getFaculty().getId());
    }

    public Leaf<FacultyWithPlayers> search(int id){
        return super.search(new FacultyWithPlayers(new Faculty(id, "")));
    }
}
