package br.com.picpay.picpay.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import java.util.ArrayList;

import br.com.picpay.picpay.R;
import br.com.picpay.picpay.enums.InputTypeEnum;
import br.com.picpay.picpay.util.CardUtil;
import br.com.picpay.picpay.util.Util;
import br.com.picpay.picpay.validate.CardDateValidator;
import br.com.picpay.picpay.validate.CardValidator;
import br.com.picpay.picpay.validate.IValidate;
import br.com.picpay.picpay.validate.NameValidator;
import br.com.picpay.picpay.validate.ValueValidator;
import io.card.payment.CardType;

public class TitleEditText extends LinearLayout implements IValidate, CreditCardEditText.CreditCardTextListerner {

    private Context context;
    private AppCompatTextView title;
    private AppCompatTextView tvError;
    private CustomEditText customEditText;

    private IValidate validate;

    private Drawable iconRight;
    private Drawable iconCard;

    private boolean required = false;
    private boolean editable = true;
    private boolean isCardValid = false;

    private String errorMessage;

    private InputTypeEnum inputTypeEnum;
    private int inputTypeDefault;
    private Util util = new Util();

    public TitleEditText(Context context) {
        super(context);
        init(context, null);
    }

    public TitleEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public TitleEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TitleEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(final Context context, @Nullable AttributeSet attrs) {
        this.context = context;

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleEditText);
            int inputType = a.getInteger(R.styleable.TitleEditText_customInputType, 100);
            inputTypeDefault = a.getInt(R.styleable.TitleEditText_android_inputType, EditorInfo.TYPE_NULL);
            inputTypeEnum = InputTypeEnum.getOptionEnum(inputType);
            a.recycle();
        }

        setIconRight(ContextCompat.getDrawable(context, R.drawable.icon_error));

        setOrientation(LinearLayout.VERTICAL);

        removeAllViews();

        title = new AppCompatTextView(context);
        title.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);

        if (inputTypeEnum == InputTypeEnum.CARD) {
            customEditText = new CreditCardEditText(context);
        } else if (inputTypeEnum == InputTypeEnum.DATE_CARD) {
            customEditText = new CardDateEditText(context);
        } else if (inputTypeEnum == InputTypeEnum.VALUE) {
            customEditText = new ValueTextEditText(context);
        } else {
            customEditText = new CustomEditText(context);
        }
        customEditText.setSingleLine(true);
        customEditText.setTextColor(ContextCompat.getColor(context, R.color.colorSecondText));
        customEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvError.setText("");
                customEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, iconCard, null);
                customEditText.setBackground(ContextCompat.getDrawable(context, R.drawable.line_background));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (customEditText instanceof CreditCardEditText) {
            ((CreditCardEditText) customEditText).setListerner(this);
        }

        tvError = new AppCompatTextView(context);
        tvError.setTextColor(ContextCompat.getColor(context, R.color.lineError));
        tvError.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        params.topMargin = util.convertDpToPixel(7).intValue();
        tvError.setLayoutParams(params);
        tvError.setMinHeight(util.convertDpToPixel(20).intValue());
        setValuesXML(context, attrs);

        addView(title);
        addView(customEditText);
        addView(tvError);

    }

    private void setValuesXML(@NonNull Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TitleEditText);
            String title = a.getString(R.styleable.TitleEditText_title);
            String hint = a.getString(R.styleable.TitleEditText_hint);
            errorMessage = a.getString(R.styleable.TitleEditText_errorMessage);
            required = a.getBoolean(R.styleable.TitleEditText_required, false);
            editable = a.getBoolean(R.styleable.TitleEditText_editable, true);
            boolean textAllCaps = a.getBoolean(R.styleable.TitleEditText_allCaps, false);

            int titleTextSize = a.getInteger(R.styleable.TitleEditText_titleTextSize, 17);
            int maxLength = a.getInteger(R.styleable.TitleEditText_maxLength, -1);

            setInputType(inputTypeEnum);
            setTitle(title == null ? "" : title);
            setHint(hint == null ? "" : hint);
            setEditable(editable);
            setSizeTitleText(titleTextSize);

            if (maxLength >= 0) {
                addFilter(new InputFilter.LengthFilter(maxLength));
            }

            setAllCaps(textAllCaps);

            a.recycle();
        }

        customEditText.setBackground(ContextCompat.getDrawable(context, R.drawable.line_background));

        if (customEditText instanceof CreditCardEditText) {
            customEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

    public void setAllCaps(boolean allCaps) {
        if (allCaps) {
            addFilter(new InputFilter.AllCaps());
        } else {
            ArrayList<InputFilter> inputFilters = new ArrayList<>();
            InputFilter[] editFilters = customEditText.getFilters();
            for (InputFilter editFilter : editFilters) {
                if (!(editFilter instanceof InputFilter.AllCaps)) {
                    inputFilters.add(editFilter);
                }
            }
            customEditText.setFilters(inputFilters.toArray(new InputFilter[inputFilters.size()]));
        }
    }

    public void addFilter(InputFilter filter) {
        InputFilter[] editFilters = customEditText.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = filter;
        customEditText.setFilters(newFilters);
    }

    public void setEditable(boolean enabled) {
        this.editable = enabled;
        setFocusableInTouchMode(enabled);
        setFocusable(enabled);
        customEditText.setFocusable(enabled);
        customEditText.setFocusableInTouchMode(enabled);
        customEditText.editable(enabled);
    }

    public void setValidate(IValidate validate) {
        this.validate = validate;
    }

    public IValidate getValidate() {
        return validate;
    }

    @NonNull
    public String getText() {
        return customEditText.getText().toString().trim();
    }

    public void setTitle(@NonNull String title) {
        this.title.setVisibility(title.isEmpty() ? View.GONE : VISIBLE);
        this.title.setText(title.toUpperCase());
    }

    public void setHint(@NonNull String hint) {
        customEditText.setHint(hint);
    }

    public void setTextEditText(String text) {
        customEditText.setText(text != null ? text.trim() : "");
        util.setCursorEndEdittext(customEditText);
    }

    public void setSizeTitleText(int titleTextSize) {
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleTextSize);
    }

    @Override
    public boolean validate() {
        if (isValid()) {
            customEditText.setBackground(ContextCompat.getDrawable(context, R.drawable.line_background));
        } else {
            showError();
        }
        return isValid();
    }

    public void showError() {
        customEditText.setBackground(ContextCompat.getDrawable(context, R.drawable.line_error_background));
        customEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, iconRight, null);
        tvError.setText(getErrorMessage());
    }

    public boolean isValid() {
        return !required || validate == null && !TextUtils.isEmpty(getText()) || validate != null && validate.validate();
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        return customEditText.requestFocus();
    }

    public void setIconRight(Drawable iconRight) {
        this.iconRight = iconRight;
    }

    private void setInputType(InputTypeEnum inputType) {
        if (inputType != null) {
            switch (inputType) {
                case CARD:
                    setValidate(new CardValidator(this));
                    break;
                case DATE_CARD:
                    setValidate(new CardDateValidator(this));
                    break;
                case NAME:
                    customEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    setValidate(new NameValidator(this));
                    break;
                case VALUE:
                    setValidate(new ValueValidator(this));
                    break;
            }
        } else if (inputTypeDefault != 0) {
            customEditText.setInputType(inputTypeDefault);
        } else {
            customEditText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
    }

    public CustomEditText getCustomEditText() {
        return customEditText;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @NonNull
    private String getErrorMessage() {
        return TextUtils.isEmpty(errorMessage) ? getContext().getResources().getString(R.string.campo_obrigatorio) : errorMessage.trim();
    }

    @Override
    public void onCardType(CardType cardType) {
        iconCard = CardUtil.getIconCard(cardType, context);
        isCardValid = iconCard != null;
        if (iconCard == null) {
            iconCard = ContextCompat.getDrawable(context, R.drawable.icon_undefined);
        }
        if (getText().replace(" ", "").length() < 16) {
            isCardValid = false;
        }
        customEditText.setCompoundDrawablesWithIntrinsicBounds(null, null, iconCard, null);
    }

    public boolean isCardValid() {
        return isCardValid;
    }
}
