package put.os.fileSystem;

public class Block {
	int[] data;
	
	public Block(int blockSize)
	{
		this.data = new int[blockSize];
	}
}
