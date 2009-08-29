package games.stendhal.tools.rpobjectdumper;

import games.stendhal.server.core.engine.RPClassGenerator;

import java.io.IOException;
import java.sql.SQLException;

import marauroa.common.game.RPObject;
import marauroa.server.game.db.DAORegister;
import marauroa.server.game.db.DatabaseFactory;
import marauroa.server.game.db.RPObjectDAO;

/**
 * dumps an rpobject
 *
 * @author hendrik
 */
public class RPObjectDumper {

	public static void main(String[] args) throws SQLException, IOException {
		new DatabaseFactory().initializeDatabase();

		new RPClassGenerator().createRPClasses();

		int objectid = Integer.parseInt(args[0]);
		RPObject object = DAORegister.get().get(RPObjectDAO.class).loadRPObject(objectid);
		RPObject object2 = new RPObject(object);
		System.out.println(object2);
	}
}
