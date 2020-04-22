package ie.tudublin;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import java.util.ArrayList;

public class Gantt extends PApplet {
	ArrayList<Task> tasks = new ArrayList<Task>();
	private float leftGap;
	private float Gap;

	public void settings() {
		size(800, 600);
	}

	public void loadTasks() {
		Table table = loadTable("tasks.csv", "header");
		for (TableRow row : table.rows()) {
			Task t = new Task(row);
			tasks.add(t);
		}
	}

	public void printTasks(){
		for (Task t : tasks){
			System.out.println(t);
		}
	}

	public void displayTasks() {
		int totalDays = 30;
		float x;

		fill(255);
		stroke(255);
		for (int i = 1; i <= totalDays; i++) {

			x = map(i, 1, 30, leftGap, this.width - Gap);
			line(x, Gap, x, this.height - Gap); // draws horizontal lines
			textSize(10);
			text(i, x, Gap * 0.9f);
		}

		line(161, 53, 747, 53); // top vertical line
		line(160,   548, 747, 548); // bottom vertical line

		float y = 10;
		float RectHeight = 50;
		float start, end, rectWidth;
		float colour;
		noStroke();

		for (int i = 0; i < tasks.size(); i++){
			fill(255);
			start = map(tasks.get(i).getStart(), 1, totalDays, leftGap, this.width - Gap);
			end = map(tasks.get(i).getEnd(), 1, totalDays, leftGap, this.width - Gap);
			rectWidth = end - start;
			y = map(i, 0, tasks.size(), 2 * Gap, this.height - Gap);

			textSize(20);
			text(tasks.get(i).getTask(), Gap, y);

			colour = map(i, 0, tasks.size(), i, 255);
			fill(colour, 255, 255);
			rect(start, y - (RectHeight / 2), rectWidth, RectHeight, 5);
		}
	}

	public void mousePressed() {
		println("Mouse pressed");
	}

	public void mouseDragged() {
		println("Mouse dragged");
	}


	public void setup(){
		loadTasks();
		printTasks();
		leftGap = width / 5;
		Gap = width / 15;
	}
	
	public void draw(){
		colorMode(HSB);
		background(0);
		displayTasks();
	}
}
