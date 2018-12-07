package games.stendhal.client.actions;

import games.stendhal.client.ClientSingletonRepository;
import marauroa.common.game.RPAction;

public class AcceptChallengeAction extends Action {
	
	//Name of xml file that contains the min and max no of parameters
    public static final String COMMAND_NAME = "acceptchallenge";
    
    @Override
	 protected String getCommandName() 
    {
		return COMMAND_NAME;
	 }
    

	@Override
	public boolean execute(String[] params, String remainder) {
		if(params == null) {
			return false;
		}
		RPAction action = new RPAction();
		action.put("type", "challenge");
		action.put("action", "accept");
		action.put("target", params[0]);
		ClientSingletonRepository.getClientFramework().send(action);

		return true;
	}
}
