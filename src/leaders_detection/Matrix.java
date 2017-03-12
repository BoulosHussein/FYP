package leaders_detection;

import java.util.ArrayList; 

public class Matrix {
    int size;
    int sizeValue;
    ArrayList<Integer> i;
    ArrayList<Integer> j;
    ArrayList<Double> value;
    ArrayList<Boolean> isEmpty;
    
    public Matrix(int size){
        i=new ArrayList<>();
        j=new ArrayList<>();
        value=new ArrayList<>();
        this.size = size;
        sizeValue =0;
        isEmpty = new ArrayList<>();
        for(int k=0;k<size;++k){
            isEmpty.add(true);
        }
    }
    
    public void set(int i,int j,double value){
        this.i.add(i);
        this.j.add(j);
        this.value.add(value);
        this.isEmpty.set(i, false);
        sizeValue ++;
    }
    
    public int getSizeValue(){
        return value.size();
    }
    
       public int getSize(){
        return size;
    }
 
    public ArrayList<Double> rankProduct (ArrayList<Double> v){
        ArrayList<Double> v1 = new ArrayList<>();
        double beta = 0.8;
        int max;
        if(sizeValue>size){
            max = sizeValue;
        }
        else{
            max= size;
        }
        for(int k=0;k<size;++k){
            v1.add(0.0);
        }
        for(int k=0;k<max;++k){
            if(k<sizeValue){
                v1.set(j.get(k),v1.get(j.get(k))+v.get(i.get(k))*value.get(k)*beta);
            }
            if(k<size){
                if(isEmpty.get(k)==true){
                    for(int l=0;l<size;++l){
                        v1.set(l, (v1.get(l)+ beta*v.get(k)*1.0/size));
                    }
                }
                v1.set(k, ((1.0-beta)/size)+v1.get(k));
            }       
        }
        return v1;
    }
}
