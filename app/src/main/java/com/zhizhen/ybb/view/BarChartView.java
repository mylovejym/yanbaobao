package com.zhizhen.ybb.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 柱状�?
 */
public class BarChartView extends View {
	/**
	 * 公共部分
	 */
	protected float canvasHeight;
	protected float canvasWidth;
	private Paint mPaint;
	private float maxValue = 0;// Y轴最大值
	private float minValue;
	// private float titleHeight = 0.0f;
	private float taggingHeight; // 标注的高度
	private float marginTop; // 与顶部留的空隙
	private float marginBottom = 0; // 与底部留的空隙
	private float marginLeft; // 与顶部留的空隙
	private float marginRight = 0; // 与底部留的空隙

	private int horizontalNum = 5;// 横坐标数量

	private RectF coordinateRect = new RectF(); // 画图区域

	private int colorCoordinates = 0xFF999999; // 坐标轴的颜色

	private int[] colors = { 0xFF4A94F2, 0xFFEE5755, 0xFFF27744 }; // 柱子的颜色
	private int yIndex = 5; // Y轴位置
	private List<List<Float>> yRawData = new ArrayList<>();

	private int priceWeight = 1; // 倍数

	/**
	 * 横坐标值
	 */
	private List<String> xRawDatas = new ArrayList<>();

	private String company = ""; // 单位
	private boolean isCompanyUpdate = false; // 单位
	private String companyNews; // 单位
	private List<String> tagging = new ArrayList<>(); // 标注
	private List<TaggingCoordinate> taggingCoordinates = new ArrayList<>(); // 标注的坐标

	private int offsetWidth = 0;
	private int offsetWidthMax = 0;
	private GestureDetector mGestureDetector;
	private boolean isInteger = false;// 是否是整数坐标
	private float cutoffwidth = 0;

	public BarChartView(Context context, List<String> tagging,
			List<String> xRawData, List<Float>... yRawData) {
		super(context);
		initView();
		setData(tagging, xRawData, yRawData);
	}

