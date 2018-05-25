/*
 * Title: CPS 5121 Assignment - Search-Based Test Data Generation
 * Author: Karl Farrugia 59796M
 */

package runner;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.uom.cps5121.SearchAlgorithmDomainSpecificOptimisedStrategy;
import com.uom.cps5121.SearchAlgorithmDomainSpecificStrategy;
import com.uom.cps5121.SearchAlgorithmHillClimbingRandomRestart;
import com.uom.cps5121.SearchAlgorithmRandomStrategy;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import static com.uom.cps5121.SearchAlgorithmAbstract.WRITER;
import static com.uom.cps5121.SearchAlgorithmAbstract.WRITER2;

/**
 * Runner is responsible for executing the three implemented search based algorithms
 * each execution is repeated 5 times to assure that the obtained results are not
 * anomalous results.
 *
 * @author Karl Farrugia
 * @see com.uom.cps5121.SearchAlgorithm
 * @see com.uom.cps5121.SearchAlgorithmRandomStrategy
 * @see com.uom.cps5121.SearchAlgorithmHillClimbingRandomRestart
 * @see com.uom.cps5121.SearchAlgorithmDomainSpecificStrategy
 * @see com.uom.cps5121.SearchAlgorithmDomainSpecificOptimisedStrategy
 */

public class runner {

    /**
     * The amount of iterations each test should be carried out
     */
    public static int TEST_NUMBER = 3;

    /**
     * Runs each {@link com.uom.cps5121.SearchAlgorithm} algorithm 5 times. Before each algorithm execution a new
     * PrintWriter to record the number of requests vs the current algorithm fitness to file is created. The
     * PrintWriter is then closed after the execution finished. The algorithms are run in the following order:
     * {@link  com.uom.cps5121.SearchAlgorithmRandomStrategy},
     * {@link  com.uom.cps5121.SearchAlgorithmHillClimbingRandomRestart} and
     * {@link  com.uom.cps5121.SearchAlgorithmDomainSpecificStrategy} and
     * {@link  com.uom.cps5121.SearchAlgorithmDomainSpecificOptimisedStrategy}.
     *
     * @throws UnirestException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public runner() throws UnirestException, FileNotFoundException, UnsupportedEncodingException {
        for (int i = 0; i < TEST_NUMBER; i++) {

            /**
             * The random strategy approach
             */
            WRITER = new PrintWriter("SearchAlgorithmRandomStrategy" + (i + 1) + ".csv", "UTF-8");
            WRITER2 = new PrintWriter("SearchAlgorithmRandomStrategyResults" + (i + 1) + ".txt", "UTF-8");
            SearchAlgorithmRandomStrategy randomSearch = new SearchAlgorithmRandomStrategy();
            randomSearch.randomRunner();
            WRITER.close();
            WRITER2.close();

            /**
             * The hill climb strategy approach
             */
            WRITER = new PrintWriter("SearchAlgorithmHillClimbingRandomRestart" + (i + 1) + ".csv", "UTF-8");
            WRITER2 = new PrintWriter("SearchAlgorithmHillClimbingRandomRestartResults" + (i + 1) + ".txt", "UTF-8");
            SearchAlgorithmHillClimbingRandomRestart hillClimbingRandomSearch = new SearchAlgorithmHillClimbingRandomRestart();
            hillClimbingRandomSearch.randomRunner();
            WRITER.close();
            WRITER2.close();

            /**
             * The domain specific strategy approach 1
             */
            WRITER = new PrintWriter("SearchAlgorithmDomainSpecificStrategy" + (i + 1) + ".csv", "UTF-8");
            WRITER2 = new PrintWriter("SearchAlgorithmDomainSpecificStrategyResults" + (i + 1) + ".txt", "UTF-8");
            SearchAlgorithmDomainSpecificStrategy domainSpecificSearch = new SearchAlgorithmDomainSpecificStrategy();
            domainSpecificSearch.randomRunner();
            WRITER.close();
            WRITER2.close();

            /**
             * The domain specific strategy approach 2
             */
            WRITER = new PrintWriter("SearchAlgorithmDomainSpecificOptimisedStrategy" + (i + 1) + ".csv", "UTF-8");
            WRITER2 = new PrintWriter("SearchAlgorithmDomainSpecificOptimisedStrategyResults" + (i + 1) + ".txt", "UTF-8");
            SearchAlgorithmDomainSpecificOptimisedStrategy domainSpecificOptimisedSearch = new SearchAlgorithmDomainSpecificOptimisedStrategy();
            domainSpecificOptimisedSearch.randomRunner();
            WRITER.close();
            WRITER2.close();
        }
    }

    /**
     * Starts the execution.
     *
     * @param args
     * @throws UnirestException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws UnirestException, FileNotFoundException, UnsupportedEncodingException {
        new runner();
    }
}
