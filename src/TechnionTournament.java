import java.util.ArrayList;

public class TechnionTournament implements Tournament{

    TechnionTournament(){};

    @Override
    public void init() {
        facultyTreeById=new FacultyTreeById();
        facultyTreeByPoints=new FacultyTreeByPoints();
        playerTree=new PlayerTree();
    }

    @Override
    public void addFacultyToTournament(Faculty faculty) {
        FacultyWithPlayers facultyWithPlayers=new FacultyWithPlayers(faculty);
        facultyTreeById.insert(facultyWithPlayers);
        facultyTreeByPoints.insert(facultyWithPlayers);
    }

    @Override
    public void removeFacultyFromTournament(int faculty_id){
        //Leaf<FacultyWithPlayers> deletedFaculty = facultyTreeById.search(faculty_id);
        facultyTreeById.Delete(new FacultyWithPlayers(new Faculty(faculty_id,"")));
        //do we need to make the player a free agent now?
        //we need to figure out how to delete faculty by id
    }

    @Override
    public void addPlayerToFaculty(int faculty_id,Player player) {
        Leaf<FacultyWithPlayers> requiredFaculty = facultyTreeById.search(faculty_id);
        PlayerWithGoals playerWithGoals=new PlayerWithGoals(player);
        requiredFaculty.getKey().getPlayers().add(playerWithGoals);
        playerTree.insert(playerWithGoals);
    }

    @Override
    public void removePlayerFromFaculty(int faculty_id, int player_id) {
        Leaf<FacultyWithPlayers> requiredFaculty = facultyTreeById.search(faculty_id);
        PlayerWithGoals playerWithGoals = null;
        for (int i = 0; i <requiredFaculty.getKey().getPlayers().size() ; i++) {
            if (requiredFaculty.getKey().getPlayers().get(i).getPlayer().getId() == player_id){
                playerWithGoals=requiredFaculty.getKey().getPlayers().get(i);
            }
        }
        if (playerWithGoals != null) {
            requiredFaculty.getKey().getPlayers().remove(playerWithGoals);
            //it will delete the player also from facultyTreeByPoints
            playerTree.Delete(playerWithGoals);
        }
    }

    @Override
    public void playGame(int faculty_id1, int faculty_id2, int winner,
                         ArrayList<Integer> faculty1_goals, ArrayList<Integer> faculty2_goals) {
        //we need to update the tree such that it will still remain sorted
        //need to update facultytree by points
        Leaf<FacultyWithPlayers> faculty1 = facultyTreeById.search(faculty_id1);
        Leaf<FacultyWithPlayers> faculty2 = facultyTreeById.search(faculty_id2);
        for (int i = 0; i < faculty1_goals.size(); i++) {//is in complexity?
            for (int j = 0; j <faculty1.getKey().getPlayers().size() ; j++) {
                if (faculty1_goals.get(i) == faculty1.getKey().getPlayers().get(j).getId()){
                    PlayerWithGoals changedPlayer = faculty1.getKey().getPlayers().get(j);
                    playerTree.Delete(changedPlayer);
                    changedPlayer.setNumGoals(changedPlayer.getNumGoals()+1);
                    playerTree.insert(changedPlayer);
                }
            }
        }
        for (int i = 0; i < faculty2_goals.size(); i++) {
            for (int j = 0; j <faculty2.getKey().getPlayers().size() ; j++) {
                if (faculty2_goals.get(i) == faculty2.getKey().getPlayers().get(j).getId()){
                    PlayerWithGoals changedPlayer = faculty2.getKey().getPlayers().get(j);
                    playerTree.Delete(changedPlayer);
                    changedPlayer.setNumGoals(changedPlayer.getNumGoals()+1);
                    playerTree.insert(changedPlayer);
                }
            }
        }


    }

    @Override
    public void getTopScorer(Player player) {
        player=playerTree.getMax().getKey().getPlayer();
    }

    @Override
    public void getTopScorerInFaculty(int faculty_id, Player player) {
        Leaf<FacultyWithPlayers> requiredFaculty = facultyTreeById.search(faculty_id);
        ArrayList<PlayerWithGoals> facultyPlayers = requiredFaculty.getKey().getPlayers();
        PlayerWithGoals playerWithMaxGoals = facultyPlayers.get(0);
        int maxGoals=facultyPlayers.get(0).getNumGoals();
        for (int i = 1; i <facultyPlayers.size() ; i++) {
            if (facultyPlayers.get(i).getNumGoals() >= maxGoals){
                if (facultyPlayers.get(i).getNumGoals()==maxGoals
                        && Integer.compare(facultyPlayers.get(i).getId(),playerWithMaxGoals.getId()) < 0){
                    playerWithMaxGoals=facultyPlayers.get(i); //equal goals but smaller id
                }else{
                    playerWithMaxGoals=facultyPlayers.get(i); //more goals
                    maxGoals = playerWithMaxGoals.getNumGoals();
                }
            }
        }
        player=playerWithMaxGoals.getPlayer();
    }

    @Override
    public void getTopKFaculties(ArrayList<Faculty> faculties, int k, boolean ascending) {
        Leaf<FacultyWithPlayers> topFaculty = facultyTreeByPoints.getMax();
        Leaf<FacultyWithPlayers> currentFaculty=topFaculty;
        int index=0;
        while (index < k && currentFaculty!=null){
            faculties.add(currentFaculty.getKey().getFaculty());
            currentFaculty=currentFaculty.getPredecessor();
            index++;
        }
        if (ascending==true && faculties.size()==k){
            for (int i = 0; i < k/2 ; i++) {
                Faculty tmp=faculties.get(i);
                faculties.set(i,faculties.get(k/2-i));
                faculties.set(k/2-i,tmp);
            }
        }
    }

    @Override
    public void getTopKScorers(ArrayList<Player> players, int k, boolean ascending) {
        Leaf<PlayerWithGoals> topPlayer = playerTree.getMax();
        Leaf<PlayerWithGoals> currentPlayer=topPlayer;
        int index=0;
        while (index < k && currentPlayer!=null){
            players.add(currentPlayer.getKey().getPlayer());
            currentPlayer=currentPlayer.getPredecessor();
            index++;
        }
        if (ascending==true && players.size()==k){
            for (int i = 0; i < k/2 ; i++) {
                Player tmp=players.get(i);
                players.set(i,players.get(k/2-i));
                players.set(k/2-i,tmp);
            }
        }
    }

    @Override
    public void getTheWinner(Faculty faculty) {
        faculty=facultyTreeByPoints.getMax().getKey().getFaculty();
    }

    ///TODO - add below your own variables and methods
    FacultyTreeById facultyTreeById;
    FacultyTreeByPoints facultyTreeByPoints;
    PlayerTree playerTree;
}
