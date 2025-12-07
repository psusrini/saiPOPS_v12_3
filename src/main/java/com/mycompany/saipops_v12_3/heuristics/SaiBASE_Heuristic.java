/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.saipops_v12_3.heuristics;
      
import static com.mycompany.saipops_v12_3.Constants.*;
import static com.mycompany.saipops_v12_3.Parameters.PERF_VARIABILITY_RANDOM_GENERATOR;
import com.mycompany.saipops_v12_3.constraints.*;
import com.mycompany.saipops_v12_3.utils.MathUtils;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author sst119
 *  
 * 
 *  
 */
public abstract class SaiBASE_Heuristic {
    //
    protected  Set<Attributes> attributes;
    protected  TreeMap<String, Double>  objectiveFunctionMap;
    
    protected  TreeMap<String , Double>  neutralVariables_WithScore   = new  TreeMap<String , Double>   ();
    
    protected int lowestKnownSecondaryDimension = BILLION;
    //secondary variable and the primary variables it will fix if the lowestKnownSecondaryDimension is 1 
    protected  TreeMap<String , TreeSet<String>  >  secondaryVariables_AtLowestDim   = new  TreeMap<String , TreeSet<String>  >   ();
    protected  TreeSet<String > allFractionalSecondaryVariables = new TreeSet<String >();
    
    protected int lowestKnownPrimaryDimension = BILLION;
    protected  TreeMap<String , Double>  primaryVariablesWithFrequency_AtLowestDim   = new  TreeMap<String , Double>   ();
    protected int lowestKnownPrimarySize = BILLION;
    protected  TreeMap<String , Double> primaryvariablesWithFrequency_AtLowestSize = new TreeMap<String , Double> ();
     
    protected double largestKnownVice = DOUBLE_ZERO;
    protected  TreeSet<String > fractionalPrimaryVariables_LargerThanVice = new TreeSet<String >();
    
    public SaiBASE_Heuristic (  Set<Attributes> attributes ,     
            TreeMap<String, Double>  objectiveFunctionMap  ){
         
        this. objectiveFunctionMap = objectiveFunctionMap;
        this.attributes = attributes;    
        
        // populate the maps  
        for (Attributes attr: attributes){
            
            if (attr.hasFractionalNeutralVariables()){
                double score = Math.pow( TWO, TWO - attr.constraintSize);
                for (String neutralVar : attr.fractionalNeutralVariables){
                    //
                    Double currentScore =  neutralVariables_WithScore.get (neutralVar)  ;
                    if (null == currentScore)currentScore= DOUBLE_ZERO;
                    neutralVariables_WithScore.put (neutralVar,score+ currentScore)  ;
                }
            }
            
            if (attr.hasFractionalSecondaryVariables()){ 
                
                allFractionalSecondaryVariables.addAll( attr.fractionalSecondaryVariables);
                
                if (lowestKnownSecondaryDimension > attr.secondaryDimension){
                    lowestKnownSecondaryDimension = attr.secondaryDimension;
                    secondaryVariables_AtLowestDim.clear();                   
                } 
                if (lowestKnownSecondaryDimension == attr.secondaryDimension){
                    //for every fractional secondary var, append the list of primary variables in this constraint
                    for (String sVar : attr.fractionalSecondaryVariables){
                        TreeSet<String> current = secondaryVariables_AtLowestDim.get (sVar) ;
                        if (current ==null)   current=     new TreeSet<String> ();
                        current.addAll( attr.allPrimaryVariables);
                        secondaryVariables_AtLowestDim.put (sVar, current) ;
                    }
                }
            }
            
                             
            if (attr.hasFractionalPrimaryVariables()){
                if (lowestKnownPrimaryDimension > attr.primaryDimension){
                    
                    lowestKnownPrimaryDimension = attr.primaryDimension;                   
                    this.primaryVariablesWithFrequency_AtLowestDim.clear();  
                    
                    fractionalPrimaryVariables_LargerThanVice.clear();
                    largestKnownVice  = DOUBLE_ZERO;
                    
                }
                if (lowestKnownPrimaryDimension == attr.primaryDimension){
                    
                    final double VICE = getVice (attr);
                                        
                    if (largestKnownVice < VICE)     {
                        largestKnownVice = VICE;
                        fractionalPrimaryVariables_LargerThanVice.clear();
                    }
                    
                    for (String var: attr.fractionalPrimaryVariables ){
                        
                        Double currentFreq= primaryVariablesWithFrequency_AtLowestDim .get ( var);
                        if (null==currentFreq)currentFreq=DOUBLE_ZERO;
                        primaryVariablesWithFrequency_AtLowestDim .put ( var, currentFreq + DOUBLE_ONE);
                        
                        if (largestKnownVice == VICE)   {                                                        
                            if (VICE <= Math.abs (this.objectiveFunctionMap.get(var))) fractionalPrimaryVariables_LargerThanVice.add (var ); 
                        }
                    }  
                                        
                }   
                
                if (attr.constraintSize < lowestKnownPrimarySize){
                    primaryvariablesWithFrequency_AtLowestSize.clear();
                    lowestKnownPrimarySize=attr.constraintSize;
                }
                if (attr.constraintSize == lowestKnownPrimarySize){
                    for (String var: attr.fractionalPrimaryVariables){
                        Double currentFreq=  primaryvariablesWithFrequency_AtLowestSize .get ( var);
                        if (null==currentFreq)currentFreq=DOUBLE_ZERO;
                        primaryvariablesWithFrequency_AtLowestSize  .put ( var, currentFreq + DOUBLE_ONE); 
                    }                     
                }
                
            }
                      
        }//for all attrs
          
    }//end constructor method
    
    public String getBranchingVariable() {
        TreeSet<String>  candidates  ;
        
        if ( primaryVariablesWithFrequency_AtLowestDim.isEmpty()){            
            //Only neutral vars (no primary). Use MOM_S on neutral vars         
            candidates = getMOMS ( ) ;
        } else  candidates =   selectBranchingVariable()  ;
        
        //random tiebreak        
        String[] candidateArray = candidates.toArray(new String[ZERO]);        
        return candidateArray[ PERF_VARIABILITY_RANDOM_GENERATOR.nextInt(candidates.size())];
    }
    
    protected abstract TreeSet<String>  selectBranchingVariable  ();
    
    protected TreeSet<String>  getPrimaryOnlyVariables  (){
        TreeSet<String>  winners = new  TreeSet<String> ();
        
        winners.addAll( this.primaryVariablesWithFrequency_AtLowestDim.keySet());
        winners.removeAll( this.allFractionalSecondaryVariables);
        
        return winners;        
    }
    
    // 
    private TreeSet<String>  getMOMS  (){
        TreeSet<String>  candidates   = new TreeSet<String> ();
        candidates.addAll( neutralVariables_WithScore.keySet());
        return     MathUtils.findMaxValue (candidates, neutralVariables_WithScore  ) ;        
    }
    
    private double getVice (Attributes attr) {
        double vice = DOUBLE_ZERO;
        if (!attr.vice_ObjMagn_primaryVariable.isEmpty()){
            vice = attr.vice_ObjMagn_primaryVariable.firstEntry().getValue();
        }
        return vice;
    }
}
