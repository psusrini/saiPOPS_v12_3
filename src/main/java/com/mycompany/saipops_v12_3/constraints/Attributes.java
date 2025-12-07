/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.saipops_v12_3.constraints;
     
import static com.mycompany.saipops_v12_3.Constants.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author sst119
 */
public class Attributes {
    
    public String constraintName ;
    
    public int primaryDimension = BILLION; 
    public TreeSet<String   >  fractionalPrimaryVariables    = new TreeSet<String   > ();
    public TreeSet<String   >  allPrimaryVariables    = new TreeSet<String   > ();
    
    public TreeMap<String, Double> highest_ObjMagn_primaryVariable = new TreeMap<String, Double> () ;
    public TreeMap<String, Double> vice_ObjMagn_primaryVariable    = new TreeMap<String, Double> () ;
    
    public int secondaryDimension = BILLION; 
    public TreeSet<String   >  fractionalSecondaryVariables    = new TreeSet<String   > ();
    
    
    public int constraintSize = -ONE;
    public TreeSet<String>  fractionalNeutralVariables = new TreeSet<String>(); 
 
    public boolean hasFractionalVariables () {
        return fractionalPrimaryVariables .size() +  fractionalSecondaryVariables.size()  + 
                fractionalNeutralVariables .size()   > ZERO;
    } 
    
    public boolean hasFractionalPrimaryVariables () {
        return fractionalPrimaryVariables .size()   > ZERO;
    }
    
    public boolean hasFractionalSecondaryVariables () {
        return this.fractionalSecondaryVariables.size()   > ZERO;
    }
   
    public boolean hasFractionalNeutralVariables () {
        return fractionalNeutralVariables.size()  > ZERO;
    }
    
}
