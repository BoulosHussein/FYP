package leaders_detection;

import java.util.ArrayList; 
import java.util.HashMap;

public class Graph {
    int _size; 
    ArrayList<ArrayList<Integer>> g;
    HashMap<Integer , ArrayList<Integer>> map;
    
    public Graph(int i){
        
        g = new ArrayList<>();
        this._size = i;
        for(int j=0;j<_size;++j){
            g.add(new ArrayList<>());
        }
        map = new HashMap<>();
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
    
    public void attribution(HashMap<Long,ArrayList<Long>> extracted , HashMap<Long,Integer> attributed){
        for(Long key : extracted.keySet()){
            ArrayList<Long> temp = new ArrayList<>();
            temp = extracted.get(key);
            ArrayList<Integer> followers = new ArrayList<>();
            for(int l=0;l<extracted.get(key).size();++l){
                Long a = temp.get(l);
                followers.add(attributed.get(extracted.get(key).get(l)));
            }
            map.put(attributed.get(key), followers);
        }
        for(Integer key : map.keySet()){
            for(int i=0;i<map.get(key).size();++i){
                this.connect(map.get(key).get(i),key);
            }
        }
    }    
}
