package com.nacro.indentationtextlayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.nacro.indent.IndentSpanString;
import com.nacro.indent.IndentationTextLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        IndentationTextLayout sample =  (IndentationTextLayout) findViewById(R.id.itl_sample);
        IndentationTextLayout sub2 = (IndentationTextLayout) findViewById(R.id.itl_sub2);

        SpannableString str1 = new SpannableString("programming item1");
        str1.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString str2 = new SpannableString("programming item2");
        str2.setSpan(new ForegroundColorSpan(Color.parseColor("#ff0000")), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString str3 = new SpannableString("programming item3");
        str3.setSpan(new ForegroundColorSpan(Color.parseColor("#00ff00")), 0, str1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        sub2.setContent(new SpannableString[] {str1, str2, str3});
    }
}
