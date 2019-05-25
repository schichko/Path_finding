import java.util.*;

public class Fringe {
    private ArrayList<Node> myFringe = new ArrayList<Node>();

    public Fringe(){

    }

    public ArrayList<Node> getNodes(){
        return myFringe;
    }

    public void addNode(Node n){
        myFringe.add(n);
    }

    public void removeNode(Node n){
        myFringe.remove(n);
    }

    public void printFringe(){
        for (Node n: myFringe){
            System.out.println(n.getInfo());
        }
    }


}
