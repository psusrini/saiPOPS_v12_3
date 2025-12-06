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
public   class SaiTED_LOW_Heuristic  extends SaiBASE_Heuristic{
    //
       
    public SaiTED_LOW_Heuristic (  Set<Attributes> attributes ,     
            TreeMap<String, Double>  objectiveFunctionMap  ){
          
        super(attributes ,   objectiveFunctionMap) ;
          
    }//end constructor method
    
    
    //Emphasize large number of 0 fixes in the down branch.
    //Tie break on the largest objective magnitude
    @Override
    protected TreeSet<String>  selectBranchingVariable  (){
         return null;
    }    
     
}







































