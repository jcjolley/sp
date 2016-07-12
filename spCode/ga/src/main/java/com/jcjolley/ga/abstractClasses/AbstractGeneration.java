package com.jcjolley.ga.abstractClasses;

import com.jcjolley.ga.interfaces.Generation;
import com.jcjolley.ga.interfaces.Individual;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;
/**	
 * Take care of the stuff that will almost always be the same for a Generation
 * @author josh
 */
public abstract class AbstractGeneration implements Generation, Comparable<Generation> {

	protected List<Individual> individuals;
	protected static int staticCount;
	protected int count;

	@Override
	public List<Individual> getIndividuals() {
		return individuals;
	}

	@Override
	public void setIndividuals(List<Individual> individuals) {
		this.individuals = individuals;
	}

	@Override
	public int getMaxFitness(){
		return individuals.get(0).getFitness();
	}

	@Override
	public double getAverageFitness() {
		return individuals.stream().collect(Collectors.averagingDouble(Individual::getFitness));
	}

	@Override
	public List<Individual> select() {
		return individuals.stream().limit(individuals.size() / 3).collect(toList());
	}

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##0.00" );
		return "Generation " + count + ":\n\tMax Fitness = " + getMaxFitness() + 
			   "\n\tAverage Fitness = " + new Double(df2.format(getAverageFitness())) +
			   "\nGenSize: " + individuals.size() + "\n" + individuals.stream().map(Individual::getFitness).collect(toList()) + "\n\n";
	}

	@Override
	public int compareTo(Generation o) {
		return Integer.compare(count, o.getCount());	
	}

	public static void resetStaticCount()
	{
		staticCount = 0;
	}
}
