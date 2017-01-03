package put.os.fileSystem;

public class CatalogPosition // pozycja katalogu
{
	private String fileName;
	private int indexOfINode;
	//private int blockCount;
	public CatalogPosition (String _fileName, int _indexOfINode)
	{
		fileName = _fileName;
		indexOfINode = _indexOfINode;
		//blockCount = _blockCount;
	}
	
	public String GetFileName()
	{
		return fileName;
	}
	
	public int GetIndexOfINode()
	{
		return indexOfINode;
	}
	
	//public int GetBlockCount()
	//{
	//	return blockCount;
	//}
}