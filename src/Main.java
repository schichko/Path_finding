//Bugs for later
// For
// instance, the cost of one flip between pancake 4 and 1 from the state “4123” to “4321” is equal
// to 3. Use the same heuristic function that was discussed in the class: the ID of the largest
// pancake that is still out of place. For DFS, you don’t need to consider a cost and heuristic.
// function.

//Break TIES IN GREEDY USING >

import java.util.*;
public class Main {
    static Node startN;
    static char[] b;    //The whole user input
    static char[] mG;   //The input minus the seach algorithm letter (mG = make graph)
    static boolean debug = false;
    public static void main(String[] args) {
        boolean exit = false;   //For exitig the infinite loop
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        while(exit == false) {  
            System.out.println("Input an order for the pancakes (#) along with a character to determine the sorting algorithm(X),####X, or type exit to exit the program");
            System.out.println("d - Depth First Search, u - Uniform Cost Search, g - Greedy, a - A* :");
            System.out.println("type debug to toggle debug mode (more step by step)");
            String s = reader.next();
            b = s.toCharArray();    //For General Purpose
            mG = s.substring(0,(s.length() -1 )).toCharArray(); //For making the graph
            if(s.compareTo("exit") == 0){
                exit = true;
                reader.close();
            }
            else if(s.compareTo("debug") == 0){
                debug = !debug;
                System.out.println("Debug mode: "+debug+"\n");
            }
            else if(b.length == 5){         //#### is b[0] b[1] b[2] b[3]
                makeGraph();
                switch(b[4]){
                    case 'd':
                        System.out.println("Depth First Search:");
                        dfs();
                        break;
                    case 'u':
                        System.out.println("Uniform Cost Search:");
                        ucs();
                        break;
                    case 'g':
                        System.out.println("Greedy Search:");
                        greedy();
                        break;
                    case 'a':
                        System.out.println("A* Search:");
                        aStar();
                        break;
                    default:
                        System.out.println("Wrong type of search input:");
                        break;


                }
            }
            else{
                System.out.println("Wrong input length try again");
            }
        }
    }

    //Going for the cheapest based on h() + d()
    public static void aStar(){
        boolean foundG = false; //Used as a check for the goal, if we havent found the goal we keep checking
        Fringe myFringe = new Fringe(); //Gives us the fringe
        Node lastExpanded = startN;  //Start node, dont want to change this so we assign it to current node
        myFringe.addNode(lastExpanded);  //We want to add our first node to the fringe
        //Checks to make sure were not already at the goal node
        if(lastExpanded.isGoal() == true){
            foundG = true;  //If this happens we ignore while loop below (Basically only happens when we are given 4321)
        }

        while(foundG == false){ 
            Node temp = null;   //Temp node for checking
            int maxD = -1; //Impossible distance value
            int maxNV = 0; //Max Numerical Value

            //Adds our last expanded nodes children to the fringe
            for(int i = 0; i < 3; i++){
                if(lastExpanded.getNext(i)!=null){
                    myFringe.addNode(lastExpanded.getNext(i));
                    if(debug == true){
                        System.out.println("Adding "+lastExpanded.getNext(i).getNumbericalVal()+" to fringe");
                    }
                }
            }
            //Lets us know that the last expanded node has been visited
            lastExpanded.setVisited(true);

                for(Node n:myFringe.getNodes()){
                        //If we havent already expanded this node and it is the next smallest
                        if(debug == true){
                            System.out.println("Node: "+n.getNumbericalVal()+"  Distance: "+n.getDistance()+"\nHeuristic: "+n.getHeuristic()+"   Is Visted: "+n.getVisited());
                        }
                        //Checks, If h()+g() < max, which is the a* function. If so we make n the new temp
                        if(((n.getDistance() + n.getHeuristic() < maxD) && (n.getVisited() == false)) || ((maxD == -1) && (n.getVisited() == false))){
                            temp = n;
                            maxD = n.getDistance() + n.getHeuristic();
                            maxNV = n.getNumbericalVal();
                        }
                        //If they are equal, we check based on the numerical values
                        else if((n.getDistance() + n.getHeuristic() == maxD) && (n.getVisited() == false) && (maxNV < n.getNumbericalVal())){
                            temp = n;
                            maxD = n.getDistance() + n.getHeuristic();
                            maxNV = n.getNumbericalVal();
                        }
                    }
                    //Otherwise we skip that node because it cannot possibly be part of this path
            lastExpanded = temp;
            if(debug == true){
                System.out.println("Last Expanded: "+lastExpanded.getNumbericalVal());
            }
            if(lastExpanded.isGoal() == true){
                foundG = true;
            }
        }
      
        Path myPath = new Path();  
        myPath.makePathFromChild(lastExpanded);
        //System.out.println("\nMy Fringe:");
        //myFringe.printFringe();
        System.out.println("\nMy Path:");
        myPath.printPath();
        System.out.println("\n");
    }

