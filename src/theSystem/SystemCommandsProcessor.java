package theSystem;

import java.util.ArrayList;

import fileOps.FileIO;
import lists.ListsManager;
import lists.NamedList;
import mainClasses.Numbers;
import mainClasses.Pair;
import stack.IntStack;
import systemGeneralClasses.Command;
import systemGeneralClasses.CommandActionHandler;
import systemGeneralClasses.CommandProcessor;
import systemGeneralClasses.FixedLengthCommand;
import systemGeneralClasses.SystemCommand;

/**
 * 
 * @author Pedro I. Rivera-Vega
 *
 */
public class SystemCommandsProcessor extends CommandProcessor {

	// NOTE: The HelpProcessor is inherited...

	// To initially place all lines for the output produced after a
	// command is entered. The results depend on the particular command.
	private ArrayList<String> resultsList;

	SystemCommand attemptedSC;
	// The system command that looks like the one the user is
	// trying to execute.

	boolean stopExecution;
	// This field is false whenever the system is in execution
	// Is set to true when in the "administrator" state the command
	// "shutdown" is given to the system.

	////////////////////////////////////////////////////////////////
	// The following are references to objects needed for management
	// of data as required by the particular octions of the command-set..
	// The following represents the object that will be capable of
	// managing the different lists that are created by the system
	// to be implemented as a lab exercise.
	private ListsManager listsManager = new ListsManager();
	public static final String LISTNAME = "TupleList";

	/**
	 * Initializes the list of possible commands for each of the states the
	 * system can be in.
	 */
	public SystemCommandsProcessor() {

		// stack of states
		currentState = new IntStack();

		// The system may need to manage different states. For the moment, we
		// just assume one state: the general state. The top of the stack
		// "currentState" will always be the current state the system is at...
		currentState.push(GENERALSTATE);

		// Maximum number of states for the moment is assumed to be 1
		// this may change depending on the types of commands the system
		// accepts in other instances......
		createCommandList(1);
		listsManager.createNewList(LISTNAME);// only 1 state -- GENERALSTATE

		// commands for the state GENERALSTATE

		// the following are just for demonstration...
		/*
		 * add(GENERALSTATE, SystemCommand.getVLSC("testoutput int", new
		 * TestOutputProcessor())); // just for demonstration add(GENERALSTATE,
		 * SystemCommand.getVLSC("addnumbers int_list", new
		 * AddNumbersProcessor())); // just for demonstration
		 * 
		 * // the following are for the different commands that are accepted by
		 * // the shell-like system that manage lists of integers
		 * 
		 * // the command to create a new list is treated here as a command of
		 * variable length // as in the case of command testoutput, it is done
		 * so just to illustrate... And // again, all commands can be treated as
		 * of variable length command... // One need to make sure that the
		 * corresponding CommandActionHandler object // is also working (in
		 * execute method) accordingly. See the documentation inside // the
		 * CommandActionHandler class for testoutput command.
		 * //add(GENERALSTATE, SystemCommand.getVLSC("create name", new
		 * CreateProcessor()));
		 * 
		 * // the following commands are treated as fixed lentgh commands....
		 * add(GENERALSTATE, SystemCommand.getFLSC("showlists", new
		 * ShowListsProcessor())); add(GENERALSTATE, SystemCommand.getFLSC(
		 * "add name int_position int_value", new AddProcessor()));
		 * add(GENERALSTATE, SystemCommand.getVLSC("appendvalues name int_list",
		 * new AppendValuesProcessor())); add(GENERALSTATE,
		 * SystemCommand.getFLSC("append name int", new AppendProcessor()));
		 * add(GENERALSTATE, SystemCommand.getFLSC("remove name int", new
		 * RemoveProcessor())); add(GENERALSTATE, SystemCommand.getFLSC(
		 * "showall name", new ShowAllProcessor())); add(GENERALSTATE,
		 * SystemCommand.getVLSC("show name int_list", new ShowProcessor()));
		 * add(GENERALSTATE, SystemCommand.getFLSC("showsize name", new
		 * ShowSizeProcessor())); add(GENERALSTATE,
		 * SystemCommand.getFLSC("exit", new ShutDownProcessor()));
		 * add(GENERALSTATE, SystemCommand.getFLSC("help", new
		 * HelpProcessor()));
		 * 
		 * // need to follow this pattern to add a SystemCommand for each //
		 * command that has been specified... // ...
		 */
		add(GENERALSTATE, SystemCommand.getFLSC("var var_name", new CreateVariableProcessor()));
		add(GENERALSTATE, SystemCommand.getFLSC("delete var_name", new DeleteVariableProcessor()));
		add(GENERALSTATE, SystemCommand.getFLSC("show var_name", new ShowVariableProcessor()));
		add(GENERALSTATE, SystemCommand.getFLSC("save file_name", new SaveToFileProcessor()));
		add(GENERALSTATE, SystemCommand.getFLSC("add var_name value value", new AddNumbersProcessor()));
		add(GENERALSTATE, SystemCommand.getFLSC("sub var_name value value", new SubtractNumbersProcessor()));
		add(GENERALSTATE, SystemCommand.getFLSC("mult var_name value value", new MultiplyNumbersProcessor()));
		add(GENERALSTATE, SystemCommand.getFLSC("help", new HelpProcessor()));
		add(GENERALSTATE, SystemCommand.getFLSC("exit", new ShutDownProcessor()));
		// set to execute....
		stopExecution = false;

	}

