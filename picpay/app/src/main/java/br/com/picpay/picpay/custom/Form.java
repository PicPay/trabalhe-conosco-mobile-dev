package br.com.picpay.picpay.custom;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import br.com.picpay.picpay.validate.IValidate;

public class Form extends LinearLayout {

    private boolean correctFocus;

    public Form(Context context) {
        super(context);
        init();
    }

    public Form(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Form(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Form(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOrientation(LinearLayout.VERTICAL);
    }

    public boolean validate() {
        return !verifyItem(this, true);
    }

    private boolean verifyItem(ViewGroup view, boolean isPrimary) {
        boolean isError = false;
        for (int x = 0; x < view.getChildCount(); x++) {
            if (view.getChildAt(x) instanceof IValidate) {
                IValidate iValidate = (IValidate) view.getChildAt(x);
                if (!iValidate.validate()) {
                    if (isPrimary) {
                        isPrimary = false;
                        if (getParent() != null) {
                            if (getParent() instanceof NestedScrollView) {
                                NestedScrollView nestedScrollView = (NestedScrollView) getParent();
                                int scrollTo = ((View) view.getChildAt(x).getParent().getParent()).getTop() + view.getChildAt(x).getTop();
                                nestedScrollView.smoothScrollTo(0, scrollTo);
                            }
                            view.getChildAt(x).requestFocus();
                        }
                    }
                    if (!isError) {
                        isError = true;
                    }
                }
            } else if (view.getChildAt(x) instanceof ViewGroup && ((ViewGroup) view.getChildAt(x)).getChildCount() > 0) {
                boolean error = verifyItem((ViewGroup) view.getChildAt(x), isPrimary);
                if (!isError && error) {
                    isError = true;
                }
            }
        }
        return isError;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!correctFocus) {
            correctFocus = true;
            verifyFocus(this, null);
        }
    }

    private TitleEditText verifyFocus(ViewGroup view, TitleEditText vP) {
        TitleEditText viewPrevius = vP;
        for (int x = 0; x < view.getChildCount(); x++) {
            if (view.getChildAt(x) instanceof TitleEditText && ((TitleEditText) view.getChildAt(x)).getCustomEditText().getId() != 0) {
                TitleEditText viewNext = (TitleEditText) view.getChildAt(x);
                if (viewPrevius != null && viewNext.getCustomEditText().getId() != 0) {
                    viewPrevius.getCustomEditText().setNextFocusDownId(viewNext.getCustomEditText().getId());
                    viewPrevius.getCustomEditText().setNextFocusRightId(viewNext.getCustomEditText().getId());
                } else if (viewPrevius == null && viewNext.getCustomEditText().isEnabled()) {
                    viewNext.getCustomEditText().requestFocus();
                }
                viewPrevius = viewNext;
            } else if (view.getChildAt(x) instanceof ViewGroup && ((ViewGroup) view.getChildAt(x)).getChildCount() > 0) {
                viewPrevius = verifyFocus((ViewGroup) view.getChildAt(x), viewPrevius);
            }
        }
        return viewPrevius;
    }
}
