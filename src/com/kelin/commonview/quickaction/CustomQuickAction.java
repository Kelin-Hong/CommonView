package com.kelin.commonview.quickaction;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.kelin.commonview.R;


public class CustomQuickAction extends CustomPopupWindow {
    private final View root;
    private final ImageView mArrowUp;
    private final ImageView mArrowDown;
    private final LayoutInflater inflater;
    private final Context context;

    protected static final int ANIM_GROW_FROM_LEFT = 1;
    protected static final int ANIM_GROW_FROM_RIGHT = 2;
    protected static final int ANIM_GROW_FROM_CENTER = 3;
    protected static final int ANIM_REFLECT = 4;
    protected static final int ANIM_AUTO = 5;
    private static final int ARROW_UP = 0;
    private static final int ARROW_DOWN = 1;

    private int animStyle;
    private ViewGroup mTrack;
    private ScrollView scroller;
    private ArrayList<ActionItem> actionList;
    private int mSelectedIndex = -1;
    private boolean mShowAllIcon = false;

    /**
     * Constructor
     *
     * @param anchor
     *            {@link View} on where the popup window should be displayed
     */
    public CustomQuickAction(View anchor) {
        super(anchor);

        actionList = new ArrayList<ActionItem>();
        context = anchor.getContext();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        root = (ViewGroup) inflater.inflate(R.layout.view_quickaction, null);

        mArrowDown = (ImageView) root.findViewById(R.id.quickaction2_arrow_down);
        mArrowUp = (ImageView) root.findViewById(R.id.quickaction2_arrow_up);

        setContentView(root);

        mTrack = (ViewGroup) root.findViewById(R.id.quickaction2_tracks);
        scroller = (ScrollView) root.findViewById(R.id.quickaction2_scroller);
        animStyle = ANIM_AUTO;
    }

    /**
     * Set animation style
     *
     * @param animStyle
     *            animation style, default is set to ANIM_AUTO
     */
    public void setAnimStyle(int animStyle) {
        this.animStyle = animStyle;
    }

    /**
     * Add action item
     *
     * @param action
     *            {@link ActionItem2} object
     */
    public void addActionItem(ActionItem action) {
        actionList.add(action);
    }

    public void setSelectedIndex(int selectedIndex) {
        this.mSelectedIndex = selectedIndex;
    }

    public void setShowAllIcon(boolean show) {
        this.mShowAllIcon = show;
    }

    /**
     * Show popup window. Popup is automatically positioned, on top or bottom of
     * anchor view.
     *
     */
    public void show() {
        show(null);
    }

