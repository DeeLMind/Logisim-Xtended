package net.net16.jeremiahlowe.logisim.logisim_xtended.parts.logic;

import com.cburch.logisim.data.BitWidth;
import com.cburch.logisim.data.Bounds;
import com.cburch.logisim.data.Value;
import com.cburch.logisim.instance.InstancePainter;
import com.cburch.logisim.util.GraphicsUtil;

public class SquareAND extends SquareBase{

	public SquareAND() {
		super("Square AND gate");
	}

	@Override
	protected Value doLogicalOperation(Value[] inputs) {
		Value output = inputs[0];
		int w = output.getWidth();
		for(int i = 1; i < inputs.length; i++){
			Value tmpOutput = Value.createKnown(BitWidth.create(w), 0);
			//Loop through the value and and each bit individually
			for(int j = 0; j < w; j++){
				Value bitA = inputs[i].get(j);
				Value bitB = output.get(j);
				Value res = bitA.and(bitB);
				if(res.isErrorValue()) return Value.createError(BitWidth.create(w));
				if(!res.isFullyDefined()) return Value.createUnknown(BitWidth.create(w));
				System.out.println(j + ": " + bitA.toDisplayString(2) + " & " + bitB.toDisplayString(2) + ": " + res.toDisplayString(2));
				Value[] tmp = tmpOutput.getAll();
				tmp[j] = res;
				tmpOutput = Value.create(tmp);
			}
			output = tmpOutput;
		}
		return output;
	}
	
	@Override
	public void paintInstance(InstancePainter painter) {
		Bounds bds = painter.getBounds();
		String lbl = bds.getWidth() > 25 ? "AND" : "&";
		GraphicsUtil.drawCenteredText(painter.getGraphics(), lbl, bds.getX() + bds.getWidth() / 2, bds.getY() + bds.getHeight() / 2);
		painter.drawBounds();
		painter.drawPorts();
	}
}
