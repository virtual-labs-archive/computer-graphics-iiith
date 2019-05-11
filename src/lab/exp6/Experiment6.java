package experiments.experiment6;

import java.awt.Container;
import java.io.FileNotFoundException;
import java.net.URL;

import javax.swing.JFrame;

import engine.CoordSystem;
import engine.Instance;
import engine.Mesh;
import engine.Transformation;
import engine.Vector;
import engine.World;
import experiments.Experiment;

public class Experiment6 extends Experiment {
	@Override
	protected URL getInstructionsURL() {
		return Experiment.class.getResource("experiment6/instructions/1.html");
	}

	public Experiment6() {
		world = new World();
		//CoordSystem c = world.addCoordSystem(Vector.Z.negate(), Vector.Y, Vector.X.negate(), new Vector(3, 0, -5));
		//CoordSystem c = world.addCoordSystem(Vector.X, Vector.Y, new Vector(0,0,1), Vector.O);
		//world.addOrthographicCamera(1, 20, -5, 5, -5, 5, c, t);

		CoordSystem c = world.addCoordSystem(Vector.X, Vector.Y, new Vector(5,0,-5));
		Transformation t = world.addTransformation(0);
		t.rotateY(90, 5000);
		world.addPerspectiveCamera(1, 20, -1, 1, -1, 1, c, t);

		rotation.x = 30;
		rotation.y = -30;
		world.display.is3D = true;
		lockVertices = true;

		try {
			Mesh m = world.addMesh("ply/bunny.ply");
			//Mesh m = world.addMesh("ply/cube.ply");
			t = world.addTransformation(0);
			//t.rotateY(360, 1000);
			//Instance bunny = world.associate(m, world.addCoordSystem(Vector.X.multiply(50), Vector.Y.multiply(50),
			Instance bunny = world.associate(m, world.addCoordSystem(Vector.X.multiply(50), Vector.Y.multiply(50),
					Vector.Z.multiply(50), new Vector(0, -5, -10)), t, true, false);

			t = world.addTransformation(3000);
			t.rotateZ(180, 1000);
			world.associate(bunny, world.addCoordSystem(Vector.X, Vector.Y, Vector.O), t, true, true);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void prepare(Container mainContainer) {
		prepare(mainContainer, new Experiment6());
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Experiment 6");
		frame.setSize(1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		prepare(frame);
		frame.setVisible(true);
		start();
	}
}
