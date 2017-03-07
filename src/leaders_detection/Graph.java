package leaders_detection;

import java.util.ArrayList; 
import java.util.HashMap;

public class Graph {
    int _size; 
    ArrayList<ArrayList<Integer>> g;
    
    public Graph(int i){
        g = new ArrayList<>();
        this._size = i;
        for(int j=0;j<_size;++j){
            g.add(new ArrayList<>());
        }
    }
    
    public void connect(int i, int j){
        g.get(i).add(j);
    }
    
    public int getSize(){
        return _size;
    }
    
    public int deg(int node){
        int degree;
        degree = g.get(node).size();
        return degree;
    } 
    
    /*public void integration(HashMap<Long , ArrayList<Long>> map){
        for(int i=0;i<map.size();++i){
            for(int j=0;j<map.values().size();++j){
            
            }
        }
    }*/
    
}
