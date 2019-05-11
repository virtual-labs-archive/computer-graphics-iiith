package experiments.experiment2;

import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.JFrame;

import engine.CoordSystem;
import engine.Mesh;
import engine.Transformation;
import engine.Vector;
import engine.World;
import experiments.Experiment;

public class Experiment2 extends Experiment {
	@Override
	protected URL getInstructionsURL() {
		return Experiment.class.getResource("experiment2/instructions/1.html");
	}

	public Experiment2() {
		world = new World();

		// Load PLY files
		try {
			world.addMesh("ply"+File.separator+"cube.ply");
			world.addMesh("ply"+File.separator+"dodecahedron.ply");
			world.addMesh("ply"+File.separator+"icosahedron.ply");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		world.display.is3D = false;
		world.display.transformCoordSystems = false;
		lockVertices = true;

		Mesh s1 = world.addPoint(new Vector(5, 5, 3));
		CoordSystem c1 = world.addCoordSystem(new Vector(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 0));
		Transformation t1 = world.addTransformation(0);
		t1.translate(3, 2, 0, 1000);
		world.associate(s1, c1, t1, false, true);
		s1.startTracking(0);
	}

	public static void prepare(Container mainContainer) {
		prepare(mainContainer, new Experiment2());
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Experiment 2");
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		prepare(frame);
		frame.setVisible(true);
		start();
	}
}
