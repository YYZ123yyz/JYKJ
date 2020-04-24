package util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class LinkdoodTextView extends TextView {

	public LinkdoodTextView(Context context) {
		super(context);
	}

	public LinkdoodTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public LinkdoodTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		if (text == null) {
			text = "";
		}
		super.setText(text, type);
	}
}