	public ArrayList<String> getResultsList() {
		return resultsList;
	}

	// INNER CLASSES -- ONE FOR EACH VALID COMMAND --
	/**
	 * The following are inner classes. Notice that there is one such class for
	 * each command. The idea is that enclose the implementation of each command
	 * in a particular unique place. Notice that, for each command, what you
	 * need is to implement the internal method "execute(Command c)". In each
	 * particular case, your implementation assumes that the command received as
	 * parameter is of the type corresponding to the particular inner class. For
	 * example, the command received by the "execute(...)" method inside the
	 * "LoginProcessor" class must be a "login" command.
	 *
	 */

	private class ShutDownProcessor implements CommandActionHandler {
		public ArrayList<String> execute(Command c) {

			resultsList = new ArrayList<String>();
			resultsList.add("SYSTEM IS SHUTTING DOWN!!!!");
			stopExecution = true;
			return resultsList;
		}
	}

	private class CreateVariableProcessor implements CommandActionHandler {

		@Override
		public ArrayList<String> execute(Command c) {
			// TODO Auto-generated method stub
			resultsList = new ArrayList<String>();
			FixedLengthCommand fc = (FixedLengthCommand) c;
			int listIndex = listsManager.getListIndex(LISTNAME);
			if (listIndex == -1)
				resultsList.add("No such list: " + LISTNAME);
			else {
				String varName = fc.getOperand(1);
				Pair pair = new Pair(new Numbers("0.0"), varName);
				listsManager.addElement(listIndex, pair);
				resultsList.add(varName + " has been added and initialized to 0.0");
			}
			return resultsList;
		}

	}

	private class DeleteVariableProcessor implements CommandActionHandler {

		@Override
		public ArrayList<String> execute(Command c) {
			// TODO Auto-generated method stub
			resultsList = new ArrayList<String>();
			FixedLengthCommand fc = (FixedLengthCommand) c;
			int listIndex = listsManager.getListIndex(LISTNAME);
			if (listIndex == -1)
				resultsList.add("No such list: " + LISTNAME);
			else {
				String varName = fc.getOperand(1);
				int index = 0;
				try {
					VariableName.validateVariable(listsManager, varName, listIndex);
				} catch (Exception E) {
					resultsList.add("Invalid Variable");
					return resultsList;
				}
				Pair tpl = (Pair) listsManager.removeElement(listIndex, index);
				resultsList.add(tpl.getName() + " has been removed");

			}
			return resultsList;
		}

	}

	private class ShowVariableProcessor implements CommandActionHandler {

		@Override
		public ArrayList<String> execute(Command c) {
			// TODO Auto-generated method stub
			resultsList = new ArrayList<String>();
			FixedLengthCommand fc = (FixedLengthCommand) c;
			int listIndex = listsManager.getListIndex(LISTNAME);
			if (listIndex == -1)
				resultsList.add("No such list: " + LISTNAME);
			else {
				String varName = fc.getOperand(1);
				int index = 0;
				try {
					index = VariableName.validateVariable(listsManager, varName, listIndex);
				} catch (Exception E) {
					resultsList.add("Invalid Variable");
					return resultsList;
				}
				Pair pair = (Pair) listsManager.getElement(listIndex, index);
				resultsList.add(pair.toString());

			}
			return resultsList;
		}

	}

