package vfs;

import java.nio.file.Path;
import java.nio.file.Paths;


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
	public void go() throws InvalidInput, DuplicatedNameException {
		boolean sourceIsFile = vfs.checkPath(this.vdName,this.pathSource);
		boolean targetIsFile = vfs.checkPath(this.vdName,this.pathTarget);
		Path source = Paths.get(this.pathSource);
		Path target = Paths.get(this.pathTarget);
		if (source.getParent().equals(target.getParent())){
			String newName = target.getFileName().toString();
			if (sourceIsFile) vfs.renameFile(vdName, pathSource, newName);
			else vfs.renameDirectory(vdName, pathSource, newName);
			}
		else {
			if (targetIsFile) throw new InvalidInput("the destination must be a directory");
			else {
				vfs.move(vdName, pathSource, pathTarget);
			}
			
			}
		
		
	}

}
