package project.picpay.test.creditcard.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import project.picpay.test.R;
import project.picpay.test.creditcard.model.CreditCardModel;
import project.picpay.test.creditcard.util.Utils;
import project.picpay.test.creditcard.viewmodel.CreditCardViewModel;
import project.picpay.test.databinding.ActivityNewCardBinding;

public class NewCreditCardActivity extends AppCompatActivity {

    private boolean showingGray = true;
    private AnimatorSet inSet;
    private AnimatorSet outSet;
    private ActivityNewCardBinding binding;
    private CreditCardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_card);

        configToolbar();
        configTextChangeListerner();
        configEditorActionListerner();
        configCardAdapter();
        configListerners();
        configAnimators();
        configViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_reset, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:
                clearFormCard();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void configToolbar() {
        setSupportActionBar(binding.toolbarMain.toolbar);
        setTitle(R.string.s_title_new_card);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    private void configTextChangeListerner() {
        binding.inputEditCardNumber.addTextChangedListener(new TextWatcher() {
            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    flipToBlue();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lock || s.length() > 16) {
                    return;
                }
                lock = true;
                for (int i = 4; i < s.length(); i += 5) {
                    if (s.toString().charAt(i) != ' ') {
                        s.insert(i, " ");
                    }
                }
                lock = false;
            }
        });

        binding.inputEditExpiredDate.addTextChangedListener(new TextWatcher() {

            private boolean lock;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (lock || s.length() > 4) {
                    return;
                }
                lock = true;
                if (s.length() > 2 && s.toString().charAt(2) != '/') {
                    s.insert(2, "/");
                }
                lock = false;
            }
        });
    }

    private void configEditorActionListerner() {
        TextView.OnEditorActionListener onEditorActionListener = (v, actionId, event) -> {
            boolean handled = false;
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                binding.viewPager.setCurrentItem(binding.viewPager.getCurrentItem() + 1);
                handled = true;
            }
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                saveNewCard();
                handled = true;
            }
            return handled;
        };

        binding.inputEditCardNumber.setOnEditorActionListener(onEditorActionListener);
        binding.inputEditExpiredDate.setOnEditorActionListener(onEditorActionListener);
        binding.inputEditCardHolder.setOnEditorActionListener(onEditorActionListener);
        binding.inputEditCvvCode.setOnEditorActionListener(onEditorActionListener);

        binding.inputEditCardNumber.requestFocus();
        Utils.showKeyboard(this, binding.inputEditCardNumber);
    }

    private void configCardAdapter() {
        int width = Utils.returnWidthSize(this);

        PagerAdapter adapter = new PageCardAdapter();
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setPadding(width / 4, 0, width / 4, 0);
        binding.viewPager.setPageMargin(width / 14);
        binding.viewPager.setPagingEnabled(false);
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        binding.inputEditCardNumber.setFocusableInTouchMode(true);
                        binding.inputEditExpiredDate.setFocusable(false);
                        binding.inputEditCardHolder.setFocusable(false);
                        binding.inputEditCvvCode.setFocusable(false);
                        binding.inputEditCardNumber.requestFocus();
                        return;
                    case 1:
                        binding.inputEditCardNumber.setFocusable(false);
                        binding.inputEditExpiredDate.setFocusableInTouchMode(true);
                        binding.inputEditCardHolder.setFocusable(false);
                        binding.inputEditCvvCode.setFocusable(false);
                        binding.inputEditExpiredDate.requestFocus();
                        return;
                    case 2:
                        binding.inputEditCardNumber.setFocusable(false);
                        binding.inputEditExpiredDate.setFocusable(false);
                        binding.inputEditCardHolder.setFocusableInTouchMode(true);
                        binding.inputEditCvvCode.setFocusable(false);
                        binding.inputEditCardHolder.requestFocus();
                        return;
                    case 3:
                        binding.inputEditCardNumber.setFocusable(false);
                        binding.inputEditExpiredDate.setFocusable(false);
                        binding.inputEditCardHolder.setFocusable(false);
                        binding.inputEditCvvCode.setFocusableInTouchMode(true);
                        binding.inputEditCvvCode.requestFocus();
                        return;
                    case 4:
                        binding.inputEditCardNumber.setFocusable(false);
                        binding.inputEditExpiredDate.setFocusable(false);
                        binding.inputEditCardHolder.setFocusable(false);
                        binding.inputEditCvvCode.setFocusable(false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void configListerners() {
        OnClickListerners listerners = new OnClickListerners(this);
        binding.setListerners(listerners);
    }

    private void configAnimators() {
        inSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_in);
        outSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.card_flip_out);
    }

    private void configViewModel() {
        viewModel = ViewModelProviders.of(this).get(CreditCardViewModel.class);
    }

    private void saveNewCard() {
        binding.viewPager.setCurrentItem(4);
        CreditCardModel card = new CreditCardModel();
        card.setCardNumber(binding.inputEditCardNumber.getText().toString());
        card.setExpiredDate(binding.inputEditExpiredDate.getText().toString());
        card.setCardHolder(binding.inputEditCardHolder.getText().toString());
        card.setCvvCode(binding.inputEditCvvCode.getText().toString());

        new Handler().postDelayed(() -> {
            binding.inputLayoutCvvCode.setVisibility(View.INVISIBLE);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            Utils.hideKeyboard(NewCreditCardActivity.this, binding.inputEditCvvCode);
            binding.progressCircle.setVisibility(View.VISIBLE);
            viewModel.inserNewCard(card);
            finish();
        }, 300);
    }

    private void clearFormCard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        binding.inputLayoutCvvCode.setVisibility(View.VISIBLE);
        binding.progressCircle.setVisibility(View.GONE);

        flipToGray();
        binding.viewPager.setCurrentItem(0);
        binding.inputEditCardNumber.setText("");
        binding.inputEditExpiredDate.setText("");
        binding.inputEditCardHolder.setText("");
        binding.inputEditCvvCode.setText("");
        binding.inputEditCardNumber.requestFocus();
        Utils.showKeyboard(NewCreditCardActivity.this, binding.inputEditCardNumber);
    }

    private void flipToGray() {
        if (!showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = true;

            binding.cardBlue.setCardElevation(0);
            binding.cardGray.setCardElevation(0);

            outSet.setTarget(binding.cardBlue);
            outSet.start();

            inSet.setTarget(binding.cardGray);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    binding.cardGray.setCardElevation(convertDpToPixel(12, NewCreditCardActivity.this));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
    }

    private void flipToBlue() {
        if (showingGray && !outSet.isRunning() && !inSet.isRunning()) {
            showingGray = false;

            binding.cardGray.setCardElevation(0);
            binding.cardBlue.setCardElevation(0);

            outSet.setTarget(binding.cardGray);
            outSet.start();

            inSet.setTarget(binding.cardBlue);
            inSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    binding.cardBlue.setCardElevation(convertDpToPixel(12, NewCreditCardActivity.this));
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            inSet.start();
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return dp * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public class OnClickListerners {

        Context context;

        OnClickListerners(Context context) {
            this.context = context;
        }

        public void cvvNumberInfo(View view) {
            Toast.makeText(NewCreditCardActivity.this, "The CVV Number (\"Card Verification Value\") is a 3 or 4 digit number on your credit and debit cards", Toast.LENGTH_LONG).show();
        }
    }

    private class PageCardAdapter extends PagerAdapter {

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            int resId = 0;
            switch (position) {
                case 0:
                    resId = R.id.input_layout_card_number;
                    break;
                case 1:
                    resId = R.id.input_layout_expired_date;
                    break;
                case 2:
                    resId = R.id.input_layout_card_holder;
                    break;
                case 3:
                    resId = R.id.input_layout_cvv_code;
                    break;
                case 4:
                    resId = R.id.space;
                    break;

            }
            return findViewById(resId);
        }


        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

    }

}