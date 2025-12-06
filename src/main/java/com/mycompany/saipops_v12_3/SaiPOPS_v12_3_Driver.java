/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.saipops_v12_3;

import static com.mycompany.saipops_v12_3.Constants.ONE;
import static com.mycompany.saipops_v12_3.Parameters.printParameters;
import com.mycompany.saipops_v12_3.utils.Solver;
import ilog.cplex.IloCplex;
import static java.lang.System.exit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author sst119
 */
public class SaiPOPS_v12_3_Driver {

    private static final Logger logger ;
    private static  IloCplex cplex;
    
    static {
        logger= Logger.getLogger(SaiPOPS_v12_3_Driver.class.getSimpleName() );
        //logger.setLevel(Level.INFO);
        try {
            FileHandler fileHandler = new FileHandler(SaiPOPS_v12_3_Driver.class.getSimpleName()+ ".log");

            fileHandler.setFormatter(new SimpleFormatter());

            logger.addHandler(fileHandler);

            logger.info("Logging initialized.");
            

        } catch (Exception e) {
            System.err.println(e.getMessage()) ;
            exit(ONE);
        }
        
    }

    public static void main(String[] args) throws Exception {
        
        printParameters();
        
        Solver solver = new Solver ( ) ;
        
        solver.solve ( );
        logger.info("Test Completed Successfully!");
        
              
        
    }
}
