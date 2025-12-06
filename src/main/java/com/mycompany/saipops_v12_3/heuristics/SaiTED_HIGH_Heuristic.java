/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.saipops_v12_3.heuristics;
    
 
import static com.mycompany.saipops_v12_3.Constants.*;
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
public   class SaiTED_HIGH_Heuristic  extends SaiBASE_Heuristic{
    //
       
    public SaiTED_HIGH_Heuristic (  Set<Attributes> attributes ,     
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
        
        TreeMap<String, Double> modifiedObjFuncMap = getModifiedObjFuncMap (winners);
        
        //highest obj magn
        winners = MathUtils.getMaxObjMagn(winners, modifiedObjFuncMap.isEmpty()? this.objectiveFunctionMap:  modifiedObjFuncMap);
        
        //tie break on highest frequency
        winners = MathUtils.getMaxiMinFrequency (winners, this.primaryVariablesWithFrequency_AtLowestDim, new  TreeMap<String, Double>  ())  ;  
        
        return winners;
    }    
    
    private TreeMap<String, Double>  getModifiedObjFuncMap ( TreeSet<String> winners){
        TreeMap<String, Double> modifiedObjFuncMap = new TreeMap<String, Double> () ;
                
        TreeSet<String>  temp = new  TreeSet<String> ();
        temp.addAll(this.secondaryVariables_AtLowestDim.keySet() );
        temp.retainAll( winners);
        if (!temp.isEmpty()  && this.lowestKnownSecondaryDimension ==ONE){
                  
            for (String var: winners ){
                modifiedObjFuncMap.put (var, Math.abs ( this.objectiveFunctionMap.get(var))) ;
            }
        
            getModifiedObjFuncMap ( temp, modifiedObjFuncMap );
        }
        
        return     modifiedObjFuncMap;    
    }
    
    private void  getModifiedObjFuncMap ( TreeSet<String> winners, TreeMap<String, Double> modifiedObjFuncMap){
        
        TreeSet<String> dominatedvariables = new TreeSet<String>   ();
         
        for (;;){
            int dominatedCount_Initial = dominatedvariables.size();
            for (String key : secondaryVariables_AtLowestDim.keySet()){
                 
                if (dominatedvariables.contains(key))  continue;
                //
                TreeSet<String> currentImplications  = secondaryVariables_AtLowestDim.get(key);
                TreeSet<String> currentImplicationsCopy = new  TreeSet<String>  ();
                currentImplicationsCopy.addAll(currentImplications);
                for (String impl: currentImplications){
                    TreeSet<String> implicationssToAdd =   secondaryVariables_AtLowestDim.get(impl);
                    if (implicationssToAdd !=null) {
                        currentImplicationsCopy.addAll( implicationssToAdd);
                        if (winners.contains(key)){
                            dominatedvariables.add(impl);
                        }
                    }                    
                }        
                secondaryVariables_AtLowestDim.put(key,currentImplicationsCopy );                
            } 
            int dominatedCount_Final = dominatedvariables.size(); 
            if (dominatedCount_Final ==dominatedCount_Initial) break;
        }
        
        for ( String dom:  dominatedvariables){
            modifiedObjFuncMap.put (dom, DOUBLE_ZERO );
        }
        for (String winner: winners){
            if (dominatedvariables.contains(winner))continue;
            double current = modifiedObjFuncMap.get (winner );
            modifiedObjFuncMap.put (winner, current + MathUtils.objMagnSum(secondaryVariables_AtLowestDim.get(winner) , objectiveFunctionMap) );
        }             
    }
   
}







