	public BarChartView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		setWillNotDraw(false);
		this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		marginBottom = dip2px(25);
		marginLeft = dip2px(45);
		marginRight = dip2px(10);
		marginTop = dip2px(10);
		taggingHeight = 0;
		mGestureDetector = new GestureDetector(getContext(),
				new GestureListener());
	}

	private float mDownPosX = 0;
	private float mDownPosY = 0;

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if (offsetWidthMax != 0) {
			final float x = event.getX();
			final float y = event.getY();
			final int action = event.getAction();
			switch (action) {
			case MotionEvent.ACTION_DOWN:
				mDownPosX = x;
				mDownPosY = y;
				break;
			case MotionEvent.ACTION_MOVE:
				final float deltaX = Math.abs(x - mDownPosX);
				final float deltaY = Math.abs(y - mDownPosY);
				if (deltaX > deltaY) {
					getParent().requestDisallowInterceptTouchEvent(true);
				} else {
					getParent().requestDisallowInterceptTouchEvent(false);
					return true;
				}
				break;
			}
			mGestureDetector.onTouchEvent(event);
			return true;
		} else {
			getParent().requestDisallowInterceptTouchEvent(false);
			return super.dispatchTouchEvent(event);
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		this.canvasHeight = h;
		this.canvasWidth = w;
		coordinateRect = new RectF(marginLeft, marginTop + taggingHeight,
				canvasWidth - marginRight, canvasHeight - marginBottom);
		horizontalNum = (int) (coordinateRect.width() / dip2px(60));
		updateTagging();
		updateCutoffwidth();
	}

	public int getSize() {
		if (xRawDatas == null) {
			return 0;
		}
		return xRawDatas.size();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		mPaint.setStrokeWidth(dip2px(1));
		if (occupyingText != null) {
			drawText(occupyingText, canvasWidth / 2, canvasHeight / 2
					+ dip2px(15), canvas, Align.CENTER, 20, colorCoordinates);
		} else {
			drawBar(canvas);
			drawTagging(canvas);
		}
		drawAllXLine(canvas);
	}

	/**
	 * 画所有横向表格，包括X轴
	 */
	private void drawAllXLine(Canvas canvas) {
		float cutoffHeight = coordinateRect.height() / 5;
		float averageValue = (maxValue - minValue) / 5;
		// float deviation = cutoffHeight / 5;
		float startX = coordinateRect.left + offsetWidth;
		float stopX = coordinateRect.right + offsetWidth;
		if (companyNews != null) {
			drawText(companyNews, startX + dip2px(2), coordinateRect.top
					- dip2px(2), canvas, Align.LEFT, 9, colorCoordinates);
		}

		for (int i = 0; i < 6; i++) {
			if (yIndex == i) {
				drawText("0", startX - dip2px(5), coordinateRect.top
						+ cutoffHeight * i + dip2px(2), canvas, Align.RIGHT, 8,
						colorCoordinates);
				mPaint.setColor(colorCoordinates);
				canvas.drawLine(startX, coordinateRect.top, startX,
						coordinateRect.bottom, mPaint);
				canvas.drawLine(startX, coordinateRect.top + cutoffHeight * i,
						stopX, coordinateRect.top + cutoffHeight * i, mPaint);
				canvas.drawCircle(startX, coordinateRect.top, dip2px(2), mPaint);
				canvas.drawCircle(startX, coordinateRect.bottom, dip2px(2),
						mPaint);
				canvas.drawCircle(startX,
						coordinateRect.top + cutoffHeight * i, dip2px(2),
						mPaint);
				canvas.drawCircle(stopX, coordinateRect.top + cutoffHeight * i,
						dip2px(2), mPaint);
			} else {
				drawText(getTwoStepAndInt((maxValue - averageValue * i)
						/ priceWeight)
						+ "", startX - dip2px(5), coordinateRect.top
						+ cutoffHeight * i + dip2px(2), canvas, Align.RIGHT, 8,
						colorCoordinates);
			}
		}
	}

	/**
	 * 画柱子
	 */
	private void drawBar(Canvas canvas) {
		mPaint.setStyle(Style.FILL);
		float originY = coordinateRect.top + coordinateRect.height() * yIndex
				/ 5;

		for (int i = 0; i < xRawDatas.size(); i++) {
			for (int j = 0; j < yRawData.size(); j++) {
				List<Float> data = yRawData.get(j);
				float startX = coordinateRect.left + cutoffwidth * i
						* (yRawData.size() + 1) + cutoffwidth + cutoffwidth * j;
				float stopX = coordinateRect.left + cutoffwidth * i
						* (yRawData.size() + 1) + cutoffwidth * 2 + cutoffwidth
						* j;
				if (stopX <= offsetWidth + coordinateRect.left) {
					continue;
				}
				Float yData = data.get(i);
				if (yData == Float.MAX_VALUE) {
					continue;
				}
				float stopY = getCutoffKLY(yData);
				mPaint.setColor(colors[j % colors.length]);

				if (startX < offsetWidth + coordinateRect.left) {
					startX = offsetWidth + coordinateRect.left;
				} else if (stopX > offsetWidth + coordinateRect.right) {
					stopX = offsetWidth + coordinateRect.right;
				} else {
					if (stopY < originY) {
						drawText(getTwoStepAndInt(data.get(i) / priceWeight),
								(startX + stopX) / 2, stopY - dip2px(2),
								canvas, Align.CENTER, 8, colors[j
										% colors.length]);
					} else {
						drawText(getTwoStepAndInt(data.get(i) / priceWeight),
								(startX + stopX) / 2, stopY + dip2px(10),
								canvas, Align.CENTER, 8, colors[j
										% colors.length]);
					}
				}
				if (stopY < originY) {
					canvas.drawRect(startX, stopY, stopX, originY, mPaint);
				} else {
					canvas.drawRect(startX, originY, stopX, stopY, mPaint);
				}
			}
			if (coordinateRect.left + cutoffwidth * i * (yRawData.size() + 1)
					+ cutoffwidth > offsetWidth + coordinateRect.left) {
				drawText(xRawDatas.get(i), coordinateRect.left + cutoffwidth
						* i * (yRawData.size() + 1) + cutoffwidth + cutoffwidth
						* yRawData.size() / 2, canvasHeight - dip2px(5),
						canvas, Align.CENTER, 10, Color.BLACK); // X轴上的坐标
			}
		}
	}

	/**
	 * 设置标注
	 */
	private void drawTagging(Canvas canvas) {
		if (tagging == null) {
			return;
		}
		mPaint.setStyle(Style.FILL);
		for (int i = 0; i < tagging.size(); i++) {
			TaggingCoordinate mTaggingCoordinate = taggingCoordinates.get(i);
			float y = mTaggingCoordinate.height();
			float startX = mTaggingCoordinate.width() + offsetWidth;
			mPaint.setColor(colors[i % colors.length]);
			canvas.drawRect(startX, y - dip2px(5), startX + dip2px(10), y
					+ dip2px(5), mPaint);
			startX += dip2px(12);
			drawText(tagging.get(i), startX, y + dip2px(4), canvas, Align.LEFT,
					10, colors[i % colors.length]);
		}
	}

	private String occupyingText;

	/**
	 * 设置占位文字
	 *
	 * @param occupyingText
	 */
	public void setOccupyingText(String occupyingText) {
		this.occupyingText = occupyingText;
		invalidate();
	}

	/**
	 * 设置值 tagging：标注 xRawData：x轴坐标 yRawData：为柱形图的内容
	 */
	public void setData(List<String> tagging, List<String> xRawData,
			List<Float>... yRawData) {
		occupyingText = null;
		this.xRawDatas.clear();
		this.yRawData.clear();
		this.xRawDatas.addAll(xRawData);
		this.tagging = tagging;
		Collections.addAll(this.yRawData, yRawData);
		updateTagging();
		updateCutoffwidth();
		scroll();
		invalidate();
	}

	/**
	 * 设置标注的坐标
	 */
	private void updateTagging() {
		if (tagging != null) {
			taggingCoordinates.clear();
			mPaint.setTextSize(sp2px(10));
			float taggingCoordinateW = marginLeft;// 坐标
			float taggingCoordinateH = dip2px(15);
			int lineNum = 1;
			for (int i = 0; i < tagging.size(); i++) {
				float taggingWidth = mPaint.measureText(tagging.get(i))
						+ dip2px(20);
				if (coordinateRect.right - taggingCoordinateW < taggingWidth) {
					lineNum++;
					for (int j = i - 1; j >= 0; j--) {
						TaggingCoordinate mTaggingCoordinate = taggingCoordinates
								.get(j);
						if (mTaggingCoordinate.height() == taggingCoordinateH) {
							mTaggingCoordinate.setWidth(mTaggingCoordinate
									.width()
									+ coordinateRect.right
									- taggingCoordinateW);
						} else {
							break;
						}
					}
					taggingCoordinateW = marginLeft;
					taggingHeight = +dip2px(20);
				}
				TaggingCoordinate mTaggingCoordinate = new TaggingCoordinate();
				mTaggingCoordinate.setHeight(taggingCoordinateH);
				mTaggingCoordinate.setWidth(taggingCoordinateW);
				taggingCoordinates.add(mTaggingCoordinate);

				taggingCoordinateW += taggingWidth;
			}
			for (int i = tagging.size() - 1; i >= 0; i--) {
				TaggingCoordinate mTaggingCoordinateNews = taggingCoordinates
						.get(i);
				if (mTaggingCoordinateNews.height() == taggingCoordinateH) {
					mTaggingCoordinateNews.setWidth(mTaggingCoordinateNews
							.width()
							+ coordinateRect.right
							- taggingCoordinateW);
				} else {
					break;
				}
			}
			taggingHeight = lineNum * dip2px(20) + dip2px(5);

		} else {
			taggingHeight = dip2px(5);
		}
	}

	private void updateCutoffwidth() {
		if (xRawDatas.size() > horizontalNum) {
			offsetWidthMax = (int) (((this.yRawData.size() + 1)
					* xRawDatas.size() + 1)
					* coordinateRect.width()
					/ ((this.yRawData.size() + 1) * horizontalNum + 1) - coordinateRect
					.width());
			offsetWidth = offsetWidthMax;
			cutoffwidth = coordinateRect.width()
					/ (horizontalNum * (this.yRawData.size() + 1) + 1);
			int start = (int) (offsetWidth / ((this.yRawData.size() + 1) * cutoffwidth));
			int stop = (int) ((offsetWidth + coordinateRect.width() + cutoffwidth
					* this.yRawData.size()) / ((this.yRawData.size() + 1) * cutoffwidth));
			initMaxAndMin(start, stop);
		} else {
			cutoffwidth = coordinateRect.width()
					/ (xRawDatas.size() * (this.yRawData.size() + 1) + 1);
			if (cutoffwidth > coordinateRect.width() / 8) {
				cutoffwidth = coordinateRect.width() / 8;
			}
			initMaxAndMin(0, this.xRawDatas.size());
			offsetWidth = 0;
			offsetWidthMax = 0;
		}
		scroll();
	}

	private boolean initMaxAndMin(int start, int stop) {
		maxValue = 10;
		minValue = 0;
		if (isCompanyUpdate) {
			if (maxValue > 300000000 || minValue < -300000000) {
				companyNews = "亿" + company;
				priceWeight = 100000000;
			} else if (maxValue > 3000000 || minValue < -3000000) {
				companyNews = "百万" + company;
				priceWeight = 1000000;
			} else if (maxValue > 30000 || minValue < -30000) {
				companyNews = "万" + company;
				priceWeight = 10000;
			} else if (maxValue > 300 || minValue < -300) {
				companyNews = "百" + company;
				priceWeight = 100;
			} else {
				companyNews = company;
			}
		} else {
			companyNews = company;
		}
		if (minValue < 0) {
			marginBottom = dip2px(30);
		}
		coordinateRect = new RectF(marginLeft, marginTop + taggingHeight,
				canvasWidth - marginRight, canvasHeight - marginBottom);

		return true;
	}

