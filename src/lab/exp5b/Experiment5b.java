package experiments.experiment5b;

import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import engine.CoordSystem;
import engine.Instance;
import engine.Shape;
import engine.Transformation;
import engine.Vector;
import engine.World;
import experiments.Experiment;

public class Experiment5b extends Experiment {
	@Override
	protected URL getInstructionsURL() {
		return Experiment.class.getResource("experiment5b/instructions/1.html");
	}

	private CoordSystem defaultCoordSystem(double x, double y, double z) {
		CoordSystem c = world.addCoordSystem(Vector.X, Vector.Y, Vector.Z, new Vector(x,y,z), 5, true, false);
		return c;
	}

	private CoordSystem defaultCoordSystem() {
		CoordSystem c = world.addCoordSystem(Vector.X, Vector.Y, Vector.Z, Vector.O, 5, true, false);
		return c;
	}

	public Experiment5b() {
		world = new World();

		Shape s;

		try {
			/* Load PLY files */
			s = world.addMesh("ply" + File.separator + "cube.ply");
//			world.addMesh("ply" + File.separator + "dodecahedron.ply");
	//		world.addMesh("ply" + File.separator + "icosahedron.ply");
		//	world.addMesh("ply" + File.separator + "bunny.ply");

			/* Display configuration */
			world.display.is3D = true;
			lockVertices = true;
			rotation.x = 35;
			rotation.y = -30;

			Transformation t = world.addTransformation(0);
			t.scale(2, 0.5, 2, 0);
			t.translate(1, 1, 0, 0);
			Shape palm = world.associate(s, defaultCoordSystem(), t, false, false, "Palm");

			t = world.addTransformation(800);
			t.rotateZ(45, 400);
			Instance wrist = world.associate(palm, defaultCoordSystem(6,0,0), t, true, false, "Wrist");

			t = world.addTransformation(0);
			t.scale(3, 0.5, 0.5, 0);
			t.translate(1, 1, 0, 0);
			Shape forearm = world.associate(s, defaultCoordSystem(), t, false, false, "Fore-arm");

			t = world.addTransformation(400);
			t.rotateZ(45, 400);
			Instance elbow = world.associate(forearm, defaultCoordSystem(1,-4,0), t, false, false, "Elbow");
			elbow.addShape(wrist);

			t = world.addTransformation(0);
			t.scale(0.5, 2, 0.5, 0);
			t.translate(1, -1, 0, 0);
			Shape arm = world.associate(s, defaultCoordSystem(), t, false, false, "Arm");

			CoordSystem c = defaultCoordSystem();
			c.isDrawn = true;
			t = world.addTransformation(0);
			t.rotateY(90, 400);
			Instance shoulder = world.associate(arm, c, t, true, true, "Shoulder");
			shoulder.addShape(elbow);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null,
					"A necessary ply file failed to load. The experiment could not be set up.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void prepare(Container mainContainer) {
		prepare(mainContainer, new Experiment5b());
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Experiment 5b");
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		prepare(frame);
		frame.setVisible(true);
		start();
	}
}
