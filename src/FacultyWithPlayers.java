import java.util.ArrayList;

public class FacultyWithPlayers {
    private Faculty faculty;
    private int numPoints;
    private ArrayList<Player> players;

    public FacultyWithPlayers(Faculty faculty) {
        this.faculty = faculty;
        this.players=new ArrayList<>();
        this.numPoints=0;
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

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


}

