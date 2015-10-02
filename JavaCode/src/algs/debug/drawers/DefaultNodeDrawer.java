package algs.debug.drawers;

import algs.debug.IGraphEntity;
import algs.debug.INodeDrawer;
import algs.debug.ISelectFont;

/**
 * Capable of drawing default nodes in the DOTTY debugging output.
 *  
 * @author George Heineman
 * @version 1.0, 6/15/08
 * @since 1.0
 */
public class DefaultNodeDrawer implements INodeDrawer {

	/** 
	 * Default node is drawn simply using its node label.
	 *
	 * Is aware of {@link ISelectFont} interface. Takes action if the desired
	 * fontName is not null (but only then). 
	 * 
	 * At one point we were able to embed infinity symbols into the DOTTY output, but this is 
	 * not platform independent, so we have eliminated this capability. Should it become available
	 * in the future, we can bring this back in.
	 *
	 * @param n   Node to be drawn.
	 */
	public String draw(IGraphEntity n) {
		String specialFont = "";
		
//		if (n instanceof ISelectFont) {
//			String fontName = ((ISelectFont)n).fontName();
//			if (fontName != null) {
//				specialFont = "fontname=\"" + ((ISelectFont)n).fontName() + "\" ";
//				int sz = ((ISelectFont)n).fontSize();
//				if (sz != 0) {
//					specialFont += "fontsize=" + sz + " ";
//				}
//			}
//		}
		
		return  "[" + specialFont + "label=\"" + n.nodeLabel() + "\"]";
	}
}