//	private boolean initMaxAndMin(int start, int stop) {
//		if (stop <= 0) {
//			return true;
//		}
//		if (stop > this.xRawDatas.size()) {
//			stop = this.xRawDatas.size();
//		}
//		List<Float> yRawDataNews = new ArrayList<>();
//		for (int i = 0; i < this.yRawData.size(); i++) {
//			yRawDataNews.addAll(yRawData.get(i).subList(start, stop));
//		}
//		float maxValueNews = getMaxArray(yRawDataNews);
//		float minValueNews = getMinArray(yRawDataNews);
//		if (isInteger) {
//			if (maxValueNews >= 0 && minValueNews >= 0) {
//				minValueNews = 0;
//				maxValueNews = ((int) ((maxValueNews - 1) / 5 + 1)) * 5;
//				yIndex = 5;
//			} else if (maxValueNews <= 0 && minValueNews < 0) {
//				maxValueNews = 0;
//				minValueNews = ((int) ((minValueNews) / 5)) * 5;
//				yIndex = 0;
//			} else {
//				float proportion = Math.abs(maxValueNews / minValueNews); // 比例
//				if (proportion >= 4) {
//					maxValueNews = ((int) ((maxValueNews - 1) / 4 + 1)) * 4;
//					minValueNews = -maxValueNews / 4;
//					yIndex = 4;
//				} else if (proportion >= 1) {
//					maxValueNews = ((int) ((maxValueNews - 1) / 3 + 1)) * 3;
//					minValueNews = -maxValueNews * 2 / 3;
//					yIndex = 3;
//				} else if (proportion >= 1 / 4) {
//					minValueNews = ((int) ((minValueNews) / 3)) * 3;
//					maxValueNews = -minValueNews * 2 / 3;
//					yIndex = 2;
//				} else {
//					minValueNews = ((int) ((minValueNews) / 4)) * 4;
//					maxValueNews = -minValueNews / 4;
//					yIndex = 1;
//				}
//			}
//		} else {
//			if (minValueNews < 0 && maxValueNews > 0) {
//				float proportion = Math.abs(maxValueNews / minValueNews); // 比例
//				if (proportion >= 4) {
//					minValueNews = -maxValueNews / 4;
//					yIndex = 4;
//				} else if (proportion >= 1) {
//					minValueNews = -maxValueNews * 2 / 3;
//					yIndex = 3;
//				} else if (proportion >= 1 / 4) {
//					maxValueNews = -minValueNews * 2 / 3;
//					yIndex = 2;
//				} else {
//					maxValueNews = -minValueNews / 4;
//					yIndex = 1;
//				}
//			} else if (minValueNews >= 0 && maxValueNews > 0) {
//				yIndex = 5;
//				minValueNews = 0;
//			} else if (minValueNews < 0 && maxValueNews <= 0) {
//				yIndex = 0;
//				maxValueNews = 0;
//			}
//		}
//
//		if (maxValueNews == minValueNews) {
//			minValueNews = +1;
//		}
//		if (maxValueNews == maxValue && minValue == minValueNews) {
//			return true;
//		}
//		maxValue = maxValueNews;
//		minValue = minValueNews;
//		if (isCompanyUpdate) {
//			if (maxValue > 300000000 || minValue < -300000000) {
//				companyNews = "亿" + company;
//				priceWeight = 100000000;
//			} else if (maxValue > 3000000 || minValue < -3000000) {
//				companyNews = "百万" + company;
//				priceWeight = 1000000;
//			} else if (maxValue > 30000 || minValue < -30000) {
//				companyNews = "万" + company;
//				priceWeight = 10000;
//			} else if (maxValue > 300 || minValue < -300) {
//				companyNews = "百" + company;
//				priceWeight = 100;
//			} else {
//				companyNews = company;
//			}
//		} else {
//			companyNews = company;
//		}
//		if (minValue < 0) {
//			marginBottom = dip2px(30);
//		}
//		coordinateRect = new RectF(marginLeft, marginTop + taggingHeight,
//				canvasWidth - marginRight, canvasHeight - marginBottom);
//		return false;
//	}

	/**
	 * 根据数据大小返回Y坐标
	 */
	private float getCutoffKLY(float price) {
		float priceY = coordinateRect.bottom - coordinateRect.height()
				* (price - minValue) / (maxValue - minValue);
		if (priceY < coordinateRect.top)
			priceY = coordinateRect.top;
		if (priceY > coordinateRect.bottom)
			priceY = coordinateRect.bottom;
		return priceY;
	}

	public String getCompany() {
		return company;
	}

	/**
	 * 设置单位，是否需要替换单位
	 *
	 * @param company
	 * @param isCompanyUpdate
	 */
	public void setCompany(String company, boolean isCompanyUpdate) {
		this.isCompanyUpdate = isCompanyUpdate;
		this.company = company;
	}

	/**
	 * 设置是否内容为整型
	 *
	 * @param integer
	 */
	public void setInteger(boolean integer) {
		isInteger = integer;
	}

	/**
	 * 设置颜色
	 *
	 * @param colors
	 */
	public void setColors(int[] colors) {
		this.colors = colors;
	}

	private static class TaggingCoordinate {
		private float height;
		private float width;

		public float height() {
			return height;
		}

		public void setHeight(float height) {
			this.height = height;
		}

		public float width() {
			return width;
		}

		public void setWidth(float width) {
			this.width = width;
		}
	}

	/**
	 * 返回最大值
	 **/
	protected float getMaxArray(List<Float> array) {
		if (array.size() == 0)
			return 0;
		float max = array.get(0) == Float.MAX_VALUE ? 0 : array.get(0);
		for (float i : array) {
			if (i == Float.MAX_VALUE) {
				continue;
			}
			max = max > i ? max : i;
		}

		return max;
	}

	/**
	 * 返回坐标最小值
	 **/
	protected float getMinArray(List<Float> array) {
		if (array.size() == 0)
			return 0;
		float min = array.get(0) == Float.MAX_VALUE ? 0 : array.get(0);
		for (float i : array) {
			if (i == Float.MAX_VALUE) {
				continue;
			}
			min = min < i ? min : i;
		}
		return min;
	}

	protected void drawText(String text, float x, float y, Canvas canvas,
			Align align, float textSize, @ColorInt int color) {
		Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
		p.setTextSize(sp2px(textSize));
		p.setColor(color);
		p.setTextAlign(align);
		canvas.drawText(text, x, y, p);
	}

	private class GestureListener extends
			GestureDetector.SimpleOnGestureListener {
		/**
		 * Touch了滑动时触发
		 */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			if (offsetWidthMax == 0) {
				return super.onScroll(e1, e2, distanceX, distanceY);
			}
			if (distanceX < 0) {
				// 往左滑动
				if (offsetWidth > 0) {
					offsetWidth += distanceX;
					if (offsetWidth < 0) {
						offsetWidth = 0;
					}
					scroll();
				}
			} else {
				// 往右滑动
				if (offsetWidth < offsetWidthMax) {
					offsetWidth = offsetWidth + distanceX < offsetWidthMax ? (int) (offsetWidth + distanceX)
							: offsetWidthMax;
					scroll();
				}
			}
			return super.onScroll(e1, e2, distanceX, distanceY);
		}
	}

	public void scroll() {
		scrollTo(offsetWidth, 0);
		int start = (int) (offsetWidth / ((yRawData.size() + 1) * cutoffwidth));
		int stop = (int) ((offsetWidth + coordinateRect.width() + yRawData
				.size() * cutoffwidth) / ((yRawData.size() + 1) * cutoffwidth));
		if (!initMaxAndMin(start, stop)) {
			invalidate();
		}
	}

	protected float px2sp(float pxValue) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (pxValue / scale + 0.5f);
	}

	protected float sp2px(float spValue) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (spValue * scale + 0.5f);
	}

	protected float dip2px(float dpValue) {
		final float scale = getContext().getResources().getDisplayMetrics().density;
		return (dpValue * scale + 0.5f);
	}

	public static String getTwoStepAndInt(double vaule) {
		try {
			if (vaule == (int) vaule) {
				return ((int) vaule) + "";
			} else {
				DecimalFormat df = new DecimalFormat("###########0.00");
				return df.format(Math.round(vaule * 100) / 100.0);
			}
		} catch (Exception e) {
		}
		return Math.round(vaule * 100) / 100.00f + "";
	}

}
