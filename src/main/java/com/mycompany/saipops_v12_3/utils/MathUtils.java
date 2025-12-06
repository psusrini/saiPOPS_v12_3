/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.saipops_v12_3.utils;
  
import static com.mycompany.saipops_v12_3.Constants.*;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author sst119
 */
public class MathUtils {
    
    public static double objMagnSum (TreeSet<String> variables , TreeMap<String, Double>  objectiveFunctionMap) {
        double sum = DOUBLE_ZERO;
        
        for (String var: variables ){
            sum += Math.abs ( objectiveFunctionMap.get( var));
        }
        
        return sum;
    }
   
    
    public static    TreeSet<String>  getMaxObjMagn (TreeSet<String> candidates ,  TreeMap<String, Double>  objectiveFunctionMap){
        TreeSet<String >  winners = new  TreeSet<String>();
        double bestKnownObjMagn = -ONE;
        
        for (String var : candidates ){
            Double objval =   objectiveFunctionMap.get( var);
            
            double thisObjMagn = Math.abs ( objval);
            if (thisObjMagn > bestKnownObjMagn){
                bestKnownObjMagn = thisObjMagn;
                winners.clear();
            }
            if (bestKnownObjMagn ==thisObjMagn){
                winners.add (var );
            }
        }
        
        return winners ;
    }
    
    public static TreeSet<String>  findMaxValue (TreeSet<String>  candidates, TreeMap<String, Double> variables_WithScore  ) {
        TreeSet<String >  winners = new  TreeSet<String>();
                
        double LARGEST_KNOWN = -ONE;
        
        for (String cand :  candidates){
            Double thisScore  = variables_WithScore.get (cand);
            if (null==thisScore)thisScore=DOUBLE_ZERO-TWO;
            if (LARGEST_KNOWN < thisScore){
                LARGEST_KNOWN=thisScore;
                winners.clear();
                winners.add (cand);
            }else if (LARGEST_KNOWN == thisScore){
                winners.add (cand);
            }
        }
         
        return winners; 
    } 
    
    public static   TreeSet<String> getMaxiMinFrequency(TreeSet<String>  candidates,  TreeMap<String, Double>  mapOne,  TreeMap<String, Double>  mapTwo ) {
        TreeSet<String >  winners = new  TreeSet<String>();
        
        double smallestKnownFreq = DOUBLE_ZERO; 
        double largestKnownFreq = DOUBLE_ZERO;
        
        //find var whose smaller freq is as large as possible
        
        
        for (String candidate:  candidates){
            Double pFreq = mapOne.get (candidate );
            if (null == pFreq) pFreq = DOUBLE_ZERO;
            Double nFreq = mapTwo.get (candidate );
            if (null == nFreq) nFreq = DOUBLE_ZERO;
            
            pFreq= Math.abs (pFreq );
            nFreq= Math.abs (nFreq);
            
            double smaller = Math.min (pFreq,  nFreq);
            double larger = Math.max (pFreq,  nFreq);
            
            if ( (smaller >smallestKnownFreq ) || (smaller == smallestKnownFreq && largestKnownFreq<larger  )) {
                winners.clear();
                smallestKnownFreq =smaller;
                largestKnownFreq= larger;
                winners.add (candidate );
            }  else if (smaller == smallestKnownFreq && largestKnownFreq==larger  ) {
                winners.add (candidate );
            }
              
        }
               
        return winners;
    }   
    
    
    public static   TreeSet<String> miniMax(TreeSet<String>  candidates,  TreeMap<String, Double>  mapOne,  TreeMap<String, Double>  mapTwo ) {
        TreeSet<String >  winners = new  TreeSet<String>();
        
        double CURRENT_SMALL  = DOUBLE_ZERO + BILLION; 
        double CURRENT_LARGE = DOUBLE_ZERO+ BILLION; 
        
        //find var whose largest freq is as small as possible
        
        
        for (String candidate:  candidates){
            Double pFreq = mapOne.get (candidate );
            
            Double nFreq = mapTwo.get (candidate );
              
            
            double smaller = Math.min (pFreq,  nFreq);
            double larger = Math.max (pFreq,  nFreq);
            
            if (larger < CURRENT_LARGE || (larger == CURRENT_LARGE  && smaller < CURRENT_SMALL )){
                CURRENT_LARGE = larger;
                CURRENT_SMALL = smaller;
                winners.clear();
                winners.add(candidate);
            }else if (larger ==  CURRENT_LARGE && smaller == CURRENT_SMALL){
                 winners.add(candidate);
            }
             
              
        }
               
        return winners;
    }   
    
}
