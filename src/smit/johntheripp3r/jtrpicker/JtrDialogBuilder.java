/*
 * Copyright [2013] Smit Choksi
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 * 
 */

package smit.johntheripp3r.jtrpicker;

import java.util.ArrayList;
import java.util.List;

import net.simonvt.numberpicker.NumberPicker;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author johntheripp3r
 */
public class JtrDialogBuilder extends Builder
{
	private Context mContext;
	private LinearLayout mLinearLayout;
	private LayoutInflater mInflater;
	private AlertDialog mAlertDialog;
	private OnClickListener positiveClickListener, negativeClickListener, neuralClickListener;
	private CharSequence positiveText, negativeText, neutralText;
	private List<NumberPicker> mNumberPickers;
	
	// public final static String POSITIVE_BUTTON_TEXT = "Ok";
	// public final static String NEGATIVE_BUTTON_TEXT = "Cancel";
	// public final static String NEUTRAL_BUTTON_TEXT = "Ok";
	
	public JtrDialogBuilder(Context context)
	{
		super(context);
		this.mContext = context;
		this.mLinearLayout = new LinearLayout(mContext);
		this.mLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
		this.mLinearLayout.setGravity(Gravity.CENTER);
		this.mInflater = LayoutInflater.from(mContext);
	}
	
	public JtrDialogBuilder addPickerWithValues(int minValue, int maxValue, int defaultValue)
	{
		NumberPicker np = (NumberPicker) mInflater.inflate(R.layout.number_holo_light, null);
		np.setValue(defaultValue);
		np.setMaxValue(maxValue);
		np.setMinValue(minValue);
		this.mLinearLayout.addView(np);
		return this;
	}
	
	public JtrDialogBuilder addPicker(NumberPicker numberPicker)
	{
		this.mLinearLayout.addView(numberPicker);
		return this;
	}
	
	public JtrDialogBuilder addPickerWithValues(String[] displayedValues, int defaultValue)
	{
		NumberPicker np = (NumberPicker) mInflater.inflate(R.layout.number_holo_light, null);
		np.setMinValue(0);
		np.setMaxValue(displayedValues.length - 1);
		np.setValue(defaultValue);
		np.setDisplayedValues(displayedValues);
		this.mLinearLayout.addView(np);
		return this;
	}
	
	public JtrDialogBuilder addPickerWithValues(String... displayedValues)
	{
		NumberPicker np = (NumberPicker) mInflater.inflate(R.layout.number_holo_light, null);
		np.setMinValue(0);
		np.setMaxValue(displayedValues.length - 1);
		np.setDisplayedValues(displayedValues);
		this.mLinearLayout.addView(np);
		return this;
	}
	
	public JtrDialogBuilder addPickerWithValues(int minValue, int maxValue)
	{
		NumberPicker np = (NumberPicker) mInflater.inflate(R.layout.number_holo_light, null);
		np.setMaxValue(maxValue);
		np.setMinValue(minValue);
		this.mLinearLayout.addView(np);
		return this;
	}
	
	public JtrDialogBuilder addText(String text)
	{
		TextView tv = new TextView(mContext);
		tv.setTextSize(23.5f);
		tv.setText(text);
		this.mLinearLayout.addView(tv);
		return this;
	}
	
	@Override
	public AlertDialog create()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
				.setView(mLinearLayout);
		
		if(this.positiveClickListener != null)
			builder.setPositiveButton(this.positiveText, positiveClickListener);
		if(this.negativeClickListener != null)
			builder.setNegativeButton(this.negativeText, negativeClickListener);
		if(this.neuralClickListener != null)
			builder.setNegativeButton(this.neutralText, neuralClickListener);
		this.mAlertDialog = builder.create();
		return this.mAlertDialog;
	}
	
	public JtrDialogBuilder setPositiveButtonListner(CharSequence text, OnClickListener onClickListener)
	{
		this.positiveClickListener = onClickListener;
		this.positiveText = text;
		super.setPositiveButton(text, positiveClickListener);
		return this;
	}
	
	public JtrDialogBuilder setNegativeButtonListner(CharSequence text, OnClickListener onClickListener)
	{
		this.negativeClickListener = onClickListener;
		this.negativeText = text;
		super.setNegativeButton(text, negativeClickListener);
		return this;
	}
	
	public JtrDialogBuilder setNeutralButtonListner(CharSequence text, OnClickListener onClickListener)
	{
		this.neuralClickListener = onClickListener;
		this.neutralText = text;
		super.setNeutralButton(text, positiveClickListener);
		return this;
	}
	
	public List<NumberPicker> getAllPickers()
	{
		refreshPickers();
		return mNumberPickers;
	}
	
	public void refreshPickers()
	{
		List<NumberPicker> pickers = new ArrayList<NumberPicker>();
		for(int i = 0; i < mLinearLayout.getChildCount(); i++)
		{
			View childAt = mLinearLayout.getChildAt(i);
			if(childAt instanceof NumberPicker)
			{
				pickers.add((NumberPicker) childAt);
			}
		}
		this.mNumberPickers = pickers;
	}
	
	public NumberPicker getPickerAt(int index)
	{
		refreshPickers();
		return (mNumberPickers != null && mNumberPickers.size() > 0) ? mNumberPickers.get(index) : null;
	}
	
	public static NumberPicker getNumberPickerFrom(Context context, Theme theme)
	{
		if(theme.equals(Theme.DARK))
			return (NumberPicker) LayoutInflater.from(context).inflate(R.layout.number_holo_dark, null);
		else
			return (NumberPicker) LayoutInflater.from(context).inflate(R.layout.number_holo_light, null);
	}
	
	public static NumberPicker getNumberPickerFrom(Context context)
	{
		return (NumberPicker) LayoutInflater.from(context).inflate(R.layout.number_holo_light, null);
	}
	
	public enum Theme
	{
		DARK, LIGHT;
	}
}
