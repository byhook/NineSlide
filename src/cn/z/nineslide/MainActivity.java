package cn.z.nineslide;

import android.app.Activity;
import android.os.Bundle;

import cn.z.widget.NineDragController;
import cn.z.widget.NineDragLayout;
import cn.z.widget.NineSlide;

public class MainActivity extends Activity {

	private NineDragController mDragController;

	private NineSlide nineSlide;

	private NineDragLayout nineDragLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		mDragController = new NineDragController();
		initView();
	}

	private void initView() {
		nineSlide = (NineSlide) findViewById(R.id.nineLayout);
		nineSlide.setController(mDragController);
		nineDragLayout = (NineDragLayout) findViewById(R.id.nineLayout);
		nineDragLayout.setController(mDragController);
	}

}
