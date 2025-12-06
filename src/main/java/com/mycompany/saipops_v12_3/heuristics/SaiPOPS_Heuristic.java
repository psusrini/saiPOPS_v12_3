/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.saipops_v12_3.heuristics;
    
 
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
public   class SaiPOPS_Heuristic  extends SaiBASE_Heuristic{
    //
       
    public SaiPOPS_Heuristic (  Set<Attributes> attributes ,     
            TreeMap<String, Double>  objectiveFunctionMap  ){
          
        super(attributes ,   objectiveFunctionMap) ;
          
    }//end constructor method
    
    @Override
    protected TreeSet<String>  selectBranchingVariable  (){
        TreeSet<String>  winners = new  TreeSet<String> ();
   
        winners.addAll( getPrimaryOnlyVariables());
        
        if (winners.isEmpty()){
            winners.addAll(this.primaryVariablesWithFrequency_AtLowestDim.keySet() );
        }
        
        //highest obj magn
        winners = MathUtils.getMaxObjMagn(winners, objectiveFunctionMap );
        
        //tie break on highest frequency
        winners = MathUtils.getMaxiMinFrequency (winners, this.primaryVariablesWithFrequency_AtLowestDim, new  TreeMap<String, Double>  ())  ;  
        
        return winners;
    }    
   
}
