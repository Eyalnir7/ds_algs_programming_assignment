public class PlayerWithGoals {
    private Player player;
    private int numGoals;

    public PlayerWithGoals(Player player) {
        this.player = new Player(player.getId(), player.getName());
        this.numGoals = 0;
    }

    public int getId() {
        return this.player.getId();
    }

    public void setId(int id){
        this.player.setId(id);
    }

    public String getName(){
        return this.player.getName();
    }

    public void setName(String name){
        this.player.setName(name);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getNumGoals() {
        return numGoals;
    }

    public void setNumGoals(int numGoals) {
        this.numGoals = numGoals;
    }
}
