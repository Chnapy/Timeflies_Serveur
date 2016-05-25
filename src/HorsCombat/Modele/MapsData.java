/*
 * 
 * 
 * 
 */
package HorsCombat.Modele;

import static Main.Data.MAPS_PATH;
import Serializable.HorsCombat.Map.MapSerializable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * MapsData.java
 *
 */
public class MapsData {

	public static final ArrayList<MapSerializable> ALL_MAPS = new ArrayList();

	public static void loadAllMaps() throws FileNotFoundException, IOException {
		File directoryToScan = new File(MAPS_PATH);
		for (File file : directoryToScan.listFiles()) {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
			try {
				ALL_MAPS.add((MapSerializable) ois.readObject());
			} catch (ClassNotFoundException ex) {
				Logger.getLogger(MapsData.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}

	public static MapSerializable getRandomMap() {
		return ALL_MAPS.get((int) (Math.random() * ALL_MAPS.size()));
	}

}
