package bionet;

import java.util.Random;

public class Organism implements Comparable<Organism>{
	private int numChromasomes = 0;
	private double[] genome;
	
	public Organism(int numChromasomes)
	{
		this.numChromasomes = numChromasomes;
		this.genome = new double[numChromasomes];
	}
	
	public void setGenome(double[] genome)
	{
		System.arraycopy(genome, 0, this.genome, 0, genome.length);
	}
	
	public double[] getGenome()
	{
		return this.genome;
	}
	
	public double scoreSurvival()
	{
		boolean isNegative = false;
		double sum = 0;
		for(int i = 0; i<genome.length; i++)
		{
			if(isNegative)
			{
				sum-=genome[i];
				isNegative = false;
			}
			else
			{
				sum+=genome[i];
				isNegative = true;
			}
		}
		return sum;
	}
	
	public int compareTo(Organism o)
	{
		double a = o.scoreSurvival();
		double b = this.scoreSurvival();
		
		if(a>b)
			return 1;
		else if(a<b)
			return -1;
		else
			return 0;
	}
	
	public void mutate(double mutationRate)
	{
		for(int i = 0; i<genome.length; i++)
		{
			if(new Random(System.nanoTime()).nextDouble()>=mutationRate)
				genome[i] = new Random(System.nanoTime()).nextDouble();
		}
	}
	
}
