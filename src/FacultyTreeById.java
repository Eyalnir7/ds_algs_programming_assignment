public class FacultyTreeById extends TwoThreeTree<FacultyWithPlayers>{
    @Override
    protected int compareKeys(FacultyWithPlayers key1, FacultyWithPlayers key2) {
        return Integer.compare(key1.getFaculty().getId(), key2.getFaculty().getId());
    }
}
