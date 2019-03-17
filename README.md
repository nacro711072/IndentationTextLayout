# IndentationTextLayout

android 元件, 可以自動排序且縮排, 快速生成簡單的文字排版畫面。
![Main screen](/screenshot/main_screen.PNG)

# Feature
 - 快速生成具有縮排效果的文字
 - 支持多種排序樣式
 - 能遞迴顯示清單內容

# Usage
在需要的xml裡加入 `com.nacro.indent.IndentationTextLayout` :
```xml
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <com.nacro.indent.IndentationTextLayout
        android:id="@+id/itl_sample"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:padding="8dp"
        app:array_text="@array/sample1"
        app:label_style="dot"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        >

    </com.nacro.indent.IndentationTextLayout>
</android.support.constraint.ConstraintLayout>
```
其中`@array/sample1`可以在你的resource裡設置, 比如`strings.xml`: 
```xml
<string-array name="sample1">
    <item>item1</item>
    <item>item2</item>
    <item>item3</item>
    <item>item4</item>
</string-array>
```
### IndentationTextLayout
以下是 IndentationTextLayout 可以設置的所有屬性
```xml
        <com.nacro.indent.IndentationTextLayout
            android:id="@+id/itl"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:title="title"
            app:text_attr="@layout/indent_text_style"
            app:label_style="chinese_dayton_number"
            app:label_color="@color/black"
            app:sub_text_of_label="1"
            app:array_text="@array/sub_content4"
            app:leading_margin="24">
            
        </com.nacro.indent.IndentationTextLayout>
```


# License
```
   Copyright [2019] [Chia-Yang Hsu]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
