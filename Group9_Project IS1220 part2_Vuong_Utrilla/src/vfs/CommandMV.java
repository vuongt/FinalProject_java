package vfs;

import java.nio.file.Path;


public class CommandMV extends CommandBehaviour{
	private String vdName;
	private String pathSource;
	private String pathTarget;

	public CommandMV(VFS vfs,String vdName,String pathSource,String pathTarget) {
		super(vfs);
		this.vdName = vdName;
		this.pathSource = pathSource;
		this.pathTarget = pathTarget;
	}

	@Override
	public void go() throws InvalidInput, DuplicatedNameException, InvalidNameException {
		Path source = vfs.toAbsolutePath(vdName,this.pathSource);
		Path target = vfs.toAbsolutePath(vdName,this.pathTarget);
		
		if (target.getParent() == null) { //the destination is root, then it can only be a "move" situation
			vfs.move(vdName, pathSource, pathTarget);
			System.out.println("your file or directory is moved to " + target.toString());
		}
		else{
			boolean sourceIsFile = vfs.checkPath(this.vdName,this.pathSource);
			boolean targetIsFile = vfs.checkPath(this.vdName,target.getParent().toString());
			if (source.getParent() == null) throw new InvalidInput("you cannot move or rename the root");
			else{
				if (source.getParent().equals(target.getParent())){
					String newName = target.getFileName().toString();
					if (sourceIsFile) {
						vfs.renameFile(vdName, pathSource, newName);
						System.out.println("Your file is renamed");
					}
					else {
						vfs.renameDirectory(vdName, pathSource, newName);
						System.out.println("Your directory is renamed");
					}
				}
				else {
					if (targetIsFile) throw new InvalidInput("the destination must be a directory");
					else {
						vfs.move(vdName, pathSource, pathTarget);
						System.out.println("your file/directory is moved to " + target.toString());
					}
				}
			}
		}
		
	}

}
