import java.util.*;
public class Path {
    private int pathDistance = 0;
    private ArrayList<Node> list = new ArrayList<Node>();
    private Node lastNode = null;
    public Path(){

    }

    public void add(Node n){
        //Adds a node to the list
        this.list.add(n);
       // this.pathScore += n.getScore();
        //Makes that new node the last node
        this.lastNode = n;
        //Recalculates distance(Not really used in this program)
        setDistance();
    }

    //Prints the path
    public void printPath(){
        for(Node n: this.list){
            //This is used for putting the | in, sets the node to know where the flip occurs to make it equal to the next node in the path
            if(n.getPrev() != null){
                n.getPrev().setFlip(n.getFlip());
            }
        }


        for (Node n: this.list){    
            char temp[] = n.getInfo();
            char goal[] = new char[] { '4', '3', '2', '1'};
            //If n is equal to the goal we do not have to flip anymore, however n still has a flip value which is used to show how it got there
            if(Arrays.equals(temp,goal)){
                System.out.print(n.getInfo());
                System.out.println(" g="+n.getDistance()+", h="+n.getHeuristic());
            }
            else{
                switch(n.getFlip()){
                    case 4:
                        System.out.println("|"+temp[0]+""+temp[1]+""+temp[2]+""+temp[3]+" g="+n.getDistance()+", h="+n.getHeuristic());
                        break;
                    case 3:
                        System.out.println(temp[0]+"|"+temp[1]+""+temp[2]+""+temp[3]+" g="+n.getDistance()+", h="+n.getHeuristic());
                        break;
                    case 2:
                        System.out.println(temp[0]+""+temp[1]+"|"+temp[2]+""+temp[3]+" g="+n.getDistance()+", h="+n.getHeuristic());
                        break;
                    default:
                        break;
                }
            }
            
        }
    }

    //Sets the distance for the path(Not used)
    public void setDistance(){
        int temp = 0 ;
        for (Node n: this.list){
            //System.out.println(n.getInfo());
            temp += n.getDistance();
        }
        this.pathDistance = temp;
    }

    public int getPathDistance(){
        return this.pathDistance;
    }

    public Node getLastNode(){
        return this.lastNode;
    }

    //If we are given a node, it returns the path all the way back to the root
    public void makePathFromChild(Node n){
        Node temp = n;
        this.add(temp);
        while(temp.getPrev() != null){
            this.add(temp.getPrev());
            temp = temp.getPrev();
        }
       
            // Arraylist for storing reversed elements 
            ArrayList<Node> revArrayList = new ArrayList<Node>(); 
            for (int i = this.list.size() - 1; i >= 0; i--) { 
      
                // Append the elements in reverse order 
                revArrayList.add(this.list.get(i)); 
            } 
      
            // Return the reversed arraylist 
            this.list = revArrayList;
        
    }
}
