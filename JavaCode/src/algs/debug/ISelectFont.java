package algs.debug;

/**
 * Should a graphical entity choose to select a different font for its node
 * drawing (for example, to show symbols) then it must implement this interface.
 * 
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public interface ISelectFont {

	/** 
	 * Determine font to use.
	 * 
	 * If the default font is to be used, then return null;
	 * @return  font name to use in drawing.
	 */
	public String fontName();
	
	/** 
	 * Determine font size to use.
	 * 
	 * This method is only invoked should {@link #fontName()} return non-null, in
	 * which case this should return the desired font size. If 0 is returned, then
	 * the default font size is used.
	 * @return  font size to use in drawing.
	 */
	public int fontSize();
}
