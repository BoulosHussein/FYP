package leaders_detection;

import static java.lang.Math.*;
import java.util.ArrayList; 

public class PageRank {
    
    Graph myGraph;
    Matrix matrixGraph;
    ArrayList<Double> rankVector;
    
    
    public PageRank(Graph myGraph){
        this.myGraph = myGraph; 
        
        matrixGraph = new Matrix();
        
        rankVector = new ArrayList<>();
        
        for(int i=0;i<myGraph.getSize();++i){
            for(int j=0;j<myGraph.getSize();++j){
                if(myGraph.g.get(i).contains(j)){
                    matrixGraph.set(i, j, 1.0/myGraph.deg(i));
                }
            }
        }
        
        for(int i=0;i<myGraph.getSize();++i){
            rankVector.add(i,1.0/myGraph.getSize());
        }
        
            matrixGraph.transpose();
    }
    
    public void displayMatrix(){
        ArrayList<Integer> iValues = new ArrayList<>();
        ArrayList<Integer> jValues = new ArrayList<>();
        System.out.println("Matrix :");
        for(int k=0;k<myGraph.getSize();++k){
            for(int l=0;l<myGraph.getSize();++l){
                if(matrixGraph.i.contains(k)){
                    for(int m=0;m<matrixGraph.getSize();++m){
                        if(matrixGraph.i.get(m)==k){
                            iValues.add(m);
                        }
                    }
                    for(int m=0;m<iValues.size();++m){
                            jValues.add(matrixGraph.j.get(iValues.get(m)));
                    }
                    if(jValues.contains(l)){
                        System.out.print(matrixGraph.get(k, l)+"   ");
                    }
                    else{
                      System.out.print(0+"   ");  
                    } 
                }
                else{
                    System.out.print(0+"   ");
                }
                jValues.clear();
            }
            System.out.println();
            iValues.clear();
        }
        System.out.println();
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
    
    public ArrayList<Double> product(ArrayList<Double> v1){
        ArrayList<Integer> iValues = new ArrayList<>();
        ArrayList<Integer> jValues = new ArrayList<>();
        ArrayList<Double> v= new ArrayList<>();
        for(int i=0;i<myGraph.getSize();++i){
            v.add(0.0);
        }
        for(int k=0;k<myGraph.getSize();++k){
            for(int l=0;l<myGraph.getSize();++l){
                if(matrixGraph.i.contains(k)){
                    for(int m=0;m<matrixGraph.getSize();++m){
                        if(matrixGraph.i.get(m)==k){
                            iValues.add(m);
                        }
                    }
                    for(int m=0;m<iValues.size();++m){
                            jValues.add(matrixGraph.j.get(iValues.get(m)));
                    }
                    if(jValues.contains(l)){
                        v.set(k,v.get(k)+v1.get(k)*matrixGraph.get(k,l));
                    }
                }
                jValues.clear();
            }
            iValues.clear();
        }
        return v;
    }
    
    public void index() throws Exception{
            ArrayList<Double> rankIterated = new ArrayList<>();
            ArrayList<Integer> zeroColumns = new ArrayList<>();
            for(int i=0;i<myGraph.getSize();++i){
                if(myGraph.g.get(i).isEmpty()){
                    zeroColumns.add(i);
                }
            }
            boolean iteration = true;
            double beta = 0.8;
            do{
                rankIterated = this.product(rankVector);
                
                for(int i=0;i<rankIterated.size();++i){
                    rankIterated.set(i, beta*rankIterated.get(i));
                }
                
                for(int i=0;i<zeroColumns.size();++i){
                    for(int j=0;j<rankIterated.size();++j){
                        rankIterated.set(j, rankIterated.get(j)+rankVector.get(zeroColumns.get(i))*1.0/myGraph.getSize());
                    }
                }
                
                for(int i=0;i<myGraph.getSize();++i){
                    rankIterated.set(i, ((1.0-beta)/myGraph.getSize())+rankIterated.get(i));
                }
                
                if(this.diffModule(rankIterated, rankVector)<0.05){
                    iteration = false;
                }
                for(int i =0;i<myGraph.getSize();++i){
                    rankVector.set(i, rankIterated.get(i));
                }
                rankIterated.clear();
            }while(iteration);
    }
    
    public void displayRank(){
        System.out.println("Rank :");
        double s=0;
        for(int i=0;i<rankVector.size();++i){
            System.out.print(rankVector.get(i)+" ");
        }
        System.out.println();
        for(int i=0;i<rankVector.size();++i){
            s+=rankVector.get(i);
        }
        System.out.println();
        System.out.println("Rank Vector sum :");
        System.out.println(s);
    }
}