    //Going for the cheapest based on distance 
    public static void ucs(){
        boolean foundG = false; //Used as a check for the goal, if we havent found the goal we keep checking
        Fringe myFringe = new Fringe(); //Gives us the fringe
        Node lastExpanded = startN;  //Start node, dont want to change this so we assign it to current node
        myFringe.addNode(lastExpanded);  //We want to add our first node to the fringe
        //Checks to make sure were not already at the goal node
        if(lastExpanded.isGoal() == true){
            foundG = true;  //If this happens we ignore while loop below (Basically only happens when we are given 4321)
        }

        while(foundG == false){ 
            Node temp = null;   //Temp node for checking
            int maxD = -1; //Impossible real value
            int maxNV = 0; //Max Numerical Value

            //Adds the node that was last expandeds children to the fringe
            for(int i = 0; i < 3; i++){
                if(lastExpanded.getNext(i)!=null){
                    myFringe.addNode(lastExpanded.getNext(i));
                    if(debug == true){
                        System.out.println("Adding "+lastExpanded.getNext(i).getNumbericalVal()+" to fringe");
                    }
                }
            }
            lastExpanded.setVisited(true);

                for(Node n:myFringe.getNodes()){
                        //If we havent already expanded this node and it is the next smallest
                        if(debug == true){
                            System.out.println("Node: "+n.getNumbericalVal()+"  Distance: "+n.getDistance()+"  Is Visted: "+n.getVisited());
                        }
                        //Compares distance, in this case distance for a node is calculated to be the total distance from the root, + distance of that ndoe
                        if((n.getDistance() < maxD) && (n.getVisited() == false) || (maxD == -1) && (n.getVisited() == false)){
                            temp = n;
                            maxD = n.getDistance() ;
                            maxNV = n.getNumbericalVal();
                        }
                        else if((n.getDistance() == maxD) && (n.getVisited() == false) && (maxNV < n.getNumbericalVal())){
                            temp = n;
                            maxD = n.getDistance() ;
                            maxNV = n.getNumbericalVal();
                        }
                    }
                    //Otherwise we skip that node because it cannot possibly be part of this path
            lastExpanded = temp;
            if(debug == true){
                System.out.println("Last Expanded: "+lastExpanded.getNumbericalVal());
            }
            if(lastExpanded.isGoal() == true){
                foundG = true;
            }
        }
      
        Path myPath = new Path();  
        myPath.makePathFromChild(lastExpanded);
        //System.out.println("\nMy Fringe:");
        //myFringe.printFringe();
        System.out.println("\nMy Path:");
        myPath.printPath();
        System.out.println("\n");
    }

