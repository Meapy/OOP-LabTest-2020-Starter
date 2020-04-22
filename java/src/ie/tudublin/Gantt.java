package ie.tudublin;

import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import java.util.ArrayList;

public class Gantt extends PApplet{
	ArrayList<Task> tasks = new ArrayList<>();
	float RectHeight = 50;
	private float leftGap;
	private float Gap;
	private int left = 0;
	private int right = 0;

	public void settings() {
		size(800, 600);
	}

	public void loadTasks(){
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

			x = map(i, 1, 30, this.leftGap, this.width - this.Gap);
			line(x, Gap, x, this.height - this.Gap); // draws vertical lines
			textSize(10); // make the number text smaller
			text(i, x, Gap * 0.9f);
		}

		line(161, 53, 747, 53); // top horizontal line
		line(160,   548, 747, 548); // bottom horizontal line

		float y;
		float start, end, rectWidth;
		float colour;
		noStroke();

		for (int i = 0; i < tasks.size(); i++){
			fill(255);
			start = map(tasks.get(i).getStart(), 1, totalDays, this.leftGap, this.width - Gap); //used to find where the rectangle starts
			end = map(tasks.get(i).getEnd(), 1, totalDays, this.leftGap, this.width - Gap); // where it ends//
			rectWidth = end - start;
			y = map(i, 0, tasks.size(), 2 * this.Gap, this.height - this.Gap);

			textSize(20); //make the task names bigger font
			text(tasks.get(i).getTask(), Gap, y);

			colour = map(i, 0, tasks.size(), i, 255);
			fill(colour, 255, 255);
			rect(start, y - (this.RectHeight / 2), rectWidth, this.RectHeight, 5);
		}
	}

	public void mousePressed() {
		println("Mouse pressed");
		float x1, x2, y1, y2;
		float pixelsMoved = 20;
		for(int i = 0; i < tasks.size(); i++)
		{
			Task task = tasks.get(i);
			x1 = map(task.getStart(), 1, 30, this.leftGap, this.width - Gap);
			x2 = map(task.getEnd(), 1, 30, this.leftGap, this.width - Gap);
			y1 = map(i, 0, tasks.size(), 2 * this.Gap, this.height - Gap) - this.RectHeight / 2;
			y2 = y1 + this.RectHeight;

			if(mouseY >= y1 && mouseY <= y2)
			{
				if(mouseX < x1 + pixelsMoved && mouseX > x1 - pixelsMoved)
				{
					this.left = i;
					this.right = -1;
				}
				else if(mouseX < x2 + pixelsMoved && mouseX > x2 - pixelsMoved)
				{
					this.left = -1;
					this.right = i;
				}
			}
		}
	}

	public void mouseDragged() {
		println("Mouse dragged");
		int day;

		if(this.left > -1)
		{
			Task task = tasks.get(this.left);
			day = (int) map(mouseX, 0, this.width, 0, 30);
			if(day > 0 && day < task.getEnd() && task.getEnd() - day >= 1)
			{
				task.setStart(day);
			}
		}
		else if(this.right > -1)
		{
			Task task = tasks.get(this.right);
			day = (int) map(mouseX, 0, this.width, 0, 30);
			if(day <= 30 && day > task.getStart() && day - task.getStart() >= 1)
			{
				task.setEnd(day);
			}
		}
	}


	public void setup(){
		loadTasks();
		printTasks();
		leftGap = width / 5f;
		Gap = width / 15f;
	}
	
	public void draw(){
		colorMode(HSB);
		background(0);
		displayTasks();
	}
}
