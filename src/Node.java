import java.util.*;

public class Node {
    final char[] goal = new char[] { '4', '3', '2', '1'};
    private Node next[] = new Node[] {null,null,null};
    private Node prev;
    private char order[];
    private boolean isGoal = false;
    private int score =0;
    private int flip = 0;
    private int distance = 0;
    private int heuristic = 0;
    private boolean visited = false;

    public Node(char info[], Node prev, int dis) {
        this.order = info;
        //System.out.print("Making Node:");
        //System.out.println(this.order);
        //this.next = next;
        this.prev = prev;
        setDistance(dis);
        setScore();
        setHeuristic();
        //System.out.println(score);
        if (Arrays.equals(this.order,goal)){
            this.isGoal = true;
            //System.out.println("Goal Node");
        }
        else {
            makeChildren();
        }
    }

    public void setInfo(char info[]) {
        this.order = info;
    }

    //Distance is set based on the number of flips, it also adds the parent nodes distance to give total distance
    public void setDistance(int d){
        this.flip = d;
        if(this.prev != null){
            this.distance = d + this.prev.getDistance();
        }
        else{
            this.distance = 0;
        }
    }

    //Score is not used, It was for testing
    public void setScore() {
        for(int i = 0;i <4;i ++){
            if(this.order[i] == goal[i]){
                score ++;
            }
        }
    }

    //Sets the amount that a node is flipped, is used in the print path function
    public void setFlip(int i){
        this.flip = i;
    }

    //Sets parent node
    public void setPrev(Node node) {
        prev = node;
    }

    //Sets if it has already been on the fringe and if its parents have already been explored 
    public void setVisited(boolean b){
        this.visited = b;
    }

    //Sets the heuristic based on largest out of place number
    public void setHeuristic(){
        int max = 0;
        for(int i = 0;i <4;i ++){
            if(this.order[i] != goal[i]){
                if(Integer.parseInt(String.valueOf(this.order[i])) > max){
                    max = Integer.parseInt(String.valueOf(this.order[i]));
                }
            }
        }
        this.heuristic = max;
    }

    //Gets the numerical value of the node, for tie breaking
    public int getNumbericalVal(){
        return (Integer.parseInt(String.valueOf(this.order)));    
    }

    //Gets the number of flips
    public int getFlip(){
        return this.flip;
    }

    //Gets the heuristic number
    public int getHeuristic(){
        return this.heuristic;
    }

    public int getDistance(){
        return this.distance;
    }

    public char[] getInfo() {
        return this.order;
    }

    //Number of out of place 
    public int getScore(){
        return this.score;
    }

    //Returns if it is a goal node
    public boolean isGoal() {
        return this.isGoal;
    }

    //Returns if it has been visited
    public boolean getVisited(){
        return this.visited;
    }

    //Returns the next node at index n
    public Node getNext(int n) {
        //System.out.println(n);
        if(n>2){
           System.out.println("Error n > 2");
        }
        return next[n];
    }

    //Returns parent node
    public Node getPrev() {
        return prev;
    }
    
    //Makes the current nodes children if the node is a parent node, it does not make that child
    public void makeChildren(){
        if(isGoal != true) {
            for (int i = 0; i < 3; i++) { //First |#### //Second #|### //Third ##|##
                char[] temp = new char[4];
                boolean alreadyDone = false;
                Node parentTemp = this;
                switch (i){
                    case 0:
                        temp[0] = this.order[3];    //Swap Bottom = Top
                        temp[1] = this.order[2];    //Swap Third = Second
                        temp[2] = this.order[1];    //Swap Second = Third
                        temp[3] = this.order[0];    //Swap Top = Bottom
                        //Check and make
                        //System.out.println(temp);
                        while((parentTemp != null) && (alreadyDone == false)){
                            //System.out.print("Parent Order:");
                            //System.out.println(parentTemp.order);
                            if(Arrays.equals(parentTemp.order,temp)){  //Must do this when comparing arrays, char.equals will not work
                                //System.out.println("Already Done");
                                alreadyDone = true;
                            }
                            else{
                                //System.out.println("We gucci");
                                parentTemp = parentTemp.prev;
                            }
                        }
                        if(alreadyDone == false){
                            Node n1 = new Node(temp,this,4);
                            this.next[0] = n1;
                        }
                        break;
                    case 1:
                        temp[0] = this.order[0];    //Bottom = Bottom
                        temp[1] = this.order[3];    //Third = First
                        temp[2] = this.order[2];    //Second = Second
                        temp[3] = this.order [1];    //First = Third
                        //Check and make
                        //System.out.println(temp);
                        while((parentTemp != null) && (alreadyDone == false)){
                            //System.out.print("Parent Order:");
                            //System.out.println(parentTemp.order);
                            if(Arrays.equals(parentTemp.order,temp)){  //Must do this when comparing arrays, char.equals will not work
                                //System.out.println("Already Done");
                                alreadyDone = true;
                            }
                            else{
                                //System.out.println("We gucci");
                                parentTemp = parentTemp.prev;
                            }
                        }
                        if(alreadyDone == false){
                            Node n1 = new Node(temp,this,3);
                            this.next[1] = n1;
                        }
                        break;
                    case 2:
                        temp[0] = this.order[0];
                        temp[1] = this.order[1];
                        temp[2] = this.order[3];
                        temp[3] = this.order[2];
                        //Check and make
                        //System.out.println(temp);
                        while((parentTemp != null) && (alreadyDone == false)){
                            //System.out.print("Parent Order:");
                            //System.out.println(parentTemp.order);
                            if(Arrays.equals(parentTemp.order,temp)){  //Must do this when comparing arrays, char.equals will not work
                                //System.out.println("Already Done");
                                alreadyDone = true;
                            }
                            else{
                                //System.out.println("We gucci");
                                parentTemp = parentTemp.prev;
                            }
                        }
                        if(alreadyDone == false){
                            Node n1 = new Node(temp,this,2);
                            this.next[2] = n1;
                        }
                        break;

                }
            }
        }
    }
}

