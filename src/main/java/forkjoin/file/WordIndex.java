package forkjoin.file;

public class WordIndex {
	private long line;
	private int localIndex;
	public WordIndex(long line, int localIndex){
		this.line = line;
		this.localIndex = localIndex;
	}
	@Override
	public String toString(){
		return String.format("Line = %s, localIndex = %s", line, localIndex);
	}
	
}
