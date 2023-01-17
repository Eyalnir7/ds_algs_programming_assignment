import java.util.ArrayList;

public class TechnionTournament implements Tournament{

    TechnionTournament(){};

    @Override
    public void init() {
        facultyTreeById = new FacultyTreeById();
        facultyTreeByPoints = new FacultyTreeByPoints();
        playerTree = new PlayerTree();
    }

    @Override
    public void addFacultyToTournament(Faculty faculty) {
        FacultyWithPlayers facultyWithPlayers = new FacultyWithPlayers(faculty);
        facultyTreeById.insert(facultyWithPlayers);
        facultyTreeByPoints.insert(facultyWithPlayers);
    }

    @Override
    public void removeFacultyFromTournament(int faculty_id){
        Leaf<FacultyWithPlayers> deletedFaculty = facultyTreeById.search(faculty_id);
        facultyTreeById.delete(faculty_id);
        facultyTreeByPoints.delete(deletedFaculty.getKey());
    }

    @Override
    public void addPlayerToFaculty(int faculty_id, Player player) {
        Leaf<FacultyWithPlayers> requiredFaculty = facultyTreeById.search(faculty_id);
        PlayerWithGoals playerWithGoals = new PlayerWithGoals(player);
        requiredFaculty.getKey().addPlayer(playerWithGoals);
        playerTree.insert(playerWithGoals);
    }

    @Override
    public void removePlayerFromFaculty(int faculty_id, int player_id) {
        Leaf<FacultyWithPlayers> requiredFaculty = facultyTreeById.search(faculty_id);
        requiredFaculty.getKey().deletePlayerById(player_id);
    }

    @Override
    public void playGame(int faculty_id1, int faculty_id2, int winner,
                         ArrayList<Integer> faculty1_goals, ArrayList<Integer> faculty2_goals) {
        //we need to update the tree such that it will still remain sorted
        //need to update facultytree by points
        Leaf<FacultyWithPlayers> faculty1 = facultyTreeById.search(faculty_id1);
        Leaf<FacultyWithPlayers> faculty2 = facultyTreeById.search(faculty_id2);
        for (Integer playerId: faculty1_goals) {
            PlayerWithGoals changedPlayer = faculty1.getKey().getPlayerById(playerId);
            playerTree.delete(changedPlayer);
            changedPlayer.setNumGoals(changedPlayer.getNumGoals() + 1);
            playerTree.insert(changedPlayer);
        }
        for (Integer playerId: faculty2_goals) {
            PlayerWithGoals changedPlayer = faculty2.getKey().getPlayerById(playerId);
            playerTree.delete(changedPlayer);
            changedPlayer.setNumGoals(changedPlayer.getNumGoals() + 1);
            playerTree.insert(changedPlayer);
        }
        FacultyWithPlayers faculty1WithPoints = facultyTreeByPoints.search(faculty_id1,faculty1.getKey().getNumPoints()).getKey();
        FacultyWithPlayers faculty2WithPoints = facultyTreeByPoints.search(faculty_id2,faculty2.getKey().getNumPoints()).getKey();
        facultyTreeByPoints.delete(faculty1WithPoints);
        facultyTreeByPoints.delete(faculty2WithPoints);

        if (winner==1) {
            faculty1WithPoints.setNumPoints(faculty1WithPoints.getNumPoints() + 3);
        }else if (winner==2){
            faculty2WithPoints.setNumPoints(faculty2WithPoints.getNumPoints() + 3);
        }else{
            faculty1WithPoints.setNumPoints(faculty1WithPoints.getNumPoints() + 1);
            faculty2WithPoints.setNumPoints(faculty2WithPoints.getNumPoints() + 1);
        }
        facultyTreeByPoints.insert(faculty1WithPoints);
        facultyTreeByPoints.insert(faculty2WithPoints);
    }

    @Override
    public void getTopScorer(Player player) {
        Player tempPlayer = playerTree.getMax().getKey().getPlayer();
        player.setName(tempPlayer.getName());
        player.setId(tempPlayer.getId());
    }

    @Override
    public void getTopScorerInFaculty(int faculty_id, Player player) {
        Leaf<FacultyWithPlayers> requiredFaculty = facultyTreeById.search(faculty_id);
        ArrayList<PlayerWithGoals> facultyPlayers = requiredFaculty.getKey().getPlayers();
        PlayerWithGoals playerWithMaxGoals = facultyPlayers.get(0);
        int maxGoals = playerWithMaxGoals.getNumGoals();
        for (int i = 1; i <facultyPlayers.size() ; i++) {
            if (facultyPlayers.get(i).getNumGoals() >= maxGoals){
                if (facultyPlayers.get(i).getNumGoals()==maxGoals){
                        if (facultyPlayers.get(i).getId() < playerWithMaxGoals.getId()){
                            playerWithMaxGoals=facultyPlayers.get(i); //equal goals but smaller id
                        }
                }else {//if bigger
                    playerWithMaxGoals = facultyPlayers.get(i); //more goals
                    maxGoals = playerWithMaxGoals.getNumGoals();
                }
            }
        }
        Player tempPlayer=playerWithMaxGoals.getPlayer();
        player.setName(tempPlayer.getName());
        player.setId(tempPlayer.getId());
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
        if (ascending && faculties.size()==k){
            for (int i = 0; i < k/2 ; i++) {
                Faculty tmp=faculties.get(i);
                faculties.set(i,faculties.get(k-1-i));
                faculties.set(k-1-i,tmp);
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
        if (ascending && players.size()==k){
            for (int i = 0; i < k/2 ; i++) {
                Player tmp=players.get(i);
                players.set(i,players.get(k-1-i));
                players.set(k-1-i,tmp);
            }
        }
    }

    @Override
    public void getTheWinner(Faculty faculty) {
        Faculty faculty2=facultyTreeByPoints.getMax().getKey().getFaculty();
        faculty.setId(faculty2.getId());
        faculty.setName(faculty2.getName());
    }

    ///TODO - add below your own variables and methods
    FacultyTreeById facultyTreeById;
    FacultyTreeByPoints facultyTreeByPoints;
    PlayerTree playerTree;
}
