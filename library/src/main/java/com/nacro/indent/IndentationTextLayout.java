package com.nacro.indent;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;

import android.text.SpannableString;
import android.text.Spanned;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import static com.nacro.indent.IndentSpanString.LabelType_Number;

public class IndentationTextLayout extends LinearLayout {
    private boolean mHasTitle;
    private int[] mIndexOfLabelInLinearLayout;
    private int mLabelStyle;
    private int mSubTextOfLabel;
    private int mLeadingMargin;
    private int mLabelColor;
    private AttributeSet mTextAttr;
    private static final String TAG = "IndentationTextView";

    public IndentationTextLayout(Context context) {
        this(context, null);
    }

    public IndentationTextLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndentationTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

        if (attrs == null) {
            mLeadingMargin = convertDpToPx(getContext(), 24);
            mLabelStyle = LabelType_Number;
            mSubTextOfLabel = 0;
            return;
        }

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.IndentationTextLayout, defStyleAttr, 0);
        String title = a.getString(R.styleable.IndentationTextLayout_title);
        int strArrayId = a.getResourceId(R.styleable.IndentationTextLayout_array_text, 0);
        mLabelStyle = a.getInt(R.styleable.IndentationTextLayout_label_style, LabelType_Number);
        mSubTextOfLabel = a.getInt(R.styleable.IndentationTextLayout_sub_text_of_label, 0);
        mLeadingMargin = convertDpToPx(getContext(), a.getInt(R.styleable.IndentationTextLayout_leading_margin, 24));
        mLabelColor = a.getColor(R.styleable.IndentationTextLayout_label_color, 0);
        int textAttrId = a.getResourceId(R.styleable.IndentationTextLayout_text_attr, 0);
        if (textAttrId != 0) {
            setTextViewAttr(textAttrId);
        }

        a.recycle();

        setTitle(title);
        if (strArrayId != 0) {
            setContent(getResources().getStringArray(strArrayId));
        }
    }

    public AttributeSet getTextViewAttr() {
        return mTextAttr;
    }

    public void setTextViewAttr(int textAttrId) {
        XmlResourceParser parser = getResources().getLayout(textAttrId);
        int state = 0;
        do {
            try {
                state = parser.next();
            } catch (XmlPullParserException | IOException e1) {
                e1.printStackTrace();
            }
            if (state == XmlPullParser.START_TAG) {
                if (parser.getName().equals("TextView")) {
                    mTextAttr = Xml.asAttributeSet(parser);
                    break;
                }
            }
        } while(state != XmlPullParser.END_DOCUMENT);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {

        if (child instanceof IndentationTextLayout &&
                ((IndentationTextLayout) child).getSubTextOfLabel() != 0 &&
                ((IndentationTextLayout) child).getSubTextOfLabel() != mIndexOfLabelInLinearLayout.length) {

            int which = ((IndentationTextLayout) child).getSubTextOfLabel();
            super.addView(child, mIndexOfLabelInLinearLayout[which], params);
            updateIndex(which);
        } else {
            super.addView(child, index, params);
        }
    }

    private void updateIndex(int start) {
        for (int i = start + 1; i < mIndexOfLabelInLinearLayout.length; ++i) {
            mIndexOfLabelInLinearLayout[i] += 1;
        }
    }

    public boolean hasTitle() {
        return mHasTitle;
    }

    public void setContent(CharSequence[] array) {
        if (hasTitle()) {
            removeViews(1, getChildCount() - 1);
        } else {
            removeAllViews();
        }

        mIndexOfLabelInLinearLayout = new int[array.length];

        for (int i = 0; i < array.length; ++i) {
            mIndexOfLabelInLinearLayout[i] = hasTitle() ? i + 1 : i;
            TextView textView = new TextView(getContext(), mTextAttr);
            SpannableString spStr = new SpannableString(array[i]);
            spStr.setSpan(new IndentSpanString(mLeadingMargin, mLabelStyle, i + 1, mLabelColor), 0, spStr.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            textView.setText(spStr);
            addView(textView);
        }
    }

    public void setContentOfLabel(int which, CharSequence content) throws Exception {
        if (mIndexOfLabelInLinearLayout == null) {
            throw new Exception("No labels");
        }

        SpannableString spStr;
        if (content instanceof SpannableString) {
            spStr = (SpannableString) content;

        } else {
            spStr = new SpannableString(content);
        }

        IndentSpanString[] indentSpanString = spStr.getSpans(0, content.length(), IndentSpanString.class);
        if (indentSpanString.length == 0) {
            spStr.setSpan(new IndentSpanString(mLeadingMargin, mLabelStyle, which, mLabelColor), 0, content.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }

        View view = getChildAt(mIndexOfLabelInLinearLayout[which - 1]);
        if (view == null) {
            TextView textView = new TextView(getContext(), mTextAttr);
            textView.setText(spStr);
            addView(textView);
        } else {
            ((TextView) view).setText(spStr);
        }
    }

    public void setTitle(CharSequence title) {
        if (title == null) {
            mHasTitle = false;
            return;
        }

        View view = getChildAt(0);
        if (view != null) {
            ((TextView) view).setText(title);
        } else {
            TextView textView = new TextView(getContext(), mTextAttr);
            textView.setText(title);
            addView(textView, 0);
        }
        mHasTitle = true;
    }

    public void setSubTextOfLabel(int subTextOfLabel) {
        mSubTextOfLabel = subTextOfLabel;
    }

    public void setLeadingMargin(int leadingMargin) {
        mLeadingMargin = leadingMargin;
    }

    public int getSubTextOfLabel() {
        return mSubTextOfLabel;
    }

    public CharSequence getContentOfLabel(int which) {
        return ((TextView) getChildAt(mIndexOfLabelInLinearLayout[which - 1])).getText();
    }

    public CharSequence[] getContents() {
        ArrayList<CharSequence> contents = new ArrayList();

        for (int i = 0; i < mIndexOfLabelInLinearLayout.length; ++i) {
            contents.add(((TextView) getChildAt(mIndexOfLabelInLinearLayout[i - 1])).getText());
        }
        return contents.toArray(new CharSequence[] {});
    }

    private int convertDpToPx(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }
}