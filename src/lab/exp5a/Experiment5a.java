package experiments.experiment5a;

import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.JFrame;

import engine.CoordSystem;
import engine.Instance;
import engine.Mesh;
import engine.Transformation;
import engine.Triangle;
import engine.Vector;
import engine.World;
import experiments.Experiment;

public class Experiment5a extends Experiment {
	@Override
	protected URL getInstructionsURL() {
		return Experiment.class.getResource("experiment5a/instructions/1.html");
	}

	public Experiment5a() {
		world = new World();

		// Load PLY files
		try {
			world.addMesh("ply" + File.separator + "cube.ply");
			world.addMesh("ply" + File.separator + "dodecahedron.ply");
			world.addMesh("ply" + File.separator + "icosahedron.ply");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		world.display.is3D = false;
		world.display.transformCoordSystems = false;
		lockVertices = true;

		Vector points[] = { new Vector(2, 1, 0), new Vector(4, 3, 0), new Vector(0, 4, 0) };
		Triangle triangles[] = { new Triangle(0, 1, 2) };
		Mesh s1 = world.addMesh(points, triangles);

		CoordSystem c1 = world.addCoordSystem(new Vector(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 0));
		Transformation t1 = world.addTransformation(0);
		t1.translate(points[0].x, points[0].y, points[0].z, 1000);
		t1.rotateZ(180, 1000);
		t1.translate(-points[0].x, -points[0].y, -points[0].z, 1000);
		t1.scale(3, 2, 1, 1000);
		Instance si1 = world.associate(s1, c1, t1, true, true);
		s1.startTracking(0);

		si1.isActive = false;
	}

	public static void prepare(Container mainContainer) {
		prepare(mainContainer, new Experiment5a());
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Experiment 5a");
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		prepare(frame);
		frame.setVisible(true);
		start();
	}
}