    //Going for the cheapest overall based on heuristic 
    public static void greedy(){
        boolean foundG = false; //Used as a check for the goal, if we havent found the goal we keep checking
        Path myPath = new Path();   //For giving us the path (Maybe want to return the path)
        Fringe myFringe = new Fringe(); //Gives us the fringe, not really used in DFS but still imporant to have (Can print unused nodes in this case as a check)
        Node lastExpanded = startN;  //Start node, dont want to change this so we assign it to current node
        //myPath.add(lastExpanded);    //Add it to our path (root node)
        myFringe.addNode(lastExpanded);  //We want to add our first node to the fringe
        //Checks to make sure were not already at the goal node
        if(lastExpanded.isGoal() == true){
            foundG = true;  //If this happens we ignore while loop below (Basically only happens when we are given 4321)
        }

        while(foundG == false){ 
            Node temp = null;   //Temp node for checking
            int maxH = 5; //Impossible real value
            int maxNV = 0; //Max Numerical Value
            //Add Children Nodes to fringe
            //System.out.println("LAST EXPANDED: "+lastExpanded.getNumbericalVal());
            for(int i = 0; i < 3; i++){
                if(lastExpanded.getNext(i)!=null){
                    myFringe.addNode(lastExpanded.getNext(i));
                }
            }
            lastExpanded.setVisited(true);
            
            //Go though looking for next lowest H
            for(Node n:myFringe.getNodes()){
                //If we havent already expanded this node and it is the next smallest
                if(debug == true){
                    System.out.println("Node: "+n.getNumbericalVal()+"  Heuristic: "+n.getHeuristic()+"  Is Visted: "+n.getVisited());
                }
                //Greedy is just looking at the lowest heuristic out of all nodes on the fringe
               if((n.getHeuristic() < maxH) && (n.getVisited() == false)){
                   temp = n;
                   maxH = n.getHeuristic();
                   maxNV = n.getNumbericalVal();
               }
               else if((n.getHeuristic() == maxH) && (n.getVisited() == false) && maxNV < n.getNumbericalVal()){
                   temp = n;
                   maxH = n.getHeuristic();
                   maxNV = n.getNumbericalVal();
               }
            }
            //System.out.println("temp: "+temp.getNumbericalVal());
            lastExpanded = temp;
            if(debug == true){
                System.out.println("Last Expanded: "+lastExpanded.getNumbericalVal());
            }
            if(lastExpanded.isGoal() == true){
                foundG = true;
            }
        }
        System.out.println("\nMy Path:");
        myPath.makePathFromChild(lastExpanded);
        myPath.printPath();
        System.out.println("\n");
        //System.out.println("\nMy Fringe:");
        //myFringe.printFringe();
    }

    public static void dfs(){
        boolean foundG = false; //Used as a check for the goal, if we havent found the goal we keep checking
        Path myPath = new Path();   //For giving us the path (Maybe want to return the path)
        Fringe myFringe = new Fringe(); //Gives us the fringe, not really used in DFS but still imporant to have (Can print unused nodes in this case as a check)
        Node currentNode = startN;  //Start node, dont want to change this so we assign it to current node
        myPath.add(currentNode);    //Add it to our path (root node)
        myFringe.addNode(currentNode);  //We want to add our first node to the fringe
        //Checks to make sure were not already at the goal node
        if(currentNode.isGoal() == true){
            foundG = true;  //If this happens we ignore while loop below (Basically only happens when we are given 4321)
        }

        while(foundG == false){ 
            Node temp = null;   //Temp node for checking
            for(int i = 0; i<3;i++){
                if((currentNode.getNext(i)!=null) && (temp == null || (temp.getNumbericalVal() < currentNode.getNext(i).getNumbericalVal()))){  //If our node is not null, and our numerical value is higher than the other node
                    temp = currentNode.getNext(i);
                }
                //Adds all of the children to the fringe
                if(currentNode.getNext(i)!=null){
                    myFringe.addNode(currentNode.getNext(i));
                    if(debug == true){
                        System.out.println("Adding "+currentNode.getNext(i).getNumbericalVal()+" to fringe");
                    }
                }
            }
            
            currentNode = temp;
            if(debug == true){
                System.out.println("Expanding "+currentNode.getNumbericalVal());
            }
            myPath.add(currentNode);
            if(currentNode.isGoal() == true){
                foundG = true;
            }
        }
        System.out.println("\nMy Path:");
        myPath.printPath();
        System.out.println("\n");
        //System.out.println("\nMy Fringe:");
        //myFringe.printFringe();
    }

    //Makes the graph and assigns the start node 
    public static void makeGraph(){
        startN = new Node(mG,null,0);
        //printGraph(startN);
    }

    //Helper function for printing the graph (Not nessesary)
    public static void printGraph(Node n1){
        System.out.print("Parent Node:");
        System.out.println(n1.getInfo());
        for(int i = 0; i < 3; i++){
            System.out.print("Child Node:");
            if(n1.getNext(i)!= null) {
                System.out.println(n1.getNext(i).getInfo());
                printGraph(n1.getNext(i));
            }
        }
    }
}
