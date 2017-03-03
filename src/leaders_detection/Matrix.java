package leaders_detection;

import java.util.ArrayList; 

public class Matrix {
    ArrayList<Integer> i;
    ArrayList<Integer> j;
    ArrayList<Double> value;
    
    public Matrix(){
        i=new ArrayList<>();
        j=new ArrayList<>();
        value=new ArrayList<>();
    }
    
    public void set(int i,int j,double value){
        this.i.add(i);
        this.j.add(j);
        this.value.add(value);
    }
    
    public double get(int i, int j){
        int indexOfValue=0;
        for(int k=0;k<this.getSize();++k){
            if(this.i.get(k)==i){
                 if(this.j.get(k)==j){
                     indexOfValue=k;
                }
            }
        }
        return this.value.get(indexOfValue);
    }
    
    public int getSize(){
        return value.size();
    }
    
    public void transpose(){
        Matrix trans = new Matrix();
        trans.i=this.i;
        trans.j=this.j;
        trans.value=this.value;
        this.i=trans.j;
        this.j=trans.i;
    }
}
