package vfs;

public class CommandHELP extends CommandBehaviour{

	
	private String command;
	
	public CommandHELP(VFS vfs, String command){
		super(vfs);
		this.command=command;
		
	}
	
	public CommandHELP(VFS vfs){
		super(vfs);
		this.command="";
		
	}
	
	public void go() throws InvalidInput{
		
		
		switch(this.command){
		case "":
			System.out.println("******************VFS INTERFACE******************\n");
			
			System.out.println("CLUI command: <commandName> <Argument1> <Argument2> ...\n\n");
			
			System.out.println("Type 'help <commandName>' to get the instructions of each command.\n\n");
			
			System.out.println("COMMANDS:\n"
					+"ls      List the elements in a position of a virtual disk\n"
					+"cd      Change current position of a virtual disk\n"
					+"touch   Create a file\n"
					+"mkdir   Create a directory\n"
					+"rm      Remove a file/directory\n"
					+"cp\n"
					+"mv\n"
					+"exp     Export a file/directory into the host file system\n"
					+"impvfs  Import a file/directory into the host file system\n"
					+"crvfs   Create a new virtual disk\n"
					+"rmvfs   Remove a virtual disk\n"
					+"expvfs  Export a virtual disk\n"
					+"savevfs Save the state of a virtual disk\n"
					+"free    Show the free/occupied space of a virtual disk\n"
					+"pwd     Show the current position in a virtual disk\n"
					+"find    Find a file in a virtual disk\n"
					+"stop    Close the vfs\n"
					+"help    Help instructions");
			break;
		case "ls":
			System.out.println("List the elements inside the directory of position 'vfspath' of a virtual disk named 'vfsname'.\n"
					+ "Usage: ls vfsname [args] [vfspath]\n"
					+ "arg=-l the size of each element is specified.\n"
					+ "If no vfspath specified, current position is taken.");
			
			break;
		case "cd":
			
			System.out.println("Change current position to 'vfspath' of a virtual disk 'vfsname'. \n"
					+ "Usage: cd vfsname vfspath");
			break;
		case "touch":
			
			System.out.println("Create a file named 'filename' inside the directoy of position 'vfspath' of the virtual disk 'vfsname'\n"
					+ "Usage: cd vfsname vfspath filename");
			
			break;
		case "mkdir":
			System.out.println("Create a directory named 'dirname' inside the directoy of position 'vfspath' of the virtual disk 'vfsname'\n"
					+ "Usage: cd vfsname vfspath dirname");
			
			break;
		case "rm":
			
			System.out.println("Remove the file or directory of position 'vfspath' of the virtual disk 'vfsname'\n"
					+ "Usage: cd vfsname vfspath");
			break;
			
		case "cp":
			break;
		case "mv":
			break;
			
			
		case "exp":
			
			System.out.println("Export the file or directory of position 'vfspath' of the virtual disk 'vfsname'\n into the location 'hostPath' of the host file system.\n"
					+ "Usage: cd hostpath vfsname vfspath\n"
					+ "hostpath is an absolute path of the host file system");
			break;
		case "impvfs":
			
			System.out.println("Import the file or directory of position 'hostpath' of the host file system\n into the location 'vfspath' of the virtual disk 'vfsname'.\n"
					+ "Usage: cd hostpath vfsname vfspath\n"
					+ "hostpath is an absolute path of the host file system");
			break;
		case "crvfs":
			
			System.out.println("Create a virtual disk named 'vfsname' of maximal dimension 'dim' (bytes).\n"
					+ "Usage: cd vfsname dim");
			break;
		case "rmvfs":
			
			System.out.println("Delete a virtual disk named 'vfsname'.\n"
					+ "Usage: cd vfsname ");
			break;
		case "expvfs":
			System.out.println("Export a virtual disk named 'vfsname' to the directory of location 'hostpath' of the host file system.\n"
					+ "Usage: cd vfsname hostpath\n"
					+ "hostpath is an absolute path of the host file system ");
			
			break;
		case "savevfs":
			
			System.out.println("Update the state of the virtual disk 'vfsname' into the last location of the host file system it was exported\n"
					+ "Usage: cd vfsname");
			break;
		case "free":
			System.out.println("Show the free/occupied space of a virtual disk 'vfsname'\n"
					+ "Usage: free vfsname");
			break;
		case "pwd":
			System.out.println("Show the current position of a virtual disk 'vfsname'\n"
					+ "Usage: pwd vfsname");
			break;
		case "find":
			System.out.println("Search if a file named 'vfsname' exists in a virtual disk 'vfsname'\n"
					+ "Usage: find vfsname filename");
			break;
		case "stop":
			System.out.println("Close the vfs\n"
					+ "Usage: stop");
			break;
		case "help":
			System.out.println("Show instructions to use the command 'commandname'\n"
					+ "Usage: help [commandname]\n"
					+ "If no commandname specified, general instructions showed.");
			break;
			
		default:
			throw new InvalidInput("InvalidInput. Type 'help' for instructions.");
			
		}	
	}
}
	
