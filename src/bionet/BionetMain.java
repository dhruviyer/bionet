package bionet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class BionetMain {
	private static int numChromasomes;
	private static int population;
	private static double mutationRate;
	private static ArrayList<Organism> organisms;

	public static void main(String[] args) throws IOException
	{
		System.out.print("Number of Chromasomes:  ");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		numChromasomes = Integer.parseInt(reader.readLine());
		if(numChromasomes==0)
		{
			System.err.println("Must be a polynomial! Exiting...");
			System.exit(-1);
		}
		
		System.out.print("Initial Population:  ");
		population = Integer.parseInt(reader.readLine());
		if(population%2==1)
		{
			population+=1;
			System.out.println("Initial population must be even! Rounding up to "+population);
		}
		
		boolean cont = false;
		
		while(cont == false)
		{
			System.out.print("Mutation Rate:  ");
			mutationRate = 0.01*Integer.parseInt(reader.readLine());
			System.out.print(mutationRate*100+"% ? (Y/N): ");
			
			String ans = reader.readLine().toLowerCase();
			if(ans.equals("y"))
				cont = true;
			else
				cont = false;
		}
		
		organisms = new ArrayList<Organism>();
		for(int i = 0; i<population; i++)
		{
			organisms.add(new Organism(numChromasomes));
		}
		
		initOrganismGenome();
		
		while(organisms.size()>2)
		{
			mutate();
			
			killOff();
			
			if(organisms.size()%2==1)
				addOrganism();
		}

		
		printSurvival();
		
		
	}
	
	public static void initOrganismGenome()
	{
		for(Organism organism: organisms)
		{
			double[] tempGenome = new double[numChromasomes];
			for(int i = 0; i<numChromasomes; i++)
			{
				tempGenome[i] = new Random(System.nanoTime()).nextDouble();
			}
			organism.setGenome(tempGenome);
		}
	}
	
	public static void mutate()
	{
		for(Organism organism:organisms)
		{
			organism.mutate(mutationRate);
		}
	}
	
	public static void sequenceGenomes()
	{
		for(int i = -1; i<numChromasomes; i++)
		{
			String str1 = "";
			if(i == -1)
			{
				for(int j = 0; j<population; j++)
				{
					str1+="O_"+(j+1)+"\t";
				}
			}else
			{
				for(int j = 0; j<population; j++)
				{
					str1+=organisms.get(j).getGenome()[i]+"\t";
				}
			}
			System.out.println(str1);
		}
	}
	
	public static void printSurvival()
	{
		int counter = 1;
		for(Organism organism:organisms)
		{
			System.out.println("O_"+counter+": "+organism.scoreSurvival());
			counter++;
		}
	}
	
	public static void addOrganism()
	{
		organisms.add(new Organism(numChromasomes));
		double[] tempGenome = new double[numChromasomes];
		for(int i = 0; i<numChromasomes; i++)
		{
			tempGenome[i] = new Random(System.nanoTime()).nextDouble();
		}
		organisms.get(organisms.size()-1).setGenome(tempGenome);
	}

	public static void killOff()
	{
		
		for(int i = 0; i<organisms.size();)
		{
			if(organisms.get(i).scoreSurvival()<=0)
				organisms.remove(i);
			else
				i++;
		}
	}
}
