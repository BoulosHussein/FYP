package leaders_detection;

public class Leaders_Detection {

    
    public static void main(String[] args) throws Exception {
        Graph g = new Graph(5);
        //g.connect(0,1);
        g.connect(0,2);
        g.connect(0,3);
        g.connect(1,0);
        g.connect(1,3);
        g.connect(1,4);
        g.connect(2,0);
        g.connect(2,4);
        g.connect(2,1);
        g.connect(3,1);
        g.connect(3,0);
        g.connect(3,4);
        g .connect(4,0);
        g.connect(4,2);
        PageRank rank = new PageRank(g);
        rank.displayMatrix();
        rank.index();
        rank.displayRank();
    }
    
}
