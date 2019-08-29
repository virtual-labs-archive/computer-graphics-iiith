package experiments.experiment4;

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

public class Experiment4 extends Experiment {
	@Override
	protected URL getInstructionsURL() {
		return Experiment.class.getResource("experiment4/instructions/1.html");
	}

	public Experiment4() {
		world = new World();

		world.display.is3D = false;
		world.display.transformCoordSystems = false;
		lockVertices = true;
		
		// Load PLY files
		try {
			world.addMesh("ply" + File.separator + "cube.ply");
			world.addMesh("ply" + File.separator + "dodecahedron.ply");
			world.addMesh("ply" + File.separator + "icosahedron.ply");

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Vector points[] = { new Vector(2, 1, 0), new Vector(4, 3, 0), new Vector(0, 4, 0) };
		Triangle triangles[] = { new Triangle(0, 1, 2) };
		Mesh s1 = world.addMesh(points, triangles);

/*		Vector points2[] = { new Vector(2, 1, 3), new Vector(4, 3, 3), new Vector(0, 4, 3) };
		Triangle triangles2[] = { new Triangle(0, 1, 2) };
		Mesh s2 = world.addMesh(points2, triangles2);

		Vector points3[] = { new Vector(2, 1, -3), new Vector(4, 3, -3), new Vector(0, 4, -3) };
		Triangle triangles3[] = { new Triangle(0, 1, 2) };
		Mesh s3 = world.addMesh(points3, triangles3);
*/
		CoordSystem c1 = world.addCoordSystem(new Vector(1, 0, 0), new Vector(0, 1, 0), new Vector(0, 0, 0));
		Transformation t1 = world.addTransformation(0);
//		t1.translate(points[0].x, points[0].y, points[0].z, 1000);
//		t1.rotateZ(180, 1000);
//		t1.translate(-points[0].x, -points[0].y, -points[0].z, 1000);
		t1.scale(3, 2, 1, 2000);
		Instance si1 = world.associate(s1, c1, t1, true, true);
		s1.startTracking(0);

/*		Instance si2 = world.associate(s2, c1, t1, true, true);
		Instance si3 = world.associate(s3, c1, t1, true, true);
		si2.isActive = false;
		si3.isActive = false;
*/
		
		si1.isActive = false;
	}

	public static void prepare(Container mainContainer) {
		prepare(mainContainer, new Experiment4());
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Experiment 4");
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		prepare(frame);
		frame.setVisible(true);
		start();
	}
}