    public void show(Rect anchorRect) {
        preShow();

        int xPos, yPos;

        if (anchorRect == null) {
            anchorRect = new Rect();
            View v = anchor;
            Rect r = new Rect();
            v.getGlobalVisibleRect(anchorRect);
            try {
                v = (View) v.getParent();
                v.getGlobalVisibleRect(r);
                if (!r.isEmpty()) {
                    if (!r.contains(anchorRect)) {
                        anchorRect = r;
                        anchor = v;
                    }
                }
            } catch (Exception e) {
            }
        }

        createActionList();

        root.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        root.measure(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        int rootHeight = root.getMeasuredHeight();
        int rootWidth = root.getMeasuredWidth();

        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        // automatically get X coord of popup (top left)
//        System.out.println("===0== " + anchorRect.left + " : " + anchor.getWidth() + " : " + rootWidth);
        if ((anchorRect.left + rootWidth) > screenWidth) {
            xPos = screenWidth - rootWidth;
            //anchorRect.left - (rootWidth - anchor.getWidth()) *
            //        (screenWidth == anchor.getWidth() ? -1 : 1);
//            System.out.println("===1== " + rootWidth + " : " + anchor.getWidth() + " : " + xPos);
        } else {
            if (anchorRect.width() > rootWidth) {
                xPos = anchorRect.centerX() - (rootWidth / 2);
            } else {
                xPos = anchorRect.left;
            }
        }

        int statusBarHeight = context.getResources().getDimensionPixelSize(R.dimen.status_bar_height);
        int dyTop = anchorRect.top;
        int dyBottom = screenHeight - anchorRect.bottom;

        boolean onTop = dyTop - statusBarHeight > dyBottom;

        if (onTop) {
            if (rootHeight > dyTop) {
                yPos = statusBarHeight;
                LayoutParams l = scroller.getLayoutParams();
                l.height = dyTop - anchor.getHeight();
            } else {
                yPos = anchorRect.top - rootHeight;
            }
        } else {
            yPos = anchorRect.bottom;

            if (rootHeight > dyBottom) {
                LayoutParams l = scroller.getLayoutParams();
                l.height = dyBottom;
            }
        }

//        System.out.println("==== " + anchorRect.centerX() + " : " + xPos);
        showArrow(((onTop) ? ARROW_DOWN : ARROW_UP), anchorRect.centerX() - xPos);

        setAnimationStyle(screenWidth, anchorRect.centerX(), onTop);

        window.showAtLocation(anchor, Gravity.NO_GRAVITY, xPos, yPos);
    }

    /**
     * Set animation style
     *
     * @param screenWidth
     *            screen width
     * @param requestedX
     *            distance from left edge
     * @param onTop
     *            flag to indicate where the popup should be displayed. Set TRUE
     *            if displayed on top of anchor view and vice versa
     */
    private void setAnimationStyle(int screenWidth, int requestedX, boolean onTop) {
        int arrowPos = requestedX - mArrowUp.getMeasuredWidth() / 2;

        switch (animStyle) {
        case ANIM_GROW_FROM_LEFT:
            window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
            break;

        case ANIM_GROW_FROM_RIGHT:
            window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right : R.style.Animations_PopDownMenu_Right);
            break;

        case ANIM_GROW_FROM_CENTER:
            window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
            break;

        case ANIM_REFLECT:
            window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Reflect : R.style.Animations_PopDownMenu_Reflect);
            break;

        case ANIM_AUTO:
            if (arrowPos <= screenWidth / 4) {
                window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Left : R.style.Animations_PopDownMenu_Left);
            } else if (arrowPos > screenWidth / 4 && arrowPos < 3 * (screenWidth / 4)) {
                window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Center : R.style.Animations_PopDownMenu_Center);
            } else {
                window.setAnimationStyle((onTop) ? R.style.Animations_PopUpMenu_Right : R.style.Animations_PopDownMenu_Right);
            }

            break;
        }
    }

    /**
     * Create action list
     */
    private void createActionList() {

        for (int i = 0; i < actionList.size(); i++) {
            ActionItem item = actionList.get(i);

            View view = (LinearLayout) inflater.inflate(R.layout.quickaction_item, null);
            View viewSelected = view.findViewById(R.id.quickaction2_icon);
            if (mShowAllIcon) {
                viewSelected.setVisibility(View.VISIBLE);
            } else if (mSelectedIndex == -1) {
                viewSelected.setVisibility(View.GONE);
            } else {
                viewSelected.setVisibility(this.mSelectedIndex == i ? View.VISIBLE : View.INVISIBLE);
            }
            item.setViews(view);

            if (i > 0) {
                mTrack.addView(getSeparator());
            }
            mTrack.addView(view);
        }
    }

    private View getSeparator() {
        View v = inflater.inflate(R.layout.quickaction_separator, null);
        return v;
    }

    /**
     * Show arrow
     *
     * @param whichArrow
     *            arrow type Resource id
     * @param requestedX
     *            distance from left screen
     */
    private void showArrow(int whichArrow, int requestedX) {
        final View showArrow = (whichArrow == ARROW_UP) ? mArrowUp : mArrowDown;
        final View hideArrow = (whichArrow == ARROW_UP) ? mArrowDown : mArrowUp;

        final int arrowWidth = mArrowUp.getMeasuredWidth();

        showArrow.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams param = (ViewGroup.MarginLayoutParams) showArrow.getLayoutParams();

        param.leftMargin = requestedX > arrowWidth ? requestedX - arrowWidth / 2 : arrowWidth / 2;

        hideArrow.setVisibility(View.INVISIBLE);
    }
}
