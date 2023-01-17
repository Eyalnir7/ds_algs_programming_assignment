import java.util.ArrayList;

public class FacultyWithPlayers {
    private Faculty faculty;
    private int numPoints;
    private ArrayList<PlayerWithGoals> players;

    public FacultyWithPlayers(Faculty faculty) {
        this.faculty = faculty;
        this.players=new ArrayList<>();
        this.numPoints=0;
    }
    public void deletePlayerById(int id){
        for (PlayerWithGoals player : players) {
            if (player.getId() == id){
                players.remove(player);
            }
        }
    }
    public PlayerWithGoals getPlayerById(int id){
        for (PlayerWithGoals player : players) {
            if (player.getId() == id){
                return player;
            }
        }
        return null;
    }
    public void addPlayer(PlayerWithGoals player){
        players.add(player);
    }
    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getNumPoints() {
        return numPoints;
    }

    public void setNumPoints(int numPoints) {
        this.numPoints = numPoints;
    }

    public ArrayList<PlayerWithGoals> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<PlayerWithGoals> players) {
        this.players = players;
    }


}

