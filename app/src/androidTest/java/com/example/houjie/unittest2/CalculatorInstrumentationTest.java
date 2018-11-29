package com.example.houjie.unittest2;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CalculatorInstrumentationTest {
    ActivityScenario mActivityScenario;

    @Before
    public void launchActivity() throws Exception {
        mActivityScenario = ActivityScenario.launch(CalculatorActivity.class);
    }

    @Test
    public void noOperandShowsComputationError() {
        final String expectedResult = "Error";
        onView(withId(R.id.operation_add_btn)).perform(click());
        onView(withId(R.id.operation_result_text_view)).check(matches(withText(expectedResult)));
    }

    @Test
    public void typeOperandsAndPerformAddOperation() {
        performOperation(R.id.operation_add_btn, "16.0", "16.0", "32.0");
    }

    @Test
    public void typeOperandsAndPerformSubOperation() {
        performOperation(R.id.operation_sub_btn, "32.0", "16.0", "16.0");
    }

    @Test
    public void typeOperandsAndPerformDivOperation() {
        performOperation(R.id.operation_div_btn, "128.0", "16.0", "8.0");
    }

    @Test
    public void divZeroForOperandTwoShowsError() {
        final String expectedResult = getApplicationContext().getString(R.string.computationError);
        performOperation(R.id.operation_div_btn, "128.0", "0.0", expectedResult);
    }

    @Test
    public void typeOperandsAndPerformMulOperation() {
        performOperation(R.id.operation_mul_btn, "16.0", "16.0", "256.0");
    }

    @Test
    public void testLifecycle() {
        Log.d("CalculatorActivity", "testLifecycle begin");
        mActivityScenario.onActivity(new ActivityScenario.ActivityAction<CalculatorActivity>(){
            public void perform(CalculatorActivity activity){

            }
        });
        Log.d("CalculatorActivity", "testLifecycle STARTED");
        mActivityScenario.moveToState(Lifecycle.State.STARTED);
        mActivityScenario.onActivity(new ActivityScenario.ActivityAction<CalculatorActivity>(){
            public void perform(CalculatorActivity activity){

            }
        });
        Log.d("CalculatorActivity", "testLifecycle RESUMED");
        mActivityScenario.moveToState(Lifecycle.State.RESUMED);
        mActivityScenario.onActivity(new ActivityScenario.ActivityAction<CalculatorActivity>(){
            public void perform(CalculatorActivity activity){

            }
        });
        Log.d("CalculatorActivity", "testLifecycle DESTROYED");
        mActivityScenario.moveToState(Lifecycle.State.DESTROYED);
    }

    private void performOperation(int btnOperationResId, String operandOne,
            String operandTwo, String expectedResult) {
        onView(withId(R.id.operand_one_edit_text)).perform(typeText(operandOne),
                closeSoftKeyboard());
        onView(withId(R.id.operand_two_edit_text)).perform(typeText(operandTwo),
                closeSoftKeyboard());
        onView(withId(btnOperationResId)).perform(click());
        onView(withId(R.id.operation_result_text_view)).check(matches(withText(expectedResult)));
    }
}