	private class SaveToFileProcessor implements CommandActionHandler {

		@Override
		public ArrayList<String> execute(Command c) {
			// TODO Auto-generated method stub
			resultsList = new ArrayList<String>();
			FixedLengthCommand fc = (FixedLengthCommand) c;
			int listIndex = listsManager.getListIndex(LISTNAME);
			if (listIndex == -1)
				resultsList.add("No such list: " + LISTNAME);
			else {
				String fileName = fc.getOperand(1);
				NamedList<Pair> list = listsManager.getNamedList(listIndex);
				FileIO.saveToFile(fileName, list);
				resultsList.add("Saved Successfully");
			}
			return resultsList;
		}

	}

	private class AddNumbersProcessor implements CommandActionHandler {

		@Override
		public ArrayList<String> execute(Command c) {
			// TODO Auto-generated method stub
			resultsList = new ArrayList<String>();
			FixedLengthCommand fc = (FixedLengthCommand) c;
			int listIndex = listsManager.getListIndex(LISTNAME);
			if (listIndex == -1)
				resultsList.add("No such list: " + LISTNAME);
			else {
				String varName = fc.getOperand(1);
				try {
					VariableName.validateVariable(listsManager, varName, listIndex);
				} catch (Exception E) {
					resultsList.add("Invalid Variable");
					return resultsList;
				}
				String firstNum = fc.getOperand(2);
				String secondNum = fc.getOperand(3);
				Numbers A = new Numbers(firstNum);
				Numbers B = new Numbers(secondNum);
				Numbers result = Numbers.correctOperation(A, B, "add");
				Pair p = (Pair) listsManager.getElement(listIndex, listsManager.getElementIndex(varName, listIndex));
				p.setNumbers(result);
				resultsList.add("The result is: " + result.toString());
			}
			return resultsList;
		}

	}

	private class SubtractNumbersProcessor implements CommandActionHandler {

		@Override
		public ArrayList<String> execute(Command c) {
			resultsList = new ArrayList<String>();
			FixedLengthCommand fc = (FixedLengthCommand) c;
			int listIndex = listsManager.getListIndex(LISTNAME);
			if (listIndex == -1)
				resultsList.add("No such list: " + LISTNAME);
			else {
				String var = fc.getOperand(1);
				try {
					VariableName.validateVariable(listsManager, var, listIndex);
				} catch (Exception E) {
					resultsList.add("Invalid Variable");
					return resultsList;
				}
				String firstNum = fc.getOperand(2);
				String secondNum = fc.getOperand(3);
				Numbers A = new Numbers(firstNum);
				Numbers B = new Numbers(secondNum);
				Numbers result = Numbers.correctOperation(A, B, "sub");
				Pair p = (Pair) listsManager.getElement(listIndex, listsManager.getElementIndex(var, listIndex));
				p.setNumbers(result);
				resultsList.add("The result is: " + result.toString());
			}
			return resultsList;
		}

	}

	private static class VariableName {
		public static int validateVariable(ListsManager listsManager, String varName, int listIndex)
				throws IllegalArgumentException {

			int index = listsManager.getElementIndex(varName, listIndex);
			if (index == -1) {
				throw new IllegalArgumentException("non existing variable");
			}

			return index;
		}
	}

	private class MultiplyNumbersProcessor implements CommandActionHandler {

		@Override
		public ArrayList<String> execute(Command c) {
			resultsList = new ArrayList<String>();
			FixedLengthCommand fc = (FixedLengthCommand) c;
			int listIndex = listsManager.getListIndex(LISTNAME);
			if (listIndex == -1)
				resultsList.add("No such list: " + LISTNAME);
			else {
				String var = fc.getOperand(1);
				try {
					VariableName.validateVariable(listsManager, var, listIndex);
				} catch (Exception E) {
					resultsList.add("Invalid Variable");
					return resultsList;
				}
				String firstNum = fc.getOperand(2);
				String secondNum = fc.getOperand(3);
				Numbers A = new Numbers(firstNum);
				Numbers B = new Numbers(secondNum);
				Numbers result = Numbers.correctOperation(A, B, "mult");
				Pair p = (Pair) listsManager.getElement(listIndex, listsManager.getElementIndex(var, listIndex));
				p.setNumbers(result);
				resultsList.add("The result is: " + result.toString());
			}
			return resultsList;
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean inShutdownMode() {
		return stopExecution;
	}

}
