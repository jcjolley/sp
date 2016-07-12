package com.jcjolley.rest;

import com.jcjolley.ga.interfaces.Generation;
import com.jcjolley.ga.interfaces.Individual;
import com.jcjolley.ga.interfaces.Population;
import com.jcjolley.ga.maze.solver.MazeGeneration;
import com.jcjolley.ga.maze.solver.MazeIndividual;
import com.jcjolley.ga.maze.solver.MazePopulation;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.jcjolley.maze.Maze;
import com.jcjolley.maze.Pos;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.toList;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("sp")
public class MazeResources{

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "Got it!";
    }

	 @Path("maze")
	 @GET
	 @Produces(MediaType.TEXT_PLAIN)
	 public String getMaze() {
		System.out.println("Starting maze generation");
		Maze m = Maze.create(30);
		System.out.println("Maze generation complete");
		String output = m.toString();

		output += "\n Shortest path from entrance to exit is: " + m.getShortestPath(m.getEntrance(), m.getExit()).size();
		return output; 
	 }

	 @Path("htmlMaze")
	 @GET
	 @Produces(MediaType.TEXT_HTML)
	 public String getHtmlMaze(){
		int mazeSize = 30;
		//make a maze
		Maze m = Maze.create(mazeSize);
		System.out.println("Maze generation complete");
		String mString = m.toString();

		//Create a population
		Population p = MazePopulation.create(m, 50);
		System.out.println("The maze is:\n" + m.toString());
	
		int mazeShortestPath = m.getShortestPath(m.getEntrance(), m.getExit()).size();
		//Figure out what the best path is
		System.out.println("The shortest path from entrance to exit is: " + mazeShortestPath);
		
		//Run the genetic algorithm a maximum of 200 times
		int count = 0;
		LinkedList<Integer> bests = new LinkedList();
		Boolean stop;
		do {
			stop = true;
			p.select();
			System.out.println(p.getGenerations().get(p.getGenerations().size() - 1));
			Integer bestFitness = p.getGenerations().stream()
					.flatMap(g -> g.getIndividuals().stream())
					.sorted()
					.limit(1)
					.map(Individual::getFitness)
					.findFirst()
					.get();
			bests.addLast(bestFitness);
			if (bests.size() >= 20) {
				Integer test = bests.getFirst();
				bests.removeFirst();
				for (Integer best : bests) {
					if (test != best)		
						stop = false;
				}
			}
			count++;
		}	while ((count < 20) || !stop);

		p.setGenerations(p.getGenerations().stream().limit(p.getGenerations().size() - 19).collect(toList()));
		//Get ready to display on the webpage
		List<String> stats = p.getStatistics();
		
		System.out.println("All done");
	
		//Get the best individual
		List<Individual> bestIndividuals = p.getGenerations().stream()
				.flatMap(g -> g.getIndividuals().stream())
				.sorted()
				.distinct()
				.limit(20)
				.collect(toList());

		System.out.println("Best Individuals " + bestIndividuals.stream()
																.map(i -> i.getFitness())
																.collect(toList()));

		System.out.println("Best Individuals" + bestIndividuals.stream().map(i -> (MazeIndividual)i).map(i -> i.getPathLength()).collect(toList()));

		MazeIndividual bestIndividual = bestIndividuals.stream()
				.filter(b -> b.getFitness() == bestIndividuals.get(0).getFitness())
				.distinct()
				.map(i -> (MazeIndividual)i)
				.sorted((i1, i2) -> Integer.compare(i1.getPathLength(), i2.getPathLength()))
				.findFirst().get();

		System.out.println("Best individual fitness: " + bestIndividual.getFitness());
		System.out.println("Best individual pathlen: " + bestIndividual.getPathLength());

		System.out.println("BEST INDIVIDUAL PATH - SHORTEST PATH: " + (bestIndividual.getPathLength() - mazeShortestPath));

		List<Pos> path = bestIndividual.getPath().stream()
				.map(n -> n.getPos())
				.collect(toList());
		
		System.out.println("Path: " + path.toString());	
		
		//Once we're finished, spit out the output for the webpage to consume.
		String output = "<div class='row'><div class='col-md-4'><div class='maze'>";		
		int x = 0;
		int y = 29;

		List<Pos> shortestPath = m.getShortestPath(m.getEntrance(), m.getExit()).stream().map(n -> n.getPos()).collect(toList());
		//Print the maze, HTML style
		for(int i = 0; i < mString.length(); i++)
		{
			switch (mString.charAt(i))
			{
				case '.':
					output += "<div id='" + x + "-" + y + "' class='square open " + onShortestPath(shortestPath, x, y) + " " + onPath(path, x, y) + " "  +"'></div>";
					x++;
					break;
				case 'X':
					output += "<div id='" + x + "-" + y + "' class='square wall " + onShortestPath(shortestPath, x, y) + " " +onPath(path, x, y) + "'></div>";
					x++;
					break;
				case 'e':
					output += "<div id='" + x + "-" + y + "' class='square entrance " + onShortestPath(shortestPath, x, y) + " " +onPath(path, x, y) +"'></div>";	
					x++;
					break;
				case 'E':
					output += "<div id='" + x + "-" + y + "' class='square exit " + onShortestPath(shortestPath, x, y) + " " + onPath(path, x, y) +"'></div>";
					x++;
					break;
				case '\n':
					x = 0;
					y--;
			}
		}
		
		output += "</div></div><div class='col-md-8'>";
		
		//Print the related statistics
		output += "<div class='stats well' style='margin-left:40px; max-height:450px; overflow:auto;'><h2>Statistics</h2>";
		output += "<table class='table-striped table-condensed'>";
		for (String line : stats)
		{
			output += "<tr class='info'><td> " + line + "</td></tr>";
		}
		output += "</table>";
		output += "</div></div></div>";

		output += "NEWSECTION";
		output += p.getAverageFitnesses();
		output += "NEWSECTION";
		output += p.getMaxFitnesses();
		MazeGeneration.resetStaticCount();
		return output;
	 }

	 private String onPath(List<Pos> path, int x, int y) {
		if (path.contains(Pos.create(x, y + 1)))
			return "individual";
		return "";
	 }

	 private String onShortestPath(List<Pos> path, int x, int y) {
		 if (path.contains(Pos.create(x, y + 1)))
			return "shortest";
		 return ""; 
	 }
}
