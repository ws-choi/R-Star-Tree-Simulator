package r.tree;

public class Log {

	private String Log ;
	private int mode;

	public static final int SET_TEXT = 0;
	public static final int APPEND_TEXT =1;
	public Log() {
		setLog("");
		setMode(SET_TEXT);
	}
	public String getLog() {
		return Log;
	}
	public void setLog(String string) {
		Log = string;
		System.gc();
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public static void main(String[] args) {
		Log log = new Log();
		log.setMode(1);
		log.setLog("123");
		log.setLog("113");
		
		System.out.println(log.getLog());
	}
	
	
}
