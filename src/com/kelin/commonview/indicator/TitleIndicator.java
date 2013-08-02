package com.kelin.commonview.indicator;


import java.util.List;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commonview.R;
import com.example.commonview.R.id;
import com.example.commonview.R.layout;
import com.example.commonview.R.styleable;



/**
 * A TitleIndicator is a Indicator which displays the title of left view
 * (if exist), the title of the current select view (centered) and the title of
 * the right view (if exist). When the user scrolls the ViewFlow then titles are
 * also scrolled.
 */
@SuppressWarnings("static-access")
public class TitleIndicator extends LinearLayout implements View.OnClickListener,
        OnFocusChangeListener {

    private static final String TAG = "TitleFlowIndicator";

    private static final float FOOTER_LINE_HEIGHT = 4.0f;

    private static final int FOOTER_COLOR = 0xFFFFC445;

    private static final float FOOTER_TRIANGLE_HEIGHT = 10;

    private int mCurrentScroll = 0;

    private List<TabInfo> mTabs;

    private ViewPager mViewPager;

    private ColorStateList mTextColor;

    private float mTextSizeNormal;
    private float mTextSizeSelected;

    private Path mPath = new Path();

    private Paint mPaintFooterLine;

    private Paint mPaintFooterTriangle;

    private float mFooterTriangleHeight;

    private float mFooterLineHeight;

    private int mSelectedTab = 0;

    private Context mContext;

    private final int BSSEEID = 0xffff00;;

    private boolean mChangeOnClick = true;

    private int mCurrID = 0;

    private int mPerItemWidth = 0;

    private int mTotal = 0;

    private LayoutInflater mInflater;


    /**
     * Default constructor
     */
    public TitleIndicator(Context context) {
        super(context);
        initDraw(FOOTER_LINE_HEIGHT, FOOTER_COLOR);
    }

    /**
     * The contructor used with an inflater
     *
     * @param context
     * @param attrs
     */
    public TitleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setOnFocusChangeListener(this);
        mContext = context;
        // Retrieve styles attributs
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleIndicator);
        // Retrieve the colors to be used for this view and apply them.
        int footerColor = a.getColor(R.styleable.TitleIndicator_footerColor, FOOTER_COLOR);
        mTextColor = a.getColorStateList(R.styleable.TitleIndicator_textColor);
        mTextSizeNormal = a.getDimension(R.styleable.TitleIndicator_textSizeNormal, 0);
        mTextSizeSelected = a.getDimension(R.styleable.TitleIndicator_textSizeSelected, mTextSizeNormal);
        mFooterLineHeight = a.getDimension(R.styleable.TitleIndicator_footerLineHeight,
                FOOTER_LINE_HEIGHT);
        mFooterTriangleHeight = a.getDimension(R.styleable.TitleIndicator_footerTriangleHeight,
                FOOTER_TRIANGLE_HEIGHT);

        initDraw(mFooterLineHeight, footerColor);
        a.recycle();
    }

    /**
     * Initialize draw objects
     */
    private void initDraw(float footerLineHeight, int footerColor) {
        mPaintFooterLine = new Paint();
        mPaintFooterLine.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintFooterLine.setStrokeWidth(footerLineHeight);
        mPaintFooterLine.setColor(footerColor);
        mPaintFooterTriangle = new Paint();
        mPaintFooterTriangle.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintFooterTriangle.setColor(footerColor);
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /*
     * (non-Javadoc)
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float scroll_x = 0;
        if (mTotal != 0) {
            mPerItemWidth = getWidth() / mTotal;
            int tabID = mSelectedTab;
            scroll_x = (mCurrentScroll - ((tabID) * (getWidth() + mViewPager.getPageMargin()))) / mTotal;
        } else {
            mPerItemWidth = getWidth();
            scroll_x = mCurrentScroll;
        }
        Path path = mPath;
        path.rewind();
        float offset = 0;
        float left_x = mSelectedTab * mPerItemWidth + offset + scroll_x;
        float right_x = (mSelectedTab + 1) * mPerItemWidth - offset + scroll_x;
        float top_y = getHeight() - mFooterLineHeight - mFooterTriangleHeight;
        float bottom_y = getHeight() - mFooterLineHeight;

        path.moveTo(left_x, top_y + 1f);
        path.lineTo(right_x, top_y + 1f);
        path.lineTo(right_x, bottom_y + 1f);
        path.lineTo(left_x, bottom_y + 1f);
        path.close();
        canvas.drawPath(path, mPaintFooterTriangle);
    }

    /**
     * Returns the title
     *
     * @param pos
     * @return
     */
    private String getTitle(int pos) {
        // Set the default title
        String title = "title " + pos;
        // If the TitleProvider exist
        if (mTabs != null && mTabs.size() > pos) {
            title = mTabs.get(pos).getName();
        }
        return title;
    }

    private int getIcon(int pos) {
        int ret = 0;
        if (mTabs != null && mTabs.size() > pos) {
            ret = mTabs.get(pos).getIcon();
        }
        return ret;
    }

    private boolean hasTips(int pos) {
        boolean ret = false;
        if (mTabs != null && mTabs.size() > pos) {
            ret = mTabs.get(pos).hasTips;
        }
        return ret;
    }

    public void onScrolled(int h) {
        mCurrentScroll = h;
        invalidate();
    }

    public synchronized void onSwitched(int position) {
        if (mSelectedTab == position) {
            return;
        }
        setCurrentTab(position);
        invalidate();
    }

    public void init(int startPos, List<TabInfo> tabs, ViewPager mViewPager) {
        removeAllViews();
        this.mViewPager = mViewPager;
        this.mTabs = tabs;
        this.mTotal = tabs.size();
        for (int i = 0; i < mTotal; i++) {
            add(i, getTitle(i), getIcon(i), hasTips(i));
        }
        setCurrentTab(startPos);
        invalidate();
    }

    public void updateChildTips(int postion, boolean showTips) {
        View child = getChildAt(postion);
        final ImageView tips = (ImageView) child.findViewById(R.id.tab_title_tips);
        if (showTips) {
            tips.setVisibility(View.VISIBLE);
        } else {
            tips.setVisibility(View.GONE);
        }
    }

    protected void add(int index, String label, int icon, boolean hasTips) {
        View tabIndicator = mInflater.inflate(R.layout.title_flow_indicator, this, false);
        final TextView tv = (TextView) tabIndicator.findViewById(R.id.tab_title);
        final ImageView tips = (ImageView) tabIndicator.findViewById(R.id.tab_title_tips);
        if (mTextColor != null) {
            tv.setTextColor(mTextColor);
        }
        if (mTextSizeNormal > 0) {
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSizeNormal);
        }
        tv.setText(label);
        if (icon > 0) {
            tv.setCompoundDrawablesWithIntrinsicBounds(0, icon, 0, 0);
        }
        if (hasTips) {
            tips.setVisibility(View.VISIBLE);
        } else {
            tips.setVisibility(View.GONE);
        }
        tabIndicator.setId(BSSEEID + (mCurrID++));
        tabIndicator.setOnClickListener(this);
        LinearLayout.LayoutParams lP = (LinearLayout.LayoutParams) tabIndicator.getLayoutParams();
        lP.gravity = Gravity.CENTER_VERTICAL;
        addView(tabIndicator);
    }

    public void setDisplayedPage(int index) {
        mSelectedTab = index;
    }

    public void setChangeOnClick(boolean changeOnClick) {
        mChangeOnClick = changeOnClick;
    }

    public boolean getChangeOnClick() {
        return mChangeOnClick;
    }

    @Override
    public void onClick(View v) {
        int position = v.getId() - BSSEEID;
        setCurrentTab(position);
    }

    public int getTabCount() {
        int children = getChildCount();
        return children;
    }

    public synchronized void setCurrentTab(int index) {
        if (index < 0 || index >= getTabCount()) {
            return;
        }
        View oldTab = getChildAt(mSelectedTab);
        oldTab.setSelected(false);
        setTabTextSize(oldTab, false);

        mSelectedTab = index;
        View newTab = getChildAt(mSelectedTab);
        newTab.setSelected(true);
        setTabTextSize(newTab, true);
        ((ImageView) newTab.findViewById(R.id.tab_title_tips)).setVisibility(View.GONE);

        mViewPager.setCurrentItem(mSelectedTab);
        invalidate();
    }

    private void setTabTextSize(View tab, boolean selected) {
        TextView tv = (TextView) tab.findViewById(R.id.tab_title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                selected ? mTextSizeSelected : mTextSizeNormal);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == this && hasFocus && getTabCount() > 0) {
            getChildAt(mSelectedTab).requestFocus();
            return;
        }

        if (hasFocus) {
            int i = 0;
            int numTabs = getTabCount();
            while (i < numTabs) {
                if (getChildAt(i) == v) {
                    setCurrentTab(i);
                    break;
                }
                i++;
            }
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mCurrentScroll == 0 && mSelectedTab != 0) {
            mCurrentScroll = (getWidth() + mViewPager.getPageMargin()) * mSelectedTab;
        }
    }
}
