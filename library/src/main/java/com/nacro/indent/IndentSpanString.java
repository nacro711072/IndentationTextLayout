package com.nacro.indent;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

/**
 * Created by terry on 2018/7/24.
 */

public class IndentSpanString implements LeadingMarginSpan {
    private int mLeadingMargin;
    private int mLabelStyle;
    private int mLabelOrder;
    private int mLabelColor;

    private CharSequence mCustomBullet;

    public static final int LabelType_None = -1;
    public static final int LabelType_Number = 0;
    public static final int LabelType_Dot = 1;
    public static final int LabelType_Letter = 2;
    public static final int LabelType_Parentheses = 3;
    public static final int LabelType_Chinese_Num_Dayton = 4;
    public static final int LabelType_Chinese_Num_Parentheses = 5;
    public static final int LabelType_Custom = -2;

    public IndentSpanString(int leadingMargin) {
        this(leadingMargin, 0);
    }

    public IndentSpanString(int leadingMargin, int labelStyle) {
        this(leadingMargin, labelStyle, 1, 0);
    }

    public IndentSpanString(int leadingMargin, int labelStyle, int labelOrder, int labelColor) {
        mLeadingMargin = leadingMargin;
        mLabelStyle = labelStyle;
        mLabelOrder = labelOrder > 0 ? labelOrder : 1;
        mLabelColor = labelColor;
    }

    public IndentSpanString(int leadingMargin, CharSequence customBullet, int labelColor) {
        mLeadingMargin = leadingMargin;
        mLabelStyle = LabelType_Custom;
        mCustomBullet = customBullet;
        mLabelColor = labelColor;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return mLeadingMargin;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {
        int tempColor = p.getColor();
        if (mLabelColor != 0) {
            p.setColor(mLabelColor);
        }

        if (first) {
            switch (mLabelStyle) {
                case LabelType_None:
                    break;
                case LabelType_Number:
                    c.drawText(mLabelOrder + ".", 0, baseline, p);
                    break;
                case LabelType_Dot:
                    c.drawText("・", 0, baseline, p);
                    break;
                case LabelType_Letter:
                    c.drawText(String.valueOf(Character.toChars(mLabelOrder + 96)) + ".", 0, baseline, p);
                    break;
                case LabelType_Parentheses:
                    c.drawText("(" + mLabelOrder + ")", 0, baseline, p);
                    break;
                case LabelType_Chinese_Num_Dayton:
                    c.drawText(NumToChineseNum.toChineseNum(mLabelOrder) + "、 ", 0, baseline, p);
                    break;
                case LabelType_Chinese_Num_Parentheses:
                    c.drawText("(" + NumToChineseNum.toChineseNum(mLabelOrder) + ")", 0, baseline, p);
                    break;
                case LabelType_Custom:
                    if (mCustomBullet != null) {
                        c.drawText(mCustomBullet, 0, mCustomBullet.length(), 0, baseline, p);
                    }
                    break;
            }
        }
        p.setColor(tempColor);
    }
}
