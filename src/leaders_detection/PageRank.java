package leaders_detection;

import static java.lang.Math.*;
import java.util.ArrayList; 

public class PageRank {
    
    Graph myGraph;
    Matrix matrixGraph;
    ArrayList<Double> rankVector;
    
    
    public PageRank(Graph myGraph){
        this.myGraph = myGraph; 
        
        matrixGraph = new Matrix(myGraph.getSize());
        
        rankVector = new ArrayList<>();
        
        for(int i=0;i<myGraph.getSize();++i){
            for(int j=0;j<myGraph.getSize();++j){
                if(myGraph.g.get(i).contains(j)){
                    matrixGraph.set(i, j, 1.0/myGraph.deg(i));
                }
            }
            rankVector.add(i,1.0/myGraph.getSize());
        }
    }
   
    public double diffModule(ArrayList<Double> v1,ArrayList<Double> v2) throws Exception{
        if(v1.size()==v2.size()){
            double moduleV1 =0 ,moduleV2 =0, diff;
            for(int i=0;i<v1.size();++i){
                moduleV1+=v1.get(i)*v1.get(i);
                moduleV2+=v2.get(i)*v2.get(i);
            }
            diff = sqrt(moduleV1)-sqrt(moduleV2);
            return diff;
        }
        else{
            throw new Exception("Vector Size");
        }
    }
    
    public void index() throws Exception{
            ArrayList<Double> rankIterated = new ArrayList<>();

            boolean iteration = true;
            
            do{
                rankIterated = matrixGraph.rankProduct(rankVector);
                
                if(this.diffModule(rankIterated, rankVector)<0.01){
                    iteration = false;
                }
                for(int i =0;i<myGraph.getSize();++i){
                    rankVector.set(i, rankIterated.get(i));
                }
                        
                rankIterated.clear();
            }while(iteration);
    }
    
    public int displayRank(){
        System.out.println("Rank :");
        double s=0;
        double max =-1;
        int indice =0;
        for(int i=0;i<rankVector.size();++i){
            if(max<rankVector.get(i)){
                indice = i;
            }
            s+=rankVector.get(i);
            System.out.print(rankVector.get(i)+" ");
        }
        System.out.println();
        System.out.println("Rank Vector sum :");
        System.out.println(s);
        
        
        //printing max
        System.out.println("Our leaders is "+max);
        return indice;
    }
}
